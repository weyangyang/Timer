package com.qsvxin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import com.qsvxin.bean.GroupBuysListBean;
import com.qsvxin.bean.LoginBean;
import com.qsvxin.bean.VorderFormBean;
import com.qsvxin.engine.GetGroupBuysList;
import com.qsvxin.net.interf.GroupBuysListParserData;
import com.qsvxin.utils.CommonUtils;
import com.qsvxin.utils.PreferenceUtils;
import com.qsvxin.utils.QsConstants;
import com.qsvxin.view.slideview.XListView;
import com.qsvxin.view.slideview.XListView.IXListViewListener;

public class VgroupBuyActivity extends BaseActivity implements
		OnItemClickListener, IXListViewListener {
	private XListView mListView;
	private SlideAdapter mAdapter;
	private ArrayList<GroupBuysListBean> beans = new ArrayList<GroupBuysListBean>();
	private String str_secretKey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vgroupbuy_form);
		initView();
		str_secretKey = LoginBean.getInstance().getStrSecretKey();
		// str_secretKey = "qPC8sd3VvK@BtyvIy9JrUw==";
		if (!TextUtils.isEmpty(str_secretKey) && CommonUtils.checkNet(this)) {
			initDataFromNet(str_secretKey);
		} else {
			CommonUtils.createDialog(this, "提示", "网络异常，请检查网络连接");
			beans = initDataFromDB();
			mAdapter.notifyDataSetChanged();
		}
	}

	private void initDataFromNet(final String str_secretKey) {
		// new VmembersFormTask(this, str_secretKey, netCallBack).execute();
		new GetGroupBuysList(this, str_secretKey,
				new GroupBuysListParserData() {

					@Override
					public void getParserErrData(int errCode, String errMsg) {
						// TODO 打印日志
						VgroupBuyActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								onLoad();
							}
						});

					}

					@Override
					public void getNetErrData(int errCode, String errMsg) {
						if (errCode == QsConstants.CLIENT_PROTOCOL_EXCEPTION) {
//							initDataFromNet(str_secretKey,false);
							VgroupBuyActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									CommonUtils
											.errorTipsDialog(
													VgroupBuyActivity.this,
													VgroupBuyActivity.this
															.getString(R.string.str_prompt),
															VgroupBuyActivity.this
															.getString(R.string.str_net_timeout));
								}
							});
						} else {
							CommonUtils
									.errorTipsDialog(
											VgroupBuyActivity.this,
											VgroupBuyActivity.this
													.getString(R.string.str_prompt),
											VgroupBuyActivity.this
													.getString(R.string.str_vmember_net_error));
						}
						VgroupBuyActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								onLoad();
							}
						});
					}

					@Override
					public void getGroupBuysListSuccData(String succCode,
							String succMsg,
							final ArrayList<GroupBuysListBean> mArrayList) {
						VgroupBuyActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								onLoad();
								beans = mArrayList;
								setDB();
								mAdapter.notifyDataSetChanged();
							}

						});
					}

					@Override
					public void getGroupBuysListErrData(String errCode,
							String errMsg) {
						// TODO 打印日志
						VgroupBuyActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								onLoad();
							}
						});
					}
				}).excute();

	}

	private void setDB() {
		for (GroupBuysListBean bean : beans) {
			bean.insert(GroupBuysListBean.COLUMN_GROUP_BUYS_ID, GroupBuysListBean.COLUMN_GROUP_BUYS_ID);
		}
	}

	private ArrayList<GroupBuysListBean> initDataFromDB(){
		ArrayList<GroupBuysListBean> beans = (ArrayList<GroupBuysListBean>) new GroupBuysListBean().query(null, null, null, null, null, null);
		return beans;
	}
	private void initView() {
		mListView = (XListView) findViewById(R.id.mListView);
		mAdapter = new SlideAdapter();
		mListView.setAdapter(mAdapter);
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
				convertView = View.inflate(VgroupBuyActivity.this,
						R.layout.vgroupbuy_form_item, null);
				holder.ivHead = (ImageView) convertView
						.findViewById(R.id.ivHead);
				holder.tvGroupBuyId = (TextView) convertView
						.findViewById(R.id.tvGroupBuyId);
				holder.tvGroupBuyTitle = (TextView) convertView
						.findViewById(R.id.tvGroupBuyTitle);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			GroupBuysListBean bean = beans.get(position);
			holder.tvGroupBuyId.setText("团购ID: " + bean.getStrGroupBuysID()
					+ "");
			holder.tvGroupBuyTitle.setText("团购标题: "
					+ bean.getStrGroupBuysTitle());
			return convertView;
		}
	}

	private static class ViewHolder {
		private ImageView ivHead;
		private TextView tvGroupBuyId;
		private TextView tvGroupBuyTitle;

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position < 1 || position > beans.size()) {
			return;
		}
		EngineStaticBean.getInstance().setGroupBuysListBean(
				beans.get(position - 1));
		Intent intent = new Intent(this, VgroupBuysOrderActivity.class);
		startActivity(intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qsvxin.view.slideview.XListView.IXListViewListener#onRefresh()
	 */
	@Override
	public void onRefresh() {
		String strTime = PreferenceUtils.getPrefString(VgroupBuyActivity.this, "vgroupbuy_refresh", "");
		mListView.setHeaderTime(strTime);
		initDataFromNet(str_secretKey);
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
		PreferenceUtils.setPrefString(VgroupBuyActivity.this, "vgroupbuy_refresh", new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss")
				.format(new Date()));
		mListView.stopRefresh();
		mListView.stopLoadMore();
	}
}
