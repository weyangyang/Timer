package com.qsvxin.net.interf;

import java.util.ArrayList;

import com.qsvxin.bean.GetUserMsgBean;

public interface GetUserMsgParserData {
    /**
     * 获取用户聊天信息失败的数据
     * 
     * @param errCode
     *            错误编号
     * @param errMsg
     *            错误信息
     */
public void getUserMsgErrData(String errCode, String errMsg);
/**
 * 获取用户聊天信息成功的数据
 * @param succCode
 * @param succMsg 
 * @param mArrayList 
 */
public void getUserMsgSuccData(String succCode, String succMsg,ArrayList<GetUserMsgBean> mArrayList);
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
