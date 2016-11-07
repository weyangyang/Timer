package com.qsvxin.net.interf;

import java.util.ArrayList;

import com.qsvxin.bean.UpdateAppVersionBean;
/**
 * 获取版本更新接口
 *
 */
public interface UpdateVersionParserData {
    /**
     * 获取版本更新数据失败
     * 
     * @param errCode
     *            错误编号
     * @param errMsg
     *            错误信息
     */
public void getUpdateVersionErrData(String errCode, String errMsg);
/**
 * 获取版本更新信息成功的数据
 * @param succCode
 * @param succMsg
 * @param mArrayList  
 */
public void getUpdateVersionSuccData(String succCode, String succMsg,UpdateAppVersionBean mUpdateAppBean);
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
