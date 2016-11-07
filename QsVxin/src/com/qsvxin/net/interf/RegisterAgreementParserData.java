package com.qsvxin.net.interf;

import com.qsvxin.bean.RegisterAgreementBean;
/**
 * 
 *获取服务器上返回的注册协议
 */
public interface RegisterAgreementParserData {
    /**
     * 获取服务器上返回的注册协议失败的数据
     * 
     * @param errCode
     *            错误编号
     * @param errMsg
     *            错误信息
     */
    public void getRAErrData(String errCode, String errMsg);

   /**
    * 获取服务器上返回的注册协议成功后的数据
    * @param mLoginBean
    */
    public void getRASuccData(String succCode,String succMsg, RegisterAgreementBean mRegisterAgreementBean);
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
