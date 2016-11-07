package com.qsvxin.net.interf;

public interface CompleteGBorderParserData {
    /**
     * 完成团购订单失败的数据
     * 
     * @param errCode
     *            错误编号
     * @param errMsg
     *            错误信息
     */
    public void CompleteGBorderErrData(String errCode, String errMsg);
    /**
     * 完成团购订单成功后的数据
     * @param succCode
     * @param succMsg
     */
    public void CompleteGBorderSuccData(String succCode, String succMsg);
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
