package com.qsvxin;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qsvxin.adapter.VorderDetailAdapter;
import com.qsvxin.bean.BuyGoodsBean;
import com.qsvxin.bean.LoginBean;
import com.qsvxin.bean.VorderFormBean;
import com.qsvxin.engine.AcceptOrderForm;
import com.qsvxin.engine.DeleteStoreOrderForm;
import com.qsvxin.net.interf.AfterAcceptOFParserData;
import com.qsvxin.net.interf.DeleteStoreOrderParserData;
import com.qsvxin.utils.CommonUtils;
import com.qsvxin.utils.QsConstants;
import com.qsvxin.view.slideview.ScrollListView;

/**
 * @Description: TODO
 * @author liang_xs
 * @date 2014-9-22
 */
public class VorderDetailsActivity extends BaseActivity implements
		OnClickListener {
	private Button btnLeft, btnDalete, btnOrder, btnCall, btnAccept;
	private ScrollListView listView;
	private ScrollView scrollView;
	private List<BuyGoodsBean> list = new ArrayList<BuyGoodsBean>();
	private String str_secretKey, orderId, phone;
	private TextView tvOrderId, tvCreatTime, tvOfstatus, tvFinishTime,
			tvPaystatus, tvPayTime, tvDistrstatus, tvDistrTime, tvBuyername,
			tvBuyerphone, tvText, tvBuyeraddress, tvPaymode, tvMoney;
	private boolean typeOrder = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qsvxin.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vorder_details);
		str_secretKey = LoginBean.getInstance().getStrSecretKey();
		// str_secretKey = "qPC8sd3VvK@BtyvIy9JrUw==";
		initView();
		initData();
	}

	private void initView() {
		btnLeft = (Button) findViewById(R.id.btnLeft);
		btnDalete = (Button) findViewById(R.id.btn_delete_detail);
		btnOrder = (Button) findViewById(R.id.btn_store_order_shipments);
		btnCall = (Button) findViewById(R.id.btn_make_call);
		btnAccept = (Button) findViewById(R.id.btn_accept_order);
		btnCall.setOnClickListener(this);
		btnDalete.setOnClickListener(this);
		btnOrder.setOnClickListener(this);
		btnAccept.setOnClickListener(this);
		btnLeft.setOnClickListener(this);
		listView = (ScrollListView) findViewById(R.id.scroll_list);
		tvOrderId = (TextView) findViewById(R.id.tvOrderId);
		tvCreatTime = (TextView) findViewById(R.id.tvCreatTime);
		tvOfstatus = (TextView) findViewById(R.id.tvOfstatus);
		tvFinishTime = (TextView) findViewById(R.id.tvFinishTime);
		tvPaystatus = (TextView) findViewById(R.id.tvPaystatus);
		tvPayTime = (TextView) findViewById(R.id.tvPayTime);
		tvDistrstatus = (TextView) findViewById(R.id.tvDistrstatus);
		tvDistrTime = (TextView) findViewById(R.id.tvDistrTime);
		tvBuyername = (TextView) findViewById(R.id.tvBuyername);
		tvBuyerphone = (TextView) findViewById(R.id.tvBuyerphone);
		tvText = (TextView) findViewById(R.id.tvText);
		tvBuyeraddress = (TextView) findViewById(R.id.tvBuyeraddress);
		tvPaymode = (TextView) findViewById(R.id.tvPaymode);
		tvMoney = (TextView) findViewById(R.id.tvMoney);
		scrollView = (ScrollView) findViewById(R.id.scrollView);

	}

	private void initData() {
		VorderFormBean vorderFormBean = (VorderFormBean) getIntent()
				.getSerializableExtra("Data");
		System.out.println(vorderFormBean.toString());
		list = vorderFormBean.getmArrayListBuyGoods();
		for(int i = 0 ; i < 3 ; i++){
			BuyGoodsBean bean = new BuyGoodsBean();
			bean.setStrGoodsCount("a"+i);
			bean.setStrGoodsID("a"+1);
			bean.setStrGoodsNorms("a"+i);
			bean.setStrGoodsNormsName("a"+i);
			bean.setStrGoodsPrice("a"+i);
			list.add(bean);
		}
		VorderDetailAdapter vorderDetailAdapter = new VorderDetailAdapter(this,
				list);
		listView.setAdapter(vorderDetailAdapter);
		scrollView.post(new Runnable() { 
	        public void run() { 
	        	scrollView.fullScroll(ScrollView.FOCUS_UP); 
	        } 
		}); 
		orderId = vorderFormBean.getStrOrderFormID();
		tvOrderId.setText(orderId);
		tvCreatTime.setText(vorderFormBean.getStrCreateTime());
		tvFinishTime.setText(vorderFormBean.getStrFinishOrderTime());
		tvPayTime.setText(vorderFormBean.getStrPayTime());
		tvDistrTime.setText(vorderFormBean.getStrShipmentsTime());
		tvBuyername.setText(vorderFormBean.getStrBuyerName());
		phone = vorderFormBean.getStrBuyerPhoneNum();
		tvBuyerphone.setText(phone);
		tvMoney.setText(vorderFormBean.getStrTotalPrice() + " 元");
		tvText.setText(vorderFormBean.getStrBuyerLeaveMsg());
		tvBuyeraddress.setText(vorderFormBean.getStrBuyerAddress());
		switch (Integer.parseInt(vorderFormBean.getStrOrderFormStatus())) {
		case 1:
			tvOfstatus.setText("创建");
			btnAccept.setVisibility(View.VISIBLE);
			btnDalete.setVisibility(View.VISIBLE);
			typeOrder = false;
			break;
		case 2:
			tvOfstatus.setText("付款");
			break;
		case 3:
			tvOfstatus.setText("退款");
			break;
		case 4:
			tvOfstatus.setText("发货");
			break;
		case 5:
			tvOfstatus.setText("退货");
			break;
		case 6:
			tvOfstatus.setText("收货");
			break;
		case 7:
			tvOfstatus.setText("换货");
			break;
		case 8:
			tvOfstatus.setText("作废");
			break;
		case 9:
			tvOfstatus.setText("完成");
			break;
		case 10:
			tvOfstatus.setText("受理");
			break;
		default:
			break;
		}
		if (!TextUtils.isEmpty(vorderFormBean.getStrOrderPayStatus())) {
			switch (Integer.parseInt(vorderFormBean.getStrOrderPayStatus())) {
			case 1:
				tvPaystatus.setText("已付款");
				break;
			case 2:
				tvPaystatus.setText("未付款");
				break;
			default:
				break;
			}
		}
		switch (Integer.parseInt(vorderFormBean.getStrShipmentsStatus())) {
		case 1:
			tvDistrstatus.setText("已发货");
			typeOrder = false;
			break;
		case 2:
			tvDistrstatus.setText("未发货");
			typeOrder = true;
			break;
		default:
			break;
		}
		switch (Integer.parseInt(vorderFormBean.getStrPayMode())) {
		case 1:
			tvPaymode.setText("货到付款");
			break;

		default:
			break;
		}
		if (!typeOrder) {
			btnOrder.setVisibility(View.GONE);
		}
	}

	EditText editText;

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
		case R.id.btn_delete_detail:
			new AlertDialog.Builder(this)
					.setTitle("是否删除")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									new DeleteStoreOrderForm(
											VorderDetailsActivity.this,
											str_secretKey, orderId,
											new DeleteStoreOrderParserData() {

												@Override
												public void getSuccData(
														String succCode,
														final String succMsg) {
													VorderDetailsActivity.this
															.runOnUiThread(new Runnable() {

																@Override
																public void run() {
																	toast(succMsg);
																	VorderDetailsActivity.this
																			.finish();
																}
															});

												}

												@Override
												public void getParserErrData(
														int errCode,
														String errMsg) {
													// TODO 保存日志
												}

												@Override
												public void getNetErrData(
														int errCode,
														String errMsg) {
													if (errCode == QsConstants.CLIENT_PROTOCOL_EXCEPTION) {
//														initDataFromNet(str_secretKey,false);
														VorderDetailsActivity.this.runOnUiThread(new Runnable() {
															@Override
															public void run() {
																CommonUtils
																		.errorTipsDialog(
																				VorderDetailsActivity.this,
																				VorderDetailsActivity.this
																						.getString(R.string.str_prompt),
																						VorderDetailsActivity.this
																						.getString(R.string.str_net_timeout));
															}
														});
													} else {
														VorderDetailsActivity.this.runOnUiThread(new Runnable() {
															@Override
															public void run() {
																CommonUtils
																		.errorTipsDialog(
																				VorderDetailsActivity.this,
																				VorderDetailsActivity.this
																						.getString(R.string.str_prompt),
																						VorderDetailsActivity.this
																						.getString(R.string.str_vmember_net_error));
															}
														});
													}
												}

												@Override
												public void getErrData(
														String errCode,
														String errMsg) {
													// TODO 保存日志
												}
											}).excute();
								}
							}).setNegativeButton("取消", null).show();
			break;
		case R.id.btn_store_order_shipments:
			Intent sendorderintent = new Intent(VorderDetailsActivity.this, SendOrderActivity.class);
			sendorderintent.putExtra("secretKey", str_secretKey);
			sendorderintent.putExtra("orderId", orderId);
			break;
		case R.id.btn_make_call:
			// 传入服务， parse（）解析号码
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ phone));
			// 通知activtity处理传入的call服务
			this.startActivity(intent);
			break;
		case R.id.btn_accept_order:
			new AcceptOrderForm(VorderDetailsActivity.this, str_secretKey,
					orderId, new AfterAcceptOFParserData() {

						@Override
						public void getSuccData(String succCode,
								final String succMsg) {
							VorderDetailsActivity.this
									.runOnUiThread(new Runnable() {

										@Override
										public void run() {
											toast(succMsg);
											VorderDetailsActivity.this.finish();
										}
									});
						}

						@Override
						public void getSuccData(String succCode,
								String succMsg,
								ArrayList<VorderFormBean> mArrayList) {
						}

						@Override
						public void getParserErrData(int errCode, String errMsg) {
							System.out.println(errCode + "," + errMsg);
						}

						@Override
						public void getNetErrData(int errCode, String errMsg) {
							System.out.println(errCode + "," + errMsg);
						}

						@Override
						public void getErrData(String errCode, String errMsg) {
							System.out.println(errCode + "," + errMsg);
						}
					}).excute();
			break;
		default:
			break;
		}
	}
}
