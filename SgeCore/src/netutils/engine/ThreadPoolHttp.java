package netutils.engine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;

import netutils.http.HttpHandler;
import netutils.http.RequestCallBack;
import netutils.http.RequestParams;
import netutils.http.RetryHandler;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

import sgecore.utils.SystemUtils;

public class ThreadPoolHttp {

    private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8 * 1024; //8KB
    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final String ENCODING_GZIP = "gzip";

    private static int maxConnections = 10; //http请求最大并发连接数
    private static int socketTimeout = 10 * 1000; //超时时间，默认10秒
    private static int maxRetries = 5;//错误尝试次数，错误异常表请在RetryHandler添加
    protected static int httpThreadCount = 5;//http线程池数量
    protected final DefaultHttpClient httpClient;
    protected final HttpContext httpContext;
    protected String charset = "utf-8";
    
    private final Map<String, String> clientHeaderMap;
    
    private static final ThreadFactory  sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);
        public Thread newThread(Runnable r) {
        	Thread tread = new Thread(r, "FinalHttp #" + mCount.getAndIncrement());
        	tread.setPriority(Thread.NORM_PRIORITY - 1);
            return tread;
        }
    };
    
    protected static final Executor executor =Executors.newFixedThreadPool(httpThreadCount, sThreadFactory);
    
    public ThreadPoolHttp() {
        BasicHttpParams httpParams = new BasicHttpParams();
        ConnManagerParams.setTimeout(httpParams, socketTimeout);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(maxConnections));
        ConnManagerParams.setMaxTotalConnections(httpParams, 10);
        HttpConnectionParams.setSoTimeout(httpParams, socketTimeout);
        HttpConnectionParams.setConnectionTimeout(httpParams, socketTimeout);
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
        HttpConnectionParams.setSocketBufferSize(httpParams, DEFAULT_SOCKET_BUFFER_SIZE);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(httpParams, SystemUtils.getUserAgent());
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(httpParams, schemeRegistry);

        httpContext = new SyncBasicHttpContext(new BasicHttpContext());
        httpClient = new DefaultHttpClient(cm, httpParams);
        httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest request, HttpContext context) {
                if (!request.containsHeader(HEADER_ACCEPT_ENCODING)) {
                    request.addHeader(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
                }
                for (String header : clientHeaderMap.keySet()) {
                    request.addHeader(header, clientHeaderMap.get(header));
                }
            }
        });

        httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(HttpResponse response, HttpContext context) {
                final HttpEntity entity = response.getEntity();
                if (entity == null) {
                    return;
                }
                final Header encoding = entity.getContentEncoding();
                if (encoding != null) {
                    for (HeaderElement element : encoding.getElements()) {
                        if (element.getName().equalsIgnoreCase(ENCODING_GZIP)) {
                            response.setEntity(new InflatingEntity(response.getEntity()));
                            break;
                        }
                    }
                }
            }
        });

        httpClient.setHttpRequestRetryHandler(new RetryHandler(maxRetries));

        clientHeaderMap = new HashMap<String, String>();
        
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public HttpContext getHttpContext() {
        return this.httpContext;
    }
    
    public void configCharset(String charSet){
    	if(charSet!=null && charSet.trim().length()!=0)
    		this.charset = charSet;
    }

    public void configCookieStore(CookieStore cookieStore) {
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    }


    public void configUserAgent(String userAgent) {
        HttpProtocolParams.setUserAgent(this.httpClient.getParams(), userAgent);
    }
    

    /**
     * 设置网络连接超时时间，默认为10秒钟
     * @param timeout
     */
    public void configTimeout(int timeout){
        final HttpParams httpParams = this.httpClient.getParams();
        ConnManagerParams.setTimeout(httpParams, timeout);
        HttpConnectionParams.setSoTimeout(httpParams, timeout);
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
    }

    /**
     * 设置https请求时  的 SSLSocketFactory
     * @param sslSocketFactory
     */
    public void configSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
    	Scheme scheme = new Scheme("https", sslSocketFactory, 443);
        this.httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
    }
    
    /**
     * 配置错误重试次数
     * @param retry
     */
    public void configRequestExecutionRetryCount(int count){
    	this.httpClient.setHttpRequestRetryHandler(new RetryHandler(count));
    }
    
   /**
    * 添加http请求头
    * @param header
    * @param value
    */
    public void addHeader(String header, String value) {
        clientHeaderMap.put(header, value);
    }

    
    //---------------------下载---------------------------------------
    public HttpHandler<File> download(String url,String target,RequestCallBack<File> callback){
    	return download(url, null, target, false, callback);
    }
    

    public HttpHandler<File> download(String url,String target,boolean isResume,RequestCallBack<File> callback){
    	 return download(url, null, target, isResume, callback);
    }
    
    public HttpHandler<File> download( String url,RequestParams params, String target, RequestCallBack<File> callback) {
   	 	return download(url, params, target, false, callback);
    }
    
    public HttpHandler<File> download( String url,RequestParams params, String target,boolean isResume, RequestCallBack<File> callback) {
    	final HttpGet get =  new HttpGet(getUrlWithQueryString(url, params));
    	HttpHandler<File> handler = new HttpHandler<File>(httpClient, httpContext, callback,charset);
    	handler.executeOnExecutor(executor,get,target,isResume);
    	return handler;
    }
    
    public static String getUrlWithQueryString(String url, RequestParams params) {
        if(params != null) {
            String paramString = params.getParamString();
            url += "?" + paramString;
        }
        return url;
    }

    private static class InflatingEntity extends HttpEntityWrapper {
        public InflatingEntity(HttpEntity wrapped) {
            super(wrapped);
        }

        @Override
        public InputStream getContent() throws IOException {
            return new GZIPInputStream(wrappedEntity.getContent());
        }

        @Override
        public long getContentLength() {
            return -1;
        }
    }
}
