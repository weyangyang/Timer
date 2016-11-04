package com.timer.weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.text.TextUtils;

import com.timer.BaseApplication;
import com.timer.utils.AsyncTask;
import com.timer.utils.HConstants;
import com.timer.utils.PreferenceUtils;
import com.timer.utils.SubstringUtils;

public class GetNetWeather {
 public static void getWeatherWithNet(final String strCityName){
	
	 if(TextUtils.isEmpty(strCityName)){
		 return;
	 }
	
	 new AsyncTask() {
		
		@Override
		protected void onPreExectue() {
			
		}
		
		@Override
		protected void onPostExecute() {
			
		}
		
		@Override
		protected void doInbackgroud() {
			 String jsonResult = request("http://www.tianqi.com/air/", "");
			 String temp = SubstringUtils.substringBetween(jsonResult, "\">"+strCityName,"</tr>");
			 String pm25 = SubstringUtils.substringBetween(temp, "<td>","</td>");
			 PreferenceUtils.setPrefString(BaseApplication.getInstance().getApplicationContext(),HConstants.SP_PM25, pm25);
			 PreferenceUtils.setPrefString(BaseApplication.getInstance().getApplicationContext(),HConstants.SP_CURRENT_CITY, strCityName);
		}
	}.execute();
 }


 /**
  * @param urlAll
  *            :请求接口
  * @param httpArg
  *            :参数
  * @return 返回结果
  */
 public static String request(String httpUrl, String httpArg) {
     BufferedReader reader = null;
     String result = null;
     StringBuffer sbf = new StringBuffer();
     httpUrl = httpUrl + httpArg;
//     httpUrl = httpUrl + "?" + httpArg;

     try {
         URL url = new URL(httpUrl);
         HttpURLConnection connection = (HttpURLConnection) url
                 .openConnection();
         connection.setRequestMethod("GET");
         // 填入apikey到HTTP header				// ec0bf175d44eaec93c501f37298e94f6
        // connection.setRequestProperty("apikey",  "ec0bf175d44eaec93c501f37298e94f6");
         connection.connect();
         InputStream is = connection.getInputStream();
         reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
         String strRead = null;
         while ((strRead = reader.readLine()) != null) {
             sbf.append(strRead);
             sbf.append("\r\n");
         }
         reader.close();
         result = sbf.toString();
     } catch (Exception e) {
         e.printStackTrace();
     }
     return result;
 }
 
}
