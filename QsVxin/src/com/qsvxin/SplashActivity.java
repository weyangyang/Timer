package com.qsvxin;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import com.igexin.sdk.PushManager;
import com.qsvxin.bean.NewSplashBean;
import com.qsvxin.engine.GetNewSplashImg;
import com.qsvxin.net.interf.NewSplashImgParserData;
import com.qsvxin.net.utils.NetUtils;
import com.qsvxin.utils.FileMgr;
import com.qsvxin.utils.PreferenceUtils;
import com.qsvxin.utils.QsConstants;

public class SplashActivity extends BaseActivity {
	private ImageView ivSplash;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		ivSplash = (ImageView) findViewById(R.id.ivSplash);
		
		initSplash();
		checkSplashPic();
		new Handler().postDelayed(new Runnable() {
			public void run() {
			    boolean isPushTurnOn = PreferenceUtils.getPrefBoolean(SplashActivity.this, "isPushTurnOn", true);
			    if(isPushTurnOn){
                    PushManager.getInstance().turnOnPush(SplashActivity.this);
			    }else{
			        PushManager.getInstance().turnOffPush(SplashActivity.this);
			    }
				boolean isInstall = PreferenceUtils.getPrefBoolean(
						SplashActivity.this, QsConstants.SP_ISINSTALL, true);
				// TODO:目前测试用，跳过介绍页面
				boolean isLogin = PreferenceUtils.getPrefBoolean(
						SplashActivity.this, "isLogin", false);
				if (isLogin) {
					startNewActivity(MainUIActivity.class);
				} else if (isInstall) {
					startNewActivity(WelcomeActivity.class);
				} else {
					startNewActivity(GuideActivity.class);
				}
			}

			private void startNewActivity(Class mClass) {
				Intent intent = new Intent(SplashActivity.this, mClass);
				startActivity(intent);
				SplashActivity.this.finish();
			}
		}, 1000);
	}


	private void checkSplashPic() {
		new GetNewSplashImg(new NewSplashImgParserData() {

			@Override
			public void getParserErrData(int errCode, String errMsg) {
				// TODO 打印日志
			}

			@Override
			public void getNetErrData(int errCode, String errMsg) {
				// TODO 打印日志
			}

			@Override
			public void getNewSplashImgErrData(String errCode, String errMsg) {
				// TODO 打印日志
			}

			@Override
			public void getNewSplashImgSuccData(String succCode,
					String succMsg, final NewSplashBean mNewSplashBean) {
				String strDownPic = PreferenceUtils.getPrefString(SplashActivity.this, "imageUrl", "");
				if (strDownPic.equals(mNewSplashBean.getStrImageUrl())) {
					return;
				}
				File file = FileMgr.creatDir(FileMgr.LOGO_FOLDER_NAME);
				final String strPath = file.getAbsolutePath() + File.separator
						+ "logo.png";
				boolean isSucc = false;
				try {
					isSucc = NetUtils.getDownload(mNewSplashBean.getStrImageUrl(), strPath);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (isSucc){
					PreferenceUtils.setPrefString(SplashActivity.this, "beginTime", mNewSplashBean.getStrBeginTime());
					PreferenceUtils.setPrefString(SplashActivity.this, "endTime", mNewSplashBean.getStrEndTime());
					PreferenceUtils.setPrefString(SplashActivity.this, "imageUrl", mNewSplashBean.getStrImageUrl());
				}
			}

		}).excute();
	}

	
	private void initSplash() {
        String strBegin = PreferenceUtils.getPrefString(SplashActivity.this, "beginTime", "");
        String strEnd = PreferenceUtils.getPrefString(SplashActivity.this, "endTime", "");
        // 图片时间生效判断
        long timeMillis = System.currentTimeMillis();
        if (!TextUtils.isEmpty(strBegin) && !TextUtils.isEmpty(strEnd)) {
        	long longBegin = Long.valueOf(strBegin);
        	long longEnd = Long.valueOf(strEnd);
        	if (timeMillis > longBegin && timeMillis < longEnd && FileMgr.sdCardAvailable()) {
            	File file = FileMgr.creatDir(FileMgr.LOGO_FOLDER_NAME);
    			final String filePath = file.getAbsolutePath() + File.separator + "logo.png";
                Bitmap logo = BitmapFactory.decodeFile(filePath);
                if(logo!=null){
                	ivSplash.setImageBitmap(logo);
                }
            }
        }
        
	}
}
