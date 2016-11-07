package com.qsvxin.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.qsvxin.db.BaseDbBean;
import com.qsvxin.db.ColumnAnnotation;
import com.qsvxin.view.slideview.SlideView;

public class VorderFormBean extends BaseListViewItem implements Serializable {
    private static final long serialVersionUID = -1317668597233957417L;
    
    public static final String TABLE_NAME = "VorderForm";
    
    public static final String COLUMN_ORDERFORM_ID = "OrderFormId";
    public static final String COLUMN_OPEN_ID = "OpenId";
    public static final String COLUMN_TOTAL_PRICE = "TotalPrice";
    public static final String COLUMN_CREATE_TIME = "CreateTime";
    public static final String COLUMN_PAY_TIME = "PayTime";
    public static final String COLUMN_SHIPMeNTS_TIME = "ShipmentsTime";
    public static final String COLUMN_FINISH_ORDER_TIME = "FinishOrderTime";
    public static final String COLUMN_ORDER_FORM_STATUS = "OrderFormStatus";
    public static final String COLUMN_ORDER_PAY_STATUS = "OrderPayStatus";
    public static final String COLUMN_SHIPMENTS_STATUS = "ShipmentsStatus";
    public static final String COLUMN_PAY_MODE = "PayMode";
    public static final String COLUMN_BUYER_LEAVE_MSG = "BuyerLeaveMsg";
    public static final String COLUMN_BUYER_NAME = "BuyerName";
    public static final String COLUMN_BUYER_PHONE_NUM = "BuyerPhoneNum";
    public static final String COLUMN_BUYER_ADDRESS = "BuyerAddress";
    public static final String COLUMN_BUYGOODS_JSON = "BuyGoodsJson";
    /**
     * 订单ID
     */
    @ColumnAnnotation(column = COLUMN_ORDERFORM_ID, info = "text unique")
    public String strOrderFormID;
    /**
     * 微信openid
     */
    @ColumnAnnotation(column = COLUMN_OPEN_ID)
    public String strOpenID;
    /**
     * 订单总价
     */
    @ColumnAnnotation(column = COLUMN_TOTAL_PRICE)
    public String strTotalPrice;
    /**
     * 订单创建时间
     */
    @ColumnAnnotation(column = COLUMN_CREATE_TIME)
    public String strCreateTime;
    /**
     * 订单支付时间
     */
    @ColumnAnnotation(column = COLUMN_PAY_TIME)
    public String strPayTime;
    /**
     * 订单发货时间
     */
    @ColumnAnnotation(column = COLUMN_SHIPMeNTS_TIME)
    public String strShipmentsTime;
    /**
     * 订单完成时间
     */
    @ColumnAnnotation(column = COLUMN_FINISH_ORDER_TIME)
    public String strFinishOrderTime;
    /**
     * 订单状态 创建=1, 付款=2, 退款=3, 发货=4, 退货=5, 收货=6, 换货=7, 作废=8, 完成=9, 受理=10
     */
    @ColumnAnnotation(column = COLUMN_ORDER_FORM_STATUS)
    public String strOrderFormStatus;
    /**
     * paystatus 支付状态 已付款=1, 未付款=2
     */
    @ColumnAnnotation(column = COLUMN_ORDER_PAY_STATUS)
    public String strOrderPayStatus;
    /**
     * 发货状态: 已发货=1, 未发货=2
     */
    @ColumnAnnotation(column = COLUMN_SHIPMENTS_STATUS)
    public String strShipmentsStatus;
    /**
     * 支付方式: 货到付款=1
     */
    @ColumnAnnotation(column = COLUMN_PAY_MODE)
    public String strPayMode;
    /**
     * 买家留言
     */
    @ColumnAnnotation(column = COLUMN_BUYER_LEAVE_MSG)
    public String strBuyerLeaveMsg;
    /**
     * 买家姓名
     */
    @ColumnAnnotation(column = COLUMN_BUYER_NAME)
    public String strBuyerName;
    /**
     * 买家电话
     */
    @ColumnAnnotation(column = COLUMN_BUYER_PHONE_NUM)
    public String strBuyerPhoneNum;
    /**
     * 买家地址
     */
    @ColumnAnnotation(column = COLUMN_BUYER_ADDRESS)
    public String strBuyerAddress;
    /**
     * 订单商品数据列表
     */
    public ArrayList<BuyGoodsBean> mArrayListBuyGoods;
    @ColumnAnnotation(column = COLUMN_BUYGOODS_JSON)
    public String BuyGoodsJson;
    
    
    public String getBuyGoodsJson() {
		return BuyGoodsJson;
	}

