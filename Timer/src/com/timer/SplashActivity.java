package com.timer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.timer.view.WheelView;
public class SplashActivity extends Activity implements OnClickListener {
	
	private static final int BTN_START1_TAG = 55;
	private static final int BTN_START2_TAG = 56;
	private static final int BTN_START3_TAG = 57;
	private static final int BTN_STOP1_TAG = 65;
	private static final int BTN_STOP2_TAG = 66;
	private static final int BTN_STOP3_TAG = 67;
	private static final int BTN_ACTIVATION_TAG = 75;
	private static final int BTN_PRO_LIB_TAG = 76;
	private static final int BTN_PRO_BUY_TAG = 77;
	private static final int WHEELVIEW1_TAG = 85;
	private static final int WHEELVIEW2_TAG = 86;
	private static final int WHEELVIEW3_TAG = 87;
	private static final Uri BTN_PRO_LIB_URL = Uri.parse("https://www.baidu.com");//产品知识库url
	private static final Uri BTN_PRO_BUY_URL = Uri.parse("https://www.jd.com");//产品优惠购url
	private WheelView mWheelView1;
	private WheelView mWheelView2;
	private WheelView mWheelView3;
	private Button btnStart1;
	private Button btnStart2;
	private Button btnStart3;
	private Button btnStop1;
	private Button btnStop2;
	private Button btnStop3;
	private Button btnActivation;
	private Button btnProLib;
	private Button btnProBuy;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
        setListener(); 
    }

	private void setListener() {
		// TODO Auto-generated method stub
		  setWheelViewListener(mWheelView1);
		  setWheelViewListener(mWheelView2);
		  setWheelViewListener(mWheelView3);
		  
		  btnStart1.setOnClickListener(this);
		  btnStart2.setOnClickListener(this);
		  btnStart3.setOnClickListener(this);
		  btnStop1.setOnClickListener(this);
		  btnStop2.setOnClickListener(this);
		  btnStop3.setOnClickListener(this);
		  
		  btnActivation.setOnClickListener(this);
		  btnProLib.setOnClickListener(this);
		  btnProBuy.setOnClickListener(this);
	}

	private void setWheelViewListener(final WheelView view) {
		view.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
	            @Override
	            public void onSelected(int selectedIndex, String item) {
	                Log.d("sge", "selectedIndex: " + selectedIndex + ", item: " + item);
	                switch((Integer)view.getTag()){
	                case WHEELVIEW1_TAG:
	                	Toast.makeText(SplashActivity.this, "第1个selectedIndex: " + selectedIndex + ", item: " + item, Toast.LENGTH_SHORT).show();
	                	break;
	                case WHEELVIEW2_TAG:
	                	Toast.makeText(SplashActivity.this, "第2个selectedIndex: " + selectedIndex + ", item: " + item, Toast.LENGTH_SHORT).show();
	                	break;
	                case WHEELVIEW3_TAG:
	                	Toast.makeText(SplashActivity.this, "第3个selectedIndex: " + selectedIndex + ", item: " + item, Toast.LENGTH_SHORT).show();
	                	break;
	                	
	                }
	            }
	        });
	}

	private void initView() {
		// TODO Auto-generated method stub
		 View cardView1 =  findViewById(R.id.item_cardview);
         mWheelView1 = (WheelView)cardView1.findViewById(R.id.main_wv);
         mWheelView1.setTag(WHEELVIEW1_TAG);
         btnStart1 = (Button) cardView1.findViewById(R.id.btn_start);
         btnStart1.setTag(BTN_START1_TAG);
         btnStop1 = (Button) cardView1.findViewById(R.id.btn_stop);
         btnStop1.setTag(BTN_STOP1_TAG);
         
         View cardView2 =  findViewById(R.id.item_cardview2);
         mWheelView2 = (WheelView)cardView2.findViewById(R.id.main_wv);
         mWheelView2.setTag(WHEELVIEW2_TAG);
         btnStart2 = (Button) cardView2.findViewById(R.id.btn_start);
         btnStart2.setTag(BTN_START2_TAG);
         btnStop2 = (Button) cardView2.findViewById(R.id.btn_stop);
         btnStop2.setTag(BTN_STOP2_TAG);
         
         View cardView3 =  findViewById(R.id.item_cardview3);
         mWheelView3 = (WheelView)cardView3.findViewById(R.id.main_wv);
         mWheelView3.setTag(WHEELVIEW3_TAG);
         btnStart3 = (Button) cardView3.findViewById(R.id.btn_start);
         btnStart3.setTag(BTN_START3_TAG);
         btnStop3 = (Button) cardView3.findViewById(R.id.btn_stop);
         btnStop3.setTag(BTN_STOP3_TAG);
         
         btnActivation = (Button) findViewById(R.id.btn_activation);
         btnActivation.setTag(BTN_ACTIVATION_TAG);
         btnProLib = (Button) findViewById(R.id.btn_pro_lib);
         btnProLib.setTag(BTN_PRO_LIB_TAG);
         btnProBuy = (Button) findViewById(R.id.btn_pro_buy);
         btnProBuy.setTag(BTN_PRO_BUY_TAG);
	}

	private void initData() {
		// TODO Auto-generated method stub
		List<String> numList = new ArrayList<String>();
        for(int i =0;i<100;i++){
        	numList.add(i+1+" ");
        }
        mWheelView1.setOffset(1);
        mWheelView1.setSeletion(10);
        mWheelView1.setItems(numList);
        
        mWheelView2.setOffset(1);
        mWheelView2.setSeletion(10);
        mWheelView2.setItems(numList);
        
        mWheelView3.setOffset(1);
        mWheelView3.setSeletion(10);
        mWheelView3.setItems(numList);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch ((Integer)v.getTag()) {
		case BTN_START1_TAG:
			Toast.makeText(SplashActivity.this, "第1个card start", 0).show();
			break;
		case BTN_START2_TAG:
			Toast.makeText(SplashActivity.this, "第2个card start", 0).show();
			
			break;
		case BTN_START3_TAG:
			Toast.makeText(SplashActivity.this, "第3个card start", 0).show();
			
			break;
		case BTN_STOP1_TAG:
			Toast.makeText(SplashActivity.this, "第1个card stop", 0).show();
			
			break;
		case BTN_STOP2_TAG:
			Toast.makeText(SplashActivity.this, "第2个card stop", 0).show();
			
			break;
		case BTN_STOP3_TAG:
			Toast.makeText(SplashActivity.this, "第3个card stop", 0).show();
			
			break;
		case BTN_ACTIVATION_TAG://激活软件
			Toast.makeText(SplashActivity.this, "激活软件", 0).show();
			
			break;
		case BTN_PRO_LIB_TAG://产品知识库
			Toast.makeText(SplashActivity.this, "产品知识库", 0).show();
			 intent = new Intent("android.intent.action.VIEW", BTN_PRO_LIB_URL);
			this.startActivity(intent);
			break;
		case BTN_PRO_BUY_TAG://产品优惠购
			Toast.makeText(SplashActivity.this, "产品优惠购", 0).show();
			 intent = new Intent("android.intent.action.VIEW", BTN_PRO_BUY_URL);
			this.startActivity(intent);
			break;

		default:
			break;
		}
	}


}
