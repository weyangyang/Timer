package push;

import org.json.JSONException;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.qsvxin.LoginActivity;
import com.qsvxin.MainUIActivity;
import com.qsvxin.MyApplication;
import com.qsvxin.R;
import com.qsvxin.VchatActivity;
import com.qsvxin.VchatDeatilsActivity;
import com.qsvxin.bean.GeTuiMsgBean;
import com.qsvxin.bean.LoginBean;
import com.qsvxin.dialog.DialogCallback;
import com.qsvxin.dialog.TextDialog;

/**
 * @ClassName: Notify
 */
public class Notify {
	private TextDialog dialog;
	
	private Notify() {
	};

	private static Notify mNotify;

	public static Notify getInstance() {
		if (mNotify == null) {
			mNotify = new Notify();
		}
		return mNotify;
	}

	private String str_secretKey = null;

	/**
	 * @Title: notityPushData
	 * @Description: 在通知栏中提示推送信息
	 * @return void
	 */
	public void notityPushData(String strJson) {
		str_secretKey = LoginBean.getInstance().getStrSecretKey();
		if (TextUtils.isEmpty(str_secretKey)) {
			return;
		}
		GeTuiMsgBean bean = null;
		try {
			bean = GeTuiMsgBean.getInstance().getParserGeTuiBean(strJson);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ActivityManager am = (ActivityManager) MyApplication.getApplication()
				.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		if (cn.getClassName().equals(MainUIActivity.class.getName())
				&& cn.getPackageName().equals(
						MyApplication.getApplication().getPackageName())) {
			if (MainUIActivity.instance != null) {
				if (bean.getStrMsgSource().equals("1")) {
					int intTabId = MainUIActivity.instance.getIntTabId();
					if (intTabId == 3) {
						if (VchatActivity.mActivity != null) {
							VchatActivity.mActivity.initDataFromNet(str_secretKey,
									false);
							// 加震动
							vibrate();
						}
					} else {
						if (VchatActivity.mActivity != null) {
							VchatActivity.mActivity.initDataFromNet(str_secretKey,
									false);
							// 加震动
							vibrate();
						}
						showNotify(bean);
					}
				} else if (bean.getStrMsgSource().equals("3")) {
					if (MainUIActivity.instance != null) {
						showDialog();
					}
				}
				
			}
		} else if (cn.getClassName().equals(
				VchatDeatilsActivity.class.getName())
				&& cn.getPackageName().equals(
						MyApplication.getApplication().getPackageName())) {
			if (bean.getStrMsgSource().equals("1")) {
				if (VchatDeatilsActivity.mActivity != null) {
					if (VchatActivity.mActivity != null) {
						VchatActivity.mActivity.initDataFromNet(str_secretKey,
								false);
						// 加震动
						vibrate();
					}
					VchatDeatilsActivity.mActivity.initDataFromNet(str_secretKey,
							false);
					// 加震动
					vibrate();
				}
			} else if (bean.getStrMsgSource().equals("3")) {
				if (MainUIActivity.instance != null) {
					showDialog();
				}
			}
			
		} else {
			if (bean.getStrMsgSource().equals("1")) {
				if (VchatActivity.mActivity != null) {
					VchatActivity.mActivity.initDataFromNet(str_secretKey, false);
					// 加震动
					vibrate();
				}
				showNotify(bean);
			} else if (bean.getStrMsgSource().equals("3")) {
				showNotify();
			}
			
		}

	}

	private void showNotify(GeTuiMsgBean bean) {
		final NotificationManager manager = (NotificationManager) MyApplication
				.getApplication().getSystemService(
						MyApplication.getApplication().NOTIFICATION_SERVICE);
		// 初始化通知对象 p1:通知的图标 p2:通知的状态栏显示的提示 p3:通知显示的时间
		Notification notification = new Notification(R.drawable.ic_launcher,
				bean.getStrNickName(), System.currentTimeMillis());
		// 点击通知后的Intent，此例子点击后还是在当前界面
		Intent activityIntent = new Intent(MyApplication.getApplication(),
				VchatDeatilsActivity.class);
		activityIntent.putExtra("str_userOpenID", bean.getStrUserOpenID());
		activityIntent.putExtra("str_nickname", bean.getStrNickName());
		activityIntent.putExtra("str_headUrl", bean.getStrHeadUrl());
		// 设置在通知栏中点击后Notification自动消失
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.defaults = Notification.DEFAULT_SOUND;
		long[] vibrate = { 200, 800, 200, 800 };
		notification.vibrate = vibrate;
		PendingIntent pendingIntent = PendingIntent.getActivity(
				MyApplication.getApplication(), 0, activityIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		// 设置通知信息
		notification.setLatestEventInfo(MyApplication.getApplication(),
				bean.getStrNickName(), bean.getStrMsgText(), pendingIntent);
		// 通知
		manager.notify(1, notification);
	}
	
	private void showNotify() {
		final NotificationManager manager = (NotificationManager) MyApplication
				.getApplication().getSystemService(
						MyApplication.getApplication().NOTIFICATION_SERVICE);
		// 初始化通知对象 p1:通知的图标 p2:通知的状态栏显示的提示 p3:通知显示的时间
		Notification notification = new Notification(R.drawable.ic_launcher,
				"订单提示", System.currentTimeMillis());
		// 点击通知后的Intent，此例子点击后还是在当前界面
		// 设置在通知栏中点击后Notification自动消失
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.defaults = Notification.DEFAULT_SOUND;
		long[] vibrate = { 200, 800, 200, 800 };
		notification.vibrate = vibrate;
		// 设置通知信息
		notification.setLatestEventInfo(MyApplication.getApplication(),
				"订单提示", "您有新的订单，请登录网站查看！", null);
		// 通知
		manager.notify(1, notification);
	}

	private void vibrate() {
		NotificationManager manager = (NotificationManager) MyApplication
				.getApplication().getSystemService(
						MyApplication.getApplication().NOTIFICATION_SERVICE);
		Notification notification = new Notification();
		long[] vibrate = { 400, 400 };
		notification.vibrate = vibrate;
		manager.notify(1, notification);
	}
	
	
	private void showDialog() {
		dialog = new TextDialog(MainUIActivity.instance);
		dialog.setTitleText("订单提醒");
		dialog.setContentText("您有新的订单，请登录网站查看");
		dialog.setLeftText("知道了");
		dialog.setLeftCall(new DialogCallback() {
			
			@Override
			public void Click() {
				dialog.dismiss();
			}
		});
		dialog.setRightCall(null);
		dialog.show();
	}

	// public void notityPushData2(String strJson) {
	// GeTuiMsgBean bean = null;
	// str_secretKey = LoginBean.getInstance().getStrSecretKey();
	// if (TextUtils.isEmpty(str_secretKey)) {
	// return;
	// }
	// try {
	// bean = GeTuiMsgBean.getInstance().getParserGeTuiBean(strJson);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// new GetBitmapTask(bean.getStrHeadUrl(), new GetNetBitmapInterf() {
	//
	// @Override
	// public void getBitmapErrData(int errCode, String errMsg) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void getBitmap(Bitmap mBitmap) {
	// GeTuiMsgBean bean =GeTuiMsgBean.getInstance();
	// showNotifyEngine(bean,mBitmap);
	// }
	// });
	//
	// }

	// private void showNotifyEngine(GeTuiMsgBean bean ,Bitmap mBitmap) {
	// ActivityManager am = (ActivityManager) MyApplication.getApplication()
	// .getSystemService(Context.ACTIVITY_SERVICE);
	// ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
	// if (cn.getClassName().equals(MainUIActivity.class.getName())
	// &&
	// cn.getPackageName().equals(MyApplication.getApplication().getPackageName()))
	// {
	// if (MainUIActivity.instance != null) {
	// int intTabId = MainUIActivity.instance.getIntTabId();
	// if (intTabId == 3) {
	// if (VchatActivity.mActivity != null) {
	// VchatActivity.mActivity.initDataFromNet(str_secretKey, false);
	// }
	// } else {
	// if (VchatActivity.mActivity != null) {
	// VchatActivity.mActivity.initDataFromNet(str_secretKey, false);
	// }
	// final NotificationManager manager = (NotificationManager)
	// MyApplication.getApplication()
	// .getSystemService(MyApplication.getApplication().NOTIFICATION_SERVICE);
	//
	// // showNotify(bean, manager);
	// showNotify2(bean,manager,mBitmap);
	// }
	// }
	// } else if (cn.getClassName().equals(VchatDeatilsActivity.class.getName())
	// &&
	// cn.getPackageName().equals(MyApplication.getApplication().getPackageName()))
	// {
	// if (VchatDeatilsActivity.mActivity != null) {
	// if (VchatActivity.mActivity != null) {
	// VchatActivity.mActivity.initDataFromNet(str_secretKey, false);
	// }
	// VchatDeatilsActivity.mActivity.initDataFromNet(str_secretKey, false);
	// }
	// } else {
	// if (VchatActivity.mActivity != null) {
	// VchatActivity.mActivity.initDataFromNet(str_secretKey, false);
	// }
	// final NotificationManager manager = (NotificationManager)
	// MyApplication.getApplication().getSystemService(
	// MyApplication.getApplication().NOTIFICATION_SERVICE);
	//
	// //showNotify(bean, manager);
	// showNotify2(bean,manager,mBitmap);
	// }
	// }

	// private void showNotify2(GeTuiMsgBean bean, NotificationManager manager,
	// Bitmap mBitmap) {
	// Notification notification = new Notification();
	// // 设置statusbar显示的icon
	// notification.icon = R.drawable.ic_launcher;
	// // 设置statusbar显示的文字信息
	// // myNoti.tickerText= new_msg ;
	// notification.flags = Notification.FLAG_AUTO_CANCEL;
	// // 设置notification发生时同时发出默认声音
	// notification.defaults = Notification.DEFAULT_SOUND;
	// // TODO:一会再改layout
	// RemoteViews contentView = new
	// RemoteViews(MyApplication.getApplication().getPackageName(),
	// R.layout.notification);
	// contentView.setImageViewBitmap(R.id.notification_icon, mBitmap);
	// contentView.setTextViewText(R.id.notification_title,
	// bean.getStrNickName());
	// contentView.setTextViewText(R.id.notification_name,
	// bean.getStrMsgText());
	// notification.contentView = contentView;
	// Intent activityIntent = new Intent(MyApplication.getApplication(),
	// VchatDeatilsActivity.class);
	// activityIntent.putExtra("str_userOpenID", bean.getStrUserOpenID());
	// activityIntent.putExtra("str_nickname", bean.getStrNickName());
	// activityIntent.putExtra("str_headUrl", bean.getStrHeadUrl());
	// // 设置在通知栏中点击后Notification自动消失
	// notification.flags = Notification.FLAG_AUTO_CANCEL;
	// notification.defaults = Notification.DEFAULT_SOUND;
	// long[] vibrate = { 200, 800, 200, 800 };
	// notification.vibrate = vibrate;
	// PendingIntent pendingIntent =
	// PendingIntent.getActivity(MyApplication.getApplication(), 0,
	// activityIntent,
	// PendingIntent.FLAG_CANCEL_CURRENT);
	// // 设置通知信息
	// notification.setLatestEventInfo(MyApplication.getApplication(),
	// bean.getStrNickName(), bean.getStrMsgText(),
	// pendingIntent);
	// // 通知
	// manager.notify(1, notification);
	// }

	private static void showNotify(GeTuiMsgBean bean,
			final NotificationManager manager) {
		// 初始化通知对象 p1:通知的图标 p2:通知的状态栏显示的提示 p3:通知显示的时间
		Notification notification = new Notification(R.drawable.ic_launcher,
				bean.getStrNickName(), System.currentTimeMillis());
		// 点击通知后的Intent，此例子点击后还是在当前界面
		Intent activityIntent = new Intent(MyApplication.getApplication(),
				VchatDeatilsActivity.class);
		activityIntent.putExtra("str_userOpenID", bean.getStrUserOpenID());
		activityIntent.putExtra("str_nickname", bean.getStrNickName());
		activityIntent.putExtra("str_headUrl", bean.getStrHeadUrl());
		// 设置在通知栏中点击后Notification自动消失
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.defaults = Notification.DEFAULT_SOUND;
		long[] vibrate = { 200, 800, 200, 800 };
		notification.vibrate = vibrate;
		PendingIntent pendingIntent = PendingIntent.getActivity(
				MyApplication.getApplication(), 0, activityIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		// 设置通知信息
		notification.setLatestEventInfo(MyApplication.getApplication(),
				bean.getStrNickName(), bean.getStrMsgText(), pendingIntent);
		// 通知
		manager.notify(1, notification);
	}

	/**
	 * @Title: notityDownloadFinish
	 * @Description: APK更新下载完成
	 * @return void
	 * @author liang_xs
	 */
	public static void notityDownloadFinish() {
		// 获得通知管理器，通知是一项系统服务
		final NotificationManager manager = (NotificationManager) MyApplication
				.getApplication()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// 初始化通知对象 p1:通知的图标 p2:通知的状态栏显示的提示 p3:通知显示的时间
		Notification notification = new Notification(R.drawable.ic_launcher,
				"下载完成", System.currentTimeMillis());
		// 设置在通知栏中点击后Notification自动消失
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		// 以下两句话注释后，则无法实现点击通知栏中信息后，notify自动消失的功能；
		// Intent intent = new Intent();
		// PendingIntent pendingIntent = PendingIntent.getBroadcast(
		// FenceApplication.getApplication().getApplicationContext(), 0, intent,
		// 0);
		// 设置通知信息
		notification.setLatestEventInfo(MyApplication.getApplication(), "下载完成",
				MyApplication.getApplication().getString(R.string.app_name)
						+ "下载完成", null);
		// 通知
		manager.notify(R.string.app_name, notification);
	}

	/**
	 * 从网络获取通知栏图标的notify
	 * 
	 * @param flag
	 * @param strJson
	 */
	// public void notityPushData1(String strJson) {
	// str_secretKey = LoginBean.getInstance().getStrSecretKey();
	// if (TextUtils.isEmpty(str_secretKey)) {
	// return;
	// }
	// GeTuiMsgBean bean = null;
	// try {
	// bean = GeTuiMsgBean.getInstance().getParserGeTuiBean(strJson);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// ActivityManager am = (ActivityManager) MyApplication.getApplication()
	// .getSystemService(Context.ACTIVITY_SERVICE);
	// NotificationManager manager = (NotificationManager)
	// MyApplication.getApplication().getSystemService(
	// MyApplication.getApplication().NOTIFICATION_SERVICE);
	// Notification notification = new Notification();
	// // 设置statusbar显示的icon
	// notification.icon = R.drawable.ic_launcher;
	// // 设置statusbar显示的文字信息
	// // myNoti.tickerText= new_msg ;
	// notification.flags = Notification.FLAG_AUTO_CANCEL;
	// // 设置notification发生时同时发出默认声音
	// notification.defaults = Notification.DEFAULT_SOUND;
	// // TODO:一会再改layout
	// RemoteViews contentView = new
	// RemoteViews(MyApplication.getApplication().getPackageName(),
	// R.layout.notification);
	// Bitmap bitmap = null;
	// // 此处是关键地方，可以从网络或是sdcard上获取图片，转成bitmap就可以
	// try {
	// bitmap = NetUtils.getBitmap(bean.getStrHeadUrl());
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// contentView.setImageViewBitmap(R.id.notification_icon, bitmap);
	// contentView.setTextViewText(R.id.notification_title,
	// bean.getStrNickName());
	// contentView.setTextViewText(R.id.notification_name,
	// bean.getStrMsgText());
	// notification.contentView = contentView;
	// Intent activityIntent = new Intent(MyApplication.getApplication(),
	// VchatDeatilsActivity.class);
	// activityIntent.putExtra("str_userOpenID", bean.getStrUserOpenID());
	// activityIntent.putExtra("str_nickname", bean.getStrNickName());
	// activityIntent.putExtra("str_headUrl", bean.getStrHeadUrl());
	// // 设置在通知栏中点击后Notification自动消失
	// notification.flags = Notification.FLAG_AUTO_CANCEL;
	// notification.defaults = Notification.DEFAULT_SOUND;
	// long[] vibrate = { 200, 800, 200, 800 };
	// notification.vibrate = vibrate;
	// PendingIntent pendingIntent =
	// PendingIntent.getActivity(MyApplication.getApplication(), 0,
	// activityIntent,
	// PendingIntent.FLAG_CANCEL_CURRENT);
	// // 设置通知信息
	// notification.setLatestEventInfo(MyApplication.getApplication(),
	// bean.getStrNickName(), bean.getStrMsgText(),
	// pendingIntent);
	// // 通知
	// manager.notify(1, notification);
	// }

	// 转化drawableToBitmap
	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}
	// class GetBitmapTask extends QsAsyncTask{
	// private String strUrl;
	// private GetNetBitmapInterf mBitmapCallBack;
	// public GetBitmapTask(String strUrl,GetNetBitmapInterf mBitmapCallBack){
	// this.strUrl = strUrl;
	// this.mBitmapCallBack = mBitmapCallBack;
	// }
	//
	//
	// @Override
	// protected void onPreExectue() {
	//
	// }
	//
	// @Override
	// protected void doInbackgroud() {
	// try {
	// Bitmap mBitmap = NetUtils.getBitmap(strUrl);
	// mBitmapCallBack.getBitmap(mBitmap);
	// } catch (IOException e) {
	// mBitmapCallBack.getBitmapErrData(QsConstants.IO_EXCEPTION,
	// e.getMessage());
	// }
	//
	// }
	//
	// @Override
	// protected void onPostExecute() {
	//
	// }
	//
	// }
}
