package com.qsvxin.bean;

import java.io.Serializable;

public class LoginBean implements Serializable {
    private static final long serialVersionUID = -7973319872716191766L;
    private static final LoginBean mLoginBean = new LoginBean();

    private LoginBean() {
    };

    public static final synchronized LoginBean getInstance() {
        return mLoginBean;
    }
    
    /**
     * 用户的手机号
     */
    private String strUserPhoneNum;
    /**
     * 用户的邮箱号
     */
    private String strUserEmail;
    /**
     * 用户的QQ号
     */
    private String strUserQQ;
    /**
     * 用户注册时间
     */
    private String strUserRegisterTime;
    /**
     * 用户上次登录时间
     */
    private String strUserLastLoginTime;

    /**
     * 用于获取数据的用户密钥
     */
    private String strSecretKey;
    /**
     * 用户昵称
     */
    private String strNickname;
    /**
     * 公众平台的token
     */
    private String strToken;
    /**
     * 公众平台的appid
     */
    private String strAppid;
    /**
     * 公众平台的appsecret
     */
    private String strAppsecret;
    /**
     * 公众平台的原始id
     */
    private String strGh;

    public String getStrSecretKey() {
        return strSecretKey;
    }

    public void setStrSecretKey(String strSecretKey) {
        this.strSecretKey = strSecretKey;
    }

    public String getStrNickname() {
        return strNickname;
    }

    public void setStrNickname(String strNickname) {
        this.strNickname = strNickname;
    }

    public String getStrToken() {
        return strToken;
    }

    public void setStrToken(String strToken) {
        this.strToken = strToken;
    }

    public String getStrAppid() {
        return strAppid;
    }

    public void setStrAppid(String strAppid) {
        this.strAppid = strAppid;
    }

    public String getStrAppsecret() {
        return strAppsecret;
    }

    public void setStrAppsecret(String strAppsecret) {
        this.strAppsecret = strAppsecret;
    }

    public String getStrGh() {
        return strGh;
    }

    public void setStrGh(String strGh) {
        this.strGh = strGh;
    }

    @Override
    public String toString() {
        return "LoginBean [strUserPhoneNum=" + strUserPhoneNum + ", strUserEmail=" + strUserEmail + ", strUserQQ="
                + strUserQQ + ", strUserRegisterTime=" + strUserRegisterTime + ", strUserLastLoginTime="
                + strUserLastLoginTime + ", strSecretKey=" + strSecretKey + ", strNickname=" + strNickname
                + ", strToken=" + strToken + ", strAppid=" + strAppid + ", strAppsecret=" + strAppsecret + ", strGh="
                + strGh + "]";
    }

    public String getStrUserPhoneNum() {
        return strUserPhoneNum;
    }

    public void setStrUserPhoneNum(String strUserPhoneNum) {
        this.strUserPhoneNum = strUserPhoneNum;
    }

    public String getStrUserEmail() {
        return strUserEmail;
    }

    public void setStrUserEmail(String strUserEmail) {
        this.strUserEmail = strUserEmail;
    }

    public String getStrUserQQ() {
        return strUserQQ;
    }

    public void setStrUserQQ(String strUserQQ) {
        this.strUserQQ = strUserQQ;
    }

    public String getStrUserRegisterTime() {
        return strUserRegisterTime;
    }

    public void setStrUserRegisterTime(String strUserRegisterTime) {
        this.strUserRegisterTime = strUserRegisterTime;
    }

    public String getStrUserLastLoginTime() {
        return strUserLastLoginTime;
    }

    public void setStrUserLastLoginTime(String strUserLastLoginTime) {
        this.strUserLastLoginTime = strUserLastLoginTime;
    }

    public void setLoginData(String strSecretKey, String strNickname, String strToken, String strAppid,
            String strAppsecret, String strGh, String strUserQQ, String strUserLastLoginTime,
            String strUserRegisterTime, String strUserEmail, String strUserPhoneNum) {
        this.setStrSecretKey(strSecretKey);
        this.setStrAppid(strAppid);
        this.setStrGh(strGh);
        this.setStrNickname(strNickname);
        this.setStrToken(strToken);
        this.setStrAppsecret(strAppsecret);
        
        this.setStrUserQQ(strUserQQ);
        this.setStrUserEmail(strUserEmail);
        this.setStrUserRegisterTime(strUserRegisterTime);
        this.setStrUserLastLoginTime(strUserLastLoginTime);
        this.setStrUserPhoneNum(strUserPhoneNum);
        

    }

}
