package com.qsvxin.bean;

import java.io.Serializable;

public class BuyGoodsBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 商品id
     */
    private String strGoodsID;
    /**
     * 商品规格
     */
    private String strGoodsNorms;
    /**
     * 规格名称
     */
    private String strGoodsNormsName;
    /**
     * 价格偏移
     */
    private String strGoodsPrice;
    /**
     * 商品数量
     */
    private String strGoodsCount;

    @Override
    public String toString() {
        return "BuyGoodsBean [strGoodsID=" + strGoodsID + ", strGoodsNorms=" + strGoodsNorms + ", strGoodsNormsName="
                + strGoodsNormsName + ", strGoodsPrice=" + strGoodsPrice + ", strGoodsCount=" + strGoodsCount + "]";
    }

    public String getStrGoodsID() {
        return strGoodsID;
    }

    public void setStrGoodsID(String strGoodsID) {
        this.strGoodsID = strGoodsID;
    }

    public String getStrGoodsNorms() {
        return strGoodsNorms;
    }

    public void setStrGoodsNorms(String strGoodsNorms) {
        this.strGoodsNorms = strGoodsNorms;
    }

    public String getStrGoodsNormsName() {
        return strGoodsNormsName;
    }

    public void setStrGoodsNormsName(String strGoodsNormsName) {
        this.strGoodsNormsName = strGoodsNormsName;
    }

    public String getStrGoodsPrice() {
        return strGoodsPrice;
    }

    public void setStrGoodsPrice(String strGoodsPrice) {
        this.strGoodsPrice = strGoodsPrice;
    }

    public String getStrGoodsCount() {
        return strGoodsCount;
    }

    public void setStrGoodsCount(String strGoodsCount) {
        this.strGoodsCount = strGoodsCount;
    }

}
