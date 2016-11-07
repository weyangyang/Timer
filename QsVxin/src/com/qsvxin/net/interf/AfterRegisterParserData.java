package com.qsvxin.net.interf;

public interface AfterRegisterParserData {
    /**
     * 获取注册失败的数据
     * 
     * @param errCode
     *            错误编号
     * @param errMsg
     *            错误信息
     */
    public void getRegisterErrData(String errCode, String errMsg);
    /**
     * 获取注册成功后的数据
     * @param succCode
     * @param succMsg
     */
    public void getRegisterSuccData(String succCode, String succMsg);
}
