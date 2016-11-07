package com.qsvxin.bean;

import java.io.Serializable;
/**
 * 获取splash图片javaBean
 *
 */
public class NewSplashBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 开始时间
     */
    private String strBeginTime;
    /**
     * 结束时间
     */
    private String strEndTime;
    /**
     * 说明
     */
    private String strDescription;
    /**
     * 图片地址
     */
    private String strImageUrl;
    public String getStrBeginTime() {
        return strBeginTime;
    }
    public void setStrBeginTime(String strBeginTime) {
        this.strBeginTime = strBeginTime;
    }
    public String getStrEndTime() {
        return strEndTime;
    }
    public void setStrEndTime(String strEndTime) {
        this.strEndTime = strEndTime;
    }
    public String getStrDescription() {
        return strDescription;
    }
    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }
    public String getStrImageUrl() {
        return strImageUrl;
    }
    public void setStrImageUrl(String strImageUrl) {
        this.strImageUrl = strImageUrl;
    }
    @Override
    public String toString() {
        return "NewSplashBean [strBeginTime=" + strBeginTime + ", strEndTime=" + strEndTime + ", strDescription="
                + strDescription + ", strImageUrl=" + strImageUrl + "]";
    }
    
    
}
