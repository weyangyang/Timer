package com.qsvxin;

import com.qsvxin.bean.RegisterAgreementBean;
import com.qsvxin.engine.GetRegisterAgreement;
import com.qsvxin.net.interf.RegisterAgreementParserData;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class UseAgreementActivity extends BaseActivity {
	
	private Button btnBack;
	private WebView content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_useagreement);
		btnBack = (Button) findViewById(R.id.btn_Back);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UseAgreementActivity.this.finish();
			}
		});
		content = (WebView) findViewById(R.id.agreement_content);
		new GetRegisterAgreement(this, new RegisterAgreementParserData() {
			
			@Override
			public void getRASuccData(String succCode, String succMsg,
					final RegisterAgreementBean mRegisterAgreementBean) {
				UseAgreementActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						content.loadDataWithBaseURL(null, mRegisterAgreementBean.getStrContent(), "text/html", "utf-8", null);  
					}
				});
			}
			
			@Override
			public void getRAErrData(String errCode, String errMsg) {
				
			}
			
			@Override
			public void getParserErrData(int errCode, String errMsg) {
				
			}
			
			@Override
			public void getNetErrData(int errCode, String errMsg) {
				
			}
		}).excute();
	}
}
