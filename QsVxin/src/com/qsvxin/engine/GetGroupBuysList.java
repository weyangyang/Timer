package com.qsvxin.engine;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import android.content.Context;

import com.qsvxin.net.interf.GroupBuysListParserData;
import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 获取团购列表信息
 */
public class GetGroupBuysList {
    private Context mContext;
    private String str_secretKey;
    private GroupBuysListParserData parserDataCallBack;

    /**
     * @param mContext
     * @param str_secretKey
     *            用户密钥
     * @param callBackInterf
     *            回调接口
     * @return 正确时返回JSON数据包： { "errcode": 10000, "errmsg": "ok", "data": [ {
     *         "id": 1, "title": "title" } ] } 参数 说明 errcode 错误编号, errmsg 错误信息,
     *         data 列表数据, id 团购id ,title 团购标题. 错误时的返回JSON数据包如下（示例为用户密钥错误）： {
     *         "errcode": 20001, "errmsg": "用户密钥错误" }
     * @tips 跳转Activity或显示dialog请使用Activity.this.runOnUiThread(new Runnable() {
     *       public void run() {
     * 
     *       } });
     */
    public GetGroupBuysList(Context mContext, String str_secretKey, GroupBuysListParserData parserDataCallBack) {
        this.mContext = mContext;
        this.str_secretKey = str_secretKey;
        this.parserDataCallBack = parserDataCallBack;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        new GetGroupBuysListTask(mContext, str_secretKey, new NetDataCallBackInterf() {
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
            }

            @Override
            public void getNetData(int statusCode, String strJson) {
                if (HttpStatus.SC_OK == statusCode) {
                    try {
                        ParserEngine.getInstance().parserGroupBuysList(strJson, parserDataCallBack);
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

    class GetGroupBuysListTask extends QsAsyncTask {
        public String str_secretKey;
        public NetDataCallBackInterf callBackInterf;
        public Context mContext;
        private CustomDialog mCustomDialog;

        public GetGroupBuysListTask(Context mContext, String str_secretKey, NetDataCallBackInterf callBackInterf) {
            this.str_secretKey = str_secretKey;
            this.callBackInterf = callBackInterf;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExectue() {
            mCustomDialog = CustomDialog.createLoadingDialog(mContext, "获取团购列表信息", true);
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().getGroupBuysList(str_secretKey, callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            mCustomDialog.dismiss();
        }

    }
}
