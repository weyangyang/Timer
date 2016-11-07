package com.qsvxin.bean;

import java.io.Serializable;

public class RegisterAgreementBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private String strTitle;
    private String strContent;
    public String getStrTitle() {
        return strTitle;
    }
    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }
    public String getStrContent() {
        return strContent;
    }
    public void setStrContent(String strContent) {
        this.strContent = strContent;
    }
    @Override
    public String toString() {
        return "RegisterAgreement [strTitle=" + strTitle + ", strContent=" + strContent + "]";
    }
    
}
