package com.qsvxin.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.qsvxin.MyApplication;

public class CrashHandler implements UncaughtExceptionHandler {
	public static final String TAG = "CrashHandler";

	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler实例
	private static CrashHandler INSTANCE = new CrashHandler();
	// 程序的Context对象
	private Context mContext;
	private Activity mActivity;
	// 用来存储设备信息和异常信息
//	private Map<String, String> infos = new HashMap<String, String>();
//	private Map<String, String> info = new HashMap<String, String>();
	private DateFormat currTimeFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	private Map<String, String> mapPhoneInfo;

	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context, Activity activity) {
		mActivity = activity;
		mContext = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}
			ActivityManager.finishAllActivities();
			mActivity.finish();
			// 退出程序
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				CommonUtils.toast(mContext, "很抱歉,程序出现异常,即将退出.");
				Looper.loop();
			}
		}.start();
		// 收集设备参数信息
		mapPhoneInfo = PhoneInfoUitl.getPhoneInfo(mContext, mActivity);
		// 保存日志文件
		saveCrashInfo2File(ex);
		return true;
	}
	
	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	private String saveCrashInfo2File(Throwable ex) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : mapPhoneInfo.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
			File crashFile = FileMgr.newFile(
					MyApplication.getApplication(), true,
					"CrashLog", currTimeFormat.format(new Date()) + ".txt");
			PrintWriter pw = new PrintWriter(crashFile);
			pw.write(sb.toString());
			pw.close();
			return crashFile.getName();
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
		ex.printStackTrace();
		return null;
	}
//	private Thread.UncaughtExceptionHandler mDefaultHandler;
//	private static CrashHandler INSTANCE = new CrashHandler();
//
//	private DateFormat currTimeFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//
//	private CrashHandler() {}
//
//	public static CrashHandler getInstance() {
//		return INSTANCE;
//	}
//
//	public void init(Context context, Activity activity) {
//		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
//		Thread.setDefaultUncaughtExceptionHandler(this);
//	}
//
//	@Override
//	public void uncaughtException(Thread thread, Throwable ex) {
//		saveCrashInfo2File(ex);
//		
//		if (ex == null && mDefaultHandler != null) {
//			mDefaultHandler.uncaughtException(thread, ex);
//		} else {
//			ActivityManager.finishAllActivities();
//			System.exit(1);
//		}
//	}
//
//	private void saveCrashInfo2File(Throwable ex) {
//		if (ex == null || !FileMgr.sdCardAvailable()) {
//			return;
//		}
//		ex.printStackTrace();
//		Writer writer = new StringWriter();
//		PrintWriter printWriter = new PrintWriter(writer);
//		ex.printStackTrace(printWriter);
//		Throwable cause = ex.getCause();
//		while (cause != null) {
//			cause.printStackTrace(printWriter);
//			cause = cause.getCause();
//		}
//		printWriter.close();
//		String crashContent = writer.toString();
//		Log.e("com.qsvxin", crashContent);
//		try {
//			File crashFile = FileMgr.newFile(
//					MyApplication.getApplication(), true,
//					"CrashLog", currTimeFormat.format(new Date()) + ".txt");
//			PrintWriter pw = new PrintWriter(crashFile);
//			pw.write(crashContent);
//			pw.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
