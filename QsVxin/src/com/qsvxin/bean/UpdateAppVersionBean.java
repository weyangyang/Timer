package com.qsvxin.bean;

import java.io.Serializable;
/**
 * 版本更新javaBean
 *
 */
public class UpdateAppVersionBean implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 客户端是否是最新版本(为false将返回最新版本信息)
     */
    private boolean isNewVersion ;
    /**
     * 最新版本下载地址
     */
    private String strDownLoadAppUrl;
    /**
     * 安装包介绍
     */
    private String strNewAppIntroduction;
    /**
     * 是否强制更新
     */
    private boolean isMustUpdate;
    /**
     * 版本全名称
     */
    private String strAppName;
    /**
     * 最新版本大小
     */
    private String strVersionSize;
    /**
     * 最新版本更新时间
     */
    private String strVersionUpdateTime;
    /**
     * 最新版本号
     */
    private String strAppVersionName;
    
    public String getStrAppVersionName() {
        return strAppVersionName;
    }
    public void setStrAppVersionName(String strAppVersionName) {
        this.strAppVersionName = strAppVersionName;
    }
    public boolean isNewVersion() {
        return isNewVersion;
    }
    public void setNewVersion(boolean isNewVersion) {
        this.isNewVersion = isNewVersion;
    }
    public String getStrDownLoadAppUrl() {
        return strDownLoadAppUrl;
    }
    public void setStrDownLoadAppUrl(String strDownLoadAppUrl) {
        this.strDownLoadAppUrl = strDownLoadAppUrl;
    }
    public String getStrNewAppIntroduction() {
        return strNewAppIntroduction;
    }
    public void setStrNewAppIntroduction(String strNewAppIntroduction) {
        this.strNewAppIntroduction = strNewAppIntroduction;
    }
    public boolean isMustUpdate() {
        return isMustUpdate;
    }
    public void setMustUpdate(boolean isMustUpdate) {
        this.isMustUpdate = isMustUpdate;
    }
    public String getStrAppName() {
        return strAppName;
    }
    public void setStrAppName(String strAppName) {
        this.strAppName = strAppName;
    }
    public String getStrVersionSize() {
        return strVersionSize;
    }
    public void setStrVersionSize(String strVersionSize) {
        this.strVersionSize = strVersionSize;
    }
    public String getStrVersionUpdateTime() {
        return strVersionUpdateTime;
    }
    public void setStrVersionUpdateTime(String strVersionUpdateTime) {
        this.strVersionUpdateTime = strVersionUpdateTime;
    }
    @Override
    public String toString() {
        return "UpdateAppVersionBean [isNewVersion=" + isNewVersion + ", strDownLoadAppUrl=" + strDownLoadAppUrl
                + ", strNewAppIntroduction=" + strNewAppIntroduction + ", isMustUpdate=" + isMustUpdate
                + ", strVersionName=" + strAppName + ", strVersionSize=" + strVersionSize
                + ", strVersionUpdateTime=" + strVersionUpdateTime + ", strAppVersionName=" + strAppVersionName + "]";
    }
    
}
