package com.timer;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.timer.utils.HConstants;
import com.timer.utils.PreferenceUtils;
import com.timer.view.TimerTextView;
import com.timer.view.TimerTextView.TimerTextViewListener;
import com.timer.view.WheelView;
import com.timer.weather.WeatherBean;
import com.timer.weather.WeatherManager;
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
	private static final Uri BTN_PRO_LIB_URL = Uri.parse("https://h5.koudaitong.com/v2/feature/f78g8khm");//产品知识库url
	private static final Uri BTN_PRO_BUY_URL = Uri.parse("https://h5.koudaitong.com/v2/showcase/homepage?alias=e6n5fhc1");//产品优惠购url
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
	private WeatherBean mWeatherBean;
	private int timeNum1 = 15;
	private int timeNum2 = 15;
	private int timeNum3 = 15;
	private TextView tvLoc;
	private TextView tvPmShow;
	private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        boolean isSucc = PreferenceUtils.getPrefBoolean(this, PreferenceUtils.KEY_SOFT_ACTIVATION_SUCC, false);
        if(isSucc){
        	coverView2.setVisibility(View.GONE);
        	coverView3.setVisibility(View.GONE);
        	btnActivation.setEnabled(false);
        	btnActivation.setText("已激活");
        	btnActivation.setBackgroundResource(R.drawable.btn_other_pressed_bg);
        }
        initData();
        setListener(); 
    }

	private void setListener() {
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
					mTimerTextView1.setText("计时结束：\n"+timeNum1+"小时");
					mTimerTextView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,15.0f);
					mTimerTextView1.setTextColor(R.color.gray);
					if(mTimerTextView1.isRun()){  
						mTimerTextView1.stopRun();
					}
					break;
				case TIMERTEXTVIEW2_TAG:
					btnStart2.setText("重来");
					btnStop2.setText("返回");
					mTimerTextView1.setText("计时结束：\n"+timeNum2+"小时");
					mTimerTextView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,15.0f);
					mTimerTextView1.setTextColor(R.color.gray);
					if(mTimerTextView2.isRun()){  
						 mTimerTextView2.stopRun();
			            } 
					break;
				case TIMERTEXTVIEW3_TAG:
					btnStart3.setText("重来");
					btnStop3.setText("返回");
					mTimerTextView1.setText("计时结束：\n"+timeNum3+"小时");
					mTimerTextView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,15.0f);
					mTimerTextView1.setTextColor(R.color.gray);
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
		tvLoc = (TextView) findViewById(R.id.tvLoc);
		tvPmShow = (TextView) findViewById(R.id.tvPmShow);
		 View cardView1 =  findViewById(R.id.item_cardview);
         mWheelView1 = (WheelView)cardView1.findViewById(R.id.main_wv);
         mWheelView1.setTag(WHEELVIEW1_TAG);
         btnStart1 = (Button) cardView1.findViewById(R.id.btn_start);
         btnStart1.setTag(BTN_START1_TAG);
         btnStop1 = (Button) cardView1.findViewById(R.id.btn_stop);
         btnStop1.setTag(BTN_STOP1_TAG);
         btnStop1.setEnabled(false);
         btnStop1.setBackgroundResource(R.drawable.btn_other_pressed_bg);
         mTimerTextView1 = (TimerTextView) cardView1.findViewById(R.id.timer_text_view);
         mTimerTextView1.setTag(TIMERTEXTVIEW1_TAG);
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
         btnStop2.setBackgroundResource(R.drawable.btn_other_pressed_bg);
         mTimerTextView2 = (TimerTextView) cardView2.findViewById(R.id.timer_text_view);
         mTimerTextView2.setTag(TIMERTEXTVIEW2_TAG);
         coverView2  = cardView2.findViewById(R.id.iv_cover);
         
         View cardView3 =  findViewById(R.id.item_cardview3);
         mWheelView3 = (WheelView)cardView3.findViewById(R.id.main_wv);
         mWheelView3.setTag(WHEELVIEW3_TAG);
         btnStart3 = (Button) cardView3.findViewById(R.id.btn_start);
         btnStart3.setTag(BTN_START3_TAG);
         btnStop3 = (Button) cardView3.findViewById(R.id.btn_stop);
         btnStop3.setTag(BTN_STOP3_TAG);
         btnStop3.setEnabled(false);
         btnStop3.setBackgroundResource(R.drawable.btn_other_pressed_bg);
         mTimerTextView3 = (TimerTextView) cardView3.findViewById(R.id.timer_text_view);
         mTimerTextView3.setTag(TIMERTEXTVIEW3_TAG);
         coverView3  = cardView3.findViewById(R.id.iv_cover);
         btnActivation = (Button) findViewById(R.id.btn_activation);
         btnActivation.setTag(BTN_ACTIVATION_TAG);
         btnProLib = (Button) findViewById(R.id.btn_pro_lib);
         btnProLib.setTag(BTN_PRO_LIB_TAG);
         btnProBuy = (Button) findViewById(R.id.btn_pro_buy);
         btnProBuy.setTag(BTN_PRO_BUY_TAG);
	}

	private void initData() {
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
    	String strJson = PreferenceUtils.getPrefString(this, HConstants.SP_WEATHER_JSON, "");
		if(!TextUtils.isEmpty(strJson)){
			 mWeatherBean = WeatherManager.parserWeatherJson(strJson);
			if(mWeatherBean!=null){
				setWeatherData();
				}
			}else{
				if(WeatherManager.isStartedLocation()){
					notifyRefashWeatherData();
				}else{
					WeatherManager.InitLocation(0);
					WeatherManager.startLocation();
					notifyRefashWeatherData();
				}
			}
	}
