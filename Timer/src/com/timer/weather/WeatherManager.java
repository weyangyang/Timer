package com.timer.weather;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.timer.BaseApplication;
import com.timer.utils.HConstants;
import com.timer.utils.PreferenceUtils;
import com.timer.utils.SubstringUtils;
import com.timer.utils.SystemUtils;

public class WeatherManager {
	static String  weatherServiceUrl = "http://weather.123.duba.net/static/weather_info/";
	private static LocationClient mLocationClient  = BaseApplication.getInstance().mLocationClient;
	
    /**
     * 获取天气数据bean
     * @param strCityCode 城市编码
     * @param updateTime 更新天气数据间隔时间
     * @return WeatherBean
     */
    public static void getWeatherData(String cityName){
		if(SystemUtils.checkAllNet(BaseApplication.getInstance().getApplicationContext())){
				//	String strReqUrl = weatherServiceUrl+strCityCode+".html";
					// strJson = HttpNetUtils.get(strReqUrl,null);
					 String httpUrl = "http://www.tianqi.com/air/";
					String strJson = GetNetWeather.request(httpUrl, "");
					 String temp = SubstringUtils.substringBetween(strJson, "target=\"_blank\">"+cityName,"</li>");
					 String pm25 = SubstringUtils.substringBetween(temp, "class=\"td td-4rd\">","</span>");
					 PreferenceUtils.setPrefString(BaseApplication.getInstance().getApplicationContext(),HConstants.SP_PM25, pm25);
					 PreferenceUtils.setPrefString(BaseApplication.getInstance().getApplicationContext(),HConstants.SP_CURRENT_CITY, cityName);
					
				}else{
	
				}
					
    }


    /**
     * 初始化位置配置信息
     * @param intScanSpan 更新时间 
     * 传入0时，默认1小时
     */
    public static  void InitLocation(int intScanSpan){
    	LocationClientOption option = new LocationClientOption();
    	option.setLocationMode(LocationMode.Hight_Accuracy);
    	option.setCoorType("gcj02");
    	if(0==intScanSpan){
    		intScanSpan=1000*60*10;//10分钟更新一次
    	}
    	option.setScanSpan(intScanSpan);
    	option.setIsNeedAddress(true);
    	mLocationClient.setLocOption(option);
    }
    public static void startLocation(){
    	mLocationClient.start();
    }
    public static boolean isStartedLocation(){
    	return mLocationClient.isStarted();
    }
    public static void stopLocation(){
    	if(mLocationClient.isStarted()){
    		mLocationClient.stop();
    	}
    }
}
