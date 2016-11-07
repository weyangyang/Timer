package com.qsvxin;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qsvxin.bean.EngineStaticBean;
import com.qsvxin.bean.LoginBean;
import com.qsvxin.utils.FileMgr;
import com.qsvxin.utils.ImageLoaderManager;
import com.qsvxin.utils.PreferenceUtils;
import com.qsvxin.utils.TakePhoto;
import com.sge.imageloader.core.DisplayImageOptions;
import com.sge.imageloader.core.ImageLoader;

/**
 * @Description: TODO
 * @author liang_xs
 * @date 2014-9-22
 */
public class MyselfDetailsActivity extends BaseActivity implements
		OnClickListener {
	private Button btnLeft, btnCall, btnMsg;
	private TextView tvQQ, tvLastLoginTime, tvRegisterTime, tvEmail,
			tvPhoneNum, tvNickname;
	private ImageView ivHead;
	private TakePhoto mTakePhoto;
	private File headPhotoFile = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qsvxin.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myself_details);
		initView();
		initData();
//		headPhotoFile = FileMgr.newFile(this, true, "TakeHead", "bakcground.png");
		headPhotoFile = new File(this.getFilesDir(), "bakcground.png");
		try {
			headPhotoFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileMgr.chmod(headPhotoFile.getAbsolutePath(), "777");
		mTakePhoto = new TakePhoto(this, new TakePhoto.PhotoResult() {
			@Override
			public void onPhotoResult(File outputPath) {
				Bitmap bitmap = BitmapFactory.decodeFile(outputPath.getAbsolutePath());
                ivHead.setImageBitmap(bitmap);
				PreferenceUtils.setPrefString(MyselfDetailsActivity.this,
						"myHead", headPhotoFile.getAbsolutePath());
				toast("头像替换成功");
			}
		}, headPhotoFile);
	}

	private void initData() {
		tvQQ.setText(LoginBean.getInstance().getStrUserQQ());
		tvLastLoginTime.setText(LoginBean.getInstance()
				.getStrUserLastLoginTime());
		tvRegisterTime
				.setText(LoginBean.getInstance().getStrUserRegisterTime());
		tvEmail.setText(LoginBean.getInstance().getStrUserEmail());
		tvPhoneNum.setText(LoginBean.getInstance().getStrUserPhoneNum());
		tvNickname.setText(LoginBean.getInstance().getStrNickname());
		String strHead = PreferenceUtils.getPrefString(
				MyselfDetailsActivity.this, "myHead", "");
		ivHead.setImageURI(Uri.parse(strHead));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mTakePhoto.onActivityResult(requestCode, resultCode, data);
	}

	/*
	 * (non-Javadoc)
	 * 
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
		tvQQ = (TextView) findViewById(R.id.tvQQ);
		// TextView tvQQ, tvLastLoginTime, tvRegisterTime, tvEmail, tvPhoneNum;
		tvLastLoginTime = (TextView) findViewById(R.id.tvLastLoginTime);
		tvRegisterTime = (TextView) findViewById(R.id.tvRegisterTime);
		tvEmail = (TextView) findViewById(R.id.tvEmail);
		tvPhoneNum = (TextView) findViewById(R.id.tvPhoneNum);
		ivHead = (ImageView) findViewById(R.id.ivHead);
		tvNickname = (TextView) findViewById(R.id.tvNickname);

		btnLeft.setOnClickListener(this);
		btnCall.setOnClickListener(this);
		btnMsg.setOnClickListener(this);
		ivHead.setOnClickListener(this);
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
			this.finish();
			break;
		case R.id.ivHead:
			mTakePhoto.start();
			break;

		}
	}

}
