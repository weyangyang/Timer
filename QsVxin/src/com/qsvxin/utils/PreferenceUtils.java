package com.qsvxin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.qsvxin.bean.LoginBean;

public class PreferenceUtils {

    public static void setLoginAfPrefPram(Context mContext, String strSecretKey, String strNickname, String strToken,
            String strAppid, String strAppsecret, String strGh,String strUserQQ,String strUserLastLoginTime
            ,String strUserRegisterTime,String strUserEmail,String strUserPhoneNum,boolean isLogin) {
        
        PreferenceUtils.setPrefString(mContext, "strSecretKey", strSecretKey);
        PreferenceUtils.setPrefString(mContext, "strNickname", strNickname);
        PreferenceUtils.setPrefString(mContext, "strToken", strToken);
        PreferenceUtils.setPrefString(mContext, "strAppid", strAppid);
        PreferenceUtils.setPrefString(mContext, "strAppsecret", strAppsecret);
        PreferenceUtils.setPrefString(mContext, "strGh", strGh);
        PreferenceUtils.setPrefBoolean(mContext, "isLogin", isLogin);

        PreferenceUtils.setPrefString(mContext, "strUserQQ", strUserQQ);
        PreferenceUtils.setPrefString(mContext, "strUserLastLoginTime", strUserLastLoginTime);
        PreferenceUtils.setPrefString(mContext, "strUserRegisterTime", strUserRegisterTime);
        PreferenceUtils.setPrefString(mContext, "strUserEmail", strUserEmail);
        PreferenceUtils.setPrefString(mContext, "strUserPhoneNum", strUserPhoneNum);
    }

    public static void getLoginLoginAfBean(Context mContext) {
        String strSecretKey = PreferenceUtils.getPrefString(mContext, "strSecretKey", "");
        String strNickname = PreferenceUtils.getPrefString(mContext, "strNickname", "");
        String strToken = PreferenceUtils.getPrefString(mContext, "strToken", "");
        String strAppid = PreferenceUtils.getPrefString(mContext, "strAppid", "");
        String strAppsecret = PreferenceUtils.getPrefString(mContext, "strAppsecret", "");
        String strGh = PreferenceUtils.getPrefString(mContext, "strGh", "");
        
        String strUserQQ = PreferenceUtils.getPrefString(mContext, "strUserQQ", "");
        String strUserLastLoginTime = PreferenceUtils.getPrefString(mContext, "strUserLastLoginTime", "");
        String strUserRegisterTime = PreferenceUtils.getPrefString(mContext, "strUserRegisterTime", "");
        String strUserEmail = PreferenceUtils.getPrefString(mContext, "strUserEmail", "");
        String strUserPhoneNum = PreferenceUtils.getPrefString(mContext, "strUserPhoneNum", "");
        
        LoginBean.getInstance().setLoginData(strSecretKey, strNickname, strToken, strAppid, strAppsecret, strGh, strUserQQ, strUserLastLoginTime, strUserRegisterTime, strUserEmail, strUserPhoneNum);
    }

    public static String getPrefString(Context context, String key, final String defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(key, defaultValue);
    }

    public static void setPrefString(Context context, final String key, final String value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putString(key, value).commit();
    }

    public static boolean getPrefBoolean(Context context, final String key, final boolean defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    public static boolean hasKey(Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(key);
    }

    public static void setPrefBoolean(Context context, final String key, final boolean value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putBoolean(key, value).commit();
    }

    public static void setPrefInt(Context context, final String key, final int value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putInt(key, value).commit();
    }

    public static int getPrefInt(Context context, final String key, final int defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }

    public static void setPrefFloat(Context context, final String key, final float value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putFloat(key, value).commit();
    }

    public static float getPrefFloat(Context context, final String key, final float defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getFloat(key, defaultValue);
    }

    public static void setPrefLong(Context context, final String key, final long value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putLong(key, value).commit();
    }

    public static long getPrefLong(Context context, final String key, final long defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getLong(key, defaultValue);
    }

    public static void clearPreference(Context context, final SharedPreferences p) {
        final Editor editor = p.edit();
        editor.clear();
        editor.commit();
    }
}
