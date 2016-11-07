package com.qsvxin;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qsvxin.bean.EngineStaticBean;
import com.qsvxin.bean.MemberBean;
import com.qsvxin.utils.CommonUtils;

/** 
 * @Description: TODO
 * @author liang_xs
 * @date 2014-9-22
 */
public class VmembersDetailsActivity extends BaseActivity implements OnClickListener{
	private Button btnLeft, btnCall, btnMsg;
	private TextView tvMemberName, tvSex, tvAge, tvBirthday, tvCardId, tvMemberPhoneNum, tvJoin, tvUsedIntegral, tvResidueIntegral, tvExplain;
	private ImageView ivHead, ivLevel;
	private MemberBean bean;
	
	/* (non-Javadoc)
	 * @see com.qsvxin.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vmembers_details);
		initView();
		initData();
	}

	private void initData() {
		bean = EngineStaticBean.getInstance().getmMemberBean();
		if (bean != null) {
			if (!TextUtils.isEmpty(bean.getStrMemberName())) {
				tvMemberName.setText("昵称: "+bean.getStrMemberName());
			} else {
				tvMemberName.setText("昵称: "+getString(R.string.str_vmember_visitor));
			}
			if (!TextUtils.isEmpty(bean.getStrSex())) {
				tvSex.setText("性别: "+bean.getStrSex());
			} else {
				tvSex.setText("性别: "+getString(R.string.str_vmember_secrecy));
			}
			if (bean.getIntAge() != 0) {
				tvAge.setText("年龄: "+bean.getIntAge()+"岁");
			} else {
				tvAge.setText("年龄: "+getString(R.string.str_vmember_secrecy));
			}
			if (!TextUtils.isEmpty(bean.getStrBirthday())) {
				tvBirthday.setText("生日: "+bean.getStrBirthday());
			} else {
				tvBirthday.setText("生日: "+getString(R.string.str_vmember_secrecy));
			}
			if (bean.isIsvip()) {
				tvCardId.setText(bean.getStrCardID());
			} else {
				tvCardId.setText(getString(R.string.str_vmember_is_not_vip));
			}
			if (!TextUtils.isEmpty(bean.getStrMemberPhoneNum())) {
				tvMemberPhoneNum.setText(bean.getStrMemberPhoneNum());
			} else {
				tvMemberPhoneNum.setText(getString(R.string.str_vmember_secrecy));
			}
			
			if (bean.isIsjoin()) {
				tvJoin.setText(getString(R.string.str_vmember_is_join));
				tvJoin.setTextColor(getResources().getColor(R.color.green));
			} else {
				tvJoin.setText(getString(R.string.str_vmember_is_not_join));
				tvJoin.setTextColor(getResources().getColor(R.color.red));
			}
			tvUsedIntegral.setText(bean.getIntUsedIntegral()+"");
			tvResidueIntegral.setText(bean.getIntResidueIntegral()+"");
			tvExplain.setText(bean.getStrExplain());
			if (bean.isIsvip()) {
				if (bean.getIntUsedIntegral() < 10) {
					ivLevel.setBackgroundResource(R.drawable.icon_vip);
				} else if (bean.getIntUsedIntegral() >= 10
						&& bean.getIntUsedIntegral() < 20) {
					ivLevel.setBackgroundResource(R.drawable.icon_vip_10);
				} else if (bean.getIntUsedIntegral() >= 20
						&& bean.getIntUsedIntegral() < 30) {
					ivLevel.setBackgroundResource(R.drawable.icon_vip_20);
				} else if (bean.getIntUsedIntegral() >= 30
						&& bean.getIntUsedIntegral() < 40) {
					ivLevel.setBackgroundResource(R.drawable.icon_vip_30);
				} else if (bean.getIntUsedIntegral() >= 40) {
					ivLevel.setBackgroundResource(R.drawable.icon_vip_40);
				}
			} else {
				if (bean.getStrSex().equals("女")) {
					ivLevel.setBackgroundResource(R.drawable.icon_ordinary_female);
				} else if (bean.getStrSex().equals("男")) {
					ivLevel.setBackgroundResource(R.drawable.icon_ordinary_male);
				}
			}
			if (bean.getStrSex().equals("女")) {
				ivHead.setBackgroundResource(R.drawable.icon_member_sex_female);
			} else if (bean.getStrSex().equals("男")) {
				ivHead.setBackgroundResource(R.drawable.icon_member_sex_male);
			} else {
				ivHead.setBackgroundResource(R.drawable.icon_member_sex_unknow);
			}
		} else {
			toast("数据错误，请稍候重试");
		}
	}

	/* (non-Javadoc)
	 * @see com.qsvxin.BaseActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EngineStaticBean.getInstance().setmMemberBean(null);
	}
	
	
	private void initView() {
		btnLeft = (Button) findViewById(R.id.btnLeft);
		btnCall = (Button) findViewById(R.id.btnCall);
		btnMsg = (Button) findViewById(R.id.btnMsg);
		tvMemberName = (TextView) findViewById(R.id.tvMemberName);
		tvSex = (TextView) findViewById(R.id.tvSex);
		tvAge = (TextView) findViewById(R.id.tvAge);
		tvBirthday = (TextView) findViewById(R.id.tvBirthday);
		tvCardId = (TextView) findViewById(R.id.tvCardId);
		tvMemberPhoneNum = (TextView) findViewById(R.id.tvMemberPhoneNum);
		tvJoin = (TextView) findViewById(R.id.tvJoin);
		tvUsedIntegral = (TextView) findViewById(R.id.tvUsedIntegral);
		tvResidueIntegral = (TextView) findViewById(R.id.tvResidueIntegral);
		tvExplain = (TextView) findViewById(R.id.tvExplain);
		ivHead = (ImageView) findViewById(R.id.ivHead);
		ivLevel = (ImageView) findViewById(R.id.ivLevel);
		
		btnLeft.setOnClickListener(this);
		btnCall.setOnClickListener(this);
		btnMsg.setOnClickListener(this);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLeft:
			this.finish();
			break;

		case R.id.btnCall:
			if (!TextUtils.isEmpty(bean.getStrMemberPhoneNum())) {
				startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bean.getStrMemberPhoneNum())));
			} else {
				CommonUtils.toast(getApplicationContext(), getString(R.string.str_vmember_phone_is_null));
			}
			break;
			
		case R.id.btnMsg:
			if (!TextUtils.isEmpty(bean.getStrMemberPhoneNum())) {
				startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + bean.getStrMemberPhoneNum())));
			} else {
				CommonUtils.toast(getApplicationContext(), getString(R.string.str_vmember_phone_is_null));
			}
			break;
		}
	}

}
