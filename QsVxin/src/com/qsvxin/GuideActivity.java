package com.qsvxin;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.qsvxin.utils.QsConstants;
import com.qsvxin.utils.PreferenceUtils;

public class GuideActivity extends Activity {
	
	private ViewPager mViewPager;	
	private ImageView imgv_guidePoint0;
	private ImageView imgv_guidePoint1;
	private ImageView imgv_guidePoint2;
	private ImageView imgv_guidePoint3;
	private int currIndex = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_guide);
        mViewPager = (ViewPager)findViewById(R.id.whatsnew_viewpager);        
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
       
        
        imgv_guidePoint0 = (ImageView)findViewById(R.id.imgv_guidePoint0);
        imgv_guidePoint1 = (ImageView)findViewById(R.id.imgv_guidePoint1);
        imgv_guidePoint2 = (ImageView)findViewById(R.id.imgv_guidePoint2);
        imgv_guidePoint3 = (ImageView)findViewById(R.id.imgv_guidePoint3);
//        mPage4 = (ImageView)findViewById(R.id.page4);
//        mPage5 = (ImageView)findViewById(R.id.page5);
        
      //将要分页显示的View装入数组中
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        View view_guide1 = mLayoutInflater.inflate(R.layout.guide1, null);
        View view_guide2 = mLayoutInflater.inflate(R.layout.guide2, null);
        View view_guide3 = mLayoutInflater.inflate(R.layout.guide3, null);
        View view_guide4 = mLayoutInflater.inflate(R.layout.guide4, null);
        
      //每个页面的view数据
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view_guide1);
        views.add(view_guide2);
        views.add(view_guide3);
        views.add(view_guide4);
        PagerAdapter mPagerAdapter = new PagerAdapter() {
			@Override
			public CharSequence getPageTitle(int position) {
				return super.getPageTitle(position);
			}
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}
			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
		
		mViewPager.setAdapter(mPagerAdapter);
    }    
    

    public class MyOnPageChangeListener implements OnPageChangeListener {
		
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:				
				imgv_guidePoint0.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				imgv_guidePoint1.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 1:
				imgv_guidePoint1.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				imgv_guidePoint0.setImageDrawable(getResources().getDrawable(R.drawable.page));
				imgv_guidePoint2.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 2:
				imgv_guidePoint2.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				imgv_guidePoint1.setImageDrawable(getResources().getDrawable(R.drawable.page));
				imgv_guidePoint3.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 3:
			    imgv_guidePoint3.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
			    imgv_guidePoint2.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
//			case 4:
//				mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
//				mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
//				mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page));
//				break;
//			case 5:
//				mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
//				mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
//				break;
			}
			currIndex = arg0;
		}
	
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		public void onPageScrollStateChanged(int arg0) {
		}
	}
    public void startbutton(View v) {  
         PreferenceUtils.setPrefBoolean(GuideActivity.this,QsConstants.SP_ISINSTALL, true);
      	Intent intent = new Intent();
		intent.setClass(GuideActivity.this,GuideDoorActivity.class);
		startActivity(intent);
		this.finish();
      }  
    
}
