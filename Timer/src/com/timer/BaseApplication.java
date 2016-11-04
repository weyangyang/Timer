package com.timer;

import android.app.Application;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.timer.utils.HConstants;
import com.timer.utils.PreferenceUtils;
import com.timer.utils.SubstringUtils;
import com.timer.utils.SystemUtils;
import com.timer.weather.GetNetWeather;
import com.timer.weather.WeatherManager;


public class BaseApplication extends Application{
	private static BaseApplication instance;
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
@Override
public void onCreate() {
	instance = this;
	super.onCreate();
	mLocationClient = new LocationClient(this.getApplicationContext());
	mMyLocationListener = new MyLocationListener();
	mLocationClient.registerLocationListener(mMyLocationListener);
	WeatherManager.InitLocation(0);
	mLocationClient.start();
}

public LocationClient getLocationClient(){
	return mLocationClient;
}
public static BaseApplication getInstance(){
	return instance;
}
public class MyLocationListener implements BDLocationListener {

	@Override
	public void onReceiveLocation(BDLocation location) {
		String strLastGetLocationTime = location.getTime();
		String strCity = null;
		if (location.getLocType() == BDLocation.TypeGpsLocation){
			 strCity = location.getAddrStr();
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
			 strCity = location.getAddrStr();
		}
		strCity = getCityWithLocation(location, strCity);
		//final String strCityCode = WeatherManager.getCityCode(strCity);
		if(!TextUtils.isEmpty(strCity)){
			PreferenceUtils.setPrefString(BaseApplication.getInstance().getApplicationContext(),HConstants.SP_CITY_CODE, strCity);
			if(!TextUtils.isEmpty(strLastGetLocationTime)){
				PreferenceUtils.setPrefString(BaseApplication.getInstance().getApplicationContext(),HConstants.SP_LAST_UPDATE_LOCATION_TIME, strLastGetLocationTime);
			}
			if(SystemUtils.checkAllNet(BaseApplication.getInstance().getApplicationContext())){
				GetNetWeather.getWeatherWithNet(strCity);
			}
		}
	}

	private String getCityWithLocation(BDLocation location, String strCity) {
		if(!TextUtils.isEmpty(strCity)){
			if(strCity.contains("市")){
				if(strCity.contains("市")){
					strCity = SubstringUtils.substringBeforeLast(strCity, "市");
					if(strCity.contains("省")){
						strCity = SubstringUtils.substringAfterLast(strCity, "省");
					}
					return strCity;
				}
			}
		else if(strCity.contains("县")){
				strCity = SubstringUtils.substringBeforeLast(location.getAddrStr(), "县");
				if(strCity.contains("市")){
					strCity = SubstringUtils.substringAfterLast(strCity, "市");
				}else if(strCity.contains("省")){
					strCity = SubstringUtils.substringAfterLast(strCity, "省");
				}
				return strCity;
				
			}else if(strCity.contains("区")){
				strCity = SubstringUtils.substringBeforeLast(location.getAddrStr(), "区");
				if(strCity.contains("市")){
					strCity = SubstringUtils.substringAfterLast(strCity, "市");
				}else if(strCity.contains("省")){
					strCity = SubstringUtils.substringAfterLast(strCity, "省");
				}
				return strCity;
			}
		}
		return strCity;
	}


}
}
