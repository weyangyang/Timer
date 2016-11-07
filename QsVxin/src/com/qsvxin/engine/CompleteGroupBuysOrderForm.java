package com.qsvxin.engine;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import android.content.Context;

import com.qsvxin.net.interf.CompleteGBorderParserData;
import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 完成团购订单
 */
public class CompleteGroupBuysOrderForm {
    private Context mContext;
    private String str_secretKey;
    private String str_groupBuysID;
    private CompleteGBorderParserData parserDataCallBack;

    /**
     * 
     * @param mContext
     * @param str_secretKey
     *            用户密钥
     * @param str_orderId
     *            团购id
     * @param callBackInterf
     *            回调接口
     * @return 正确时返回JSON数据包： { "errcode": 10000, "errmsg": "ok" }
     *         错误时的返回JSON数据包如下（示例为订单完成失败）： { "errcode": 20004, "errmsg":
     *         "订单完成失败" }
     */
    public CompleteGroupBuysOrderForm(Context mContext, String str_secretKey, String str_groupBuysID,
            CompleteGBorderParserData callBackInterf) {
        this.mContext = mContext;
        this.str_secretKey = str_secretKey;
        this.str_groupBuysID = str_groupBuysID;
        this.parserDataCallBack = callBackInterf;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        new CompleteTask(mContext, str_secretKey, str_groupBuysID, new NetDataCallBackInterf() {
            
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
            }
            
            @Override
            public void getNetData(int statusCode, String strJson) {
                if(HttpStatus.SC_OK==statusCode){
                    try {
                        ParserEngine.getInstance().parserCompleteGBorder(strJson, parserDataCallBack);
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

    class CompleteTask extends QsAsyncTask {
        public String str_secretKey;
        public String strGroupBuysID;
        public NetDataCallBackInterf callBackInterf;
        public Context mContext;
        private CustomDialog mCustomDialog;

        public CompleteTask(Context mContext, String str_secretKey, String strGroupBuysID,
                NetDataCallBackInterf callBackInterf) {
            this.str_secretKey = str_secretKey;
            this.strGroupBuysID = strGroupBuysID;
            this.callBackInterf = callBackInterf;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExectue() {
            mCustomDialog = CustomDialog.createLoadingDialog(mContext, "完成团购订单", true);
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().completeGroupBuysOrder(str_secretKey, strGroupBuysID, callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            mCustomDialog.dismiss();
        }

    }
}
