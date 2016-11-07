package com.qsvxin.engine;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import android.content.Context;

import com.qsvxin.net.interf.GetGroupBuysFormParserData;
import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 获取团购订单
 */
public class GetGroupBuysOrderForm {
    private Context mContext;
    private String str_secretKey;
    private String str_groupBuysID;
    private GetGroupBuysFormParserData parserDataCallBack;

    /**
     * 
     * @param mContext
     * @param str_secretKey
     *            用户密钥
     * @param str_orderId
     *            团购id
     * @param callBackInterf GetGroupBuysFormParserData 接口实例
     *            回调接口
     * @return 正确时返回JSON数据包： { "errcode": 10000, "errmsg": "ok", "data": [ {
     *         "id": 1, "name": "name", "phone": "phone", "count": 1, "sn":
     *         "sn", "state": 1 } ] } 参数 说明 errcode 错误编号 errmsg 错误信息 data 列表数据
     *         id 订单id name 买家名字 phone 买家电话 count 购买数量 sn SN验证码 state 订单状态( 1=下单
     *         2=完成) 错误时的返回JSON数据包如下（示例为用户密钥错误）： { "errcode": 20001, "errmsg":
     *         "用户密钥错误" }
     */
    public GetGroupBuysOrderForm(Context mContext, String str_secretKey, String str_groupBuysID,
            GetGroupBuysFormParserData callBackInterf) {
        this.mContext = mContext;
        this.str_secretKey = str_secretKey;
        this.str_groupBuysID = str_groupBuysID;
        this.parserDataCallBack = callBackInterf;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        new GetGroupBuysOrderFormTask(mContext, str_secretKey, str_groupBuysID, new NetDataCallBackInterf() {
            
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
            }
            
            @Override
            public void getNetData(int statusCode, String strJson) {
                if(HttpStatus.SC_OK==statusCode){
                    try {
                        ParserEngine.getInstance().parserGroupBuysForm(strJson, parserDataCallBack);
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

    class GetGroupBuysOrderFormTask extends QsAsyncTask {
        public String str_secretKey;
        public String strGroupBuysID;
        public NetDataCallBackInterf callBackInterf;
        public Context mContext;
        private CustomDialog mCustomDialog;

        public GetGroupBuysOrderFormTask(Context mContext, String str_secretKey, String strGroupBuysID,
                NetDataCallBackInterf callBackInterf) {
            this.str_secretKey = str_secretKey;
            this.strGroupBuysID = strGroupBuysID;
            this.callBackInterf = callBackInterf;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExectue() {
            mCustomDialog = CustomDialog.createLoadingDialog(mContext, "正在获取团购订单", true);
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().getGroupBuysOrder(str_secretKey, strGroupBuysID, callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            mCustomDialog.dismiss();
        }

    }
}
