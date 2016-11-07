package com.qsvxin.bean;

import java.io.Serializable;

public class TestBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String strPhone;
    private int intPhoneNum;
    public String getStrPhone() {
        return strPhone;
    }
    public void test(){
        System.out.println("我是曼城要历");
    }
    public void setStrPhone(String strPhone) {
        this.strPhone = strPhone;
    }
    public int getIntPhoneNum() {
        return intPhoneNum;
    }
    public void setIntPhoneNum(int intPhoneNum) {
        this.intPhoneNum = intPhoneNum;
    }
    
}
