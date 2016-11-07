package com.qsvxin.engine;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import android.content.Context;

import com.qsvxin.net.interf.AfterAcceptOFParserData;
import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 受理商城订单
 */
public class AcceptOrderForm {
    private Context mContext;
    private String str_secretKey;
    private String str_orderId;
    private AfterAcceptOFParserData parserDataCallBack;

    /**
     * 
     * @param mContext
     * @param str_secretKey
     *            用户密钥
     * @param str_orderId
     *            订单id
     * @param parserDataCallBack  AfterAcceptOFParserData接口实例
     *            回调接口
     * @return 正确时返回JSON数据包： { "errcode": 10000, "errmsg": "ok" }
     * 
     *         错误时的返回JSON数据包如下（示例为用户密钥错误）： { "errcode": 20001, "errmsg":
     *         "用户密钥错误" }
     */
    public AcceptOrderForm(Context mContext, String str_secretKey, String str_orderId,
            AfterAcceptOFParserData parserDataCallBack) {
        this.mContext = mContext;
        this.str_secretKey = str_secretKey;
        this.str_orderId = str_orderId;
        this.parserDataCallBack = parserDataCallBack;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        new AcceptOrderFormTask(mContext, str_secretKey, str_orderId, new NetDataCallBackInterf() {
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
            }
            @Override
            public void getNetData(int statusCode, String strJson) {
                if(HttpStatus.SC_OK==statusCode){
                    try {
                        ParserEngine.getInstance().parserAcceptOF(strJson, parserDataCallBack);
                    } catch (JSONException e) {
                        parserDataCallBack.getParserErrData(QsConstants.JSON_EXCEPTION, e.getMessage());
                    }catch(Exception e){
                        parserDataCallBack.getParserErrData(QsConstants.EXEPTION, e.getMessage());
                        
                    }
                }else{
                    parserDataCallBack.getNetErrData(statusCode, strJson);
                }
                
            }
        }).execute();
    }

    class AcceptOrderFormTask extends QsAsyncTask {
        public String str_secretKey;
        public String str_orderId;
        public NetDataCallBackInterf callBackInterf;
        public Context mContext;
        private CustomDialog mCustomDialog;

        public AcceptOrderFormTask(Context mContext, String str_secretKey, String str_orderId,
                NetDataCallBackInterf callBackInterf) {
            this.str_secretKey = str_secretKey;
            this.str_orderId = str_orderId;
            this.callBackInterf = callBackInterf;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExectue() {
            mCustomDialog = CustomDialog.createLoadingDialog(mContext, "正在受理订单", true);
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().acceptStoreOrder(str_secretKey, str_orderId, callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            mCustomDialog.dismiss();
        }

    }
}
