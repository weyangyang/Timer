package com.qsvxin.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 *接收个推消息推送javaBean
 */
public class GeTuiMsgBean implements Serializable {
	
	 private static GeTuiMsgBean mGeTuiMsgBean;

	    private GeTuiMsgBean() {

	    }

	    public synchronized static GeTuiMsgBean getInstance() {
	        if (null == mGeTuiMsgBean) {
	        	mGeTuiMsgBean = new GeTuiMsgBean();
	        }
	        return mGeTuiMsgBean;

	    }
    
    public GeTuiMsgBean getParserGeTuiBean(String strJson) throws JSONException{
        JSONObject mJSONObject = new JSONObject(strJson);
        this.setStrMsgType(mJSONObject.optString("msgtype"));
        this.setStrMsgSource(mJSONObject.optString("type"));
        this.setStrUserOpenID(mJSONObject.optString("openid"));
        this.setStrMsgText(mJSONObject.optString("text"));
        this.setStrMsgTime(mJSONObject.optString("time"));
        this.setStrNickName(mJSONObject.optString("nickname"));
        this.setStrHeadUrl(mJSONObject.optString("head"));
        return this;
    }
    private String strNickName;
    private String strHeadUrl;
    public String getStrNickName() {
        return strNickName;
    }

    public void setStrNickName(String strNickName) {
        this.strNickName = strNickName;
    }

    public String getStrHeadUrl() {
        return strHeadUrl;
    }

    public void setStrHeadUrl(String strHeadUrl) {
        this.strHeadUrl = strHeadUrl;
    }

    public String getStrMsgTime() {
        return strMsgTime;
    }

    public void setStrMsgTime(String strMsgTime) {
        this.strMsgTime = strMsgTime;
    }
    private String strMsgTime;
    private static final long serialVersionUID = 1L;
    /**
     * 消息类型， 0=未知， 1=文本, 2= 图片, 3=图文（忽略）, 4=地理位置, 5=语音, 6=视频, 7=链接, 8=事件.
     */
    private String strMsgType;
    /**
     * 消息来自于openid用户
     */
    private String strUserOpenID;
    /**
     * 消息来源
     * 1=客户消息 （待添加）
     */
    private String StrMsgSource;
    /**
     * 消息内容
     */
    private String StrMsgText;
    public String getStrMsgType() {
        return strMsgType;
    }
    public void setStrMsgType(String strMsgType) {
        this.strMsgType = strMsgType;
    }
    public String getStrUserOpenID() {
        return strUserOpenID;
    }
    public void setStrUserOpenID(String strUserOpenID) {
        this.strUserOpenID = strUserOpenID;
    }
    public String getStrMsgSource() {
        return StrMsgSource;
    }
    public void setStrMsgSource(String strMsgSource) {
        StrMsgSource = strMsgSource;
    }
    public String getStrMsgText() {
        return StrMsgText;
    }
    public void setStrMsgText(String strMsgText) {
        StrMsgText = strMsgText;
    }

    @Override
    public String toString() {
        return "GeTuiMsgBean [strNickName=" + strNickName + ", strHeadUrl=" + strHeadUrl + ", strMsgTime=" + strMsgTime
                + ", strMsgType=" + strMsgType + ", strUserOpenID=" + strUserOpenID + ", StrMsgSource=" + StrMsgSource
                + ", StrMsgText=" + StrMsgText + "]";
    }
    
}
