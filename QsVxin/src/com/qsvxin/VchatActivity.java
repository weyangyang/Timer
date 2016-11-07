package com.qsvxin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.qsvxin.bean.EngineStaticBean;
import com.qsvxin.bean.GetUserMsgListBean;
import com.qsvxin.bean.LoginBean;
import com.qsvxin.engine.GetUserMsgList;
import com.qsvxin.net.interf.GetUserMsgListParserData;
import com.qsvxin.utils.CommonUtils;
import com.qsvxin.utils.ImageLoaderManager;
import com.qsvxin.utils.PreferenceUtils;
import com.qsvxin.utils.QsConstants;
import com.qsvxin.view.slideview.XListView;
import com.qsvxin.view.slideview.XListView.IXListViewListener;
import com.sge.imageloader.core.DisplayImageOptions;
import com.sge.imageloader.core.ImageLoader;

/**
 * @Description: TODO
 * @author liang_xs
 * @date 2014-9-22
 */
public class VchatActivity extends BaseActivity implements OnItemClickListener, IXListViewListener {
	public static VchatActivity mActivity;
	private XListView mListView;
	private MyAdapter mAdapter;
	private String str_secretKey;
	private ArrayList<GetUserMsgListBean> beans = new ArrayList<GetUserMsgListBean>();
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	private EditText editSearch;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qsvxin.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vchat_form);
		mActivity = this;
		initView();
		mImageLoader = ImageLoaderManager.getImageLoader(this);
		mDisplayImageOptions = ImageLoaderManager.getImageOptions(this);
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

	public void initDataFromNet(final String str_secretKey,boolean isShowDialog) {
		new GetUserMsgList(this, str_secretKey, new GetUserMsgListParserData() {

			@Override
			public void getUserMsgListSuccData(String succCode, String succMsg,
					final ArrayList<GetUserMsgListBean> mArrayList) {
				VchatActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						onLoad();
					}
				});
				VchatActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						beans = mArrayList;
						setDB();
						mAdapter.notifyDataSetChanged();
					}
				});
			}

			@Override
			public void getUserMsgListErrData(String errCode,
					final String errMsg) {
				// TODO 打印日志
				VchatActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						onLoad();
					}
				});
			}

			@Override
			public void getParserErrData(int errCode, final String errMsg) {
				// TODO 打印日志
				VchatActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						onLoad();
					}
				});
			}

			@Override
			public void getNetErrData(int errCode, final String errMsg) {
				VchatActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						onLoad();
					}
				});
				if (errCode == QsConstants.CLIENT_PROTOCOL_EXCEPTION) {
//					initDataFromNet(str_secretKey,false);
					VchatActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							CommonUtils
									.errorTipsDialog(
											VchatActivity.this,
											VchatActivity.this
													.getString(R.string.str_prompt),
											VchatActivity.this
													.getString(R.string.str_net_timeout));
						}
					});
				} else {
					VchatActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							CommonUtils
									.errorTipsDialog(
											VchatActivity.this,
											VchatActivity.this
													.getString(R.string.str_prompt),
											VchatActivity.this
													.getString(R.string.str_vmember_net_error));
						}
					});
				}
			}
		},isShowDialog).excute();

	}

	private void setDB() {
		for (GetUserMsgListBean bean : beans) {
			bean.insert(GetUserMsgListBean.COLUMN_NICK_NAME, bean.getStrNickName());
		}
	}

	private ArrayList<GetUserMsgListBean> initDataFromDB(){
		ArrayList<GetUserMsgListBean> beans = (ArrayList<GetUserMsgListBean>) new GetUserMsgListBean().query(null, null, null, null, null, null);
		return beans;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qsvxin.BaseActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mActivity = null;
	}

	private void initView() {
		mListView = (XListView) findViewById(R.id.mListView);
		editSearch = (EditText) findViewById(R.id.editText1);
		editSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String userName = s.toString();
				if (!TextUtils.isEmpty(userName)) {
					beans.clear();
					beans = (ArrayList<GetUserMsgListBean>) new GetUserMsgListBean()
							.query(null, GetUserMsgListBean.COLUMN_NICK_NAME
									+ " like '%" + userName + "%'", null, null,
									null, null);
					mAdapter.notifyDataSetChanged();
				} else {
					beans.clear();
					beans = (ArrayList<GetUserMsgListBean>) new GetUserMsgListBean()
							.query(null, null, null, null, null, null);
					mAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		editSearch.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				return true;
			}
		});
		mAdapter = new MyAdapter();
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(true);
		mListView.setXListViewListener(this);
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
				convertView = View.inflate(VchatActivity.this,
						R.layout.vchat_form_item, null);
				holder = new ViewHolder();
				holder.ivHead = (ImageView) convertView
						.findViewById(R.id.ivHead);
				holder.tvChatName = (TextView) convertView
						.findViewById(R.id.tvChatName);
				holder.tvChatCity = (TextView) convertView
						.findViewById(R.id.tvChatCity);
				holder.tvChatCount = (TextView) convertView
						.findViewById(R.id.tvChatCount);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final GetUserMsgListBean bean = beans.get(position);
			holder.tvChatName.setText(bean.getStrNickName());
			holder.tvChatCity.setText(bean.getStrCity());
			if (bean.getStrReplyMsgCount().equals("0")) {
				holder.tvChatCount.setVisibility(View.GONE);
			} else {
				holder.tvChatCount.setVisibility(View.VISIBLE);
				holder.tvChatCount.setText(bean.getStrReplyMsgCount());
			}
			mImageLoader.displayImage(bean.getStrHeadUrl(), holder.ivHead,
					mDisplayImageOptions);
			holder.ivHead.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CommonUtils.headZoom(VchatActivity.this, bean.getStrHeadUrl());
				}
			});
			return convertView;
		}
	}

	private static class ViewHolder {
		private ImageView ivHead;
		private TextView tvChatName;
		private TextView tvChatCount;
		private TextView tvChatCity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position < 1 || position > beans.size()) {
			return;
		}
		beans.get(position-1).setStrReplyMsgCount("0");
		beans.get(position-1).update();
		EngineStaticBean.getInstance().setUserMsgListBean(beans.get(position - 1));
		Intent intent = new Intent(this, VchatDeatilsActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		mAdapter.notifyDataSetChanged();
	}
	
	/* (non-Javadoc)
	 * @see com.qsvxin.view.slideview.XListView.IXListViewListener#onRefresh()
	 */
	@Override
	public void onRefresh() {
		String strTime = PreferenceUtils.getPrefString(VchatActivity.this, "vchat_refresh", "");
		mListView.setHeaderTime(strTime);
		initDataFromNet(str_secretKey,false);
	}

	/* (non-Javadoc)
	 * @see com.qsvxin.view.slideview.XListView.IXListViewListener#onLoadMore()
	 */
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
	
	private void onLoad() {
		PreferenceUtils.setPrefString(VchatActivity.this, "vchat_refresh", new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss")
				.format(new Date()));
		mListView.stopRefresh();
		mListView.stopLoadMore();
	}
}
