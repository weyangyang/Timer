package netutils.interf;
/**
 * @description 获取网络数据的回调接口
 *
 */
public interface NetDataCallBackInterf {
	/**
	 * @description 获取网络请求返回的数据
	 * @return strJson
	 * @param strJson
	 */
	public void getSuccData(int statusCode,String strJson,String strUrl);
	/**
	 * @description 获取网络请求返回的异常数据
	 * @param errCode
	 * @param strError
	 */
	public void getErrData(int statusCode,String strJson,String strUrl);
	/**
	 * @description 获取网络请求时产生的各种异常数据
	 * @param exceptionCode
	 * @param strMsg
	 */
	public void getExceptionMsg(int exceptionCode,String strMsg,String strUrl);

}
