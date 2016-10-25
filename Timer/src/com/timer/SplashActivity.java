package com.timer;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.timer.utils.PreferenceUtils;
import com.timer.view.TimerTextView;
import com.timer.view.TimerTextView.TimerTextViewListener;
import com.timer.view.WheelView;
@SuppressLint("NewApi")
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
	private static final int TIMERTEXTVIEW1_TAG = 95;
	private static final int TIMERTEXTVIEW2_TAG = 96;
	private static final int TIMERTEXTVIEW3_TAG = 97;
	private static final int WHEELVIEW_SELECT_TIME = 14;
	private static final int WHEELVIEW_OFFSET = 1;
	private static final int REQUEST_CODE_ACTIVATION_SOFT = 898;
	private static final Uri BTN_PRO_LIB_URL = Uri.parse("https://www.baidu.com");//产品知识库url
	private static final Uri BTN_PRO_BUY_URL = Uri.parse("https://www.jd.com");//产品优惠购url
	private WheelView mWheelView1;
	private WheelView mWheelView2;
	private WheelView mWheelView3;
	private TimerTextView mTimerTextView1;
	private TimerTextView mTimerTextView2;
	private TimerTextView mTimerTextView3;
	private View coverView1;
	private View coverView2;
	private View coverView3;
	private Button btnStart1;
	private Button btnStart2;
	private Button btnStart3;
	private Button btnStop1;
	private Button btnStop2;
	private Button btnStop3;
	private Button btnActivation;
	private Button btnProLib;
	private Button btnProBuy;

	private int timeNum1 = 15;
	private int timeNum2 = 15;
	private int timeNum3 = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
        setListener(); 
        boolean isSucc = PreferenceUtils.getPrefBoolean(this, PreferenceUtils.KEY_SOFT_ACTIVATION_SUCC, false);
		if(isSucc){
			coverView2.setVisibility(View.GONE);
			coverView3.setVisibility(View.GONE);
		}
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
		  setTimerTextViewListener(mTimerTextView1);
		  setTimerTextViewListener(mTimerTextView2);
		  setTimerTextViewListener(mTimerTextView3);
	}

	private void setTimerTextViewListener(final TimerTextView view) {
		view.setOnTimerTextViewListener(new TimerTextViewListener() {
			
			@Override
			public void onCompleted() {
				switch ((Integer)view.getTag()) {
				case TIMERTEXTVIEW1_TAG:
					btnStart1.setText("重来");
					btnStop1.setText("返回");
					mTimerTextView1.setText("计时结束："+timeNum1+"小时");
					mTimerTextView1.setBackground(null);
					if(mTimerTextView1.isRun()){  
						 mTimerTextView1.stopRun();
			            } 
					break;
				case TIMERTEXTVIEW2_TAG:
					btnStart2.setText("重来");
					btnStop2.setText("返回");
					mTimerTextView2.setText("计时结束："+timeNum1+"小时");
					mTimerTextView2.setBackground(null);
					if(mTimerTextView2.isRun()){  
						 mTimerTextView2.stopRun();
			            } 
					break;
				case TIMERTEXTVIEW3_TAG:
					btnStart3.setText("重来");
					btnStop3.setText("返回");
					mTimerTextView3.setText("计时结束："+timeNum1+"小时");
					mTimerTextView3.setBackground(null);
					if(mTimerTextView3.isRun()){  
						 mTimerTextView3.stopRun();
			            } 
					break;

				default:
					break;
				}
				
			}
		});
	}

	private void setWheelViewListener(final WheelView view) {
		view.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
	            @Override
	            public void onSelected(int selectedIndex, String item) {
	                Log.d("sge", "selectedIndex: " + selectedIndex + ", item: " + item);
	                switch((Integer)view.getTag()){
	                case WHEELVIEW1_TAG:
	                	timeNum1 = selectedIndex;
	                	break;
	                case WHEELVIEW2_TAG:
	                	timeNum2 = selectedIndex;
	                	break;
	                case WHEELVIEW3_TAG:
	                	timeNum3 = selectedIndex;
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
         btnStop1.setEnabled(false);
         mTimerTextView1 = (TimerTextView) cardView1.findViewById(R.id.timer_text_view);
         coverView1  = cardView1.findViewById(R.id.iv_cover);
         coverView1.setVisibility(View.GONE);
         
         View cardView2 =  findViewById(R.id.item_cardview2);
         mWheelView2 = (WheelView)cardView2.findViewById(R.id.main_wv);
         mWheelView2.setTag(WHEELVIEW2_TAG);
         btnStart2 = (Button) cardView2.findViewById(R.id.btn_start);
         btnStart2.setTag(BTN_START2_TAG);
         btnStop2 = (Button) cardView2.findViewById(R.id.btn_stop);
         btnStop2.setTag(BTN_STOP2_TAG);
         btnStop2.setEnabled(false);
         mTimerTextView2 = (TimerTextView) cardView2.findViewById(R.id.timer_text_view);
         coverView2  = cardView2.findViewById(R.id.iv_cover);
         
         View cardView3 =  findViewById(R.id.item_cardview3);
         mWheelView3 = (WheelView)cardView3.findViewById(R.id.main_wv);
         mWheelView3.setTag(WHEELVIEW3_TAG);
         btnStart3 = (Button) cardView3.findViewById(R.id.btn_start);
         btnStart3.setTag(BTN_START3_TAG);
         btnStop3 = (Button) cardView3.findViewById(R.id.btn_stop);
         btnStop3.setTag(BTN_STOP3_TAG);
         btnStop3.setEnabled(false);
         mTimerTextView3 = (TimerTextView) cardView3.findViewById(R.id.timer_text_view);
         coverView3  = cardView3.findViewById(R.id.iv_cover);
         coverView3.setVisibility(View.GONE);
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
        mWheelView1.setOffset(WHEELVIEW_OFFSET);
        mWheelView1.setSeletion(WHEELVIEW_SELECT_TIME);
        mWheelView1.setItems(numList);
        
        mWheelView2.setOffset(WHEELVIEW_OFFSET);
        mWheelView2.setSeletion(WHEELVIEW_SELECT_TIME);
        mWheelView2.setItems(numList);
        
        mWheelView3.setOffset(WHEELVIEW_OFFSET);
        mWheelView3.setSeletion(WHEELVIEW_SELECT_TIME);
        mWheelView3.setItems(numList);
	}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if(requestCode == REQUEST_CODE_ACTIVATION_SOFT && RESULT_OK == resultCode){
		//查询软件是否激活成功
		boolean isSucc = PreferenceUtils.getPrefBoolean(this, PreferenceUtils.KEY_SOFT_ACTIVATION_SUCC, false);
		if(isSucc){
			coverView2.setVisibility(View.GONE);
			coverView3.setVisibility(View.GONE);
		}
	}
	
}
	@Override
	public void onClick(View v) {
		Intent intent;
		switch ((Integer)v.getTag()) {
		case BTN_START1_TAG:
			btnStop1.setEnabled(true);
			long[] times = new long[4]; 
			times[0]=0;
			times[1]=timeNum1;
			times[2]=0;
			times[3]=0;
			if("开始".equals(btnStart1.getText())){
				btnStop1.setText("暂停");
				//设置不可点击颜色
				mWheelView1.setVisibility(View.GONE);
				mTimerTextView1.setVisibility(View.VISIBLE);
				mTimerTextView1.setTimes(times);  
				btnStart1.setText("归零");
				if(!mTimerTextView1.isRun()){  
					mTimerTextView1.beginRun();  
				} 
			}else if("归零".equals(btnStart1.getText())){
				btnStop1.setText("继续");
				//设置不可点击颜色
				mWheelView1.setVisibility(View.GONE);
				mTimerTextView1.setVisibility(View.VISIBLE);
				mTimerTextView1.setTimes(times);
				String strTime;
				if(timeNum1>9){
					 strTime=  timeNum1+":"+"00"+":"+"00";  
				}else{
					strTime= "0"+timeNum1+":"+"00"+":"+"00";  
					
				}
				 mTimerTextView1.setText(strTime);  
				if(mTimerTextView1.isRun()){  
					mTimerTextView1.stopRun(); 
				} 
			}else if("重来".equals(btnStart1.getText())){
				btnStop1.setText("返回");
				//设置不可点击颜色
				mWheelView1.setVisibility(View.VISIBLE);
				mTimerTextView1.setVisibility(View.GONE);
				btnStart1.setText("开始");
				
			}
			
			
			break;
		case BTN_START2_TAG:
			btnStop2.setEnabled(true);
			long[] times2 = new long[4]; 
			times2[0]=0;
			times2[1]=timeNum1;
			times2[2]=0;
			times2[3]=0;
			if("开始".equals(btnStart2.getText())){
				btnStop2.setText("暂停");
				//设置不可点击颜色
				mWheelView2.setVisibility(View.GONE);
				mTimerTextView2.setVisibility(View.VISIBLE);
				mTimerTextView2.setTimes(times2);  
				btnStart2.setText("归零");
				if(!mTimerTextView2.isRun()){  
					mTimerTextView2.beginRun();  
				} 
			}else if("归零".equals(btnStart2.getText())){
				btnStop2.setText("继续");
				//设置不可点击颜色
				mWheelView2.setVisibility(View.GONE);
				mTimerTextView2.setVisibility(View.VISIBLE);
				mTimerTextView2.setTimes(times2);
				String strTime;
				if(timeNum2>9){
					 strTime=  timeNum2+":"+"00"+":"+"00";  
				}else{
					strTime= "0"+timeNum2+":"+"00"+":"+"00";  
					
				}
				 mTimerTextView2.setText(strTime);  
				if(mTimerTextView2.isRun()){  
					mTimerTextView2.stopRun(); 
				} 
			}else if("重来".equals(btnStart2.getText())){
				btnStop2.setText("返回");
				//设置不可点击颜色
				mWheelView2.setVisibility(View.VISIBLE);
				mTimerTextView2.setVisibility(View.GONE);
				btnStart2.setText("开始");
				
			}
			break;
		case BTN_START3_TAG:
			btnStop3.setEnabled(true);
			long[] times3 = new long[4]; 
			times3[0]=0;
			times3[1]=timeNum3;
			times3[2]=0;
			times3[3]=0;
			if("开始".equals(btnStart3.getText())){
				btnStop3.setText("暂停");
				//设置不可点击颜色
				mWheelView3.setVisibility(View.GONE);
				mTimerTextView3.setVisibility(View.VISIBLE);
				mTimerTextView3.setTimes(times3);  
				btnStart3.setText("归零");
				if(!mTimerTextView3.isRun()){  
					mTimerTextView3.beginRun();  
				} 
			}else if("归零".equals(btnStart3.getText())){
				btnStop3.setText("继续");
				//设置不可点击颜色
				mWheelView3.setVisibility(View.GONE);
				mTimerTextView3.setVisibility(View.VISIBLE);
				mTimerTextView3.setTimes(times3);
				String strTime;
				if(timeNum3>9){
					 strTime=  timeNum3+":"+"00"+":"+"00";  
				}else{
					strTime= "0"+timeNum3+":"+"00"+":"+"00";  
					
				}
				 mTimerTextView3.setText(strTime);  
				if(mTimerTextView3.isRun()){  
					mTimerTextView3.stopRun(); 
				} 
			}else if("重来".equals(btnStart3.getText())){
				btnStop3.setText("返回");
				//设置不可点击颜色
				mWheelView3.setVisibility(View.VISIBLE);
				mTimerTextView3.setVisibility(View.GONE);
				btnStart3.setText("开始");
				
			}
			break;
		case BTN_STOP1_TAG:
			if("暂停".equals(btnStop1.getText())){
				if(mTimerTextView1.isRun()){  
					 mTimerTextView1.stopRun(); 
		            } 
				btnStop1.setText("继续");
			}else if("继续".equals(btnStop1.getText())){
				if(!mTimerTextView1.isRun()){  
					 mTimerTextView1.beginRun();
		            } 
				btnStop1.setText("暂停");
			}else if("返回".equals(btnStop1.getText())){
				btnStop1.setText("暂停");
				btnStop1.setEnabled(false);
				//设置不可点击颜色
				mWheelView1.setVisibility(View.VISIBLE);
				mTimerTextView1.setVisibility(View.GONE);
			}
			 
			 
			break;
		case BTN_STOP2_TAG:
			if("暂停".equals(btnStop2.getText())){
				if(mTimerTextView2.isRun()){  
					 mTimerTextView2.stopRun(); 
		            } 
				btnStop2.setText("继续");
			}else if("继续".equals(btnStop2.getText())){
				if(!mTimerTextView2.isRun()){  
					 mTimerTextView2.beginRun();
		            } 
				btnStop2.setText("暂停");
			}else if("返回".equals(btnStop2.getText())){
				btnStop2.setText("暂停");
				btnStop2.setEnabled(false);
				//设置不可点击颜色
				mWheelView2.setVisibility(View.VISIBLE);
				mTimerTextView2.setVisibility(View.GONE);
			}
			
			break;
		case BTN_STOP3_TAG:
			if("暂停".equals(btnStop3.getText())){
				if(mTimerTextView3.isRun()){  
					 mTimerTextView3.stopRun(); 
		            } 
				btnStop3.setText("继续");
			}else if("继续".equals(btnStop3.getText())){
				if(!mTimerTextView3.isRun()){  
					 mTimerTextView3.beginRun();
		            } 
				btnStop3.setText("暂停");
			}else if("返回".equals(btnStop3.getText())){
				btnStop3.setText("暂停");
				btnStop3.setEnabled(false);
				//设置不可点击颜色
				mWheelView3.setVisibility(View.VISIBLE);
				mTimerTextView3.setVisibility(View.GONE);
			}
			
			break;
		case BTN_ACTIVATION_TAG://激活软件
			Toast.makeText(SplashActivity.this, "激活软件", 0).show();
			intent = new Intent(this,ActivationActivity.class);
			startActivityForResult(intent, REQUEST_CODE_ACTIVATION_SOFT);
			
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
