package netutils.impl;

import java.io.File;
import java.util.List;

import netutils.engine.NetReqCallBack;
import netutils.engine.PostFileCallBack;
import netutils.engine.ThreadPoolHttp;
import netutils.http.HttpHandler;
import netutils.http.HttpHeader;
import netutils.http.HttpNetUtils;
import netutils.http.RequestCallBack;
import netutils.http.RequestParams;
import netutils.interf.RequestInterf;

import org.apache.http.NameValuePair;

public abstract class BaseRequest implements RequestInterf {

	@Override
	public void post(String strUrl,List<NameValuePair> nameValuePairs,
			NetReqCallBack dataCallBack) {
		HttpNetUtils.post(strUrl,nameValuePairs, dataCallBack);
	}

	@Override
	public void post(String strUrl,NetReqCallBack dataCallBack, NameValuePair... params) {
		HttpNetUtils.post(strUrl,dataCallBack, params);
	}

	@Override
	public void get(String strUrl,NetReqCallBack dataCallBack) {
		HttpNetUtils.get(strUrl, dataCallBack);
	}

	@Override
	public void get(String strUrl,RequestParams params,NetReqCallBack dataCallBack) {
		HttpNetUtils.get(strUrl, params, dataCallBack);
	}

	@Override
	public HttpHandler<File> download(String strUrl, String target,
			RequestCallBack<File> callBack) {
		return new ThreadPoolHttp().download(strUrl, target, callBack);
	}

	@Override
	public HttpHandler<File> download(String strUrl, String target,
			RequestParams params, RequestCallBack<File> callBack) {
		return new ThreadPoolHttp().download(strUrl, params, target, callBack);
	}

	@Override
	public HttpHandler<File> download(String url, RequestParams params,
			String target, boolean isResume, RequestCallBack<File> callback) {
		return new ThreadPoolHttp().download(url, params, target, isResume,
				callback);
	}

	@Override
	public HttpHandler<File> download(String url, String target,
			boolean isResume, RequestCallBack<File> callback) {
		return new ThreadPoolHttp().download(url, target, isResume, callback);
	}


	@Override
	public void post(String strUrl, HttpHeader header,
			List<NameValuePair> nameValuePairs, NetReqCallBack mNetReqCallBack) {
		HttpNetUtils.post(strUrl, header, nameValuePairs, mNetReqCallBack);
	}

	@Override
	public void post(String strUrl, HttpHeader header,
			NetReqCallBack mNetReqCallBack, NameValuePair... params) {
		HttpNetUtils.post(strUrl, header, mNetReqCallBack, params);
	}

	@Override
	public void get(String strUrl, HttpHeader header,
			NetReqCallBack mNetReqCallBack) {
		HttpNetUtils.get(strUrl, header, mNetReqCallBack);
		
	}
	@Override
	public void get(String strUrl, HttpHeader header, RequestParams params,
			NetReqCallBack mNetReqCallBack) {
		HttpNetUtils.get(strUrl, header, params, mNetReqCallBack);
	}
	

	@Override
	public void delete(String url, HttpHeader header, List<NameValuePair> nameValuePairs, NetReqCallBack mNetReqCallBack) {
		HttpNetUtils.delete(url, header, nameValuePairs, mNetReqCallBack);
		
	}

	@Override
	public void delete(String url,final NetReqCallBack mNetReqCallBack) {
		HttpNetUtils.delete(url, null, mNetReqCallBack);
//		new ThreadPoolHttp().delete(url, new RequestCallBack<Object>() {
//
//			@Override
//			public void onSuccess(Object t) {
//				//TODO:XXX如适用于返回文本
//				mNetReqCallBack.getSuccData(201, t.toString());
//			}
//
//			@Override
//			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				//TODO:XXX如适用于返回文本
//				mNetReqCallBack.getErrData(403, strMsg);
//				mNetReqCallBack.getExceptionMsg(errorNo, strMsg);
//			}
//		});
	}

	@Override
	public void delete(String url,HttpHeader header,NetReqCallBack mNetReqCallBack) {
		HttpNetUtils.delete(url, header, mNetReqCallBack);
//		new ThreadPoolHttp().delete(url, header, new RequestCallBack<Object>() {
//
//			@Override
//			public void onSuccess(Object t) {
//				//TODO:XXX如适用于返回文本
//				mNetReqCallBack.getSuccData(201, t.toString());
//			}
//
//			@Override
//			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				//TODO:XXX如适用于返回文本
//				mNetReqCallBack.getErrData(403, strMsg);
//				mNetReqCallBack.getExceptionMsg(errorNo, strMsg);
//			}
//		});
	}

	@Override
	public void put(String strUrl, HttpHeader header, List<NameValuePair> nameValuePairs, NetReqCallBack mNetReqCallBack) {
		HttpNetUtils.put(strUrl, header, nameValuePairs, mNetReqCallBack);
	}

	@Override
	public String post(String url, HttpHeader header, File[] mFiles, PostFileCallBack dataCallBack) {
		return HttpNetUtils.post(url, header, mFiles, dataCallBack);
	}

	@Override
	public String post(String url, File[] mFiles, PostFileCallBack dataCallBack) {
		return HttpNetUtils.post(url, mFiles, dataCallBack);
	}

	@Override
	public String post(String url, HttpHeader header, File mFile, PostFileCallBack mPostFileCallBack) {
		return HttpNetUtils.post(url, header, mFile, mPostFileCallBack);
	}

	@Override
	public String post(String url, File mFile, PostFileCallBack mPostFileCallBack) {
		return HttpNetUtils.post(url, mFile, mPostFileCallBack);
	}

	

}
