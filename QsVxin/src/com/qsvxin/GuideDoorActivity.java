package com.qsvxin;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class GuideDoorActivity extends Activity {
	
	private ImageView imgv_guideDoorLeft;
	private ImageView imgv_guideDoorRight;
	private TextView txtv_guideDoor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_door);
        
        imgv_guideDoorLeft = (ImageView)findViewById(R.id.imgv_guideDoorLeft);
        imgv_guideDoorRight = (ImageView)findViewById(R.id.imgv_guideDoorRight);
        txtv_guideDoor = (TextView)findViewById(R.id.txtv_guideDoor);
        
        AnimationSet anim = new AnimationSet(true);
		TranslateAnimation mytranslateanim = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f);
		mytranslateanim.setDuration(2000);
		anim.setStartOffset(800);
		anim.addAnimation(mytranslateanim);
		anim.setFillAfter(true);
		imgv_guideDoorLeft.startAnimation(anim);
		
		AnimationSet anim1 = new AnimationSet(true);
		TranslateAnimation mytranslateanim1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,+1f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f);
		mytranslateanim1.setDuration(1500);
		anim1.addAnimation(mytranslateanim1);
		anim1.setStartOffset(800);
		anim1.setFillAfter(true);
		imgv_guideDoorRight.startAnimation(anim1);
		
		AnimationSet anim2 = new AnimationSet(true);
		ScaleAnimation myscaleanim = new ScaleAnimation(1f,3f,1f,3f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		myscaleanim.setDuration(1000);
		AlphaAnimation myalphaanim = new AlphaAnimation(1,0.0001f);
		myalphaanim.setDuration(1500);
		anim2.addAnimation(myscaleanim);
		anim2.addAnimation(myalphaanim);
		anim2.setFillAfter(true);
		txtv_guideDoor.startAnimation(anim2);
		
		new Handler().postDelayed(new Runnable(){
			
			public void run(){
				Intent intent = new Intent (GuideDoorActivity.this,WelcomeActivity.class);			
				startActivity(intent);			
				GuideDoorActivity.this.finish();
			}
		}, 2300);
    }

    
}
