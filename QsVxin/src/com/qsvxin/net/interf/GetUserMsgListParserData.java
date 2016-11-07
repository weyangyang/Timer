package com.qsvxin.net.interf;

import java.util.ArrayList;

import com.qsvxin.bean.GetUserMsgListBean;

public interface GetUserMsgListParserData {
    /**
     * 获取用户列表消息失败的数据
     * 
     * @param errCode
     *            错误编号
     * @param errMsg
     *            错误信息
     */
public void getUserMsgListErrData(String errCode, String errMsg);
/**
 * 获取用户消息列表成功的数据
 * @param succCode
 * @param succMsg 
 * @param mArrayList 
 */
public void getUserMsgListSuccData(String succCode, String succMsg,ArrayList<GetUserMsgListBean> mArrayList);
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
