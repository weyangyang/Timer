package com.qsvxin.net.interf;

import java.util.ArrayList;

import com.qsvxin.bean.GeTuiMsgBean;
/**
 * 获取个推推送消息接口
 *
 */
public interface GetGeTuiMsgParserData {
    /**
     * 获取个推推送信息失败的数据
     * 
     * @param errCode
     *            错误编号
     * @param errMsg
     *            错误信息
     */
public void getGeTuiMsgErrData(String errCode, String errMsg);
/**
 * 获取个推推送信息成功的数据
 * @param succCode
 * @param succMsg
 * @param mArrayList  
 * @param buyGoodsData  
 */
public void getGeTuiMsgSuccData(String succCode, String succMsg,ArrayList<GeTuiMsgBean> mArrayList);
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
