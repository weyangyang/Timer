package com.qsvxin.bean;

import java.io.Serializable;
/**
 * 团购订单javaBean
 *
 */
public class GroupBuysFormBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单ID
     */
    private String strOrderFormID;
    /**
     * 买家名字
     */
    private String strBuyerName;
    /**
     * 买家手机号
     */
    private String strBuyerPhoneNum;
    /**
     * 购买数量
     */
    private String strBuyCount;
    /**
     * SN验证码
     */
    private String strSN;
    /**
     * 订单状态( 1=下单 2=完成)
     */
    private String strOrderFormStatus;

    @Override
    public String toString() {
        return "GroupBuysFormBean [strOrderFormID=" + strOrderFormID + ", strBuyerName=" + strBuyerName
                + ", strBuyerPhoneNum=" + strBuyerPhoneNum + ", strBuyCount=" + strBuyCount + ", strSN=" + strSN
                + ", strOrderFormStatus=" + strOrderFormStatus + "]";
    }

    public String getStrOrderFormID() {
        return strOrderFormID;
    }

    public void setStrOrderFormID(String strOrderFormID) {
        this.strOrderFormID = strOrderFormID;
    }

    public String getStrBuyerName() {
        return strBuyerName;
    }

    public void setStrBuyerName(String strBuyerName) {
        this.strBuyerName = strBuyerName;
    }

    public String getStrBuyerPhoneNum() {
        return strBuyerPhoneNum;
    }

    public void setStrBuyerPhoneNum(String strBuyerPhoneNum) {
        this.strBuyerPhoneNum = strBuyerPhoneNum;
    }

    public String getStrBuyCount() {
        return strBuyCount;
    }

    public void setStrBuyCount(String strBuyCount) {
        this.strBuyCount = strBuyCount;
    }

    public String getStrSN() {
        return strSN;
    }

    public void setStrSN(String strSN) {
        this.strSN = strSN;
    }

    public String getStrOrderFormStatus() {
        return strOrderFormStatus;
    }

    public void setStrOrderFormStatus(String strOrderFormStatus) {
        this.strOrderFormStatus = strOrderFormStatus;
    }
}
