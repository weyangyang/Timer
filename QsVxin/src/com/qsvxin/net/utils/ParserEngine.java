package com.qsvxin.net.utils;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qsvxin.bean.BuyGoodsBean;
import com.qsvxin.bean.GetUserMsgBean;
import com.qsvxin.bean.GetUserMsgListBean;
import com.qsvxin.bean.GroupBuysFormBean;
import com.qsvxin.bean.GroupBuysListBean;
import com.qsvxin.bean.LoginBean;
import com.qsvxin.bean.MemberBean;
import com.qsvxin.bean.NewSplashBean;
import com.qsvxin.bean.RegisterAgreementBean;
import com.qsvxin.bean.UpdateAppVersionBean;
import com.qsvxin.bean.VorderFormBean;
import com.qsvxin.exception.ParserException;
import com.qsvxin.net.interf.AfterAcceptOFParserData;
import com.qsvxin.net.interf.AfterLoginParserData;
import com.qsvxin.net.interf.AfterRegisterParserData;
import com.qsvxin.net.interf.AfterVorderFormParserData;
import com.qsvxin.net.interf.CompleteGBorderParserData;
import com.qsvxin.net.interf.CompleteStoreOrderParserData;
import com.qsvxin.net.interf.DeleteStoreOrderParserData;
import com.qsvxin.net.interf.GetGroupBuysFormParserData;
import com.qsvxin.net.interf.GetMembersParserData;
import com.qsvxin.net.interf.GetUserMsgListParserData;
import com.qsvxin.net.interf.GetUserMsgParserData;
import com.qsvxin.net.interf.GroupBuysListParserData;
import com.qsvxin.net.interf.IdeaFeedBackParserData;
import com.qsvxin.net.interf.NewSplashImgParserData;
import com.qsvxin.net.interf.RegisterAgreementParserData;
import com.qsvxin.net.interf.SendTextMsg2UserParserData;
import com.qsvxin.net.interf.StoreOFShipmentsParserData;
import com.qsvxin.net.interf.UpdateVersionParserData;

public class ParserEngine implements Serializable {
    private static final long serialVersionUID = 1L;
    private static ParserEngine mParserEngine;

    private ParserEngine() {

    }

    public synchronized static ParserEngine getInstance() {
        if (null == mParserEngine) {
            mParserEngine = new ParserEngine();
        }
        return mParserEngine;

    }

    // ------------------------------ 微洋洋服务器全局返回码 ------------------------------
    /**
     * 请求成功后的errMsg
     */
    public static final String REQ_ERR_MSG_OK = "ok";
    /**
     * 请求成功
     */
    public static final String REQ_SUCCESS = "10000";
    /**
     * 无效用户
     */
    public static final String INVALID_USER = "10001";
    /**
     * 用户已经停用
     */
    public static final String USER_YET_STOP = "10002";
    /**
     * 仅支持用户登录
     */
    public static final String ONLY_SUPPORT_USER_LOGIN = "10003";
    /**
     * 未知的登录错误，请上报
     */
    public static final String UNKNOWN_LOGIN_ERROR = "10004";
    /**
     * 请求数据错误
     */
    public static final String REQUEST_PARAMTER_ERROR = "10005";
    /**
     * 密钥错误，获取失败
     */
    public static final String SECRET_KEY_ERROR = "20001";
    /**
     * 订单受理失败
     */
    public static final String SUPPORT_ORDER_ERROR = "20002";
    /**
     * 订单发货失败
     */
    public static final String SHIPMENTS_ORDER_ERROR = "20003";
    /**
     * 订单完成失败
     */
    public static final String ORDER_COMPLETE_ERROR = "20004";
    /**
     * 订单删除失败
     */
    public static final String ORDER_DELETE_ERROR = "20005";

    private String strErrCode = "";
    private String strErrMsg = "";

