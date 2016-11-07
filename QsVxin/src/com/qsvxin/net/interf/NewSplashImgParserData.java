package com.qsvxin.net.interf;

import com.qsvxin.bean.NewSplashBean;
/**
 * 
 *获取服务器上返回的splash图片
 */
public interface NewSplashImgParserData {
    /**
     * 获取服务器上返回的splash图片失败的数据
     * 
     * @param errCode
     *            错误编号
     * @param errMsg
     *            错误信息
     */
    public void getNewSplashImgErrData(String errCode, String errMsg);

   /**
    * 获取服务器上返回的splash图片成功后的数据
    * @param mLoginBean
    */
    public void getNewSplashImgSuccData(String succCode,String succMsg,NewSplashBean mNewSplashBean);
    /**
     * 获取网络异常
     * @param errCode
     * @param errMsg
     */
    public void getNetErrData(int errCode, String errMsg);
    /**
     * 获取解析异常数据
     * @param errCode
     * @param errMsg
     */
    public void getParserErrData(int errCode, String errMsg);
}
