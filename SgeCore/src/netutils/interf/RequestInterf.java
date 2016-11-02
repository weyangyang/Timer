package netutils.interf;

import java.io.File;
import java.util.List;

import netutils.engine.NetReqCallBack;
import netutils.engine.PostFileCallBack;
import netutils.http.HttpHandler;
import netutils.http.HttpHeader;
import netutils.http.RequestCallBack;
import netutils.http.RequestParams;

import org.apache.http.NameValuePair;

public interface RequestInterf {
	//--------------------post请求-------------------------
    public void post(String strUrl,List<NameValuePair> nameValuePairs,
            NetReqCallBack mNetReqCallBack);
    public void post(String strUrl,HttpHeader header,List<NameValuePair> nameValuePairs,
    		NetReqCallBack mNetReqCallBack);
    
    public void post(String strUrl,NetReqCallBack mNetReqCallBack, NameValuePair... params);
    public void post(String strUrl,HttpHeader header,NetReqCallBack mNetReqCallBack, NameValuePair... params);
    
    //--------------------get请求-------------------------
    public void get(String strUrl,NetReqCallBack mNetReqCallBack);
    public void get(String strUrl,HttpHeader header,NetReqCallBack mNetReqCallBack);
    
    public void get(String strUrl,HttpHeader header,RequestParams params,NetReqCallBack mNetReqCallBack);
    public void get(String strUrl,RequestParams params,NetReqCallBack mNetReqCallBack);
    
    //--------------------下载-------------------------
    public HttpHandler<File> download(String strUrl,String target,RequestCallBack<File> callBack);
    public HttpHandler<File> download(String strUrl,String target,RequestParams params, RequestCallBack<File> callBack);
    public HttpHandler<File> download( String url,RequestParams params, String target,boolean isResume, RequestCallBack<File> callback);
    public HttpHandler<File> download( String url,String target,boolean isResume, RequestCallBack<File> callback);
    
    //--------------------文件上传-------------------------
    public String post(String url, HttpHeader header, File[] mFiles, PostFileCallBack dataCallBack);
    public String post(String url, File[] mFiles, PostFileCallBack dataCallBack);
     public String post(String url, HttpHeader header, File mFile, PostFileCallBack mPostFileCallBack) ;
     public String post(String url, File mFile, PostFileCallBack mPostFileCallBack);
    
    
    
    //----------------------put请求------------------------
//   public void put(String url, RequestCallBack<? extends Object> callBack);
     public void put(String strUrl,HttpHeader header,List<NameValuePair>nameValuePairs, NetReqCallBack mNetReqCallBack);
//    public void put( String url, RequestParams params, RequestCallBack<? extends Object> callBack) ;
    
    //----------------------delete请求---------------------
    public void delete( String url,NetReqCallBack mNetReqCallBack);
    
    public void delete( String url, HttpHeader header, NetReqCallBack mNetReqCallBack);
    
    public void delete( String url, HttpHeader header, List<NameValuePair> nameValuePairs,NetReqCallBack mNetReqCallBack);
    
    
}
