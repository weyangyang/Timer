package com.qsvxin.net.interf;

import java.util.ArrayList;

import com.qsvxin.bean.MemberBean;

public interface GetMembersParserData {
    /**
     * 获取会员信息失败的数据
     * 
     * @param errCode
     *            错误编号
     * @param errMsg
     *            错误信息
     */
public void getMembersErrData(String errCode, String errMsg);
/**
 * 获取会员信息成功的数据
 * @param succCode
 * @param succMsg 
 * @param mArrayList 会员信息列表
 */
public void getMembersSuccData(String succCode, String succMsg,ArrayList<MemberBean> mArrayList);
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
