package com.qsvxin;

import android.app.Activity;
import android.os.Bundle;

import com.qsvxin.utils.ActivityManager;
import com.qsvxin.utils.CommonUtils;
import com.qsvxin.utils.CrashHandler;

public class BaseActivity extends Activity {
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CrashHandler.getInstance().init(getApplicationContext(), this);
		ActivityManager.add(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityManager.remove(this);
	}
	
	protected void toast(String msg) {
		CommonUtils.toast(this, msg);
	}
	
	protected void toast(int resId) {
		toast(getString(resId));
	}
	
}
