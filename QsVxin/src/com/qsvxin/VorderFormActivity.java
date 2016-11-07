package com.qsvxin;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qsvxin.bean.LoginBean;
import com.qsvxin.bean.VorderFormBean;
import com.qsvxin.engine.AcceptOrderForm;
import com.qsvxin.engine.GetOrderForm;
import com.qsvxin.net.interf.AfterAcceptOFParserData;
import com.qsvxin.net.interf.AfterVorderFormParserData;
import com.qsvxin.utils.CommonUtils;
import com.qsvxin.utils.QsConstants;
import com.qsvxin.view.slideview.ListViewCompat;
import com.qsvxin.view.slideview.SlideView;
import com.qsvxin.view.slideview.SlideView.OnSlideListener;

public class VorderFormActivity extends BaseActivity implements
		OnItemClickListener, OnSlideListener {
	public static VorderFormActivity mActivity;
	private ListViewCompat mListView;
	private SlideAdapter mAdapter;
	private ArrayList<VorderFormBean> beans = new ArrayList<VorderFormBean>();
	private SlideView mLastSlideViewWithStatusOn;
	private String str_secretKey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vorder_form);
		mActivity = this;
		initView();
		str_secretKey = LoginBean.getInstance().getStrSecretKey();
//		 str_secretKey = "qPC8sd3VvK@BtyvIy9JrUw==";
		if (!TextUtils.isEmpty(str_secretKey) && CommonUtils.checkNet(this)) {
			initDataFromNet(str_secretKey);
		} else {
			CommonUtils.createDialog(this, "提示", "网络异常，请检查网络连接");
			beans = initDataFromDB();
			mAdapter.notifyDataSetChanged();
		}
	}

	/* (non-Javadoc)
	 * @see com.qsvxin.BaseActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mActivity = null;
	}
	public void initDataFromNet(String str_secretKey) {
		new GetOrderForm(this, str_secretKey, new AfterVorderFormParserData() {

			@Override
			public void getVorderFormSuccData(String succCode, String succMsg,
					final ArrayList<VorderFormBean> mArrayList) {
				VorderFormActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						beans = mArrayList;
						setDB();
						mAdapter.notifyDataSetChanged();
					}
				});
			}

			@Override
			public void getVorderFormErrData(String errCode, String errMsg) {
				// TODO 打印日志

			}

			@Override
			public void getParserErrData(int errCode, String errMsg) {
				// TODO 打印日志

			}

			@Override
			public void getNetErrData(int errCode, String errMsg) {
				if (errCode == QsConstants.CLIENT_PROTOCOL_EXCEPTION) {
//					initDataFromNet(str_secretKey,false);
					VorderFormActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							CommonUtils
									.errorTipsDialog(
											VorderFormActivity.this,
											VorderFormActivity.this
													.getString(R.string.str_prompt),
													VorderFormActivity.this
													.getString(R.string.str_net_timeout));
						}
					});
				} else {
					VorderFormActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							CommonUtils
									.errorTipsDialog(
											VorderFormActivity.this,
											VorderFormActivity.this
													.getString(R.string.str_prompt),
													VorderFormActivity.this
													.getString(R.string.str_vmember_net_error));
						}
					});
				}

			}
		}).excute();
	}

	private void setDB() {
		for (VorderFormBean vorderFormBean : beans) {
			vorderFormBean.insert(VorderFormBean.COLUMN_ORDERFORM_ID, vorderFormBean.getStrOrderFormID());
		}
	}
	
	private ArrayList<VorderFormBean> initDataFromDB(){
		ArrayList<VorderFormBean> beans = (ArrayList<VorderFormBean>) new VorderFormBean().query(null, null, null, null, null, null);
		return beans;
	}

	private void initView() {
		mListView = (ListViewCompat) findViewById(R.id.mListView);
		// TODO:xxx
		mAdapter = new SlideAdapter();
		mListView.setAdapter(mAdapter);
		mListView.setFastScrollEnabled(true);
		mListView.setOnItemClickListener(this);
	}

	private class SlideAdapter extends BaseAdapter {
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
			ViewHolder holder;
			SlideView slideView = (SlideView) convertView;
			if (slideView == null) {
				View itemView = View.inflate(VorderFormActivity.this,
						R.layout.vorder_form_item, null);
				slideView = new SlideView(VorderFormActivity.this);
				slideView.setContentView(itemView);
				holder = new ViewHolder(slideView);
				slideView.setOnSlideListener(VorderFormActivity.this);
				slideView.setTag(holder);
			} else {
				holder = (ViewHolder) slideView.getTag();
			}
			final VorderFormBean bean = beans.get(position);
			bean.slideView = slideView;
			bean.slideView.shrink();
			if (bean.getStrOrderFormStatus().equals("1")) {
				bean.slideView.setRightButtonText(R.string.str_accept_order);
				holder.tv2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						new AcceptOrderForm(VorderFormActivity.this, str_secretKey, bean.getStrOrderFormID(), new AfterAcceptOFParserData() {
							
							@Override
							public void getSuccData(String succCode, final String succMsg) {
								VorderFormActivity.this.runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										toast(succMsg);
									}
								});
							}
							
							@Override
							public void getSuccData(String succCode, String succMsg,
									ArrayList<VorderFormBean> mArrayList) {
								
							}
							
							@Override
							public void getParserErrData(int errCode, String errMsg) {
								// TODO 打印日志
							}
							
							@Override
							public void getNetErrData(int errCode, String errMsg) {
								if (errCode == QsConstants.CLIENT_PROTOCOL_EXCEPTION) {
//									initDataFromNet(str_secretKey,false);
									VorderFormActivity.this.runOnUiThread(new Runnable() {
										@Override
										public void run() {
											CommonUtils
													.errorTipsDialog(
															VorderFormActivity.this,
															VorderFormActivity.this
																	.getString(R.string.str_prompt),
																	VorderFormActivity.this
																	.getString(R.string.str_net_timeout));
										}
									});
								} else {
									VorderFormActivity.this.runOnUiThread(new Runnable() {
										@Override
										public void run() {
											CommonUtils
													.errorTipsDialog(
															VorderFormActivity.this,
															VorderFormActivity.this
																	.getString(R.string.str_prompt),
																	VorderFormActivity.this
																	.getString(R.string.str_vmember_net_error));
										}
									});
								}
							}
							
							@Override
							public void getErrData(String errCode, String errMsg) {
								// TODO 打印日志
							}
						}).excute();
					}
				});
			}else{
				holder.tv2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(VorderFormActivity.this, SendOrderActivity.class);
						intent.putExtra("secretKey", str_secretKey);
						intent.putExtra("orderId", bean.getStrOrderFormID());
						startActivity(intent);
					}
				});
			}

			// TODO
			// holder.ivHead.setImageResource(item.iconRes);
			holder.tvName.setText("订单ID:  " + bean.getStrOrderFormID());
			holder.tvContent.setText("买家留言:  " + bean.getStrBuyerLeaveMsg());
			holder.tvDate.setText(bean.getStrCreateTime());
			holder.tv1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//传入服务， parse（）解析号码
		            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bean.getStrBuyerPhoneNum()));
		            //通知activtity处理传入的call服务
		            VorderFormActivity.this.startActivity(intent);
				}
			});
			return slideView;
		}
	}

	private static class ViewHolder {
		private ImageView ivHead;
		private TextView tvName;
		private TextView tvContent;
		private TextView tvDate;
		public TextView tv1;
		public TextView tv2;

		ViewHolder(View view) {
			ivHead = (ImageView) view.findViewById(R.id.ivHead);
			tvName = (TextView) view.findViewById(R.id.tvName);
			tvContent = (TextView) view.findViewById(R.id.tvContent);
			tvDate = (TextView) view.findViewById(R.id.tvDate);
			tv1 = (TextView) view.findViewById(R.id.tvSelect);
			tv2 = (TextView) view.findViewById(R.id.tvSelect2);
			tv1.setText("联系");
			tv2.setText("发货");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (mLastSlideViewWithStatusOn == null
				|| mLastSlideViewWithStatusOn.getScrollX() == 0) {
			// TODO点击item跳转
			Intent intent = new Intent(this, VorderDetailsActivity.class);
			intent.putExtra("Data", beans.get(position));
			// intent.putExtra(CommonConstants.DATA,
			// buyerList.get(position).data);
			// intent.putExtra("counting", currCountingSecond);
			startActivity(intent);
		} else {
			mLastSlideViewWithStatusOn.shrink();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			finish();
		}
	}

	@Override
	public void onSlide(View view, int status) {
		if (mLastSlideViewWithStatusOn != null
				&& mLastSlideViewWithStatusOn != view) {
			mLastSlideViewWithStatusOn.shrink();
		}

		if (status == SLIDE_STATUS_ON) {
			mLastSlideViewWithStatusOn = (SlideView) view;
		}
	}
}
