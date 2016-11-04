﻿package netutils.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.zip.GZIPInputStream;

import netutils.engine.NetConstants;
import netutils.engine.NetReqCallBack;
import netutils.engine.PostFileCallBack;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import sgecore.utils.LogUtils;
import sgecore.utils.SystemUtils;
import config.CoreConfig;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

/**
 * 
 * @author liyusheng
 * 
 */
public class HttpNetUtils {

	private static final String CHAR_SET = HTTP.UTF_8;
	private static HttpClient mHttpClient;

	private HttpNetUtils() {

	}

	public synchronized static HttpClient getHttpClient() {
		if (null == mHttpClient) {
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setContentCharset(params, CHAR_SET);
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams.setUserAgent(params,SystemUtils.getUserAgent());
			// timeout setting
			/*
			 * This defines the connection pool management from
			 * ConnectionManager remove the connection timeout.
			 */
			ConnManagerParams.setTimeout(params, 30000);
			/*
			 * This defines the connection with the server via a network
			 * timeout. Httpclient package via an asynchronous thread to create
			 * a socket connection with the server, which is connected to the
			 * socket timeout.
			 */
			HttpConnectionParams.setConnectionTimeout(params, 30000);
			/*
			 * This defines the Socket read data timeout, which fetch response
			 * data from the server time to wait.
			 */
			HttpConnectionParams.setSoTimeout(params, 30000);
			// Set our HttpClient supports HTTP and HTTPS mode.
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
			// Use thread-safe connection manager to create HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
			mHttpClient = new DefaultHttpClient(conMgr, params);
		}

		return mHttpClient;

	}

	public static String get(String strUrl, RequestParams params, NetReqCallBack mNetReqCallBack) {
		return get(strUrl, null, params, mNetReqCallBack);
	}

	public static String get(String strUrl, NetReqCallBack mNetReqCallBack) {
		return get(strUrl, null, null, mNetReqCallBack);
	}

	public static String get(String strUrl, HttpHeader header, RequestParams params, NetReqCallBack mNetReqCallBack) {
		String resultString = "";
		resultString = getRealGet(getUrlWithQueryString(strUrl, params), header, mNetReqCallBack, resultString);
		return resultString;
	}

	private static String getUrlWithQueryString(String url, RequestParams params) {
		if (params != null) {
			String paramString = params.getParamString();
			url += "?" + paramString;
		}
		return url;
	}

	public static String get(String url, HttpHeader header, NetReqCallBack mNetReqCallBack) {
		String resultString = "";
		resultString = getRealGet(url, header, mNetReqCallBack, resultString);
		return resultString;

	}

