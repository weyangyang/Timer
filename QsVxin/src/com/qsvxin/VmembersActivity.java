package com.qsvxin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qsvxin.bean.EngineStaticBean;
import com.qsvxin.bean.LoginBean;
import com.qsvxin.bean.MemberBean;
import com.qsvxin.engine.GetMemberListData;
import com.qsvxin.net.interf.GetMembersParserData;
import com.qsvxin.net.interf.NetDataCallBackInterf;
import com.qsvxin.net.utils.RequestEngine;
import com.qsvxin.utils.CommonUtils;
import com.qsvxin.utils.CustomDialog;
import com.qsvxin.utils.PreferenceUtils;
import com.qsvxin.utils.QsAsyncTask;
import com.qsvxin.utils.QsConstants;
import com.qsvxin.view.slideview.XListView;
import com.qsvxin.view.slideview.XListView.IXListViewListener;

public class VmembersActivity extends BaseActivity implements
		OnItemClickListener, IXListViewListener {
	private XListView mListView;
	private SlideAdapter mAdapter;
	private ArrayList<MemberBean> beans = new ArrayList<MemberBean>();
	private String str_secretKey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vmembers_form);
		initView();
		str_secretKey = LoginBean.getInstance().getStrSecretKey();
		// str_secretKey = "qPC8sd3VvK@BtyvIy9JrUw==";
		if (!TextUtils.isEmpty(str_secretKey) && CommonUtils.checkNet(this)) {
			initDataFromNet(str_secretKey,true);
		} else {
			CommonUtils.createDialog(this, "提示", "网络异常，请检查网络连接");
			beans = initDataFromDB();
			mAdapter.notifyDataSetChanged();
		}
	}

	private void initDataFromNet(final String str_secretKey,boolean isShowDialog) {
		new GetMemberListData(this, str_secretKey, new GetMembersParserData() {

			@Override
			public void getParserErrData(int errCode, String errMsg) {
				// TODO 打印日志
				VmembersActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						onLoad();
					}
				});

			}

			@Override
			public void getNetErrData(int errCode, String errMsg) {
				if (errCode == QsConstants.CLIENT_PROTOCOL_EXCEPTION) {
//					initDataFromNet(str_secretKey,false);
					VmembersActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							CommonUtils
									.errorTipsDialog(
											VmembersActivity.this,
											VmembersActivity.this
													.getString(R.string.str_prompt),
													VmembersActivity.this
													.getString(R.string.str_net_timeout));
						}
					});
				} else {
					CommonUtils.errorTipsDialog(VmembersActivity.this,
							VmembersActivity.this
									.getString(R.string.str_prompt),
							VmembersActivity.this
									.getString(R.string.str_vmember_net_error));
				}
				VmembersActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						onLoad();
					}
				});
			}

			@Override
			public void getMembersSuccData(String succCode, String succMsg,
					final ArrayList<MemberBean> mArrayList) {
				VmembersActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						onLoad();
					}
				});
				// MemberBean bean = new
				// MemberBean();
				// bean.setStrSex("女");
				// bean.setStrMemberName("爵色");
				// bean.setStrCardID("123456");
				// bean.setIsvip(true);
				// bean.setIsjoin(true);
				// bean.setIntUsedIntegral(10);
				// mArrayList.add(bean);
				// MemberBean bean1 = new
				// MemberBean();
				// bean1.setStrSex("女");
				// bean1.setStrMemberName("爵色");
				// bean1.setStrCardID("123456");
				// bean1.setIsvip(true);
				// bean1.setIsjoin(true);
				// bean1.setIntUsedIntegral(20);
				// mArrayList.add(bean1);
				// MemberBean bean2 = new
				// MemberBean();
				// bean2.setStrSex("女");
				// bean2.setStrMemberName("爵色");
				// bean2.setStrCardID("123456");
				// bean2.setIsvip(true);
				// bean2.setIsjoin(true);
				// bean2.setIntUsedIntegral(30);
				// mArrayList.add(bean2);
				// MemberBean bean3 = new
				// MemberBean();
				// bean3.setStrSex("女");
				// bean3.setStrMemberName("爵色");
				// bean3.setStrCardID("123456");
				// bean3.setIsvip(true);
				// bean3.setIsjoin(true);
				// bean3.setIntUsedIntegral(40);
				// mArrayList.add(bean3);
				// MemberBean bean4 = new
				// MemberBean();
				// bean4.setStrSex("女");
				// bean4.setStrMemberName("爵色");
				// bean4.setStrCardID("123456");
				// bean4.setIsvip(false);
				// bean4.setIsjoin(true);
				// bean4.setIntUsedIntegral(8);
				// bean4.setIntResidueIntegral(200);
				// bean4.setStrExplain("流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。");
				// mArrayList.add(bean4);
				// MemberBean bean5 = new
				// MemberBean();
				// bean5.setStrSex("女");
				// bean5.setStrMemberName("爵色");
				// bean5.setStrCardID("123456");
				// bean5.setIsvip(true);
				// bean5.setIsjoin(true);
				// bean5.setIntUsedIntegral(8);
				// bean5.setIntResidueIntegral(200);
				// bean5.setStrMemberPhoneNum("10010");
				// bean5.setStrExplain("流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。流动的人口，心里心外徘徊。");
				// mArrayList.add(bean5);
				VmembersActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						beans = mArrayList;
						setDB();
						mAdapter.notifyDataSetChanged();
					}
				});

			}

			@Override
			public void getMembersErrData(String errCode, String errMsg) {
				// TODO 打印日志
				VmembersActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						onLoad();
					}
				});
			}
		},isShowDialog).excute();
	}
	
	private void setDB() {
		for(MemberBean bean : beans){
			bean.insert(MemberBean.COLUMN_OPEN_ID, bean.getStrOpenID());
		}
		
	}
	
	private ArrayList<MemberBean> initDataFromDB(){
		ArrayList<MemberBean> beans = (ArrayList<MemberBean>) new MemberBean().query(null, null, null, null, null, null);
		return beans;
	}
	class VmembersFormTask extends QsAsyncTask {
		public String str_secretKey;
		public NetDataCallBackInterf callBackInterf;
		public Context mContext;
		private CustomDialog mCustomDialog;

		public VmembersFormTask(Context mContext, String str_secretKey,
				NetDataCallBackInterf callBackInterf) {
			this.str_secretKey = str_secretKey;
			this.callBackInterf = callBackInterf;
			this.mContext = mContext;
		}

		@Override
		protected void onPreExectue() {
			mCustomDialog = CustomDialog.createLoadingDialog(mContext,
					VmembersActivity.this
							.getString(R.string.str_vmember_getting), true);
		}

		@Override
		protected void doInbackgroud() {
			RequestEngine.getInstance().getMemberInfo(str_secretKey,
					callBackInterf);
		}

		@Override
		protected void onPostExecute() {
			mCustomDialog.dismiss();
		}

	}

	private void initView() {
		mListView = (XListView) findViewById(R.id.mListView);
		mAdapter = new SlideAdapter();
		mListView.setAdapter(mAdapter);
		mListView.setFastScrollEnabled(true);
		mListView.setOnItemClickListener(this);
		mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(true);
		mListView.setXListViewListener(this);
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
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(VmembersActivity.this,
						R.layout.vmembers_form_item, null);
				holder.ivHead = (ImageView) convertView
						.findViewById(R.id.ivHead);
				holder.tvMemberName = (TextView) convertView
						.findViewById(R.id.tvMemberName);
				holder.tvIsJoin = (TextView) convertView
						.findViewById(R.id.tvIsJoin);
				holder.ivVipIcon = (ImageView) convertView
						.findViewById(R.id.ivVipIcon);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final MemberBean bean = beans.get(position);
			if (bean != null) {
				if (bean.isIsjoin()) {
					holder.tvIsJoin
							.setText(getString(R.string.str_vmember_is_join));
				} else if (!bean.isIsjoin()) {
					holder.tvIsJoin
							.setText(getString(R.string.str_vmember_is_not_join));
				}
				if (!TextUtils.isEmpty(bean.getStrMemberName())) {
					holder.tvMemberName.setText("昵称: "
							+ bean.getStrMemberName());
				} else {
					holder.tvMemberName.setText("昵称: "
							+ getString(R.string.str_vmember_visitor));
				}
				if (bean.getStrSex().equals("女")) {
					holder.ivHead
							.setBackgroundResource(R.drawable.icon_member_sex_female);
				} else if (bean.getStrSex().equals("男")) {
					holder.ivHead
							.setBackgroundResource(R.drawable.icon_member_sex_male);
				} else {
					holder.ivHead
							.setBackgroundResource(R.drawable.icon_member_sex_unknow);
				}
				if (!bean.isIsvip()) {
					if (bean.getStrSex().equals("女")) {
						holder.ivVipIcon
								.setBackgroundResource(R.drawable.icon_ordinary_female);
					} else if (bean.getStrSex().equals("男")) {
						holder.ivVipIcon
								.setBackgroundResource(R.drawable.icon_ordinary_male);
					}
				} else {
					if (bean.getIntUsedIntegral() < 10) {
						holder.ivVipIcon
								.setBackgroundResource(R.drawable.icon_vip);
					} else if (bean.getIntUsedIntegral() >= 10
							&& bean.getIntUsedIntegral() < 20) {
						holder.ivVipIcon
								.setBackgroundResource(R.drawable.icon_vip_10);
					} else if (bean.getIntUsedIntegral() >= 20
							&& bean.getIntUsedIntegral() < 30) {
						holder.ivVipIcon
								.setBackgroundResource(R.drawable.icon_vip_20);
					} else if (bean.getIntUsedIntegral() >= 30
							&& bean.getIntUsedIntegral() < 40) {
						holder.ivVipIcon
								.setBackgroundResource(R.drawable.icon_vip_30);
					} else if (bean.getIntUsedIntegral() >= 40) {
						holder.ivVipIcon
								.setBackgroundResource(R.drawable.icon_vip_40);
					}
				}

			}
			return convertView;
		}
	}

	// public static class VmembersItem extends BaseListViewItem {
	// public Data data;
	//
	// public static class Data implements Serializable {
	// private static final long serialVersionUID = 1L;
	// public String strMemberName;
	// public String strCardId;
	// public int intResidueIntegral;
	// public int intUsedIntegral;
	// public String strSex;
	// public boolean isvip;
	// public boolean isjoin;
	// public String strMemberPhoneNum;
	// }
	//
	// public static VmembersItem fromBean(MemberBean bean) {
	// VmembersItem item = new VmembersItem();
	// item.data = new Data();
	// item.data.strMemberName = bean.getStrMemberName();
	// item.data.strCardId = bean.getStrCardID();
	// item.data.intResidueIntegral = bean.getIntResidueIntegral();
	// item.data.intUsedIntegral = bean.getIntUsedIntegral();
	// item.data.strSex = bean.getStrSex();
	// item.data.isvip = bean.isIsvip();
	// item.data.isjoin = bean.isIsjoin();
	// item.data.strMemberPhoneNum = bean.getStrMemberPhoneNum();
	// return item;
	// }
	// }

	private static class ViewHolder {
		private ImageView ivHead;
		private TextView tvMemberName;
		private TextView tvIsJoin;
		private ImageView ivVipIcon;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position < 1 || position > beans.size()) {
			return;
		}
		EngineStaticBean.getInstance().setmMemberBean(beans.get(position - 1));
		Intent intent = new Intent(this, VmembersDetailsActivity.class);
		startActivity(intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qsvxin.view.slideview.XListView.IXListViewListener#onRefresh()
	 */
	@Override
	public void onRefresh() {
		String strTime = PreferenceUtils.getPrefString(VmembersActivity.this, "vmembers_refresh", "");
		mListView.setHeaderTime(strTime);
		initDataFromNet(str_secretKey,false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qsvxin.view.slideview.XListView.IXListViewListener#onLoadMore()
	 */
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}

	private void onLoad() {
		PreferenceUtils.setPrefString(VmembersActivity.this, "vmembers_refresh", new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss")
				.format(new Date()));
		mListView.stopRefresh();
		mListView.stopLoadMore();
	}
}
