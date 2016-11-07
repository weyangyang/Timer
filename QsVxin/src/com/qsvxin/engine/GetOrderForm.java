package com.qsvxin.engine;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import android.content.Context;

import com.qsvxin.net.interf.AfterVorderFormParserData;
import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 获取商城订单
 */
public class GetOrderForm {
    private Context mContext;
    private String str_secretKey;
    private AfterVorderFormParserData parserDataCallBack;

    /**
     * 
     * @param mContext
     * @param str_secretKey
     *            用户密钥
     * @param str_orderId
     *            订单id
     * @param parserDataCallBack
     *            AfterVorderFormParserData接口实例 回调接口
     * @return 正确时返回JSON数据包： { "errcode": 99999, "errmsg": "test msg", "data": [
     *         { "id": 1, "openid": "openid", "money": 10000, "creattime":
     *         "1900-01-01 00:00:00", "paytime": "1900-01-01 00:00:00",
     *         "distrtime": "1900-01-01 00:00:00", "finishtime":
     *         "1900-01-01 00:00:00", "ofstatus": 1, "paystatus": 1,
     *         "distrstatus": 1, "paymode": 1, "text": "请尽快发货", "buyername":
     *         "buyername", "buyerphone": "buyerphone", "buyeraddress":
     *         "buyeraddress", "coms": [ { "comid": 1, "norms": [ { "name":
     *         "黑色", "value": 0 } ], "count": 1 } ] } ] } 参数 说明 errcode 错误编号
     *         errmsg 错误信息 data 列表数据 id 订单id openid opened money 总价 creattime
     *         创建时间 paytime 支付时间 distrtime 发货时间 finishtime 完成时间 ofstatus 订单状态
     *         创建=1, 付款=2, 退款=3, 发货=4, 退货=5, 收货=6, 换货=7, 作废=8, 完成=9, 受理=10
     *         paystatus 支付状态 已付款=1 未付款=2 distrstatus 发货状态 已发货=1 未发货=2 paymode
     *         支付方式 货到付款=1 text 买家留言 buyername 买家姓名 buyerphone 买家电话 buyeraddress
     *         买家地址 coms 列表数据（订单商品） comid 商品id norms 商品规格 name 规格名称 value 价格偏移
     *         count 商品数量 错误时的返回JSON数据包如下（示例为用户密钥错误）： { "errcode": 20001,
     *         "errmsg": "用户密钥错误" }
     */
    public GetOrderForm(Context mContext, String str_secretKey,
            AfterVorderFormParserData parserDataCallBack) {
        this.mContext = mContext;
        this.str_secretKey = str_secretKey;
        this.parserDataCallBack = parserDataCallBack;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        new GetOrderFormTask(mContext, str_secretKey,new NetDataCallBackInterf() {
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
            }

            @Override
            public void getNetData(int statusCode, String strJson) {
                if (HttpStatus.SC_OK == statusCode) {
                    try {
                        ParserEngine.getInstance().parserVorderForm(strJson, parserDataCallBack);
                    } catch (JSONException e) {
                        parserDataCallBack.getParserErrData(QsConstants.JSON_EXCEPTION, e.getMessage());
                    } catch (Exception e) {
                        parserDataCallBack.getParserErrData(QsConstants.EXEPTION, e.getMessage());
                    }
                }

            }
        }).execute();
    }

    class GetOrderFormTask extends QsAsyncTask {
        public String str_secretKey;
        public NetDataCallBackInterf callBackInterf;
        public Context mContext;
        private CustomDialog mCustomDialog;

        public GetOrderFormTask(Context mContext, String str_secretKey,
                NetDataCallBackInterf callBackInterf) {
            this.str_secretKey = str_secretKey;
            this.callBackInterf = callBackInterf;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExectue() {
            mCustomDialog = CustomDialog.createLoadingDialog(mContext, "正在获取订单信息", false);
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().getStoreOrder(str_secretKey, callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            mCustomDialog.dismiss();
        }

    }
}
