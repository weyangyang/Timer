package com.qsvxin.net.interf;

public interface StoreOFShipmentsParserData {
    /**
     * 商城订单发货失败的数据
     * 
     * @param errCode
     *            错误编号
     * @param errMsg
     *            错误信息
     */
    public void storeOFShipmentsErrData(String errCode, String errMsg);
    /**
     * 商场订单发货成功后的数据
     * @param succCode
     * @param succMsg
     */
    public void storeOFShipmentsSuccData(String succCode, String succMsg);
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
