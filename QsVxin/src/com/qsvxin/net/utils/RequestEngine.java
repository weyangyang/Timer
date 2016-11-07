package com.qsvxin.net.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.utils.QsConstants;

public class RequestEngine extends BaseRequest {
    private static RequestEngine mRequestEngine;

    private RequestEngine() {

    }

    public synchronized static RequestEngine getInstance() {
        if (null == mRequestEngine) {
            mRequestEngine = new RequestEngine();
        }
        return mRequestEngine;

    }

    /**
     * 用户登录
     * 
     * @param str_userName
     *            map参数 用户名称
     * @param str_passwd
     *            map参数 用户密码（md5 32位 小写）
     * @param str_clientID
     *            map参数 个推ClientID
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1.登录成功后返回：{ "errcode": 10000, "errmsg": "ok", "user": {
     *         "secretkey": "secretkey", "nickname": "nickname", "token":
     *         "token", "appid": "appid", "appsecret": "appsecret", "gh": "gh" }
     *         2.登录失败后返回：{ "errcode": 10001, "errmsg": "no user" }
     */
    public void login(Map<String, String> mMap, NetDataCallBackInterf netDataCallBackInterf) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", mMap.get("str_userName")));
        nameValuePairs.add(new BasicNameValuePair("pwd", mMap.get("str_passwd")));
        nameValuePairs.add(new BasicNameValuePair("clientid", mMap.get("str_clientID")));
        super.Request2Service(QsConstants.USER_LOGIN_URL, nameValuePairs, netDataCallBackInterf);
    }

    /**
     * 用户注册
     * 
     * @param str_userName
     *            map参数 用户名
     * @param str_passwd
     *            map参数 密码
     * @param str_phoneNum
     *            map参数 手机号
     * @param str_email
     *            map参数 邮箱号
     * @param str_qqNum
     *            map参数 qq号
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1.注册成功返回 { "errcode": 10000, "errmsg": "ok" } 2.注册失败返回 {
     *         "errcode":10001, "errmsg": "no user" }
     */
    public void register(Map<String, String> mMap, NetDataCallBackInterf netDataCallBackInterf) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("name", mMap.get("str_userName")));
        nameValuePairs.add(new BasicNameValuePair("pwd", mMap.get("str_passwd")));
        nameValuePairs.add(new BasicNameValuePair("phone", mMap.get("str_phoneNum")));
        nameValuePairs.add(new BasicNameValuePair("mail", mMap.get("str_email")));
        nameValuePairs.add(new BasicNameValuePair("qq", mMap.get("str_qqNum")));
        super.Request2Service(QsConstants.USER_REGISTER_URL, nameValuePairs, netDataCallBackInterf);

    }

    /**
     * 版本更新
     * @param mMap
     * @param netDataCallBackInterf
     * @parm clientHash 是 客户端软件hash值(标示软件名称) version 是 客户端版本号 type 是
     *       版本类型(1：android 2:ios)
     */
    public void updateVersion(Map<String, String> mMap, NetDataCallBackInterf netDataCallBackInterf) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("clientHash", mMap.get("str_clientHash")));
        nameValuePairs.add(new BasicNameValuePair("version", mMap.get("strVersion")));
        nameValuePairs.add(new BasicNameValuePair("type", mMap.get("strAppType")));
        super.Request2Service(QsConstants.UPDATE_VERSION_URL, nameValuePairs, netDataCallBackInterf);

    }

    /**
     * 获取会员信息
     * 
     * @param str_secretKey
     *            用户密钥
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1.成功获取后返回：{ "errcode": 10000, "errmsg": "ok", "data": [ { "id":
     *         1, "openid": "openid", "isjoin": true, "isvip": true, "name":
     *         "name", "phone": "phone", "sex": "男", "age": 27, "birthday":
     *         "1900-01-01", "explain": "这个人很懒", "cardid": 1000000, "rc": 0,
     *         "uc": 0 } ] } 2.请求不成功返回： { "errcode": 20001, "errmsg": "用户密钥错误" }
     */
    public void getMemberInfo(String str_secretKey, NetDataCallBackInterf netDataCallBackInterf) {
        NameValuePair mNameValuePair = new BasicNameValuePair("secretkey", str_secretKey);
        super.Request2Service(QsConstants.GET_MEMBER_URL, netDataCallBackInterf, mNameValuePair);
    }
    /**
     * 获取服务器最近的splash图片
     * @param netDataCallBackInterf
     */
    public void getNewSplashImg(NetDataCallBackInterf netDataCallBackInterf) {
        super.Request2Service(QsConstants.GET_NEW_SPLASH_URL, netDataCallBackInterf, null);
    }
    /**
     * 获取注册协议
     * @param netDataCallBackInterf
     */
    public void getRegisterAgreement(NetDataCallBackInterf netDataCallBackInterf) {
        super.Request2Service(QsConstants.GET_REGISTER_AGREEMENT_URL, netDataCallBackInterf, null);
    }

    /**
     * 意见反馈到服务器
     * 
     * @param str_secretKey
     * @param strContent
     * @param netDataCallBackInterf
     */
    public void getSendIdeaFeedBackData(String str_secretKey, String strContent,
            NetDataCallBackInterf netDataCallBackInterf) {
        NameValuePair mNameValuePair = new BasicNameValuePair("secretkey", str_secretKey);
        NameValuePair mNameValuePair1 = new BasicNameValuePair("content", strContent);
        super.Request2Service(QsConstants.SEND_IDEA_FEEDBACK_URL, netDataCallBackInterf, mNameValuePair,
                mNameValuePair1);
    }

    /**
     * 获取商城订单
     * 
     * @param str_secretKey
     *            用户密钥
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1.请求成功后返回： { "errcode": 99999, "errmsg": "test msg", "data": [ {
     *         "id": 1, "openid": "openid", "money": 10000, "creattime":
     *         "1900-01-01 00:00:00", "paytime": "1900-01-01 00:00:00",
     *         "distrtime": "1900-01-01 00:00:00", "finishtime":
     *         "1900-01-01 00:00:00", "ofstatus": 1, "paystatus": 1,
     *         "distrstatus": 1, "paymode": 1, "text": "请尽快发货", "buyername":
     *         "buyername", "buyerphone": "buyerphone", "buyeraddress":
     *         "buyeraddress", "coms": [ { "comid": 1, "norms": [ { "name":
     *         "黑色", "value": 0 } ], "count": 1 } ] } ] } 2.请求失败时返回：{ "errcode":
     *         20001, "errmsg": "用户密钥错误" }
     */
    public void getStoreOrder(String str_secretKey, NetDataCallBackInterf netDataCallBackInterf) {
        NameValuePair mNameValuePair = new BasicNameValuePair("secretkey", str_secretKey);
        super.Request2Service(QsConstants.GET_SHOP_ORDER_URL, netDataCallBackInterf, mNameValuePair);
    }

    /**
     * 受理商城订单
     * 
     * @param str_secretKey
     *            用户密钥
     * @param str_orderId
     *            订单ID
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1. 请求成功后返回： { "errcode": 10000, "errmsg": "ok" } 2. 请求失败后返回：{
     *         "errcode": 20001, "errmsg": "用户密钥错误" }
     */
    public void acceptStoreOrder(String str_secretKey, String str_orderId, NetDataCallBackInterf netDataCallBackInterf) {
        NameValuePair mNameValuePair1 = new BasicNameValuePair("secretkey", str_secretKey);
        NameValuePair mNameValuePair2 = new BasicNameValuePair("formid", str_orderId);
        super.Request2Service(QsConstants.ACCEPT_SHOP_ORDER_URL, netDataCallBackInterf, mNameValuePair1,
                mNameValuePair2);
    }

    /**
     * 商城订单发货
     * 
     * @param str_secretKey
     *            mMap参数 用户密钥
     * @param str_orderId
     *            mMap参数 订单ID
     * @param str_expressNum
     *            mMap参数 快递单号
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1.请求成功后返回： { "errcode": 10000, "errmsg": "ok" } 2. 请求失败后返回：{
     *         "errcode": 20001, "errmsg": "用户密钥错误" }
     */
    public void storeOrderShipments(Map<String, String> mMap, NetDataCallBackInterf netDataCallBackInterf) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("secretkey", mMap.get("str_secretKey")));
        nameValuePairs.add(new BasicNameValuePair("formid", mMap.get("str_orderId")));
        nameValuePairs.add(new BasicNameValuePair("express", mMap.get("str_expressNum")));
        super.Request2Service(QsConstants.SHOP_ORDER_SHIPMENTS_URL, nameValuePairs, netDataCallBackInterf);
    }

    /**
     * 完成商城订单
     * 
     * @param str_secretKey
     *            用户密钥
     * @param str_orderId
     *            订单ID
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1. 请求成功后返回： { "errcode": 10000, "errmsg": "ok" } 2. 请求失败后返回：{
     *         "errcode": 20001, "errmsg": "用户密钥错误" }
     */
    public void completeStoreOrder(String str_secretKey, String str_orderId, NetDataCallBackInterf netDataCallBackInterf) {
        NameValuePair mNameValuePair1 = new BasicNameValuePair("secretkey", str_secretKey);
        NameValuePair mNameValuePair2 = new BasicNameValuePair("formid", str_orderId);
        super.Request2Service(QsConstants.COMPLETE_SHOP_ORDER_URL, netDataCallBackInterf, mNameValuePair1,
                mNameValuePair2);
    }

    /**
     * 删除商城订单
     * 
     * @param str_secretKey
     *            用户密钥
     * @param str_orderId
     *            订单ID
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1. 请求成功后返回： { "errcode": 10000, "errmsg": "ok" } 2. 请求失败后返回：{
     *         "errcode": 20001, "errmsg": "用户密钥错误" }
     */
    public void deleteStoreOrder(String str_secretKey, String str_orderId, NetDataCallBackInterf netDataCallBackInterf) {
        NameValuePair mNameValuePair1 = new BasicNameValuePair("secretkey", str_secretKey);
        NameValuePair mNameValuePair2 = new BasicNameValuePair("formid", str_orderId);
        super.Request2Service(QsConstants.DELETE_SHOP_ORDER_URL, netDataCallBackInterf, mNameValuePair1,
                mNameValuePair2);
    }

    /**
     * 获取团购列表信息
     * 
     * @param str_secretKey
     *            用户密钥
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1. 请求成功后返回： { "errcode": 10000, "errmsg": "ok", "data": [ { "id":
     *         1, "title": "title" } ] } 2. 请求失败后返回：{ "errcode": 20001,
     *         "errmsg": "用户密钥错误" }
     */
    public void getGroupBuysList(String str_secretKey, NetDataCallBackInterf netDataCallBackInterf) {
        NameValuePair mNameValuePair = new BasicNameValuePair("secretkey", str_secretKey);
        super.Request2Service(QsConstants.GET_GROUPBUYS_LIST_URL, netDataCallBackInterf, mNameValuePair);
    }

    /**
     * 获取团购订单信息
     * 
     * @param str_secretKey
     *            用户密钥
     * @param str_groupBuysOrderId
     *            团购ID
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1. 请求成功后返回：{ "errcode": 10000, "errmsg": "ok", "data": [ { "id":
     *         1, "name": "name", "phone": "phone", "count": 1, "sn": "sn",
     *         "state": 1 } ] } 2. 请求失败后返回：{ "errcode": 20001, "errmsg":
     *         "用户密钥错误" }
     */
    public void getGroupBuysOrder(String str_secretKey, String str_groupBuysOrderId,
            NetDataCallBackInterf netDataCallBackInterf) {
        NameValuePair mNameValuePair1 = new BasicNameValuePair("secretkey", str_secretKey);
        NameValuePair mNameValuePair2 = new BasicNameValuePair("formid", str_groupBuysOrderId);
        super.Request2Service(QsConstants.GET_GROUPBUYS_ORDER_URL, netDataCallBackInterf, mNameValuePair1,
                mNameValuePair2);
    }

    /**
     * 删除商城订单
     * 
     * @param str_secretKey
     *            用户密钥
     * @param str_groupBuysOrderId
     *            团购订单ID
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1. 请求成功后返回： { "errcode": 10000, "errmsg": "ok" } 2. 请求失败后返回：{
     *         "errcode": 20004, "errmsg": "订单完成失败" }
     */
    public void completeGroupBuysOrder(String str_secretKey, String str_groupBuysOrderId,
            NetDataCallBackInterf netDataCallBackInterf) {
        NameValuePair mNameValuePair1 = new BasicNameValuePair("secretkey", str_secretKey);
        NameValuePair mNameValuePair2 = new BasicNameValuePair("formid", str_groupBuysOrderId);
        super.Request2Service(QsConstants.COMPLETE_GROUPBUYS_ORDER_URL, netDataCallBackInterf, mNameValuePair1,
                mNameValuePair2);
    }

    /**
     * 获取客服消息
     * 
     * @param str_secretKey
     *            用户密钥
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1. 请求成功后返回： { "errcode": 10000, "errmsg": "ok", "data": [ {
     *         "openid": "oPW6BuPM3nVkEpQkbxDUP081V7nM", "nickname": "信仰",
     *         "country": "中国", "province": "辽宁", "city": "沈阳", "head":
     *         "http://wx.qlogo.cn/mmopen/Q3auHgzwzM6Fs5iauL0vCcPbXZOUXGQhvR0nKlsH4rL8aUpvK4GCYnyNlgkuPicSXPA7cUCyZ21EbjUkPACGfTNw/0"
     *         , "count": 0 } ] } 2. 请求失败后返回：{ "errcode": 20001, "errmsg":
     *         "用户密钥错误" }
     */
    public void getServiceMessage(String str_secretKey, NetDataCallBackInterf netDataCallBackInterf) {
        NameValuePair mNameValuePair = new BasicNameValuePair("secretkey", str_secretKey);
        super.Request2Service(QsConstants.GET_CUSTOMER_MESSAGE_URL, netDataCallBackInterf, mNameValuePair);
    }

    /**
     * 获取用户消息
     * 
     * @param str_secretKey
     *            用户密钥
     * @param str_userOpenID
     *            客户openid
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1. 请求成功后返回： { "errcode": 10000, "errmsg": "ok", "data": [ { "id":
     *         2, "openid": "oPW6BuPM3nVkEpQkbxDUP081V7nM", "time":
     *         "2014-09-14 14:01:00", "iotype": 1, "msgtype": 1, "text": "哈哈" },
     *         { "id": 3, "openid": "oPW6BuPM3nVkEpQkbxDUP081V7nM", "time":
     *         "2014-09-14 14:04:30", "iotype": 1, "msgtype": 1, "text": "呵呵" }
     *         ] } 2. 请求失败后返回：{ "errcode": 20001, "errmsg": "用户密钥错误" }
     */
    public void getUserMessage(String str_secretKey, String str_userOpenID, NetDataCallBackInterf netDataCallBackInterf) {
        NameValuePair mNameValuePair1 = new BasicNameValuePair("secretkey", str_secretKey);
        NameValuePair mNameValuePair2 = new BasicNameValuePair("openid", str_userOpenID);
        super.Request2Service(QsConstants.GET_USER_MESSAGE_URL, netDataCallBackInterf, mNameValuePair1, mNameValuePair2);
    }

    /**
     * 回复用户文本消息
     * 
     * @param str_secretKey
     *            map参数 用户密钥
     * @param str_appID
     *            map参数 登录时取得的appid
     * @param str_appSecret
     *            map参数 登录时取得的appsecret
     * @param str_openID
     *            map参数 用户openid
     * @param str_sendText
     *            map参数 回复用户的文本消息
     * @param netDataCallBackInterf
     *            数据返回接口
     * @return 1.注册成功返回 { "errcode": 10000, "errmsg": "ok" } 2.注册失败返回 { {
     *         "errcode": 20001, "errmsg": "用户密钥错误" }
     */
    public void sendTextMessage(Map<String, String> mMap, NetDataCallBackInterf netDataCallBackInterf) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("secretkey", mMap.get("str_secretKey")));
        nameValuePairs.add(new BasicNameValuePair("appid", mMap.get("str_appID")));
        nameValuePairs.add(new BasicNameValuePair("appsecret", mMap.get("str_appSecret")));
        nameValuePairs.add(new BasicNameValuePair("openid", mMap.get("str_openID")));
        nameValuePairs.add(new BasicNameValuePair("text", mMap.get("str_sendText")));
        super.Request2Service(QsConstants.SEND_TEXT_MESSAGE_TO_USER_URL, nameValuePairs, netDataCallBackInterf);
    }
}
