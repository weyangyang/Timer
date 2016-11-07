package com.qsvxin;

import com.qsvxin.engine.StoreOrderShipments;
import com.qsvxin.net.interf.StoreOFShipmentsParserData;
import com.qsvxin.twocode.CaptureActivity;
import com.qsvxin.utils.CommonUtils;
import com.qsvxin.utils.QsConstants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class SendOrderActivity extends Activity {

	private EditText editText;
	private String str_secretKey, orderId;
	public static final int REQUSET = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_order);
		editText = (EditText) findViewById(R.id.edit_input_courier);
		str_secretKey = getIntent().getStringExtra("secretKey");
		orderId = getIntent().getStringExtra("orderId");
		
	}

	public void btnOk(View view) {
		String courier = editText.getText().toString();
		if (!TextUtils.isEmpty(courier)) {
			new StoreOrderShipments(this, editText.getText().toString(),
					str_secretKey, orderId, new StoreOFShipmentsParserData() {

						@Override
						public void storeOFShipmentsSuccData(String succCode,
								final String succMsg) {
							SendOrderActivity.this.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									CommonUtils.toast(SendOrderActivity.this, succMsg);
									SendOrderActivity.this.finish();
								}
							});
						}

						@Override
						public void storeOFShipmentsErrData(String errCode,
								String errMsg) {
							// TODO 保存日志
						}

						@Override
						public void getParserErrData(int errCode, String errMsg) {
							// TODO 保存日志
						}

						@Override
						public void getNetErrData(int errCode, String errMsg) {
							if (errCode == QsConstants.CLIENT_PROTOCOL_EXCEPTION) {
								// initDataFromNet(str_secretKey);
								SendOrderActivity.this.runOnUiThread(new Runnable() {
									@Override
									public void run() {
										CommonUtils
												.errorTipsDialog(
														SendOrderActivity.this,
														SendOrderActivity.this
																.getString(R.string.str_prompt),
																SendOrderActivity.this
																.getString(R.string.str_net_timeout));
									}
								});
							} else {
								SendOrderActivity.this.runOnUiThread(new Runnable() {
									@Override
									public void run() {
										CommonUtils
												.errorTipsDialog(
														SendOrderActivity.this,
														SendOrderActivity.this
																.getString(R.string.str_prompt),
																SendOrderActivity.this
																.getString(R.string.str_vmember_net_error));
									}
								});
							}
						}
					}).excute();
		}else{
			CommonUtils.toast(SendOrderActivity.this, "请输入快递单号");
		}

	}
	public void btnCancal(View view){
		this.finish();
	}
	public void scanning_code(View view){
		Intent intent = new Intent(this, CaptureActivity.class);
		intent.putExtra("type", "curier");
		startActivityForResult(intent, REQUSET);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUSET && resultCode == RESULT_OK) {
			String curier = data.getStringExtra("CODE");
			System.out.println(curier);
			editText.setText(curier);
		}
	}
}
