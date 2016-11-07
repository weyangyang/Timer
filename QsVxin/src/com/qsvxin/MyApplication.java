package com.qsvxin;

import android.app.Application;

import com.igexin.sdk.PushManager;

/**
 * @ClassName: MyApplication
 * @Description: TODO
 * @author liang_xs
 * @date 2014-8-20
 */
public class MyApplication extends Application {

	private static MyApplication mApplication;

	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		// 个推
		PushManager.getInstance().initialize(this.getApplicationContext());
	}

	public static MyApplication getApplication() {
		return mApplication;
	}
}
