package com.qsvxin.engine;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import android.content.Context;

import com.qsvxin.net.interf.GetUserMsgListParserData;
import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 获取用户消息列表信息
 * 
 */
public class GetUserMsgList {
    private Context mContext;
    private String str_secretKey;
    private boolean isShowDialog;
    private GetUserMsgListParserData parserDataCallBack;

    /**
     * @param mContext
     * @param str_secretKey
     *            用户密钥
     * @param callBackInterf
     *            回调接口
     * @return 正确时返回JSON数据包： { "errcode": 10000, "errmsg": "ok", "data": [ {
     *         "openid": "oPW6BuPM3nVkEpQkbxDUP081V7nM", "nickname": "信仰",
     *         "country": "中国", "province": "辽宁", "city": "沈阳", "head":
     *         "http://wx.qlogo.cn/mmopen/Q3auHgzwzM6Fs5iauL0vCcPbXZOUXGQhvR0nKlsH4rL8aUpvK4GCYnyNlgkuPicSXPA7cUCyZ21EbjUkPACGfTNw/0"
     *         , "count": 0 } ] } 参数 说明 errcode 错误编号 errmsg 错误信息 data 用户信息列表
     *         openid 公众平台openid nickname 用户昵称 country 国家 province 省份 city 城市
     *         head 头像（/0结尾=640*640 ，/64结尾=64*64） count 未回复消息数
     *         错误时的返回JSON数据包如下（示例为用户密钥错误）： { "errcode": 20001, "errmsg":
     *         "用户密钥错误" }
     */
    public GetUserMsgList(Context mContext, String str_secretKey,
            GetUserMsgListParserData parserDataCallBack,boolean isShowDialog) {
        this.mContext = mContext;
        this.isShowDialog = isShowDialog;
        this.str_secretKey = str_secretKey;
        this.parserDataCallBack = parserDataCallBack;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        new CustomerMsgTask(mContext, str_secretKey, new NetDataCallBackInterf() {
            
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
            }
            
            @Override
            public void getNetData(int statusCode, String strJson) {
                if(HttpStatus.SC_OK==statusCode){
                    try {
                        ParserEngine.getInstance().parserUserMsgList(strJson, parserDataCallBack);
                    } catch (JSONException e) {
                        parserDataCallBack.getParserErrData(QsConstants.JSON_EXCEPTION, e.getMessage());
                    }catch(Exception e){
                        parserDataCallBack.getParserErrData(QsConstants.EXEPTION, e.getMessage());
                        
                    }
                }else{
                    parserDataCallBack.getNetErrData(statusCode, strJson);
                }
            }
        },isShowDialog).execute();
    }

    class CustomerMsgTask extends QsAsyncTask {
        private String str_secretKey;
        private NetDataCallBackInterf callBackInterf;
        private Context mContext;
        private boolean isShowDialog;
        private CustomDialog mCustomDialog;
        public CustomerMsgTask(Context mContext, String str_secretKey, 
                NetDataCallBackInterf callBackInterf,boolean isShowDialog) {
            this.str_secretKey = str_secretKey;
            this.callBackInterf = callBackInterf;
            this.isShowDialog = isShowDialog;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExectue() {
            if(isShowDialog){
                mCustomDialog = CustomDialog.createLoadingDialog(mContext, "正在获取数据", true);
            }
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().getServiceMessage(str_secretKey, callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            if(isShowDialog){
                mCustomDialog.dismiss();
            }
        }

    }
}
