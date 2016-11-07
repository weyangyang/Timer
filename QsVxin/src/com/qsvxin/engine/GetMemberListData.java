package com.qsvxin.engine;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import android.content.Context;

import com.qsvxin.net.interf.GetMembersParserData;
import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 获取会员列表信息
 */
public class GetMemberListData {
    private Context mContext;
    private String str_secretKey;
    private boolean isShowDialog;
    private GetMembersParserData parserDataCallBack;

    /**
     * 
     * @param mContext
     * @param str_secretKey
     *            用户密钥
     * @param parserDataCallBack
     *            GetMembersParserData接口实例 回调接口
     * @return 正确时返回JSON数据包： { "errcode": 10000, "errmsg": "ok", "data": [ {
     *         "id": 1, "openid": "openid", "isjoin": true, "isvip": true,
     *         "name": "name", "phone": "phone", "sex": "男", "age": 27,
     *         "birthday": "1900-01-01", "explain": "这个人很懒", "cardid": 1000000,
     *         "rc": 0, "uc": 0 } ] } 参数 说明 errcode 错误编号 errmsg 错误信息 data 列表数据
     *         id id opened opened isjoin 是否关注(true,false) isvip
     *         是否加入会员(true,false) name 名字 phone 电话 sex 性别 age 年龄 birthday 生日
     *         explain 个人说明 cardid 会员卡号（非会员为0） rc 剩余积分 uc 已使用积分
     *         错误时的返回JSON数据包如下（示例为用户密钥错误）： { "errcode": 20001, "errmsg":
     *         "用户密钥错误" }
     */
    public GetMemberListData(Context mContext, String str_secretKey, GetMembersParserData parserDataCallBack,boolean isShowDialog) {
        this.mContext = mContext;
        this.str_secretKey = str_secretKey;
        this.isShowDialog = isShowDialog;
        this.parserDataCallBack = parserDataCallBack;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        new GetMemberTask(mContext, str_secretKey, new NetDataCallBackInterf() {
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
            }

            @Override
            public void getNetData(int statusCode, String strJson) {
                if (HttpStatus.SC_OK == statusCode) {
                    try {
                        ParserEngine.getInstance().parserMembers(strJson, parserDataCallBack);
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

    class GetMemberTask extends QsAsyncTask {
        private String str_secretKey;
        private NetDataCallBackInterf callBackInterf;
        private Context mContext;
        private boolean isShowDialog;
        private CustomDialog mCustomDialog;

        public GetMemberTask(Context mContext, String str_secretKey, NetDataCallBackInterf callBackInterf,boolean isShowDialog) {
            this.str_secretKey = str_secretKey;
            this.callBackInterf = callBackInterf;
            this.isShowDialog = isShowDialog;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExectue() {
            if(isShowDialog){
                mCustomDialog = CustomDialog.createLoadingDialog(mContext, "正在获取会员", true);
            }
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().getMemberInfo(str_secretKey, callBackInterf);

        }

        @Override
        protected void onPostExecute() {
            if(isShowDialog){
                mCustomDialog.dismiss();
            }
        }

    }
}
