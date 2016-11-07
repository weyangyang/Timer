package com.qsvxin.net.interf;

import java.util.ArrayList;

import com.qsvxin.bean.GroupBuysFormBean;

public interface GetGroupBuysFormParserData {
    /**
     * 获取团购订单信息失败的数据
     * 
     * @param errCode
     *            错误编号
     * @param errMsg
     *            错误信息
     */
public void GetGroupBuysFormErrData(String errCode, String errMsg);
/**
 * 获取团购订单信息成功的数据
 * @param succCode
 * @param succMsg 
 * @param mArrayList 团购订单信息
 */
public void GetGroupBuysFormSuccData(String succCode, String succMsg,ArrayList<GroupBuysFormBean> mArrayList);
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