	public String getStrOrderFormID() {
        return strOrderFormID;
    }

    public void setStrOrderFormID(String strOrderFormID) {
        this.strOrderFormID = strOrderFormID;
    }

    public String getStrOpenID() {
        return strOpenID;
    }

    public void setStrOpenID(String strOpenID) {
        this.strOpenID = strOpenID;
    }

    public String getStrTotalPrice() {
        return strTotalPrice;
    }

    public void setStrTotalPrice(String strTotalPrice) {
        this.strTotalPrice = strTotalPrice;
    }

    public String getStrCreateTime() {
        return strCreateTime;
    }

    public void setStrCreateTime(String strCreateTime) {
        this.strCreateTime = strCreateTime;
    }

    public String getStrPayTime() {
        return strPayTime;
    }

    public void setStrPayTime(String strPayTime) {
        this.strPayTime = strPayTime;
    }

    public String getStrShipmentsTime() {
        return strShipmentsTime;
    }

    public void setStrShipmentsTime(String strShipmentsTime) {
        this.strShipmentsTime = strShipmentsTime;
    }

    public String getStrFinishOrderTime() {
        return strFinishOrderTime;
    }

    public void setStrFinishOrderTime(String strFinishOrderTime) {
        this.strFinishOrderTime = strFinishOrderTime;
    }

    public String getStrOrderFormStatus() {
        return strOrderFormStatus;
    }

    public void setStrOrderFormStatus(String strOrderFormStatus) {
        this.strOrderFormStatus = strOrderFormStatus;
    }

    public String getStrOrderPayStatus() {
        return strOrderPayStatus;
    }

    public void setStrOrderPayStatus(String strOrderPayStatus) {
        this.strOrderPayStatus = strOrderPayStatus;
    }

    public String getStrShipmentsStatus() {
        return strShipmentsStatus;
    }

    public void setStrShipmentsStatus(String strShipmentsStatus) {
        this.strShipmentsStatus = strShipmentsStatus;
    }

    public String getStrPayMode() {
        return strPayMode;
    }

    public void setStrPayMode(String strPayMode) {
        this.strPayMode = strPayMode;
    }

    public String getStrBuyerLeaveMsg() {
        return strBuyerLeaveMsg;
    }

    public void setStrBuyerLeaveMsg(String strBuyerLeaveMsg) {
        this.strBuyerLeaveMsg = strBuyerLeaveMsg;
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

   
public String getStrBuyerAddress() {
        return strBuyerAddress;
    }

    public void setStrBuyerAddress(String strBuyerAddress) {
        this.strBuyerAddress = strBuyerAddress;
    }

    public ArrayList<BuyGoodsBean> getmArrayListBuyGoods() {
        return mArrayListBuyGoods;
    }

    public void setmArrayListBuyGoods(ArrayList<BuyGoodsBean> mArrayListBuyGoods) {
        this.mArrayListBuyGoods = mArrayListBuyGoods;
        JSONArray jsonArray = new JSONArray(mArrayListBuyGoods);
        BuyGoodsJson = jsonArray.toString();
    }


@Override
    public String toString() {
        return "VorderFormBean [strOrderFormID=" + strOrderFormID + ", strOpenID=" + strOpenID + ", strTotalPrice="
                + strTotalPrice + ", strCreateTime=" + strCreateTime + ", strPayTime=" + strPayTime
                + ", strShipmentsTime=" + strShipmentsTime + ", strFinishOrderTime=" + strFinishOrderTime
                + ", strOrderFormStatus=" + strOrderFormStatus + ", strOrderPayStatus=" + strOrderPayStatus
                + ", strShipmentsStatus=" + strShipmentsStatus + ", strPayMode=" + strPayMode + ", strBuyerLeaveMsg="
                + strBuyerLeaveMsg + ", strBuyerName=" + strBuyerName + ", strBuyerPhoneNum=" + strBuyerPhoneNum
                + ", strBuyerAddress=" + strBuyerAddress + ", mArrayListBuyGoods=" + mArrayListBuyGoods + ", BuyGoodsJson=" + BuyGoodsJson + "]";
    }




}
