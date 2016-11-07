package com.qsvxin;

import com.qsvxin.utils.CommonUtils;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends BaseActivity implements OnClickListener{
	
	private Button btnAbout,btnBack,btnUpdate;
	private TextView txtv_showVersion;
	
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		btnAbout = (Button) findViewById(R.id.btn_about);
		btnBack = (Button) findViewById(R.id.btn_Back);
		txtv_showVersion = (TextView) findViewById(R.id.txtv_showVersion);
		txtv_showVersion.setText("当前版本号："+CommonUtils.getCurrentVersionName(this));
		btnUpdate = (Button) findViewById(R.id.btn_updata);
		btnBack.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnAbout.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_about:
			Intent intent = new Intent(AboutActivity.this, UseAgreementActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_updata:
			CommonUtils.checkVersion(this, true, true);
			break;
		case R.id.btn_Back:
			this.finish();
			break;
		default:
			break;
		}
	}
}
