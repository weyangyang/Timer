package com.qsvxin.net.interf;
/**
 * @description 获取网络数据的回调接口
 * @author yusheng.li
 *
 */
public interface NetDataCallBackInterf {
	/**
	 * @description 获取网络请求返回的数据
	 * @author yusheng.li
	 * @return strJson
	 * @param strJson
	 */
	public void getNetData(int statusCode,String strJson);
	/**
	 * @description 获取网络请求返回的异常数据
	 * @author yusheng.li
	 * @param errCode
	 * @param strError
	 */
	public void getNetErrData(int errCode,String strError);

}
