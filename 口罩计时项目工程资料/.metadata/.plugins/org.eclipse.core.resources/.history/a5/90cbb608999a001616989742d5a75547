package com.timer.view;

import com.timer.view.WheelView.OnWheelViewListener;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TimerTextView extends TextView implements Runnable{  
    
    public TimerTextView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        // TODO Auto-generated constructor stub  
    }  
  
    private long mday, mhour, mmin, msecond;//天，小时，分钟，秒  
    private boolean run=false; //是否启动了  
  
    public void setTimes(long[] times) {  
        mday = times[0];  
        mhour = times[1];  
        mmin = times[2];  
        msecond = times[3];  
  
    }  
  
    /** 
     * 倒计时计算 
     */  
    private void ComputeTime() {  
        msecond--;  
        if (msecond < 0) {  
            mmin--;  
            msecond = 59;  
            if (mmin < 0) {  
                mmin = 59;  
                mhour--;  
                if (mhour < 0) {  
                    // 倒计时结束，一天有24个小时  
                    mhour = 23;  
                    mday--;  
      
                }  
            }  
      
        }  
      
    }  
  
    public boolean isRun() {  
        return run;  
    }  
  
    public void beginRun() {  
        this.run = true;  
        run();  
    }  
      
    public void stopRun(){  
        this.run = false;  
    }  
    private TimerTextViewListener onTimerTextViewListener;

    public TimerTextViewListener getOnTimerTextViewListener() {
        return onTimerTextViewListener;
    }

    public void setOnTimerTextViewListener(TimerTextViewListener onTimerTextViewListener) {
        this.onTimerTextViewListener = onTimerTextViewListener;
    }   
    public interface TimerTextViewListener {
        public void onCompleted();
    }
    @Override  
    public void run() {  
        //标示已经启动  
        if(run){  
            ComputeTime();  
  
            String strTime=  mhour+":"+ mmin+":"+msecond+"";  
            this.setText(strTime);  
            if(null !=onTimerTextViewListener&& mday==0 && mhour==0 && mmin==0 && msecond==0){
            	onTimerTextViewListener.onCompleted();
            }
            postDelayed(this, 1000);  
        }else {  
            removeCallbacks(this);  
        }  
    }  
  
}  