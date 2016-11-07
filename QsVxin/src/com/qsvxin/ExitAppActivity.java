package com.qsvxin;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class ExitAppActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_app);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
    public void btnExitNo(View v) {
        this.finish();
    }
    public void btnExitYes(View v) {
        this.finish();
        MainUIActivity.instance.finish();
    }

}
