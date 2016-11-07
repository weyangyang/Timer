package com.qsvxin;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends Activity {
    public static WelcomeActivity instance = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        instance = this;
    }
    @Override
    protected void onDestroy() {
        instance =null;
        super.onDestroy();
    }
    public void welcome_login(View v) {  
      	Intent intent = new Intent();
		intent.setClass(WelcomeActivity.this,LoginActivity.class);
		startActivity(intent);
		//this.finish();
      }  
    public void welcome_register(View v) { 
        
      	Intent intent = new Intent();
		intent.setClass(WelcomeActivity.this,RegisterActivity.class);
		startActivity(intent);
		//this.finish();
      }  
   
}
