package com.qsvxin.bean;

import java.io.Serializable;

import com.qsvxin.db.BaseDbBean;
import com.qsvxin.db.ColumnAnnotation;

/**
 * 
 * 会员信息javaBean
 */
public class MemberBean extends BaseDbBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final String TABLE_NAME = "Member";
    
    public static final String COLUMN_MEMBER_ID = "MemberID";
    public static final String COLUMN_OPEN_ID = "OpenID";
    public static final String COLUMN_IS_JOIN = "isjoin";
    public static final String COLUMN_IS_VIP = "isvip";
    public static final String COLUMN_MEMBER_NAME = "MemberName";
    public static final String COLUMN_MEMBER_PHONE_NUM = "MemberPhoneNum";
    public static final String COLUMN_SEX = "Sex";
    public static final String COLUMN_AGE = "Age";
    public static final String COLUMN_BIRTHDAY = "Birthday";
    public static final String COLUMN_EXPLAIN = "strExplain";
    public static final String COLUMN_CARD_ID = "CardID";
    public static final String COLUMN_RESIDUE_INTEGRAL = "ResidueIntegral";
    public static final String COLUMN_USED_INTEGRAL = "UsedIntegral";
    
    /**
     * id
     */
    @ColumnAnnotation(column = COLUMN_MEMBER_ID)
    public String strMemberID;
    /**
     * opened
     */
    @ColumnAnnotation(column = COLUMN_OPEN_ID, info = "text uniqe")
    public String strOpenID;
    /**
     * 是否关注(true,false)
     */
    @ColumnAnnotation(column = COLUMN_IS_JOIN)
    public boolean isjoin;
    /**
     * 是否加入会员(true,false)
     */
    @ColumnAnnotation(column = COLUMN_IS_VIP)
    public boolean isvip;
    /**
     * 会员名字
     */
    @ColumnAnnotation(column = COLUMN_MEMBER_NAME)
    public String strMemberName;
    /**
     * 会员手机号
     */
    @ColumnAnnotation(column = COLUMN_MEMBER_PHONE_NUM)
    public String strMemberPhoneNum;
    /**
     * 会员性别
     */
    @ColumnAnnotation(column = COLUMN_SEX)
    public String strSex;
    /**
     * 会员年龄
     */
    @ColumnAnnotation(column = COLUMN_AGE)
    public int intAge;
    /**
     * 会员生日
     */
    @ColumnAnnotation(column = COLUMN_BIRTHDAY)
    public String strBirthday;
    /**
     * 会员个人说明
     */
    @ColumnAnnotation(column = COLUMN_EXPLAIN)
    public String strExplain;
    /**
     * 会员卡号（非会员为0）
     */
    @ColumnAnnotation(column = COLUMN_CARD_ID)
    public String strCardID;
    /**
     * 剩余积分
     */
    @ColumnAnnotation(column = COLUMN_RESIDUE_INTEGRAL)
    public int intResidueIntegral;
    /**
     * 已使用积分
     */
    @ColumnAnnotation(column = COLUMN_USED_INTEGRAL)
    public int intUsedIntegral;

    public String getStrMemberID() {
        return strMemberID;
    }

    public void setStrMemberID(String strMemberID) {
        this.strMemberID = strMemberID;
    }

    public String getStrOpenID() {
        return strOpenID;
    }

    public void setStrOpenID(String strOpenID) {
        this.strOpenID = strOpenID;
    }

    public boolean isIsjoin() {
        return isjoin;
    }

    public void setIsjoin(boolean isjoin) {
        this.isjoin = isjoin;
    }

    @Override
    public String toString() {
        return "MemberBean [strMemberID=" + strMemberID + ", strOpenID=" + strOpenID + ", isjoin=" + isjoin
                + ", isvip=" + isvip + ", strMemberName=" + strMemberName + ", strMemberPhoneNum=" + strMemberPhoneNum
                + ", strSex=" + strSex + ", intAge=" + intAge + ", strBirthday=" + strBirthday + ", strExplain="
                + strExplain + ", strCardID=" + strCardID + ", intResidueIntegral=" + intResidueIntegral
                + ", intUsedIntegral=" + intUsedIntegral + "]";
    }

    public boolean isIsvip() {
        return isvip;
    }

    public void setIsvip(boolean isvip) {
        this.isvip = isvip;
    }

    public String getStrMemberName() {
        return strMemberName;
    }

    public void setStrMemberName(String strMemberName) {
        this.strMemberName = strMemberName;
    }

    public String getStrMemberPhoneNum() {
        return strMemberPhoneNum;
    }

    public void setStrMemberPhoneNum(String strMemberPhoneNum) {
        this.strMemberPhoneNum = strMemberPhoneNum;
    }

    public String getStrSex() {
        return strSex;
    }

    public void setStrSex(String strSex) {
        this.strSex = strSex;
    }

    public int getIntAge() {
        return intAge;
    }

    public void setIntAge(int intAge) {
        this.intAge = intAge;
    }

    public String getStrBirthday() {
        return strBirthday;
    }

    public void setStrBirthday(String strBirthday) {
        this.strBirthday = strBirthday;
    }

    public String getStrExplain() {
        return strExplain;
    }

    public void setStrExplain(String strExplain) {
        this.strExplain = strExplain;
    }

    public String getStrCardID() {
        return strCardID;
    }

    public void setStrCardID(String strCardID) {
        this.strCardID = strCardID;
    }

    public int getIntResidueIntegral() {
        return intResidueIntegral;
    }

    public void setIntResidueIntegral(int intResidueIntegral) {
        this.intResidueIntegral = intResidueIntegral;
    }

    public int getIntUsedIntegral() {
        return intUsedIntegral;
    }

    public void setIntUsedIntegral(int intUsedIntegral) {
        this.intUsedIntegral = intUsedIntegral;
    }

}
