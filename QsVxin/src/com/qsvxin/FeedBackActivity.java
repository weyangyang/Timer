package com.qsvxin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.qsvxin.bean.LoginBean;
import com.qsvxin.engine.SendIdeaFeedBack;
import com.qsvxin.net.interf.IdeaFeedBackParserData;
import com.qsvxin.utils.CommonUtils;
import com.qsvxin.utils.QsConstants;

/**
 * @ClassName: FeedbackActivity
 * @Description: TODO
 * @author liang_xs
 * @date 2014-7-8
 */
public class FeedBackActivity extends BaseActivity implements OnClickListener {
	private EditText etFeedback;
	private Button btnLeft, btnRight;
	private String str_secretKey;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		etFeedback = (EditText) findViewById(R.id.etFeedback);
		btnLeft = (Button) findViewById(R.id.btnLeft);
		btnRight = (Button) findViewById(R.id.btnRight);
		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLeft:
			FeedBackActivity.this.finish();
			break;

		case R.id.btnRight:
			if (!CommonUtils.isNetAvaliable(this)) {
				CommonUtils.createDialog(this, "提示", "网络异常，请检查网络连接");
				return;
			}
			String strFeedback = etFeedback.getText().toString();
			if (TextUtils.isEmpty(strFeedback)) {
				CommonUtils.createDialog(this, "提示", "请填写反馈内容");
				return;
			}

			etFeedback.getText().clear();
			// 将strFeedback传给服务器
			str_secretKey = LoginBean.getInstance().getStrSecretKey();
			new SendIdeaFeedBack(FeedBackActivity.this, str_secretKey, strFeedback, new IdeaFeedBackParserData() {
				
				@Override
				public void getParserErrData(int errCode, String errMsg) {
					// TODO 打印日志
				}
				
				@Override
				public void getNetErrData(int errCode, String errMsg) {
					if (errCode == QsConstants.CLIENT_PROTOCOL_EXCEPTION) {
						FeedBackActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								CommonUtils
										.errorTipsDialog(
												FeedBackActivity.this,
												FeedBackActivity.this
														.getString(R.string.str_prompt),
														FeedBackActivity.this
														.getString(R.string.str_net_timeout));
							}
						});
					} else {
						CommonUtils
								.errorTipsDialog(
										FeedBackActivity.this,
										FeedBackActivity.this
												.getString(R.string.str_prompt),
												FeedBackActivity.this
												.getString(R.string.str_vmember_net_error));
					}
				}
				
				@Override
				public void getIdeaFeedBackSuccData(String succCode, String succMsg) {
					FeedBackActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							toast("反馈成功，感谢您的支持");
						}
					});
				}
				
				@Override
				public void getIdeaFeedBackErrData(String errCode, String errMsg) {
					// TODO 打印日志
				}
			}).excute();
			break;
		}
	}

}
