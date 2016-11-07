package com.qsvxin.engine;

import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import android.content.Context;

import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.interf.SendTextMsg2UserParserData;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 发送文本消息给用户
 */
public class SendTextMsg2User {
    private Context mContext;
    private Map<String, String> mMap;
    private boolean isShowDialog;
    private SendTextMsg2UserParserData parserDataCallBack;

    /**
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
     * 
     * @param parserDataCallBack
     *            回调接口
     * @return 正确时返回JSON数据包： { "errcode": 10000, "errmsg": "ok" }
     * 
     *         错误时的返回JSON数据包如下（示例为用户密钥错误）： { "errcode": 20001, "errmsg":
     *         "用户密钥错误" }
     */
    public SendTextMsg2User(Context mContext, Map<String, String> mMap, SendTextMsg2UserParserData parserDataCallBack,boolean isShowDialog) {
        this.mContext = mContext;
        this.mMap = mMap;
        this.isShowDialog = isShowDialog;
        this.parserDataCallBack = parserDataCallBack;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        new SendTextMsg2UserTask(mContext, mMap, new NetDataCallBackInterf() {
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
            }

            @Override
            public void getNetData(int statusCode, String strJson) {
                if (HttpStatus.SC_OK == statusCode) {
                    try {
                        ParserEngine.getInstance().parserSendTextMsg2User(strJson, parserDataCallBack);
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

    class SendTextMsg2UserTask extends QsAsyncTask {
        private Map<String, String> mMap;
        private NetDataCallBackInterf callBackInterf;
        private Context mContext;
        private boolean isShowDialog;
        private CustomDialog mCustomDialog;

        public SendTextMsg2UserTask(Context mContext, Map<String, String> mMap, NetDataCallBackInterf callBackInterf,boolean isShowDialog) {
            this.callBackInterf = callBackInterf;
            this.mMap = mMap;
            this.isShowDialog = isShowDialog;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExectue() {
            if(isShowDialog){
                mCustomDialog = CustomDialog.createLoadingDialog(mContext, "发送中...", true);
            }
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().sendTextMessage(mMap, callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            if(isShowDialog){
                mCustomDialog.dismiss();
            }
        }

    }
}
