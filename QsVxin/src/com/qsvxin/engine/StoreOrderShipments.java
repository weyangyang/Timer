package com.qsvxin.engine;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import android.content.Context;

import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.interf.StoreOFShipmentsParserData;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 商城订单发货
 * 
 */
public class StoreOrderShipments {
    private Context mContext;
    private String str_secretKey;
    private String str_orderId;
    private String strExpressNum;
    private StoreOFShipmentsParserData parserDataCallBack;

    /**
     * @param mContext
     * @param str_secretKey
     *            用户密钥
     * @param str_orderId
     *            订单ID
     * @param callBackInterf
     *            回调接口
     * @param strExpressNum
     *            快递单号
     * @return 正确时返回JSON数据包： { "errcode": 10000, "errmsg": "ok" }
     * 
     *         错误时的返回JSON数据包如下（示例为用户密钥错误）： { "errcode": 20001, "errmsg":
     *         "用户密钥错误" }
     */
    public StoreOrderShipments(Context mContext, String strExpressNum, String str_secretKey, String str_orderId,
            StoreOFShipmentsParserData parserDataCallBack) {
        this.mContext = mContext;
        this.str_secretKey = str_secretKey;
        this.str_orderId = str_orderId;
        this.strExpressNum = strExpressNum;
        this.parserDataCallBack = parserDataCallBack;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        Map<String, String> mMap = new HashMap<String, String>();
        mMap.put("str_secretKey", str_secretKey);
        mMap.put("str_orderId", str_orderId);
        mMap.put("str_expressNum", strExpressNum);
        new StoreOrderShipmentsTask(mContext, mMap, new NetDataCallBackInterf() {
            
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
                
            }
            
            @Override
            public void getNetData(int statusCode, String strJson) {
                if(HttpStatus.SC_OK==statusCode){
                    try {
                        ParserEngine.getInstance().parserStoreOrderShipments(strJson, parserDataCallBack);
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

    class StoreOrderShipmentsTask extends QsAsyncTask {
        private Map<String, String> mMap;
        public NetDataCallBackInterf callBackInterf;
        public Context mContext;
        private CustomDialog mCustomDialog;

        public StoreOrderShipmentsTask(Context mContext, Map<String, String> mMap, NetDataCallBackInterf callBackInterf) {
            this.mMap = mMap;
            this.callBackInterf = callBackInterf;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExectue() {
            mCustomDialog = CustomDialog.createLoadingDialog(mContext, "正在发货", true);
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().storeOrderShipments(mMap, callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            mCustomDialog.dismiss();
        }

    }
}
