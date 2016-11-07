package com.qsvxin.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import netutils.http.RequestCallBack;
import netutils.impl.SgeHttp;
import push.Notify;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.qsvxin.MainUIActivity;
import com.qsvxin.R;
import com.qsvxin.bean.UpdateAppVersionBean;
import com.qsvxin.dialog.DialogCallback;
import com.qsvxin.dialog.TextDialog;
import com.qsvxin.engine.UpdateAppVersion;
import com.qsvxin.net.interf.UpdateVersionParserData;

public class CommonUtils {
    /**
     * 检验手机号码是否合法
     * 
     * @param number
     * @return
     */
    public static boolean validMobile(String number) {
        if (number == null) {
            return false;
        }
        return number.matches("1[3|4|5|8]\\d{9}");
    }

    public static void headZoom(Activity activity, String strUrl){
    	Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse(strUrl),
				"image/*");
		activity.startActivity(intent);
    }
    
    public static void headZoom(Activity activity, File file){
    	Intent intent = new Intent();
    	intent.setAction(android.content.Intent.ACTION_VIEW);
    	intent.setDataAndType(Uri.fromFile(file),
    			"image/*");
    	activity.startActivity(intent);
    }

    /**
     * check phone number
     * 
     * @param mobiles
     * @return boolean
     */
    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|((14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 匹配数字
     * 
     * @param str
     * @return
     */
    public static boolean validNum(String str) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(str);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 匹配字母
     * 
     * @param str
     * @return
     */
    public static boolean validWord(String str) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("[a-z|A-Z]+");
            Matcher m = p.matcher(str);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 检验是否是字母或数字
     * 
     * @param password
     *            只能是6-16位字母和数字组合
     * @return 正确时返回true
     */
    public static boolean validPassword(String password) {
        if (password == null) {
            return false;
        }
        if (password.matches("\\d+")) {
            return false;
        } else if (password.matches("[a-z|A-Z]+")) {
            return false;
        }
        return password.matches("\\w{6,16}");
    }
    /**
     * 匹配注册用户名
     * @param password
     * @return
     */
    public static boolean validUserName(String userName) {
        if (userName == null) {
            return false;
        }
        if (userName.matches("\\d+")) {
            return false;
        } else if (userName.matches("[a-z|A-Z]{6,16}")) {
            return true;
        }
        return userName.matches("\\w{6,16}");
    }

    public static boolean validVerifyCode(String code) {
        if (code == null) {
            return false;
        }
        return code.matches("\\d{6}");
    }
    /**
     * 匹配QQ号
     * @param qqNum
     * @return
     */
    public static boolean validQQNum(String qqNum) {
        if (qqNum == null) {
            return false;
        }
        return qqNum.matches("\\d{5,11}");
    }

    /**
     * 检验是否是字母或数字
     * 
     * @param password
     * @return
     */
    // public static boolean validPassword(String password) {
    // if (password == null) {
    // return false;
    // }
    // return password.matches("\\w{1,20}");
    // }

    /**
     * 检验邮箱是否合法
     * 
     * @param email
     * @return 正确时返回true
     */
    public static boolean isEmailAddress(String email) {
        if (email == null) {
            return false;
        }

        String regex = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";

        return email.matches(regex);
    }

    /**
     * 判断网络是否正常
     * 
     * @param context
     * @return
     */
    public static boolean isNetAvaliable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetWorkInfo = cm.getActiveNetworkInfo();
        return mNetWorkInfo != null && mNetWorkInfo.isConnectedOrConnecting();
    }

    /**
     * 检查网络
     * 
     * @param context
     * @return
     */
    public static boolean checkNet(Context context) {
        try {
            // 获取手机所有连接管理对象（wi_fi,net等连接的管理）
            ConnectivityManager manger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manger != null) {
                NetworkInfo info[] = manger.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 获取MAC地址
     * 
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    public static void vibrate(long milliseconds) {
        Vibrator vib = (Vibrator) BaseApplication.getApplication().getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    public void vibrate(long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) BaseApplication.getApplication().getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    /**
     * 创建吐丝
     * 
     * @param context
     * @param msg
     * @Toast.LENGTH_SHORT
     */
    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, int resId) {
        toast(context, context.getResources().getString(resId));
    }

    /**
     * 获取当前程序版本号
     * 
     * @param context
     * @return VersionCode
     */
    public static int getCurrentVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取当前程序版本
     * 
     * @param context
     * @return VersionName
     */
    public static String getCurrentVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 创建不带按钮的对话框
     * 
     * @param mContext
     * @param resID
     *            图片资源id
     * @param strTitle
     *            文本标题
     * @param strText
     *            文本内容
     */
    public static void createDialog(Context mContext, int resID, String strTitle, String strText) {
        new AlertDialog.Builder(mContext).setIcon(mContext.getResources().getDrawable(resID)).setTitle(strTitle)
                .setMessage(strText).create().show();

    }
    
    public static void errorTipsDialog(final Activity mActivity,final String strTitle,final String strContent) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommonUtils.createDialog(mActivity, R.drawable.login_error_icon,strTitle,
                        strContent);
            }
        });
    }
    public static void createDialog(Context mContext,String strTitle, String strText) {
        new AlertDialog.Builder(mContext).setTitle(strTitle)
                .setMessage(strText).create().show();

    }
    
    /**
     * @Description: TODO
     * @param activity
     * 			
     * @param isShowDialog
     * 			是否显示等待进度条
     * @param isToast
     * 			若无更新版本，是否提示toast
     */
    public static void checkVersion(final Activity activity, boolean isShowDialog,
			final boolean isToast) {
		String strVersionName = PhoneInfoUitl.getVersionName(activity);
		new UpdateAppVersion(activity, "test", strVersionName, "1",
				new UpdateVersionParserData() {

					@Override
					public void getUpdateVersionSuccData(String succCode,
							String succMsg,
							final UpdateAppVersionBean mUpdateAppBean) {
						if (!mUpdateAppBean.isNewVersion()) {
							final String strAppPath = PreferenceUtils.getPrefString(activity, "APP_PATH", "");
							File file = new File(strAppPath);
							if (file.exists()) {
								String strName = file.getName();
								String strSize = String.valueOf(file.length());
								if (strName.equals(mUpdateAppBean.getStrAppName()) && 
										strSize.equals(mUpdateAppBean.getStrVersionSize())) {
									activity.runOnUiThread(new Runnable() {
										public void run() {
											installDialog(activity, strAppPath, mUpdateAppBean.getStrNewAppIntroduction());
										}
									});
								} else {
									activity.runOnUiThread(new Runnable() {
										public void run() {
											showUpdateDialog(activity, mUpdateAppBean);
										}
									});
								}
							} else {
								activity.runOnUiThread(new Runnable() {
									public void run() {
										showUpdateDialog(activity, mUpdateAppBean);
									}
								});
							}
							
						} else {
							if (isToast) {
								activity
										.runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(activity,
														"当前已经是最新版本哦",
														Toast.LENGTH_LONG)
														.show();
											}
										});
							}
						}
					}

					@Override
					public void getUpdateVersionErrData(String errCode,
							String errMsg) {
						// TODO 打印日志
					}

					@Override
					public void getParserErrData(int errCode, String errMsg) {
						// TODO 打印日志
					}

					@Override
					public void getNetErrData(int errCode, String errMsg) {
						// TODO 打印日志
					}
				}, isShowDialog).excute();
	}

	
	/**
	 * @Title: installDialog
	 * @Description: 安装提示
	 * @return void
	 * @author liang_xs
	 */
	private static void installDialog(final Activity activity, final String strAppPath, String StrNewAppIntroduction) {
		final TextDialog installDialog = new TextDialog(activity);
		installDialog.setTitleText("发现新版本");
		installDialog.setRightText("安装");
		installDialog.setLeftText("下次再说");
		installDialog.setContentText(StrNewAppIntroduction);
		installDialog.setRightCall(new DialogCallback() {

			@Override
			public void Click() {
				// 执行安装
				FileMgr.installApp(activity, strAppPath);
			}
		});
		installDialog.setLeftCall(new DialogCallback() {

			@Override
			public void Click() {
				installDialog.dismiss();
			}
		});
		installDialog.show();
	}
	
	/**
	 * @Description: 下载新版本提示
	 * @param activity
	 * @param mUpdateAppBean
	 */
	private static void showUpdateDialog(final Activity activity, final UpdateAppVersionBean mUpdateAppBean) {
		final TextDialog mUpdateDialog = new TextDialog(activity);
		mUpdateDialog.setTitleText("发现新版本");
		mUpdateDialog.setRightText("下载");
		mUpdateDialog.setLeftText("下次再说");
		mUpdateDialog.setContentText(mUpdateAppBean.getStrNewAppIntroduction());
		mUpdateDialog.setRightCall(new DialogCallback() {

			@Override
			public void Click() {
				// 执行下载
				File file = FileMgr.creatDir(FileMgr.UPDATE_FOLDER_NAME);
				final String strPath = file.getAbsolutePath() + File.separator
						+ mUpdateAppBean.getStrAppName();
				FileMgr.delAllFile(file.getAbsolutePath(),
						mUpdateAppBean.getStrAppName());
				SgeHttp sgeHttp = new SgeHttp();
				// 调用download方法开始下载
				netutils.http.HttpHandler<File> httpHandler = sgeHttp.download(
						mUpdateAppBean.getStrDownLoadAppUrl(), strPath, true,
						new RequestCallBack<File>() {
							/*
							 * (non-Javadoc)
							 * 
							 * @see netutils.http.RequestCallBack#onStart()
							 */
							@Override
							public void onStart() {
								super.onStart();
							}

							@Override
							public RequestCallBack<File> progress(
									boolean progress, int rate) {
								// TODO Auto-generated method stub
								return super.progress(progress, rate);
							}

							@SuppressWarnings("deprecation")
							@Override
							public void onLoading(long count, long current) {
								Notification notification = null;
								RemoteViews remoteViews = null;
								NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
								// 构造notification
								if (notification == null) {
									notification = new Notification(
											R.drawable.ic_launcher,
											"新应用下载中...", System
													.currentTimeMillis());
									notification.flags = Notification.FLAG_AUTO_CANCEL
											| Notification.FLAG_ONGOING_EVENT;
									remoteViews = new RemoteViews(
											activity.getPackageName(),
											R.layout.layout_notification);
									notification.contentView = remoteViews;
									// 显示notification
									notificationManager.notify(
											R.string.app_name, notification);

								}
								long persent = current * 100 / count;
								remoteViews.setProgressBar(R.id.pb,
										(int) count, (int) current, false);
								remoteViews.setTextViewText(R.id.tv_persent,
										persent + "%");
								// 刷新notification
								notificationManager.notify(R.string.app_name,
										notification);

								super.onLoading(count, current);
							}

							@Override
							public void onSuccess(File t) {
								PreferenceUtils.setPrefString(activity, "APP_PATH", t.getAbsolutePath());
								Notify.notityDownloadFinish();
								// 执行安装
								FileMgr.installApp(activity,
										t.getAbsolutePath());
								super.onSuccess(t);
							}

							@Override
							public void onFailure(Throwable t, int errorNo,
									final String strMsg) {
								switch (errorNo) {
								case 416:
									FileMgr.installApp(activity,
											strPath);
									break;

								case 0:
									activity
											.runOnUiThread(new Runnable() {

												@Override
												public void run() {
													Toast.makeText(
															activity,
															"请检查网络链接",
															Toast.LENGTH_LONG)
															.show();
												}
											});
									break;
								case 404:
									activity
											.runOnUiThread(new Runnable() {

												@Override
												public void run() {
													Toast.makeText(
															activity,
															strMsg,
															Toast.LENGTH_LONG)
															.show();
												}
											});
									break;
								}

								super.onFailure(t, errorNo, strMsg);
							}

						});
				// 调用stop()方法停止下载
				// httpHandler.stop();
				
			}
		});
		mUpdateDialog.setLeftCall(new DialogCallback() {

			@Override
			public void Click() {
				mUpdateDialog.dismiss();
			}
		});
		mUpdateDialog.show();
	}

}
