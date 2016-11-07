package com.qsvxin;

import com.igexin.sdk.PushManager;
import com.qsvxin.utils.PreferenceUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ExitApp4SetActivity extends Activity {
    // private MyDialog dialog;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_app_set);
        // dialog=new MyDialog(this);
        layout = (LinearLayout) findViewById(R.id.exit_layout2);

        layout.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    public void exitbutton1(View v) {
        this.finish();
    }

    public void exitbutton0(View v) {
        Intent intent =  new Intent(ExitApp4SetActivity.this, LoginActivity.class);
        startActivity(intent);
        PreferenceUtils.setLoginAfPrefPram(ExitApp4SetActivity.this,  "", "", "", "", "","", "", "", "", "", "", false);
        this.finish();
        MainUIActivity.instance.finish();// 关闭Main 这个Activity
    }

}