	private static String getRealGet(String url, HttpHeader header, NetReqCallBack mNetReqCallBack, String resultString) {
		int statusCode = 0;
		HttpResponse response = null;
		try {
			url = url.replaceAll(" ", "%20");
		HttpGet mHttpGet = new HttpGet(url);
		if(header!=null){
			header.setHeader(mHttpGet);
		}
		HttpClient client = getHttpClient();
			response = client.execute(mHttpGet);
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= 200 && statusCode < 300) {
				resultString = getNetSuccData(mNetReqCallBack, resultString, response,url);
				System.out.println(resultString);
			}
		} catch (ClientProtocolException e1) {
			mNetReqCallBack.getExceptionMsg(NetConstants.CLIENT_PROTOCOL_EXCEPTION, e1.getMessage(),url);
		} catch (IOException e1) {
			mNetReqCallBack.getExceptionMsg(NetConstants.IO_EXCEPTION, e1.getMessage(),url);
		}catch (Exception e1) {
			mNetReqCallBack.getExceptionMsg(NetConstants.EXEPTION, e1.getMessage(),url);
			//ConnectionPoolTimeoutException,ConnectionTimeoutException与SocketTimeoutException。
		}
		return resultString;
	}
	public static String getWeather(String url, NetReqCallBack mNetReqCallBack) {
		int statusCode = 0;
		String resultString = "";
		HttpResponse response = null;
		try {
			url = url.replaceAll(" ", "%20");
			HttpGet mHttpGet = new HttpGet(url);
			HttpHeader header = new HttpHeader();
			header.addHeader("Host", "wthrcdn.etouch.cn");
			header.addHeader("uid", "!07dad0ffd473b043fd92d314f9110552");
			header.addHeader("width", "1080");
			header.addHeader("spam", "Channel ID");
			header.addHeader("uuid", "07dad0ffd473b043fd92d314f9110552");
			header.addHeader("height", "1776");
			header.addHeader("sid", "07dad0ffd473b043fd92d314f91105521145887");
			header.addHeader("Cache-Control", "no-cache");
			header.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0");
			header.setHeader(mHttpGet);
			HttpClient client = getHttpClient();
			response = client.execute(mHttpGet);
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= 200 && statusCode < 300) {
				HttpEntity entity = response.getEntity();
				resultString = getData(entity, statusCode, url);
				
				mNetReqCallBack.getSuccData(statusCode, resultString, url);
			}
		} catch (ClientProtocolException e1) {
			mNetReqCallBack.getExceptionMsg(NetConstants.CLIENT_PROTOCOL_EXCEPTION, e1.getMessage(),url);
		} catch (IOException e1) {
			mNetReqCallBack.getExceptionMsg(NetConstants.IO_EXCEPTION, e1.getMessage(),url);
		}catch (Exception e1) {
			mNetReqCallBack.getExceptionMsg(NetConstants.EXEPTION, e1.getMessage(),url);
			//ConnectionPoolTimeoutException,ConnectionTimeoutException与SocketTimeoutException。
		}
		return resultString;
	}

	/**
	 * 解压 Gzip
	 * 
	 * @param b
	 * @return
	 * @throws Exception
	 */
	private static byte[] unzip(byte[] b) throws Exception {
		if (b == null)
			return null;
		if (b.length > 512 * 1024)
			return null;
		byte[] unzipdata = null;

		ByteArrayInputStream bais = new ByteArrayInputStream(b);

		GZIPInputStream gzin = new GZIPInputStream(bais);

		byte[] buffer = new byte[1024 * 4];

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		int ch;

		while ((ch = gzin.read(buffer)) != -1) {
			dos.write(buffer, 0, ch);
		}
		unzipdata = baos.toByteArray();

		dos.close();
		baos.close();

		buffer = null;
		gzin.close();
		bais.close();
		return unzipdata;
	}

	private static byte[] readBytes(InputStream in, final int length) throws IOException {
		OutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 20];
		int n = 0, size = 0;

		while ((n = in.read(buffer)) > 0) {
			size += n;
			os.write(buffer, 0, n);
		}
		buffer = null;

		if (-1 != length && size != length)
			return null;

		byte[] bs = ((ByteArrayOutputStream) os).toByteArray();
		os.close();
		return bs;
	}

	public static String post(String strUrl, List<NameValuePair> nameValuePairs, NetReqCallBack mNetReqCallBack) {
		return post(strUrl, null, nameValuePairs, mNetReqCallBack);
	}
	public static void delete(String strUrl,HttpHeader header,List<NameValuePair>nameValuePairs, NetReqCallBack mNetReqCallBack){
		HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(strUrl);
		String strJson = "";
		HttpClient client = getHttpClient();
		if(header!=null){
			header.setHeader(httpDelete);
		}
		HttpResponse response;
		try {
			if(nameValuePairs!=null&&nameValuePairs.size()!=0){
				httpDelete.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			}
			response = client.execute(httpDelete);
			strJson = getNetSuccData(mNetReqCallBack, strJson, response,strUrl);
		} catch (ClientProtocolException e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.CLIENT_PROTOCOL_EXCEPTION, e.getMessage(),strUrl);
		} catch (IOException e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.IO_EXCEPTION, e.getMessage(),strUrl);
		} catch (Exception e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.EXEPTION, e.getMessage(),strUrl);
		}
		
	}
	public static void put(String strUrl,HttpHeader header,List<NameValuePair>nameValuePairs, NetReqCallBack mNetReqCallBack){
		HttpPut httpPut = new HttpPut(strUrl);
		String strJson = "";
		HttpClient client = getHttpClient();
		if(header!=null){
			header.setHeader(httpPut);
		}
		HttpResponse response;
		try {
			if(nameValuePairs!=null&&nameValuePairs.size()!=0){
				httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			}
			response = client.execute(httpPut);
			strJson = getNetSuccData(mNetReqCallBack, strJson, response,strUrl);
		} catch (ClientProtocolException e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.CLIENT_PROTOCOL_EXCEPTION, e.getMessage(),strUrl);
		} catch (IOException e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.IO_EXCEPTION, e.getMessage(),strUrl);
		} catch (Exception e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.EXEPTION, e.getMessage(),strUrl);
		}
		
	}
	public static void delete(String strUrl, HttpHeader header,NetReqCallBack mNetReqCallBack){
	delete(strUrl, header, null, mNetReqCallBack);
	}
	public static String post(String strUrl, HttpHeader header, List<NameValuePair> nameValuePairs,
			NetReqCallBack mNetReqCallBack) {
		String strJson = "";
		try {
		HttpClient client = getHttpClient();
		HttpPost httpPost = new HttpPost(strUrl);
		if(header!=null){
			header.setHeader(httpPost);
		}
		HttpResponse response = null;

			if(nameValuePairs!=null&&nameValuePairs.size()!=0){
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			}
			response = client.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			strJson =getNetSuccData(mNetReqCallBack, strJson, response, strUrl);
			
		} catch (UnsupportedEncodingException e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.UNSUPPORTED_ENCODING_EXCEPTION, e.getMessage(),strUrl);
		} catch (ClientProtocolException e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.CLIENT_PROTOCOL_EXCEPTION, e.getMessage(),strUrl);
		} catch (IOException e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.IO_EXCEPTION, e.getMessage(),strUrl);
		} catch (Exception e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.EXEPTION, e.getMessage(),strUrl);
		}
		return strJson;
	}

	private static String getNetSuccData(NetReqCallBack mNetReqCallBack, String strJson, HttpResponse response,String strUrl) throws ParseException, IOException
			 {
		HttpEntity entity = response.getEntity();
		int statusCode = response.getStatusLine().getStatusCode();
		strJson =  EntityUtils.toString(entity, CHAR_SET);
		if (200 <= statusCode && statusCode < 300) {
			mNetReqCallBack.getSuccData(statusCode, strJson,strUrl);
		} else {
			mNetReqCallBack.getErrData(statusCode, strJson,strUrl);
		}
		return strJson;
	}

	private static String getData( HttpEntity entity, int statusCode,String strUrl) throws ParseException, IOException {
		String strJson = "";
		try {
			byte[] bytesResult = null;
			// 读出下行 Byte Array
			bytesResult = readBytes(entity.getContent(), (int) entity.getContentLength());
			// 尝试解压两次
			for (int i = 1; i <= 1; i++) {
				try {
					bytesResult = unzip(bytesResult);
				} catch (Exception e) {
					break;
				}
			}
			strJson = new String(bytesResult);
		} catch (Exception e) {
			// readBytes 不成功，就按照原有的方式读出
			//mNetReqCallBack.getExceptionMsg(NetConstants.EXEPTION, e.getMessage(),strUrl);
			if(entity!=null){
					strJson = EntityUtils.toString(entity, CHAR_SET);
				
				return strJson;
			}else{
				return "";
			}
		}
		return strJson;
	}

	public static String post(String strUrl, NetReqCallBack mNetReqCallBack, NameValuePair... params) {
		return post(strUrl, null, mNetReqCallBack, params);
	}

	public static String post(String strUrl, HttpHeader header, NetReqCallBack mNetReqCallBack, NameValuePair... params) {
		String strJson = "";
		try {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (params != null) {
			for (NameValuePair p : params) {
				formparams.add(p);
			}
		}
		UrlEncodedFormEntity entity = null;
		HttpPost httpPost = new HttpPost(strUrl);
		if(header!=null){
			header.setHeader(httpPost);
		}
		HttpClient client = getHttpClient();
		HttpResponse response = null;
			entity = new UrlEncodedFormEntity(formparams, CHAR_SET);
			httpPost.setEntity(entity);
			response = client.execute(httpPost);
			strJson = getNetSuccData(mNetReqCallBack, strJson, response,strUrl);
		} catch (UnsupportedEncodingException e1) {
			mNetReqCallBack.getExceptionMsg(NetConstants.UNSUPPORTED_ENCODING_EXCEPTION, e1.getMessage(),strUrl);
		} catch (ClientProtocolException e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.CLIENT_PROTOCOL_EXCEPTION, e.getMessage(),strUrl);
		} catch (IOException e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.IO_EXCEPTION, e.getMessage(),strUrl);
		} catch (Exception e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.EXEPTION, e.getMessage(),strUrl);
		}
		return strJson;
	}

	public static String post(String strUrl, RequestParams params, NetReqCallBack mNetReqCallBack) {
		return post(strUrl, null, params, mNetReqCallBack);
	}

	public static String post(String strUrl, HttpHeader header, RequestParams params, NetReqCallBack mNetReqCallBack) {
		String strJson = "";

		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(params.getParamsList(), CHAR_SET);
		} catch (UnsupportedEncodingException e2) {
			mNetReqCallBack.getExceptionMsg(NetConstants.UNSUPPORTED_ENCODING_EXCEPTION, e2.getMessage(),strUrl);
		}
		HttpPost httpPost = new HttpPost(strUrl);
		if(header!=null){
			header.setHeader(httpPost);
		}
		HttpClient client = getHttpClient();
		HttpResponse response = null;
		try {
			httpPost.setEntity(entity);
			response = client.execute(httpPost);
			strJson = getNetSuccData(mNetReqCallBack, strJson, response,strUrl);
		} catch (UnsupportedEncodingException e1) {
			mNetReqCallBack.getExceptionMsg(NetConstants.UNSUPPORTED_ENCODING_EXCEPTION, e1.getMessage(),strUrl);
		} catch (ClientProtocolException e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.CLIENT_PROTOCOL_EXCEPTION, e.getMessage(),strUrl);
		} catch (IOException e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.IO_EXCEPTION, e.getMessage(),strUrl);
		} catch (Exception e) {
			mNetReqCallBack.getExceptionMsg(NetConstants.EXEPTION, e.getMessage(),strUrl);
		}
		return strJson;
	}

	/**
	 * 使用POST方法上传多个文件(标准的Multipart方式).
	 * 
	 * @param url
	 *            URL
	 * @param mFiles
	 *            待上传的文件(可以为一个或多个)
	 * @return
	 */
	public static String post(String url, HttpHeader header, File[] mFiles, PostFileCallBack dataCallBack) {
		final HttpClient httpClient = getHttpClient();
		HttpPost httpPost = new HttpPost(url);
		if(header!=null){
			header.setHeader(httpPost);
		}
		MultipartEntity multipartEntity = new MultipartEntity();
		for (int i = 0, len = mFiles.length; i < len; i++) {
			final File file = mFiles[i];
			if (file.exists()) {
				final String key = "file" + i;
				multipartEntity.addPart(key, file);
			} else {
				// file.getAbsolutePath() does not exist
				dataCallBack.fileNotExist();
			}
		}
		return getPostFileResult(dataCallBack, httpClient, httpPost, multipartEntity,url);
	}

	public static String post(String url, File[] mFiles, PostFileCallBack dataCallBack) {
		return post(url, null, mFiles, dataCallBack);
	}

	/**
	 * 使用POST方法上传单个文件(标准的Multipart方式).
	 * @param url
	 * @param mFile
	 * @param dataCallBack
	 * @return
	 */
	public static String post(String url, HttpHeader header, File mFile, PostFileCallBack mPostFileCallBack) {
		final HttpClient httpClient = getHttpClient();
		HttpPost httpPost = new HttpPost(url);
		if(header!=null){
			header.setHeader(httpPost);
		}
		MultipartEntity multipartEntity = new MultipartEntity();
		if (mFile.exists()) {
			String key = "file";
			multipartEntity.addPart(key, mFile);
		} else {
			// file.getAbsolutePath() does not exist
			mPostFileCallBack.fileNotExist();
		}
		return getPostFileResult(mPostFileCallBack, httpClient, httpPost, multipartEntity,url);
	}

	public static String post(String url, File mFile, PostFileCallBack mPostFileCallBack) {
		return post(url, null, mFile, mPostFileCallBack);
	}

	private static String getPostFileResult(PostFileCallBack mPostFileCallBack, final HttpClient httpClient,
			HttpPost httpPost, MultipartEntity multipartEntity,String strUrl) {
		String result = "";
		httpPost.setEntity(multipartEntity);
		try {
			final HttpResponse response = httpClient.execute(httpPost);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				//result = EntityUtils.toString(response.getEntity());
				mPostFileCallBack.getSuccData(statusCode, "true",strUrl);
			} else {
				//result = EntityUtils.toString(response.getEntity());
				mPostFileCallBack.getErrData(statusCode, "false",strUrl);
			}
		} catch (ClientProtocolException e) {
			mPostFileCallBack.getExceptionMsg(NetConstants.CLIENT_PROTOCOL_EXCEPTION, e.getMessage(),strUrl);
		} catch (IOException e) {
			mPostFileCallBack.getExceptionMsg(NetConstants.IO_EXCEPTION, e.getMessage(),strUrl);
		} catch (Exception e) {
			mPostFileCallBack.getExceptionMsg(NetConstants.EXEPTION, e.getMessage(),strUrl);
		}

		return result;
	}

	public static class MultipartEntity implements HttpEntity {
		private final static char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
				.toCharArray();
		private String boundary = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		boolean isSetLast = false;
		boolean isSetFirst = false;
		public MultipartEntity() {
			final StringBuffer buf = new StringBuffer();
			final Random rand = new Random();
			for (int i = 0; i < 30; i++) {
				buf.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
			}
			this.boundary = buf.toString();
		}

		public void writeFirstBoundaryIfNeeds() {
			if (!isSetFirst) {
				try {
					out.write(("--" + boundary + "\r\n").getBytes());
				} catch (IOException e) {
					LogUtils.e(e.getMessage());
				}
			}
			isSetFirst = true;
		}

		public void writeLastBoundaryIfNeeds() {
			if (isSetLast) {
				return;
			}
			try {
				out.write(("\r\n--" + boundary + "--\r\n").getBytes());
			} catch (IOException e) {
				LogUtils.e(e.getMessage());
			}
			isSetLast = true;
		}

		public void addPart(final String key, final String value) {
			writeFirstBoundaryIfNeeds();
			try {
				out.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n").getBytes());
				out.write("Content-Type: text/plain; charset=UTF-8\r\n".getBytes());
				out.write("Content-Transfer-Encoding: 8bit\r\n\r\n".getBytes());
				out.write(value.getBytes());
				out.write(("\r\n--" + boundary + "\r\n").getBytes());
			} catch (IOException e) {
				LogUtils.e(e.getMessage());
			}
		}

		public void addPart(final String key, final String fileName, final InputStream fin) {
			addPart(key, fileName, fin, "application/octet-stream");
		}

		public void addPart(final String key, final String fileName, final InputStream fin, String type) {
			writeFirstBoundaryIfNeeds();
			try {
				type = "Content-Type: " + type + "\r\n";
				out.write(("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + fileName + "\"\r\n")
						.getBytes());
				out.write(type.getBytes());
				out.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes());

				final byte[] tmp = new byte[4096];
				int l = 0;
				while ((l = fin.read(tmp)) != -1) {
					out.write(tmp, 0, l);
				}
				out.flush();
			} catch (IOException e) {
				LogUtils.e(e.getMessage());
			} finally {
				try {
					fin.close();
				} catch (IOException e) {
					LogUtils.e(e.getMessage());
				}
			}
		}

		public void addPart(final String key, final File value) {
			try {
				addPart(key, value.getName(), new FileInputStream(value));
			} catch (FileNotFoundException e) {
				LogUtils.e(e.getMessage());
			}
		}

		@Override
		public long getContentLength() {
			writeLastBoundaryIfNeeds();
			return out.toByteArray().length;
		}

		@Override
		public Header getContentType() {
			return new BasicHeader("Content-Type", "multipart/form-data; boundary=" + boundary);
		}

		@Override
		public boolean isChunked() {
			return false;
		}

		@Override
		public boolean isRepeatable() {
			return false;
		}

		@Override
		public boolean isStreaming() {
			return false;
		}

		@Override
		public void writeTo(final OutputStream outstream) throws IOException {
			outstream.write(out.toByteArray());
		}

		@Override
		public Header getContentEncoding() {
			return null;
		}

		@Override
		public void consumeContent() throws IOException, UnsupportedOperationException {
			if (isStreaming()) {
				throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
			}
		}

		@Override
		public InputStream getContent() throws IOException, UnsupportedOperationException {
			return new ByteArrayInputStream(out.toByteArray());
		}

	}

	private static String getUserAgent() {
		Context context = CoreConfig.getContext();
		String webUserAgent = null;
		if (context != null) {
			try {
				Class sysResCls = Class.forName("com.android.internal.R$string");
				Field webUserAgentField = sysResCls.getDeclaredField("web_user_agent");
				Integer resId = (Integer) webUserAgentField.get(null);
				webUserAgent = context.getString(resId);
			} catch (Throwable ignored) {
			}
		}
		if (TextUtils.isEmpty(webUserAgent)) {
			webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 %sSafari/533.1";
		}

		Locale locale = Locale.getDefault();
		StringBuffer buffer = new StringBuffer();
		// Add version
		final String version = Build.VERSION.RELEASE;
		if (version.length() > 0) {
			buffer.append(version);
		} else {
			// default to "1.0"
			buffer.append("1.0");
		}
		buffer.append("; ");
		final String language = locale.getLanguage();
		if (language != null) {
			buffer.append(language.toLowerCase());
			final String country = locale.getCountry();
			if (country != null) {
				buffer.append("-");
				buffer.append(country.toLowerCase());
			}
		} else {
			// default to "en"
			buffer.append("en");
		}
		// add the model for the release build
		if ("REL".equals(Build.VERSION.CODENAME)) {
			final String model = Build.MODEL;
			if (model.length() > 0) {
				buffer.append("; ");
				buffer.append(model);
			}
		}
		final String id = Build.ID;
		if (id.length() > 0) {
			buffer.append(" Build/");
			buffer.append(id);
		}
		return String.format(webUserAgent, buffer, "Mobile ");
	}
}
