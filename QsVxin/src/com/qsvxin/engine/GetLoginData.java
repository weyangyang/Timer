package com.qsvxin.engine;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import android.content.Context;

import com.qsvxin.net.interf.AfterLoginParserData;
import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 获取登录数据
 * 
 */
public class GetLoginData {
    private Context mContext;
    private Map<String,String>mMap;
    private boolean isShowDialog;
    private AfterLoginParserData parserDataCallBack;

    /**
     * 
     * @param mContext
     * 
     * @param str_userName
     *            map参数 用户名称
     * @param str_passwd
     *            map参数 用户密码（md5 32位 小写）
     * @param str_clientID
     *            map参数 个推ClientID
     * @param parserDataCallBack
     * @return 正确时返回JSON数据包： { "errcode": 10000, "errmsg": "ok", "user": {
     *         "secretkey": "secretkey", "nickname": "nickname", "token":
     *         "token", "appid": "appid", "appsecret": "appsecret", "gh": "gh",
     *         “phone”:”130123456”, “mail”:”1@1.1”, “qq”:”123456”, “regtime”:”
     *         2014-09-26 00:00:00”, “lasttime”:” 2014-09-26 00:00:00” } }
     * 
     *         参数 说明 errcode 错误编号 errmsg 错误信息 user 用户信息 secretkey 用于获取数据的用户密钥
     *         nickname 用户昵称 token 公众平台的token appid 公众平台的appid appsecret
     *         公众平台的appsecret gh 公众平台的原始id phone 用户电话 mail 用户注册时用的邮箱 qq Qq号
     *         regtime 注册时间 lasttime 最后一次登录时间（上一次） 错误时的返回JSON数据包如下（示例为无效用户）： {
     *         "errcode": 10001, "errmsg": "no user" }
     */
    public GetLoginData(Context mContext, Map<String,String>mMap,
            AfterLoginParserData parserDataCallBack,boolean isShowDialog) {
        this.mContext = mContext;
        this.mMap = mMap;
        this.isShowDialog= isShowDialog;
        this.parserDataCallBack = parserDataCallBack;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        new LoginTask(mContext, mMap, new NetDataCallBackInterf() {

            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);

            }

            @Override
            public void getNetData(int statusCode, String strJson) {
                if (HttpStatus.SC_OK == statusCode) {
                    try {
                        ParserEngine.getInstance().parserLogin(strJson, parserDataCallBack);
                    } catch (JSONException e) {
                        parserDataCallBack.getParserErrData(QsConstants.JSON_EXCEPTION, e.getMessage());
                    } catch (Exception e) {
                        parserDataCallBack.getParserErrData(QsConstants.EXEPTION, e.getMessage());
                    }
                } else {
                    parserDataCallBack.getNetErrData(statusCode, strJson);
                }
            }
        },isShowDialog).execute();
    }

    class LoginTask extends QsAsyncTask {
        private Map<String, String> mMap;
        public NetDataCallBackInterf callBackInterf;
        public Context mContext;
        private boolean isShowDialog;
        private CustomDialog mCustomDialog;

        public LoginTask(Context mContext, Map<String, String> mMap, NetDataCallBackInterf callBackInterf,boolean isShowDialog) {
            this.mMap = mMap;
            this.callBackInterf = callBackInterf;
            this.isShowDialog = isShowDialog;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExectue() {
            if(isShowDialog){
                mCustomDialog = CustomDialog.createLoadingDialog(mContext, "正在登录", true);
            }
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().login(mMap, callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            if(isShowDialog){
                mCustomDialog.dismiss();
            }
        }

    }
}
