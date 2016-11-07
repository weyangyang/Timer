package com.qsvxin.bean;

import java.io.Serializable;

import com.qsvxin.db.BaseDbBean;
import com.qsvxin.db.ColumnAnnotation;

public class GetUserMsgListBean extends BaseDbBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "GetUserMsgList";

	public static final String COLUMN_OPEN_ID = "OpenId";
	public static final String COLUMN_NICK_NAME = "NickName";
	public static final String COLUMN_COUNTRY = "Country";
	public static final String COLUMN_PROVINCE = "Province";
	public static final String COLUMN_CITY = "City";
	public static final String COLUMN_HEAD_URL = "HeadUrl";
	public static final String COLUMN_REPLY_MSG_COUNT = "ReplyMsgCount";

	/**
	 * 公众平台openid
	 */
	@ColumnAnnotation(column = COLUMN_OPEN_ID, info = "text unique")
	public String strOpenID;
	/**
	 * 用户昵称
	 */
	@ColumnAnnotation(column = COLUMN_NICK_NAME)
	public String strNickName;
	/**
	 * 国家
	 */
	@ColumnAnnotation(column = COLUMN_COUNTRY)
	public String strCountry;
	/**
	 * 省份
	 */
	@ColumnAnnotation(column = COLUMN_PROVINCE)
	public String strProvince;
	/**
	 * 城市
	 */
	@ColumnAnnotation(column = COLUMN_CITY)
	public String strCity;
	/**
	 * 头像url,（/0结尾=640*640 ，/64结尾=64*64）
	 */
	@ColumnAnnotation(column = COLUMN_HEAD_URL)
	public String strHeadUrl;
	/**
	 * 未回复消息数
	 */
	@ColumnAnnotation(column = COLUMN_REPLY_MSG_COUNT)
	public String strReplyMsgCount;

	@Override
	public String toString() {
		return "CustomerServiceMsgBean [strOpenID=" + strOpenID
				+ ", strNickName=" + strNickName + ", strCountry=" + strCountry
				+ ", strProvince=" + strProvince + ", strCity=" + strCity
				+ ", strHeadUrl=" + strHeadUrl + ", strReplyMsgCount="
				+ strReplyMsgCount + "]";
	}

	public String getStrProvince() {
		return strProvince;
	}

	public void setStrProvince(String strProvince) {
		this.strProvince = strProvince;
	}

	public String getStrOpenID() {
		return strOpenID;
	}

	public void setStrOpenID(String strOpenID) {
		this.strOpenID = strOpenID;
	}

	public String getStrNickName() {
		return strNickName;
	}

	public void setStrNickName(String strNickName) {
		this.strNickName = strNickName;
	}

	public String getStrCountry() {
		return strCountry;
	}

	public void setStrCountry(String strCountry) {
		this.strCountry = strCountry;
	}

	public String getStrCity() {
		return strCity;
	}

	public void setStrCity(String strCity) {
		this.strCity = strCity;
	}

	public String getStrHeadUrl() {
		return strHeadUrl;
	}

	public void setStrHeadUrl(String strHeadUrl) {
		this.strHeadUrl = strHeadUrl;
	}

	public String getStrReplyMsgCount() {
		return strReplyMsgCount;
	}

	public void setStrReplyMsgCount(String strReplyMsgCount) {
		this.strReplyMsgCount = strReplyMsgCount;
	}

}
