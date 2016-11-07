package com.qsvxin.utils;


public class QsConstants {
    //---------------------   SharedPreference常量    -----------------------------------------------
    public static final String SP_ISINSTALL = "isInstall";
    
    
    //-------------------------   网络请求异常码    --------------------------------
	/**
	 *  网络请求异常码 ClientProtocolException
	 */
	public static final int CLIENT_PROTOCOL_EXCEPTION = 601;
	/**
	 *  网络请求异常码 IOException
	 */
	public static final int IO_EXCEPTION = 602;
	/**
	 *  网络请求异常码 Exception
	 */
	public static final int EXEPTION = 603;
	/**
	 *  网络请求异常码 ProtocolException
	 */
	public static final int PROTOCOL_EXCEPTION = 604;
	/**
	 *  网络请求异常码 MalformedURLException
	 */
	public static final int MALFORMED_URL_EXCEPTION = 605;
	/**
	 *  网络请求异常码UnsupportedEncodingException
	 */
	public static final int UNSUPPORTED_ENCODING_EXCEPTION = 606;
	/**
	 *  网络请求异常码ParseException
	 */
	public static final int PARSE_EXCEPTION = 607;
	/**
	 *  无网络连接
	 */
	public static final int CONNTION_NULL_EXCEPTION = 608;
	/**
	 *  已经下载完成
	 */
	public static final int DOWNLOAD_COMPLETE = 416;
	/**
	 * JSONException
	 */
	public static final int JSON_EXCEPTION = 700;
	/**
	 *  errorNo=0
	 */
	public static final int URL_ERROR = 404;
	/**
	 *  无法连接到服务器errorNo=0
	 */
	public static final int CONNECTION_ERROR = 0;
	
	
	//-----------------------------  访问服务器url  -------------------------------------
	
	
	/**
	 * 用户登录url
	 */
	public static final String USER_LOGIN_URL = "http://www.weyangyang.com/appapi/defaultapi/login";
	/**
	 * 用户注册url
	 */
	public static final String USER_REGISTER_URL = "http://www.weyangyang.com/appapi/defaultapi/Registration";
	/**
	 * 获取会员url
	 */
	public static final String GET_MEMBER_URL = "http://www.weyangyang.com/appapi/memberapi/getmemberlist";
	/**
	 * 获取商城订单url
	 */
	public static final String GET_SHOP_ORDER_URL = "http://www.weyangyang.com/appapi/shopapi/getshoporderformlist";
	/**
	 * 受理商城订单url
	 */
	public static final String ACCEPT_SHOP_ORDER_URL = "http://www.weyangyang.com/appapi/shopapi/updateOrderFormShouLi";
	/**
	 * 商城订单发货url
	 */
	public static final String SHOP_ORDER_SHIPMENTS_URL = "http://www.weyangyang.com/appapi/shopapi/updateOrderFormFaHuo";
	/**
	 * 完成商城订单url
	 */
	public static final String COMPLETE_SHOP_ORDER_URL = "http://www.weyangyang.com/appapi/shopapi/updateOrderFormWanCheng";
	/**
	 * 删除商城订单url
	 */
	public static final String DELETE_SHOP_ORDER_URL = "http://www.weyangyang.com/appapi/shopapi/deleteorderform";
	/**
	 * 获取团购列表url
	 */
	public static final String GET_GROUPBUYS_LIST_URL = "http://www.weyangyang.com/appapi/groupbuysapi/getgroupbuyslist";
	/**
	 * 获取团购订单url
	 */
	public static final String GET_GROUPBUYS_ORDER_URL = "http://www.weyangyang.com/appapi/groupbuysapi/getgroupbuysorderformList";
	/**
	 * 完成团购订单url
	 */
	public static final String COMPLETE_GROUPBUYS_ORDER_URL = "http://www.weyangyang.com/appapi/groupbuysapi/updateGroupBuysOrderFormWanCheng";
	/**
	 * 获取客服消息url
	 */
	public static final String GET_CUSTOMER_MESSAGE_URL = "http://www.weyangyang.com/appapi/ChatApi/getChatUsers";
	/**
	 * 获取用户消息 url
	 */
	public static final String GET_USER_MESSAGE_URL = "http://www.weyangyang.com/appapi/ChatApi/getUserMessage";
	/**
	 * 回复用户文本消息 url
	 */
	public static final String SEND_TEXT_MESSAGE_TO_USER_URL = "http://www.weyangyang.com/appapi/chatapi/sendTextMessage";
	/**
	 * 意见反馈url
	 */
	public static final String SEND_IDEA_FEEDBACK_URL = "http://www.weyangyang.com/appapi/defaultapi/Feedback";
	/**
	 * 获取新版本url
	 */
	public static final String UPDATE_VERSION_URL = "http://www.weyangyang.com/appapi/defaultapi/updateVersion";
	/**
	 * 获取新的启动页面url
	 */
	public static final String GET_NEW_SPLASH_URL = "http://www.weyangyang.com/appapi/defaultapi/getLoginImg";
	/**
	 * 获取注册协议url
	 */
	public static final String GET_REGISTER_AGREEMENT_URL = "http://www.weyangyang.com/appapi/defaultapi/getAgreement";
	
	
	public static final long ONE_DAY = 24 * 60 * 60 * 1000;// 一天
}