    /**
     * 解析登录数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserLogin(String strJson, AfterLoginParserData parserDataCallBack) throws ParserException,
            JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            LoginBean mLoginBean = LoginBean.getInstance();
            JSONObject mJSONObject2 = mJSONObject.getJSONObject("user");
            mLoginBean.setStrAppid(mJSONObject2.optString("appid"));
            mLoginBean.setStrAppsecret(mJSONObject2.optString("appsecret"));
            mLoginBean.setStrSecretKey(mJSONObject2.optString("secretkey"));
            mLoginBean.setStrGh(mJSONObject2.optString("gh"));
            mLoginBean.setStrNickname(mJSONObject2.optString("nickname"));
            mLoginBean.setStrToken(mJSONObject2.optString("token"));
            mLoginBean.setStrUserEmail(mJSONObject2.optString("mail"));
            mLoginBean.setStrUserPhoneNum(mJSONObject2.optString("phone"));
            mLoginBean.setStrUserRegisterTime(mJSONObject2.optString("regtime"));
            mLoginBean.setStrUserLastLoginTime(mJSONObject2.optString("lasttime"));
            mLoginBean.setStrUserQQ(mJSONObject2.optString("qq"));
            parserDataCallBack.getLoginSuccData(strErrCode, strErrMsg, mLoginBean);
        } else {
            parserDataCallBack.getLoginErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析获取新的splash图片
     * @param strJson
     * @param parserDataCallBack
     * @throws ParserException
     * @throws JSONException
     */
    public void parserNewSplashImg(String strJson, NewSplashImgParserData parserDataCallBack) throws ParserException,
            JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            NewSplashBean mNewSplashBean = new NewSplashBean();
            mNewSplashBean.setStrBeginTime(mJSONObject.optString("beginTime"));
            mNewSplashBean.setStrDescription(mJSONObject.optString("description"));
            mNewSplashBean.setStrEndTime(mJSONObject.optString("endTime"));
            mNewSplashBean.setStrImageUrl(mJSONObject.optString("imageUrl"));
            parserDataCallBack.getNewSplashImgSuccData(strErrCode, strErrMsg, mNewSplashBean);
        } else {
            parserDataCallBack.getNewSplashImgErrData(strErrCode, strErrMsg);
        }
    }
    /**
     * 解析注册协议
     * @param strJson
     * @param parserDataCallBack
     * @throws ParserException
     * @throws JSONException
     */
    public void parserRA(String strJson, RegisterAgreementParserData parserDataCallBack) throws ParserException,
    JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            RegisterAgreementBean mRegisterAgreementBean = new RegisterAgreementBean();
            mRegisterAgreementBean.setStrTitle(mJSONObject.optString("title"));
            mRegisterAgreementBean.setStrContent(mJSONObject.optString("value"));
            parserDataCallBack.getRASuccData(strErrCode, strErrMsg, mRegisterAgreementBean);
        } else {
            parserDataCallBack.getRAErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析受理成功后的数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserAcceptOF(String strJson, AfterAcceptOFParserData parserDataCallBack) throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            parserDataCallBack.getSuccData(strErrCode, strErrMsg);
        } else {
            parserDataCallBack.getErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析商场订单发货数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserStoreOrderShipments(String strJson, StoreOFShipmentsParserData parserDataCallBack)
            throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            parserDataCallBack.storeOFShipmentsSuccData(strErrCode, strErrMsg);
        } else {
            parserDataCallBack.storeOFShipmentsErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析版本升级数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    @SuppressWarnings("null")
    public void parserUpdateAppVersion(String strJson, UpdateVersionParserData parserDataCallBack) throws JSONException {
        UpdateAppVersionBean mUpdateAppVersionBean = new UpdateAppVersionBean();
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");

        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            mUpdateAppVersionBean.setNewVersion(Boolean.getBoolean(mJSONObject.optString("isNew")));
            mUpdateAppVersionBean.setStrDownLoadAppUrl(mJSONObject.optString("downloadUrl"));
            mUpdateAppVersionBean.setMustUpdate(Boolean.getBoolean(mJSONObject.optString("mustUpdate")));
            mUpdateAppVersionBean.setStrNewAppIntroduction(mJSONObject.optString("introduction"));
            mUpdateAppVersionBean.setStrAppName(mJSONObject.optString("versionName"));
            mUpdateAppVersionBean.setStrVersionUpdateTime(mJSONObject.optString("versionTime"));
            mUpdateAppVersionBean.setStrVersionSize(mJSONObject.optString("versionSize"));
            mUpdateAppVersionBean.setStrAppVersionName(mJSONObject.optString("apiVersion"));

            parserDataCallBack.getUpdateVersionSuccData(strErrCode, strErrMsg, mUpdateAppVersionBean);
        } else {
            parserDataCallBack.getUpdateVersionErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析意见反馈接口反回的数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserIdeaFeedBack(String strJson, IdeaFeedBackParserData parserDataCallBack) throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            parserDataCallBack.getIdeaFeedBackSuccData(strErrMsg, strErrMsg);
        } else {
            parserDataCallBack.getIdeaFeedBackErrData(strErrMsg, strErrMsg);
        }
    }

    /**
     * 解析删除商场订单数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserDeleteStoreOF(String strJson, DeleteStoreOrderParserData parserDataCallBack) throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            parserDataCallBack.getSuccData(strErrCode, strErrMsg);
        } else {
            parserDataCallBack.getErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析回复用户文本消息接口数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserSendTextMsg2User(String strJson, SendTextMsg2UserParserData parserDataCallBack)
            throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            parserDataCallBack.getSuccData(strErrCode, strErrMsg);
        } else {
            parserDataCallBack.getErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析完成团购订单后的数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserCompleteGBorder(String strJson, CompleteGBorderParserData parserDataCallBack)
            throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            parserDataCallBack.CompleteGBorderSuccData(strErrCode, strErrMsg);
        } else {
            parserDataCallBack.CompleteGBorderErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析完成商场订单数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserCompleteStoreOrder(String strJson, CompleteStoreOrderParserData parserDataCallBack)
            throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            parserDataCallBack.CompleteStoreOrderSuccData(strErrCode, strErrMsg);
        } else {
            parserDataCallBack.CompleteStoreOrderErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析注册接口返回的数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserRegister(String strJson, AfterRegisterParserData parserDataCallBack) throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            parserDataCallBack.getRegisterSuccData(strErrCode, strErrMsg);
        } else {
            parserDataCallBack.getRegisterErrData(strErrCode, strErrMsg);
        }
        // try{
        // strErrCode = StringUtils.substringBetween(strJson, STR_ERRCODE,
        // STR_COMMA);
        // strErrMsg = StringUtils.substringBetween(strJson, STR_ERRMSG,
        // STR_QUOTATION_MARKS);
        // if (!TextUtils.isEmpty(strErrCode) || !TextUtils.isEmpty(strErrMsg))
        // if (REQ_SUCCESS.equals(strErrCode)) {
        // parserDataCallBack.getRegisterSuccData(strErrCode, strErrMsg);
        // } else if (INVALID_USER.equals(strErrCode)) {
        // parserDataCallBack.getRegisterErrData(strErrCode, strErrMsg);
        //
        // }
        // }catch(ParseException e){
        // e.printStackTrace();
        // }
    }

    /**
     * 解析获取会员接口数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserMembers(String strJson, GetMembersParserData parserDataCallBack) throws JSONException {
        ArrayList<MemberBean> listMemberBean;
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            JSONArray jsonArray = mJSONObject.getJSONArray("data");
            listMemberBean = new ArrayList<MemberBean>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject info = jsonArray.getJSONObject(i);
                MemberBean mMemberBean = new MemberBean();
                String strMemberID = info.optString("id");
                mMemberBean.setStrMemberID(strMemberID);
                String strOpenID = info.optString("openid");
                mMemberBean.setStrOpenID(strOpenID);
                String isjoin = info.optString("isjoin");
                mMemberBean.setIsjoin(Boolean.getBoolean(isjoin));
                String isvip = info.optString("isvip");
                mMemberBean.setIsvip(Boolean.getBoolean(isvip));
                String strMemberName = info.optString("name");
                mMemberBean.setStrMemberName(strMemberName);
                String strMemberPhoneNum = info.optString("phone");
                mMemberBean.setStrMemberPhoneNum(strMemberPhoneNum);
                String strSex = info.optString("sex");
                mMemberBean.setStrSex(strSex);
                String intAge = info.optString("age");
                mMemberBean.setIntAge(Integer.parseInt(intAge));
                String strBirthday = info.optString("birthday");
                mMemberBean.setStrBirthday(strBirthday);
                String strExplain = info.optString("explain");
                mMemberBean.setStrExplain(strExplain);
                String strCardID = info.optString("cardid");
                mMemberBean.setStrCardID(strCardID);
                String intResidueIntegral = info.optString("rc");
                mMemberBean.setIntResidueIntegral(Integer.parseInt(intResidueIntegral));
                String intUsedIntegral = info.optString("uc");
                mMemberBean.setIntUsedIntegral(Integer.parseInt(intUsedIntegral));
                listMemberBean.add(mMemberBean);
            }
            parserDataCallBack.getMembersSuccData(strErrCode, strErrMsg, listMemberBean);
        } else {
            parserDataCallBack.getMembersErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析获取商城订单接口数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserVorderForm(String strJson, AfterVorderFormParserData parserDataCallBack) throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            JSONArray orderJsonArray = mJSONObject.getJSONArray("data");
            ArrayList<VorderFormBean> mArrayList = new ArrayList<VorderFormBean>();
            for (int i = 0; i < orderJsonArray.length(); i++) {
                JSONObject info = orderJsonArray.getJSONObject(i);
                VorderFormBean mVorderFormBean = new VorderFormBean();
                String strOrderFormID = info.optString("id");
                mVorderFormBean.setStrOrderFormID(strOrderFormID);
                String strOpenID = info.optString("openid");
                mVorderFormBean.setStrOpenID(strOpenID);
                String strTotalPrice = info.optString("money");
                mVorderFormBean.setStrTotalPrice(strTotalPrice);
                String strCreateTime = info.optString("creattime");
                mVorderFormBean.setStrCreateTime(strCreateTime);
                String strPayTime = info.optString("paytime");
                mVorderFormBean.setStrPayTime(strPayTime);
                String strShipmentsTime = info.optString("distrtime");
                mVorderFormBean.setStrShipmentsTime(strShipmentsTime);
                String strFinishOrderTime = info.optString("finishtime");
                mVorderFormBean.setStrFinishOrderTime(strFinishOrderTime);
                String strOrderFormStatus = info.optString("ofstatus");
                mVorderFormBean.setStrOrderFormStatus(strOrderFormStatus);
                String strOrderPayStatus = info.optString("paystatus");
                mVorderFormBean.setStrOrderFormStatus(strOrderPayStatus);
                String strShipmentsStatus = info.optString("distrstatus");
                mVorderFormBean.setStrShipmentsStatus(strShipmentsStatus);
                String strPayMode = info.optString("paymode");
                mVorderFormBean.setStrPayMode(strPayMode);
                String strBuyerLeaveMsg = info.optString("text");
                mVorderFormBean.setStrBuyerLeaveMsg(strBuyerLeaveMsg);
                String strBuyerName = info.optString("buyername");
                mVorderFormBean.setStrBuyerName(strBuyerName);
                String strBuyerPhoneNum = info.optString("buyerphone");
                mVorderFormBean.setStrBuyerPhoneNum(strBuyerPhoneNum);
                String strBuyerAddress = info.optString("buyeraddress");
                mVorderFormBean.setStrBuyerAddress(strBuyerAddress);

                ArrayList<BuyGoodsBean> buyGoodsList = new ArrayList<BuyGoodsBean>();
                JSONArray goodsJsonArray = info.getJSONArray("coms");
                for (int j = 0; j < goodsJsonArray.length(); j++) {
                    String strGoodsPrice = "";
                    String strGoodsNormsName = "";
                    BuyGoodsBean mBuyGoodsBean = new BuyGoodsBean();
                    JSONObject goodsInfo = goodsJsonArray.getJSONObject(j);
                    String strGoodsID = goodsInfo.optString("comid");
                    mBuyGoodsBean.setStrGoodsID(strGoodsID);
                    String strGoodsCount = goodsInfo.optString("count");
                    mBuyGoodsBean.setStrGoodsCount(strGoodsCount);
                    JSONArray normsJsonArray = goodsInfo.getJSONArray("norms");
                    for (int k = 0; k < normsJsonArray.length(); k++) {
                        JSONObject normsInfo = normsJsonArray.getJSONObject(k);
                        strGoodsPrice = normsInfo.optString("value");
                        strGoodsNormsName = normsInfo.optString("name");
                    }
                    mBuyGoodsBean.setStrGoodsNormsName(strGoodsNormsName);
                    mBuyGoodsBean.setStrGoodsPrice(strGoodsPrice);
                    buyGoodsList.add(mBuyGoodsBean);
                }
                mVorderFormBean.setmArrayListBuyGoods(buyGoodsList);
                mArrayList.add(mVorderFormBean);
            }
            parserDataCallBack.getVorderFormSuccData(strErrCode, strErrMsg, mArrayList);
        } else {
            parserDataCallBack.getVorderFormErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析团购列表信息
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserGroupBuysList(String strJson, GroupBuysListParserData parserDataCallBack) throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            JSONArray orderJsonArray = mJSONObject.getJSONArray("data");
            ArrayList<GroupBuysListBean> mArrayList = new ArrayList<GroupBuysListBean>();
            for (int i = 0; i < orderJsonArray.length(); i++) {
                JSONObject info = orderJsonArray.getJSONObject(i);
                GroupBuysListBean mGroupBuysListBean = new GroupBuysListBean();
                String strGroupBuysID = info.optString("id");
                mGroupBuysListBean.setStrGroupBuysID(strGroupBuysID);
                String strGroupBuysTitle = info.optString("title");
                mGroupBuysListBean.setStrGroupBuysTitle(strGroupBuysTitle);
                mArrayList.add(mGroupBuysListBean);
            }
            parserDataCallBack.getGroupBuysListSuccData(strErrCode, strErrMsg, mArrayList);
        } else {
            parserDataCallBack.getGroupBuysListErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析团购订单数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserGroupBuysForm(String strJson, GetGroupBuysFormParserData parserDataCallBack) throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            JSONArray orderJsonArray = mJSONObject.getJSONArray("data");
            ArrayList<GroupBuysFormBean> mArrayList = new ArrayList<GroupBuysFormBean>();
            for (int i = 0; i < orderJsonArray.length(); i++) {
                JSONObject info = orderJsonArray.getJSONObject(i);
                GroupBuysFormBean mGroupBuysFormBean = new GroupBuysFormBean();
                String strOrderFormID = info.optString("id");
                mGroupBuysFormBean.setStrOrderFormID(strOrderFormID);
                String strBuyerName = info.optString("name");
                mGroupBuysFormBean.setStrBuyerName(strBuyerName);
                String strBuyerPhoneNum = info.optString("phone");
                mGroupBuysFormBean.setStrBuyerPhoneNum(strBuyerPhoneNum);
                String strBuyCount = info.optString("count");
                mGroupBuysFormBean.setStrBuyCount(strBuyCount);
                String strSN = info.optString("sn");
                mGroupBuysFormBean.setStrSN(strSN);
                String strOrderFormStatus = info.optString("state");
                mGroupBuysFormBean.setStrOrderFormStatus(strOrderFormStatus);
                mArrayList.add(mGroupBuysFormBean);
            }
            parserDataCallBack.GetGroupBuysFormSuccData(strErrCode, strErrMsg, mArrayList);
        } else {
            parserDataCallBack.GetGroupBuysFormErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析获取用户列表消息数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserUserMsgList(String strJson, GetUserMsgListParserData parserDataCallBack) throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            JSONArray orderJsonArray = mJSONObject.getJSONArray("data");
            ArrayList<GetUserMsgListBean> mArrayList = new ArrayList<GetUserMsgListBean>();
            for (int i = 0; i < orderJsonArray.length(); i++) {
                JSONObject info = orderJsonArray.getJSONObject(i);
                GetUserMsgListBean mCustomerServiceMsgBean = new GetUserMsgListBean();
                String strOpenID = info.optString("openid");
                mCustomerServiceMsgBean.setStrOpenID(strOpenID);
                String strNickName = info.optString("nickname");
                mCustomerServiceMsgBean.setStrNickName(strNickName);
                String strCountry = info.optString("country");
                mCustomerServiceMsgBean.setStrCountry(strCountry);
                String strCity = info.optString("city");
                mCustomerServiceMsgBean.setStrCity(strCity);
                String strHeadUrl = info.optString("head");
                mCustomerServiceMsgBean.setStrHeadUrl(strHeadUrl);
                String strReplyMsgCount = info.optString("count");
                mCustomerServiceMsgBean.setStrReplyMsgCount(strReplyMsgCount);
                String strProvince = info.optString("province");
                mCustomerServiceMsgBean.setStrProvince(strProvince);
                mArrayList.add(mCustomerServiceMsgBean);
            }
            parserDataCallBack.getUserMsgListSuccData(strErrCode, strErrMsg, mArrayList);
        } else {
            parserDataCallBack.getUserMsgListErrData(strErrCode, strErrMsg);
        }
    }

    /**
     * 解析获取用户聊天消息数据
     * 
     * @param strJson
     * @param parserDataCallBack
     * @throws JSONException
     */
    public void parserGetUserMsg(String strJson, GetUserMsgParserData parserDataCallBack) throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            JSONArray orderJsonArray = mJSONObject.getJSONArray("data");
            ArrayList<GetUserMsgBean> mArrayList = new ArrayList<GetUserMsgBean>();
            for (int i = 0; i < orderJsonArray.length(); i++) {
                JSONObject info = orderJsonArray.getJSONObject(i);
                GetUserMsgBean mGetUserMsgBean = new GetUserMsgBean();
                String strOpenID = info.optString("openid");
                mGetUserMsgBean.setStrOpenID(strOpenID);
                String strCurrentMsgTime = info.optString("time");
                mGetUserMsgBean.setStrCurrentMsgTime(strCurrentMsgTime);
                String strIOType = info.optString("iotype");
                mGetUserMsgBean.setStrIOType(strIOType);
                String strMsgType = info.optString("msgtype");
                mGetUserMsgBean.setStrMsgType(strMsgType);
                String strMsgText = info.optString("text");
                mGetUserMsgBean.setStrMsgText(strMsgText);
                mArrayList.add(mGetUserMsgBean);
            }
            parserDataCallBack.getUserMsgSuccData(strErrCode, strErrMsg, mArrayList);
        } else {
            parserDataCallBack.getUserMsgErrData(strErrCode, strErrMsg);
        }
    }

    public void tess(String strJson) throws JSONException {
        JSONObject mJSONObject = new JSONObject(strJson);
        strErrCode = mJSONObject.optString("errcode");
        strErrMsg = mJSONObject.optString("errmsg");
        if (REQ_SUCCESS.equals(strErrCode) || REQ_ERR_MSG_OK.equals(strErrMsg)) {
            JSONArray orderJsonArray = mJSONObject.getJSONArray("data");
            ArrayList<GetUserMsgListBean> mArrayList = new ArrayList<GetUserMsgListBean>();
            for (int i = 0; i < orderJsonArray.length(); i++) {
                JSONObject info = orderJsonArray.getJSONObject(i);
                GetUserMsgListBean mCustomerServiceMsgBean = new GetUserMsgListBean();
                String strOpenID = info.optString("openid");
                mCustomerServiceMsgBean.setStrOpenID(strOpenID);
                String strNickName = info.optString("nickname");
                mCustomerServiceMsgBean.setStrNickName(strNickName);
                String strCountry = info.optString("country");
                mCustomerServiceMsgBean.setStrCountry(strCountry);
                String strCity = info.optString("city");
                mCustomerServiceMsgBean.setStrCity(strCity);
                String strHeadUrl = info.optString("head");
                mCustomerServiceMsgBean.setStrHeadUrl(strHeadUrl);
                String strReplyMsgCount = info.optString("count");
                mCustomerServiceMsgBean.setStrReplyMsgCount(strReplyMsgCount);
                String strProvince = info.optString("province");
                mCustomerServiceMsgBean.setStrProvince(strProvince);
                mArrayList.add(mCustomerServiceMsgBean);
            }
        }
    }

}
