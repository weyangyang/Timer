package com.qsvxin.bean;

import java.io.Serializable;

public class GetUserMsgBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 公众平台openid
     */
    private String strOpenID;
    /**
     * 消息时间
     */
    private String strCurrentMsgTime;
    /**
     * 操作类别：1=用户发送，2=客服回复
     */
    private String strIOType;
    /**
     * 消息类型：
     * 0=未知 ,1=文本 ,2=图片, 4=位置, 5=语音 ,6=视频 ,7=链接 ,8=事件.
     */
    private String strMsgType;
    /**
     * 消息内容
     */
    private String strMsgText;
    public String getStrOpenID() {
        return strOpenID;
    }
    public void setStrOpenID(String strOpenID) {
        this.strOpenID = strOpenID;
    }
    public String getStrCurrentMsgTime() {
        return strCurrentMsgTime;
    }
    public void setStrCurrentMsgTime(String strCurrentMsgTime) {
        this.strCurrentMsgTime = strCurrentMsgTime;
    }
    public String getStrIOType() {
        return strIOType;
    }
    public void setStrIOType(String strIOType) {
        this.strIOType = strIOType;
    }
    public String getStrMsgType() {
        return strMsgType;
    }
    public void setStrMsgType(String strMsgType) {
        this.strMsgType = strMsgType;
    }
    public String getStrMsgText() {
        return strMsgText;
    }
    public void setStrMsgText(String strMsgText) {
        this.strMsgText = strMsgText;
    }
    @Override
    public String toString() {
        return "GetUserMsgBean [strOpenID=" + strOpenID + ", strCurrentMsgTime=" + strCurrentMsgTime + ", strIOType="
                + strIOType + ", strMsgType=" + strMsgType + ", strMsgText=" + strMsgText + "]";
    }
    
}
