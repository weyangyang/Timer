package com.timer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.timer.view.WheelView;
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        List<String> numList = new ArrayList<String>();
        for(int i =0;i<100;i++){
        	numList.add(i+1+" ");
        }
        WheelView wva = (WheelView)findViewById(R.id.main_wv);
        wva.setOffset(1);
        wva.setSeletion(10);
        wva.setItems(numList);
        
        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("sge", "selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });
    }


}
