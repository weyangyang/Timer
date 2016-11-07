package com.qsvxin.engine;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.interf.NewSplashImgParserData;
import com.qsvxin.net.utils.ParserEngine;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;

/**
 * 从服务器获取新的splash图片
 */
public class GetNewSplashImg {
    private NewSplashImgParserData parserDataCallBack;

    /**
     * 
     * @param parserDataCallBack
     *            AfterAcceptOFParserData接口实例 回调接口
     * @return 正确时返回JSON数据包： { "errcode": 10000, "errmsg": "ok", "beginTime":
     *         "2014-01-01 00:00:00", "endTime": "2015-01-01 00:00:00",
     *         "description": "封面", "imageUrl":
     *         "http://www.weyangyang.com/content/templateloading.gif" } 参数 说明
     *         errcode 错误编号 errmsg 错误信息 beginTime 开始时间 endTime 结束时间 description
     *         说明 imageUrl 图片地址
     */
    public GetNewSplashImg(NewSplashImgParserData parserDataCallBack) {
        this.parserDataCallBack = parserDataCallBack;
    }

    /**
     * 执行异步任务
     */
    public void excute() {
        new NewSplashImgTask(new NetDataCallBackInterf() {
            @Override
            public void getNetErrData(int errCode, String strError) {
                parserDataCallBack.getNetErrData(errCode, strError);
            }

            @Override
            public void getNetData(int statusCode, String strJson) {
                if (HttpStatus.SC_OK == statusCode) {
                    try {
                        ParserEngine.getInstance().parserNewSplashImg(strJson, parserDataCallBack);
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

    class NewSplashImgTask extends QsAsyncTask {
        public NetDataCallBackInterf callBackInterf;

        // private CustomDialog mCustomDialog;

        public NewSplashImgTask(NetDataCallBackInterf callBackInterf) {
            this.callBackInterf = callBackInterf;
        }

        @Override
        protected void onPreExectue() {
            // mCustomDialog = CustomDialog.createLoadingDialog(mContext,
            // "正在受理订单", true);
        }

        @Override
        protected void doInbackgroud() {
            RequestEngine.getInstance().getNewSplashImg(callBackInterf);
        }

        @Override
        protected void onPostExecute() {
            // mCustomDialog.dismiss();
        }

    }
}
