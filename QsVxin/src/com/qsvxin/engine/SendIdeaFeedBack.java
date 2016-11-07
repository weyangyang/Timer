package com.qsvxin.engine;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import android.content.Context;

import com.qsvxin.net.interf.IdeaFeedBackParserData;
import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 发送意见反馈信息到服务器
 */
public class SendIdeaFeedBack {
    private Context mContext;
    private String str_secretKey;
    private String str_ideaContent;
    private IdeaFeedBackParserData parserDataCallBack;

    /**
     * 
     * @param mContext
     * @param str_secretKey
     *            用户密钥
     * @param str_ideaContent
     *            意见(最大字符个数500)
     * @param parserDataCallBack
     *            IdeaFeedBackParserData接口实例
     * @return 正确时返回JSON数据包： { "errcode": 10000, "errmsg": "ok" }
     * 
     *         错误时的返回JSON数据包如下（示例为用户密钥错误）： { "errcode": 20001, "errmsg":
     *         "用户密钥错误" }
     */
    public SendIdeaFeedBack(Context mContext, String str_secretKey, String str_ideaContent,
            IdeaFeedBackParserData parserDataCallBack) {
        this.mContext = mContext;
        this.str_secretKey = str_secretKey;
        this.str_ideaContent = str_ideaContent;
        this.parserDataCallBack = parserDataCallBack;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        new SendIdeaFeedBackTask(mContext, str_secretKey, str_ideaContent, new NetDataCallBackInterf() {
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
            }

            @Override
            public void getNetData(int statusCode, String strJson) {
                if (HttpStatus.SC_OK == statusCode) {
                    try {
                        ParserEngine.getInstance().parserIdeaFeedBack(strJson, parserDataCallBack);
                    } catch (JSONException e) {
                        parserDataCallBack.getParserErrData(QsConstants.JSON_EXCEPTION, e.getMessage());
                    } catch (Exception e) {
                        parserDataCallBack.getParserErrData(QsConstants.EXEPTION, e.getMessage());

                    }
                } else {
                    parserDataCallBack.getNetErrData(statusCode, strJson);
                }

            }
        }).execute();
    }

    class SendIdeaFeedBackTask extends QsAsyncTask {
        public String str_secretKey;
        public String str_ideaContent;
        public NetDataCallBackInterf callBackInterf;
        public Context mContext;
        private CustomDialog mCustomDialog;

        public SendIdeaFeedBackTask(Context mContext, String str_secretKey, String str_ideaContent,
                NetDataCallBackInterf callBackInterf) {
            this.str_secretKey = str_secretKey;
            this.str_ideaContent = str_ideaContent;
            this.callBackInterf = callBackInterf;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExectue() {
            mCustomDialog = CustomDialog.createLoadingDialog(mContext, "正在反馈意见", true);
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().getSendIdeaFeedBackData(str_secretKey, str_ideaContent, callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            mCustomDialog.dismiss();
        }

    }
}
