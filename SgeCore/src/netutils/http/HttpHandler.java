package netutils.http;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import netutils.core.AsyncTask;
import netutils.engine.NetConstants;
import netutils.entityhandler.EntityCallBack;
import netutils.entityhandler.FileEntityHandler;
import netutils.entityhandler.StringEntityHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import android.os.SystemClock;

public class HttpHandler<T> extends AsyncTask<Object, Object, Object> implements EntityCallBack {

	private final AbstractHttpClient client;
	private final HttpContext context;

	private final StringEntityHandler mStrEntityHandler = new StringEntityHandler();
	private final FileEntityHandler mFileEntityHandler = new FileEntityHandler();

	private final RequestCallBack<T> callback;

	private int executionCount = 0;
	private String targetUrl = null; // 下载的路径
	private boolean isResume = false; // 是否断点续传
	private String charset;

	public HttpHandler(AbstractHttpClient client, HttpContext context, RequestCallBack<T> callback, String charset) {
		this.client = client;
		this.context = context;
		this.callback = callback;
		this.charset = charset;
	}

	private void makeRequestWithRetries(HttpUriRequest request) throws ReqErrException{
		if (isResume && targetUrl != null) {
			File downloadFile = new File(targetUrl);
			long fileLen = 0;
			if (downloadFile.isFile() && downloadFile.exists()) {
				fileLen = downloadFile.length();
			}
			if (fileLen > 0)
				request.setHeader("RANGE", "bytes=" + fileLen + "-");
		}

		boolean retry = true;
		ReqErrException cause = null;
		HttpRequestRetryHandler retryHandler = client.getHttpRequestRetryHandler();
		while (retry) {
			try {
				if (!isCancelled()) {
					HttpResponse response = client.execute(request, context);
					if (!isCancelled()) {
						handleResponse(response);
					}
				}
				return;
			} catch (UnknownHostException e) {
				publishProgress(UPDATE_FAILURE, e,NetConstants.UNKNOWN_HOST_EXCEPTION, "unknownHostException：can't resolve host");
				return;
			}catch(ConnectTimeoutException e){
				cause = new ReqErrException(e, NetConstants.TIMEOUT_EXCEPTION);
				retry = retryHandler.retryRequest(cause, ++executionCount, context);
			}catch(SocketTimeoutException e){
				cause = new ReqErrException(e, NetConstants.TIMEOUT_EXCEPTION);
				retry = retryHandler.retryRequest(cause, ++executionCount, context);
			}catch (IOException e) {
				cause = new ReqErrException(e, NetConstants.IO_EXCEPTION);
				retry = retryHandler.retryRequest(cause, ++executionCount, context);
			} catch (NullPointerException e) {
				cause = new ReqErrException("NPE in HttpClient" + e.getMessage(),e,NetConstants.NULL_POINTER_EXCEPTION);
				retry = retryHandler.retryRequest(cause, ++executionCount, context);
			} catch (Exception e) {
				cause = new ReqErrException("Exception" + e.getMessage(),e,NetConstants.EXEPTION);
				retry = retryHandler.retryRequest(cause, ++executionCount, context);
			}
		}
		if (cause != null)
			throw cause;
		else
			throw new ReqErrException("unknown net error",NetConstants.UNKNOWN_NET_ERROR);
	}

	@Override
	protected Object doInBackground(Object... params) {
		if (params != null && params.length == 3) {
			targetUrl = String.valueOf(params[1]);
			isResume = (Boolean) params[2];
		}
		try {
			publishProgress(UPDATE_START); // 开始
			makeRequestWithRetries((HttpUriRequest) params[0]);

		} catch (ReqErrException e) {
			publishProgress(UPDATE_FAILURE, e,e.getErrCode(), e.getMessage()); // 结束
		}

		return null;
	}

	private final static int UPDATE_START = 1;
	private final static int UPDATE_LOADING = 2;
	private final static int UPDATE_FAILURE = 3;
	private final static int UPDATE_SUCCESS = 4;

	@SuppressWarnings("unchecked")
	@Override
	protected void onProgressUpdate(Object... values) {
		int update = Integer.valueOf(String.valueOf(values[0]));
		switch (update) {
		case UPDATE_START:
			if (callback != null)
				callback.onStart();
			break;
		case UPDATE_LOADING:
			if (callback != null)
				callback.onLoading(Long.valueOf(String.valueOf(values[1])), Long.valueOf(String.valueOf(values[2])));
			break;
		case UPDATE_FAILURE:
			if (callback != null)
				callback.onFailure((Throwable) values[1], (Integer) values[2], (String) values[3]);
			break;
		case UPDATE_SUCCESS:
			if (callback != null)
				callback.onSuccess((T) values[1]);
			break;
		default:
			break;
		}
		super.onProgressUpdate(values);
	}

	public boolean isStop() {
		return mFileEntityHandler.isStop();
	}

	/**
	 * 停止下载任务
	 */
	public void stop() {
		mFileEntityHandler.setStop(true);
	}
	/**
	 * 删除下载任务
	 */
	public void delete(){
		mFileEntityHandler.setDelete(true);
		mFileEntityHandler.setStop(true);
	}
	private void handleResponse(HttpResponse response) {
		StatusLine status = response.getStatusLine();
		if (status.getStatusCode() >= 300) {
			String errorMsg = "response status error code:" + status.getStatusCode();
			if (status.getStatusCode() == NetConstants.DOWNLOAD_COMPLETE && isResume) {
				errorMsg += " \n maybe you have download complete.";
			}
			publishProgress(UPDATE_FAILURE,
					new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()),
					status.getStatusCode(), errorMsg);
		} else {
			try {
				HttpEntity entity = response.getEntity();
				Object responseBody = null;
				if (entity != null) {
					time = SystemClock.uptimeMillis();
					if (targetUrl != null) {
						responseBody = mFileEntityHandler.handleEntity(entity, this, targetUrl, isResume);
					} else {
						responseBody = mStrEntityHandler.handleEntity(entity, this, charset);
					}

				}
				publishProgress(UPDATE_SUCCESS, responseBody);

			}catch(ReqErrException e){
				publishProgress(UPDATE_FAILURE, e,e.getErrCode(), e.getMessage());
			}catch (IOException e) {
				ReqErrException cause = new ReqErrException(e, NetConstants.DOWNLOAD_FILE_IOEXCEPTION);
				publishProgress(UPDATE_FAILURE,cause,cause.getErrCode(), cause.getMessage());
			}

		}
	}

	private long time;

	@Override
	public void callBack(long count, long current, boolean mustNoticeUI) {
		if (callback != null && callback.isProgress()) {
			if (mustNoticeUI) {
				publishProgress(UPDATE_LOADING, count, current);
			} else {
				long thisTime = SystemClock.uptimeMillis();
				if (thisTime - time >= callback.getRate()) {
					time = thisTime;
					publishProgress(UPDATE_LOADING, count, current);
				}
			}
		}
	}

}
