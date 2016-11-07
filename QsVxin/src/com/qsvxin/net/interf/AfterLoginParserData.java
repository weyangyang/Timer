package com.qsvxin.net.interf;

import com.qsvxin.bean.LoginBean;

public interface AfterLoginParserData {
    /**
     * 获取登录失败的数据
     * 
     * @param errCode
     *            错误编号
     * @param errMsg
     *            错误信息
     */
    public void getLoginErrData(String errCode, String errMsg);

   /**
    * 获取登录成功后的数据
    * @param mLoginBean
    */
    public void getLoginSuccData(String succCode,String succMsg,LoginBean mLoginBean);
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
