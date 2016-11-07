package com.qsvxin.utils;

import android.app.Application;

public class BaseApplication extends Application {
	private static BaseApplication mInstance;
	private String accessToken, refreshToken;
	private String phoneNumber;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		
//		initPrefs();
//		Intent servicIntent = new Intent(this, CommonService.class);
//		startService(servicIntent);
	}
	
//	private void initPrefs() {
//		accessToken = CommonPref.getString(CommonConstants.ACCESS_TOKEN, "");
//		refreshToken = CommonPref.getString(CommonConstants.REFRESH_TOKEN, "");
//		phoneNumber = CommonPref.getString(CommonConstants.PHONE_NUMBER, "");
//	}

	public static BaseApplication getApplication() {
		return mInstance;
	}

	public String getAccessToken() {
		return accessToken;
	}

//	public void setAccessToken(String accessToken) {
//		CommonPref.setString(CommonConstants.ACCESS_TOKEN, accessToken);
//		this.accessToken = accessToken;
//	}
//
//	public void setRefreshToken(String refreshToken) {
//		CommonPref.setString(CommonConstants.REFRESH_TOKEN, refreshToken);
//		this.refreshToken = refreshToken;
//	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

//	public void setPhoneNumber(String phoneNumber) {
//		CommonPref.setString(CommonConstants.PHONE_NUMBER, phoneNumber);
//		this.phoneNumber = phoneNumber;
//	}
}
