package com.qsvxin;

import com.qsvxin.bean.GetUserMsgListBean;
import com.qsvxin.bean.GroupBuysListBean;
import com.qsvxin.bean.MemberBean;
import com.qsvxin.bean.VorderFormBean;
import com.qsvxin.utils.CommonUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ClearCacheActivity extends Activity implements OnClickListener{
	
	private Button btnDeleteVorder, btnDeleteVgroup, btnDeleteVchat, btnDeleteVmembers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clearcahce);
		btnDeleteVorder = (Button) findViewById(R.id.btn_delete_order);
		btnDeleteVgroup = (Button) findViewById(R.id.btn_delete_vgroup);
		btnDeleteVchat = (Button) findViewById(R.id.btn_delete_vchat);
		btnDeleteVmembers = (Button) findViewById(R.id.btn_delete_vmembers);
		btnDeleteVorder.setOnClickListener(this);
		btnDeleteVgroup.setOnClickListener(this);
		btnDeleteVchat.setOnClickListener(this);
		btnDeleteVmembers.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_delete_order:
			if (new VorderFormBean().deleteAll()) {
				CommonUtils.toast(this, "清除订单缓存成功");
			};
			this.finish();
			break;
		case R.id.btn_delete_vgroup:
			if (new GroupBuysListBean().deleteAll()) {
				CommonUtils.toast(this, "清除团购缓存成功");
			};
			this.finish();
			break;
		case R.id.btn_delete_vchat:
			if (new GetUserMsgListBean().deleteAll()) {
				CommonUtils.toast(this, "清除微聊缓存成功");
			};
			this.finish();
			break;
		case R.id.btn_delete_vmembers:
			if (new MemberBean().deleteAll()) {
				CommonUtils.toast(this, "清除会员缓存成功");
			};
			this.finish();
			break;
		default:
			break;
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return super.onTouchEvent(event);
	}
}
