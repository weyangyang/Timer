package com.qsvxin.engine;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import android.content.Context;

import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.interf.RegisterAgreementParserData;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 从服务器获取注册协议
 */
public class GetRegisterAgreement {
    private RegisterAgreementParserData parserDataCallBack;
    private Context mContext;

    /**
     * 
     * @param parserDataCallBack
     *            AfterAcceptOFParserData接口实例 回调接口
     * @return正确时返回JSON数据包： { "errcode": 10000, "errmsg": "ok", “title”:”服务条款”,
     *                      “value”,”……..” } 参数 说明 errcode 错误编号 errmsg 错误信息
     *                      title 标题 value 内容（unicode符号）
     */
    public GetRegisterAgreement(Context mContext, RegisterAgreementParserData parserDataCallBack) {
        this.mContext = mContext;
        this.parserDataCallBack = parserDataCallBack;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        new GetRATask(mContext, new NetDataCallBackInterf() {
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
            }

            @Override
            public void getNetData(int statusCode, String strJson) {
                if (HttpStatus.SC_OK == statusCode) {
                    try {
                        ParserEngine.getInstance().parserRA(strJson, parserDataCallBack);
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

    class GetRATask extends QsAsyncTask {
        private NetDataCallBackInterf callBackInterf;
        private Context mContext;
        private CustomDialog mCustomDialog;

        public GetRATask(Context mContext, NetDataCallBackInterf callBackInterf) {
            this.callBackInterf = callBackInterf;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExectue() {
            mCustomDialog = CustomDialog.createLoadingDialog(mContext, "正在获取协议", true);
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().getRegisterAgreement(callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            mCustomDialog.dismiss();
        }

    }
}
