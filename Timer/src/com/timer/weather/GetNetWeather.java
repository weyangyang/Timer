package com.timer.weather;

import com.timer.utils.AsyncTask;
import com.timer.utils.HConstants;

import netutils.engine.NetReqCallBack;
import netutils.http.HttpNetUtils;
import sgecore.utils.PreferenceUtils;
import android.text.TextUtils;

public class GetNetWeather {
static String  weatherServiceUrl = "http://weather.123.duba.net/static/weather_info/";
 public static void getWeatherWithNet(final String strCityName){
	 if(TextUtils.isEmpty(strCityName)){
		 return;
	 }
	 //"http://weather.123.duba.net/static/weather_info/101010100.html"
	
	 new AsyncTask() {
		
		@Override
		protected void onPreExectue() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		protected void onPostExecute() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		protected void doInbackgroud() {
			String strCityCode = WeatherManager.getCityCode(strCityName);
			 final String strReqUrl = weatherServiceUrl+strCityCode+".html";
//			 final String strReqUrl = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=101110101&imei=e32c8a29d0e8633283737f5d9f381d47&device=HM2013023&miuiVersion=JHBCNBD16.0&modDevice=&source=miuiWeatherApp";
			 
			HttpNetUtils.get(strReqUrl,new NetReqCallBack() {
				
				@Override
				public void getSuccData(int statusCode, String strJson, String strUrl) {
					if(!TextUtils.isEmpty(strJson)){
						PreferenceUtils.setPrefString(HConstants.SP_WEATHER_JSON, strJson);
					}
				}
			});	
		}
	}.execute();
 }
}