private void setWeatherData() {
	 boolean isSucc = PreferenceUtils.getPrefBoolean(this, PreferenceUtils.KEY_SOFT_ACTIVATION_SUCC, false);
		if(isSucc){
			btnActivation.setEnabled(false);
			btnActivation.setText(mWeatherBean.getStrPmLevel());
			setBtnActivationBg(mWeatherBean.getStrPm());
			tvLoc.setText(mWeatherBean.getStrCity());
			tvPmShow.setText("实时空气污染指数 :"+mWeatherBean.getStrPm());
		}
		
	}
private void setBtnActivationBg(String pm){
	int tempPM = Integer.parseInt(pm);
	if(tempPM>0 && tempPM<51){
		btnActivation.setBackgroundResource(R.drawable.bg_pm1);
	}else if(tempPM>50 && tempPM <101){
		btnActivation.setBackgroundResource(R.drawable.bg_pm2);
	}else if(tempPM>100 && tempPM <151){
		btnActivation.setBackgroundResource(R.drawable.bg_pm3);
	}else if(tempPM>150 && tempPM <201){
		btnActivation.setBackgroundResource(R.drawable.bg_pm4);
	}else if(tempPM>200 && tempPM <301){
		btnActivation.setBackgroundResource(R.drawable.bg_pm5);
	}else if(tempPM>300){
		btnActivation.setBackgroundResource(R.drawable.bg_pm6);
	}
}
private void notifyRefashWeatherData() {
	handler.postDelayed(new Runnable() {
		@Override
		public void run() {
			String strJson = PreferenceUtils.getPrefString(SplashActivity.this,HConstants.SP_WEATHER_JSON, "");
			if(!TextUtils.isEmpty(strJson)){
				 mWeatherBean = WeatherManager.parserWeatherJson(strJson);
				 if(mWeatherBean!=null){
						setWeatherData();
				}
			}
		}
	}, 1500);
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
			btnActivation.setEnabled(false);
			btnActivation.setText("已激活");
			btnActivation.setBackgroundResource(R.drawable.btn_other_pressed_bg);
			setWeatherData();
			
		}
	}
	
}
	@Override
	public void onClick(View v) {
		Intent intent;
		switch ((Integer)v.getTag()) {
		case BTN_START1_TAG:
			btnStop1.setEnabled(true);
			btnStop1.setBackgroundResource(R.drawable.btn_other_bg);
			long[] times = new long[4]; 
			times[0]=0;
			times[1]=timeNum1;
			times[2]=0;
			times[3]=0;
			if("开始".equals(btnStart1.getText())){
				setBtnStart1Program(times); 
			}else if("归零".equals(btnStart1.getText())){
				btnStop1.setText("暂停");
				btnStop1.setEnabled(false);
				btnStop1.setBackgroundResource(R.drawable.btn_other_pressed_bg);
				mWheelView1.setVisibility(View.VISIBLE);
				mTimerTextView1.setVisibility(View.GONE);
				btnStart1.setText("开始");
				if(mTimerTextView1.isRun()){  
					mTimerTextView1.stopRun();
				}
			}else if("重来".equals(btnStart1.getText())){
				setBtnStart1Program(times); 
				
			}

			break;
		case BTN_START2_TAG:
			btnStop2.setEnabled(true);
			btnStop2.setBackgroundResource(R.drawable.btn_other_bg);
			long[] times2 = new long[4]; 
			times2[0]=0;
			times2[1]=timeNum2;
			times2[2]=0;
			times2[3]=0;
			if("开始".equals(btnStart2.getText())){
				setBtnStart2Program(times2); 
			}else if("归零".equals(btnStart2.getText())){
				btnStop2.setText("暂停");
				btnStop2.setEnabled(false);
				btnStop2.setBackgroundResource(R.drawable.btn_other_pressed_bg);
				mWheelView2.setVisibility(View.VISIBLE);
				mTimerTextView2.setVisibility(View.GONE);
				btnStart2.setText("开始");
				if(mTimerTextView2.isRun()){  
					mTimerTextView2.stopRun();
				}
			}else if("重来".equals(btnStart2.getText())){
				setBtnStart2Program(times2); 
				
			}
			break;
		case BTN_START3_TAG:
			btnStop3.setEnabled(true);
			 btnStop3.setBackgroundResource(R.drawable.btn_other_bg);
			long[] times3 = new long[4]; 
			times3[0]=0;
			times3[1]=timeNum3;
			times3[2]=0;
			times3[3]=0;
			if("开始".equals(btnStart3.getText())){
				setBtnStart3Program(times3); 
			}else if("归零".equals(btnStart3.getText())){
				btnStop3.setText("暂停");
				btnStop3.setEnabled(false);
				btnStop3.setBackgroundResource(R.drawable.btn_other_pressed_bg);
				mWheelView3.setVisibility(View.VISIBLE);
				mTimerTextView3.setVisibility(View.GONE);
				btnStart3.setText("开始");
				if(mTimerTextView3.isRun()){  
					mTimerTextView3.stopRun();
				}
			}else if("重来".equals(btnStart3.getText())){
				setBtnStart3Program(times3); 
				
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
				btnStop1.setBackgroundResource(R.drawable.btn_other_pressed_bg);
				btnStart1.setText("开始");
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
				btnStop2.setBackgroundResource(R.drawable.btn_other_pressed_bg);
				btnStart2.setText("开始");
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
				btnStop3.setBackgroundResource(R.drawable.btn_other_pressed_bg);
				btnStart3.setText("开始");
				mWheelView3.setVisibility(View.VISIBLE);
				mTimerTextView3.setVisibility(View.GONE);
			}
			
			break;
		case BTN_ACTIVATION_TAG://激活软件
			intent = new Intent(this,ActivationActivity.class);
			startActivityForResult(intent, REQUEST_CODE_ACTIVATION_SOFT);
			
			break;
		case BTN_PRO_LIB_TAG://产品知识库
			 intent = new Intent("android.intent.action.VIEW", BTN_PRO_LIB_URL);
			this.startActivity(intent);
			break;
		case BTN_PRO_BUY_TAG://产品优惠购
			 intent = new Intent("android.intent.action.VIEW", BTN_PRO_BUY_URL);
			this.startActivity(intent);
			break;

		default:
			break;
		}
	}

	private void setBtnStart3Program(long[] times3) {
		btnStop3.setText("暂停");
		mWheelView3.setVisibility(View.GONE);
		mTimerTextView3.setTextColor(R.color.red);
		mTimerTextView3.setVisibility(View.VISIBLE);
		mTimerTextView3.setTimes(times3);  
		mTimerTextView3.setTextSize(TypedValue.COMPLEX_UNIT_SP,22.0f);
		//mTimerTextView3.setBackgroundColor(R.color.white);
		btnStart3.setText("归零");
		if(!mTimerTextView3.isRun()){  
			mTimerTextView3.beginRun();  
		}
	}

	private void setBtnStart2Program(long[] times2) {
		btnStop2.setText("暂停");
		mWheelView2.setVisibility(View.GONE);
		mTimerTextView2.setTextColor(R.color.red);
		mTimerTextView2.setVisibility(View.VISIBLE);
		mTimerTextView2.setTimes(times2);  
		mTimerTextView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,22.0f);
		//mTimerTextView2.setBackgroundColor(R.color.white);
		btnStart2.setText("归零");
		if(!mTimerTextView2.isRun()){  
			mTimerTextView2.beginRun();  
		}
	}

	private void setBtnStart1Program(long[] times) {
		btnStop1.setText("暂停");
		mWheelView1.setVisibility(View.GONE);
		mTimerTextView1.setTextColor(R.color.red);
		mTimerTextView1.setVisibility(View.VISIBLE);
		mTimerTextView1.setTimes(times); 
		mTimerTextView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,22.0f);
		//mTimerTextView1.setBackgroundColor(R.color.white);
		btnStart1.setText("归零");
		if(!mTimerTextView1.isRun()){  
			mTimerTextView1.beginRun();  
		}
	}


}
