package com.qsvxin.engine;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import android.content.Context;

import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.interf.UpdateVersionParserData;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 更新app版本
 */
public class UpdateAppVersion {
    private Context mContext;
    private String strClientHashs;
    private String strVersion;
    private String strType;
    private boolean isShowDialog;
    private Map<String, String> mMap;
    private UpdateVersionParserData parserDataCallBack;

    /**
     * 
     * @param mContext
     * @param strClientHashs
     *            客户端软件hash值(标示软件名称)
     * @param strVersion
     *            客户端版本号
     * @param strType
     *            版本类型(1：android 2:ios)
     * @param parserDataCallBack
     * @param isShowDialog
     *            是否显示对话框
     * @return 正确时返回JSON数据包： { "errcode": 10000, "errmsg": "ok", "isNew": false,
     *         "downloadUrl": "", "introduction": "安装包介绍", "mustUpdate": false,
     *         "versionName": "版本全名", "apiVersion": "1.0", "versionSize":
     *         "最新版本大小", "versionTime": "2014-09-23 00:00:00" }
     * 
     *         参数 说明 errcode 错误编号 errmsg 错误信息 isNew
     *         boolean,客户端是否是最新版本(为false将返回最新版本信息) downloadUrl 最新版本下载地址
     *         introduction 安装包介绍 mustUpdate boolean,是否强制更新 versionName 版本全名称
     *         apiVersion 最新版本号 versionSize 最新版本大小 versionTime 最新版本更新时间
     * 
     *         错误时的返回JSON数据包如下（示例为请求数据错误）： { "errcode": 10005, "errmsg":
     *         "请求数据错误" }
     */
    public UpdateAppVersion(Context mContext, String strClientHashs, String strVersion, String strType,
            UpdateVersionParserData parserDataCallBack, boolean isShowDialog) {
        this.mContext = mContext;
        this.strClientHashs = strClientHashs;
        this.strVersion = strVersion;
        this.strType = strType;
        this.isShowDialog = isShowDialog;
        this.parserDataCallBack = parserDataCallBack;

    }

    /**
     * 执行异步任务
     */
    public void excute() {
        mMap = new HashMap<String, String>();
        mMap.put("str_clientHash", strClientHashs);
        mMap.put("strVersion", strVersion);
        mMap.put("strAppType", strType);
        new UpdateAppVersionTask(mContext, mMap, new NetDataCallBackInterf() {
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
            }

            @Override
            public void getNetData(int statusCode, String strJson) {
                if (HttpStatus.SC_OK == statusCode) {
                    try {
                        ParserEngine.getInstance().parserUpdateAppVersion(strJson, parserDataCallBack);
                    } catch (JSONException e) {
                        parserDataCallBack.getParserErrData(QsConstants.JSON_EXCEPTION, e.getMessage());
                    } catch (Exception e) {
                        parserDataCallBack.getParserErrData(QsConstants.EXEPTION, e.getMessage());

                    }
                } else {
                    parserDataCallBack.getNetErrData(statusCode, strJson);
                }

            }
        }, isShowDialog).execute();
    }

    class UpdateAppVersionTask extends QsAsyncTask {
        public Map<String, String> mMap;
        private boolean isShowDialog;
        private Context mContext;
        private CustomDialog mCustomDialog;
        private NetDataCallBackInterf callBackInterf;

        public UpdateAppVersionTask(Context mContext, Map<String, String> mMap, NetDataCallBackInterf callBackInterf,
                boolean isShowDialog) {
            this.callBackInterf = callBackInterf;
            this.mContext = mContext;
            this.mMap = mMap;
            this.isShowDialog = isShowDialog;
        }

        @Override
        protected void onPreExectue() {
            if(isShowDialog){
                mCustomDialog = CustomDialog.createLoadingDialog(mContext, "正在检测版本", true);
            }
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().updateVersion(mMap, callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            if(isShowDialog){
                mCustomDialog.dismiss();
            }
        }

    }
}
