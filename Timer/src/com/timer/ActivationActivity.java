package com.timer;

import com.timer.view.TimerTextView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivationActivity extends Activity {
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_activation);  
    //初始化倒计时控件  
    final TimerTextView timerTextView = (TimerTextView)findViewById(R.id.timer_text_view);  
    long[] times = {0,10,5,30};  
    timerTextView.setTimes(times);  
      
      
    Button startBtn =  (Button)findViewById(R.id.main_start_btn);  
    Button stopBtn  =  (Button)findViewById(R.id.main_stop_btn);  
    //开始倒计时  
    startBtn.setOnClickListener(new View.OnClickListener() {  
          
        @Override  
        public void onClick(View v) {  
            // TODO Auto-generated method stub  
            if(!timerTextView.isRun()){  
                timerTextView.beginRun();  
            }  
        }  
    });  
      
    //停止倒计时  
    stopBtn.setOnClickListener(new View.OnClickListener() {  
          
        @Override  
        public void onClick(View v) {  
            // TODO Auto-generated method stub  
            if(timerTextView.isRun()){  
                timerTextView.stopRun();  
            }  
        }  
    }); 
}
}
