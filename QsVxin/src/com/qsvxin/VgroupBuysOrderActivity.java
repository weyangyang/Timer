package com.qsvxin;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qsvxin.bean.EngineStaticBean;
import com.qsvxin.bean.GroupBuysFormBean;
import com.qsvxin.bean.GroupBuysListBean;
import com.qsvxin.bean.LoginBean;
import com.qsvxin.engine.CompleteGroupBuysOrderForm;
import com.qsvxin.engine.GetGroupBuysOrderForm;
import com.qsvxin.net.interf.CompleteGBorderParserData;
import com.qsvxin.net.interf.GetGroupBuysFormParserData;
import com.qsvxin.utils.CommonUtils;
import com.qsvxin.utils.QsConstants;

/**
 * @Description: TODO
 * @author liang_xs
 * @date 2014-9-22
 */
public class VgroupBuysOrderActivity extends BaseActivity implements
		OnClickListener {
	private Button btnLeft;
	private TextView tvMainTitle;
	private ListView mListView;
	private MyAdapter mAdapter;
	private GroupBuysListBean bean;
	private ArrayList<GroupBuysFormBean> beans = new ArrayList<GroupBuysFormBean>();
	private String str_secretKey;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qsvxin.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vgroupbuy_order);
		initView();
		mAdapter = new MyAdapter();
		mListView.setAdapter(mAdapter);
		str_secretKey = LoginBean.getInstance().getStrSecretKey();
		// str_secretKey = "qPC8sd3VvK@BtyvIy9JrUw==";
		bean = EngineStaticBean.getInstance().getGroupBuysListBean();
		if (bean == null) {
			toast("数据错误，请稍候重试");
			return;
		}
		tvMainTitle.setText(bean.getStrGroupBuysTitle());
		if (!TextUtils.isEmpty(str_secretKey) && CommonUtils.checkNet(this)) {
			initDataFromNet(str_secretKey);
		} else {
			CommonUtils.createDialog(this, "提示", "网络异常，请检查网络连接");
		}
	}

	private void initDataFromNet(String str_secretKey) {
		new GetGroupBuysOrderForm(this, str_secretKey,
				bean.getStrGroupBuysID(), new GetGroupBuysFormParserData() {

					@Override
					public void getParserErrData(int errCode, String errMsg) {
						// TODO 打印日志
					}

					@Override
					public void getNetErrData(int errCode, String errMsg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void GetGroupBuysFormSuccData(String succCode,
							String succMsg,
							final ArrayList<GroupBuysFormBean> mArrayList) {
						VgroupBuysOrderActivity.this
								.runOnUiThread(new Runnable() {
									@Override
									public void run() {
										beans = mArrayList;
										mAdapter.notifyDataSetChanged();
									}
								});
					}

					@Override
					public void GetGroupBuysFormErrData(String errCode,
							String errMsg) {
						// TODO 打印日志
					}
				}).excute();
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
		EngineStaticBean.getInstance().setGroupBuysListBean(null);
	}

	private void initView() {
		btnLeft = (Button) findViewById(R.id.btnLeft);
		tvMainTitle = (TextView) findViewById(R.id.tvTitle);
		mListView = (ListView) findViewById(R.id.mListView);
		btnLeft.setOnClickListener(this);
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
		}
	}

	private class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return beans.size();
		}

		@Override
		public Object getItem(int position) {
			return beans.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(VgroupBuysOrderActivity.this,
						R.layout.vgroupbuy_order_item, null);
				holder = new ViewHolder();
				holder.ivHead = (ImageView) convertView
						.findViewById(R.id.ivHead);
				holder.tvBuyerName = (TextView) convertView
						.findViewById(R.id.tvBuyerName);
				holder.tvBuyerPhoneNum = (TextView) convertView
						.findViewById(R.id.tvBuyerPhoneNum);
				holder.tvOrderFormID = (TextView) convertView
						.findViewById(R.id.tvOrderFormID);
				holder.tvBuyCount = (TextView) convertView
						.findViewById(R.id.tvBuyCount);
				holder.tvSN = (TextView) convertView.findViewById(R.id.tvSN);
				holder.tvOrderFormStatus = (TextView) convertView
						.findViewById(R.id.tvOrderFormStatus);
				holder.btnCall = (Button) convertView
						.findViewById(R.id.btnCall);
				holder.btnMsg = (Button) convertView.findViewById(R.id.btnMsg);
				holder.btnFinish = (Button) convertView
						.findViewById(R.id.btnFinish);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final GroupBuysFormBean bean = beans.get(position);
			holder.tvBuyerName.setText("买家: " + bean.getStrBuyerName());
			holder.tvBuyerPhoneNum.setText("电话: " + bean.getStrBuyerPhoneNum());
			holder.tvOrderFormID.setText(bean.getStrOrderFormID());
			holder.tvBuyCount.setText(bean.getStrBuyCount());
			holder.tvSN.setText(bean.getStrSN());
			if (bean.getStrOrderFormStatus().equals("1")) {
				holder.tvOrderFormStatus.setText("未完成");
				holder.btnFinish.setVisibility(View.VISIBLE);
			} else {
				holder.tvOrderFormStatus.setText("已完成");
				holder.btnFinish.setVisibility(View.GONE);
			}
			holder.btnCall.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!TextUtils.isEmpty(bean.getStrBuyerPhoneNum())) {
						startActivity(new Intent(Intent.ACTION_CALL, Uri
								.parse("tel:" + bean.getStrBuyerPhoneNum())));
					} else {
						CommonUtils.toast(getApplicationContext(),
								getString(R.string.str_vmember_phone_is_null));
					}
				}
			});
			holder.btnMsg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!TextUtils.isEmpty(bean.getStrBuyerPhoneNum())) {
						startActivity(new Intent(Intent.ACTION_SENDTO, Uri
								.parse("smsto:" + bean.getStrBuyerPhoneNum())));
					} else {
						CommonUtils.toast(getApplicationContext(),
								getString(R.string.str_vmember_phone_is_null));
					}
				}
			});
			holder.btnFinish.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new CompleteGroupBuysOrderForm(
							VgroupBuysOrderActivity.this, str_secretKey, bean
									.getStrOrderFormID(),
							new CompleteGBorderParserData() {

								@Override
								public void getParserErrData(final int errCode,
										final String errMsg) {
									// TODO 打印日志
								}

								@Override
								public void getNetErrData(final int errCode,
										final String errMsg) {
									if (errCode == QsConstants.CLIENT_PROTOCOL_EXCEPTION) {
//										initDataFromNet(str_secretKey,false);
										VgroupBuysOrderActivity.this.runOnUiThread(new Runnable() {
											@Override
											public void run() {
												CommonUtils
														.errorTipsDialog(
																VgroupBuysOrderActivity.this,
																VgroupBuysOrderActivity.this
																		.getString(R.string.str_prompt),
																		VgroupBuysOrderActivity.this
																		.getString(R.string.str_net_timeout));
											}
										});
									} else {
										VgroupBuysOrderActivity.this
												.runOnUiThread(new Runnable() {
													@Override
													public void run() {
														CommonUtils
																.toast(getApplicationContext(),
																		getString(R.string.str_vmember_net_error));
													}
												});
									}
								}

								@Override
								public void CompleteGBorderSuccData(
										final String succCode,
										final String succMsg) {
									VgroupBuysOrderActivity.this
											.runOnUiThread(new Runnable() {
												@Override
												public void run() {
													CommonUtils
															.toast(getApplicationContext(),
																	succCode
																			+ "\n"
																			+ succMsg);
													holder.tvOrderFormStatus
															.setText("已完成");
													holder.btnFinish
															.setVisibility(View.GONE);
												}
											});
								}

								@Override
								public void CompleteGBorderErrData(
										final String errCode,
										final String errMsg) {
									// TODO 打印日志
								}
							}).excute();
				}
			});
			return convertView;
		}
	}

	private static class ViewHolder {
		private ImageView ivHead;
		private TextView tvBuyerName;
		private TextView tvBuyerPhoneNum;
		private TextView tvOrderFormID;
		private TextView tvBuyCount;
		private TextView tvSN;
		private TextView tvOrderFormStatus;
		private Button btnCall, btnMsg, btnFinish;
	}
}
