package com.qsvxin.net.interf;

import java.util.ArrayList;

import com.qsvxin.bean.GroupBuysListBean;

public interface GroupBuysListParserData {
    /**
     * 获取获取团购列表信息失败的数据
     * 
     * @param errCode
     *            错误编号
     * @param errMsg
     *            错误信息
     */
    public void getGroupBuysListErrData(String errCode, String errMsg);

    /**
     * 获取获取团购列表信息成功的数据
     * 
     * @param succCode
     * @param succMsg
     * @param mArrayList
     *            订单数据
     * @param buyGoodsData
     *            购买商品列表数据
     */
    public void getGroupBuysListSuccData(String succCode, String succMsg, ArrayList<GroupBuysListBean> mArrayList);
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
