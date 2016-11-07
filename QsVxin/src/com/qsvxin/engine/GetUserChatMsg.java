package com.qsvxin.engine;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import android.content.Context;
import com.qsvxin.net.interf.GetUserMsgParserData;
import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 获取用户聊天消息
 */
public class GetUserChatMsg {
    private Context mContext;
    private String str_secretKey;
    private String str_userOpenID;
    private boolean isShowDialog;
    private GetUserMsgParserData parserDataCallBack;

    /**
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
    public GetUserChatMsg(Context mContext, String str_secretKey, String str_userOpenID,
            GetUserMsgParserData parserDataCallBack,boolean isShowDialog) {
        this.str_secretKey = str_secretKey;
        this.str_userOpenID = str_userOpenID;
        this.isShowDialog = isShowDialog;
        this.mContext = mContext;
        this.parserDataCallBack = parserDataCallBack;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        new GetUserMsgTask(mContext, str_secretKey, str_userOpenID, new NetDataCallBackInterf() {
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
            }

            @Override
            public void getNetData(int statusCode, String strJson) {
                if (HttpStatus.SC_OK == statusCode) {
                    try {
                        ParserEngine.getInstance().parserGetUserMsg(strJson, parserDataCallBack);
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

    class GetUserMsgTask extends QsAsyncTask {
        private NetDataCallBackInterf callBackInterf;
        private String str_secretKey;
        private String str_userOpenID;
        private Context mContext;
        private boolean isShowDialog;
        private CustomDialog mCustomDialog;

        public GetUserMsgTask(Context mContext, String str_secretKey, String str_userOpenID,
                NetDataCallBackInterf callBackInterf,boolean isShowDialog) {
            this.callBackInterf = callBackInterf;
            this.str_secretKey = str_secretKey;
            this.str_userOpenID = str_userOpenID;
            this.isShowDialog = isShowDialog;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExectue() {
            if(isShowDialog){
                mCustomDialog = CustomDialog.createLoadingDialog(mContext, "正在获取信息", true);
            }
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().getUserMessage(str_secretKey, str_userOpenID, callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            if(isShowDialog){
                mCustomDialog.dismiss();
            }
        }

    }
}
