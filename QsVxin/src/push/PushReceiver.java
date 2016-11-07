package push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.igexin.sdk.PushConsts;
import com.qsvxin.MyApplication;
import com.qsvxin.utils.PreferenceUtils;

public class PushReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
//		Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
		switch (bundle.getInt(PushConsts.CMD_ACTION)) {
		case PushConsts.GET_MSG_DATA:
			// 获取透传数据
			// String appid = bundle.getString("appid");
			byte[] payload = bundle.getByteArray("payload");
			if (payload != null) {
				String data = new String(payload);
//				FenceApplication.getApplication().notityPushData(data);
				//Notify.notityPushData(data);
				Notify.getInstance().getInstance().notityPushData(data);
//				Log.d("GetuiSdkDemo", "Got Payload:" + data);
			}
			break;
		case PushConsts.GET_CLIENTID:
			// 获取ClientID(CID)
			// 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
			String cid = bundle.getString("clientid");
			PreferenceUtils.setPrefString(MyApplication.getApplication(), "clientid", cid);
//			if (GetuiSdkDemoActivity.tView != null)
//				GetuiSdkDemoActivity.tView.setText(cid);
			break;
		case PushConsts.THIRDPART_FEEDBACK:
			String appid = bundle.getString("appid");
			String taskid = bundle.getString("taskid");
			String actionid = bundle.getString("actionid");
			String result = bundle.getString("result");
			long timestamp = bundle.getLong("timestamp");

//			Log.d("GetuiSdkDemo", "appid = " + appid);
//			Log.d("GetuiSdkDemo", "taskid = " + taskid);
//			Log.d("GetuiSdkDemo", "actionid = " + actionid);
//			Log.d("GetuiSdkDemo", "result = " + result);
//			Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
			
			break;
		default:
			break;
		}
	}
}
