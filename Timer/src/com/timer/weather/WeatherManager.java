package com.timer.weather;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import netutils.http.HttpNetUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sgecore.utils.PreferenceUtils;
import sgecore.utils.SystemUtils;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.timer.BaseApplication;
import com.timer.utils.HConstants;

public class WeatherManager {
	static String  weatherServiceUrl = "http://weather.123.duba.net/static/weather_info/";
	private static LocationClient mLocationClient  = BaseApplication.getInstance().mLocationClient;
	
	/**
	 * 获取weather bean 数据
	 * @param strResult 网络请求返回的weather json
	 * @return  WeatherBean  解析失败则返回 null
	 */
	public static WeatherBean parserWeatherJson(String strResult) {
		if(TextUtils.isEmpty(strResult)){
			return null;
		}
		int start = strResult.indexOf("(");
		int end = strResult.indexOf(")");
		String jsonString = strResult.substring(start+1, end);
		WeatherBean weatherBean  =new  WeatherBean();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
			String strCity = weatherInfo.getString("city");
			weatherBean.setStrCity(strCity);
			String strDate = weatherInfo.getString("date_y");
			weatherBean.setStrDate(strDate);
			String strWeekDay = weatherInfo.getString("week");
			weatherBean.setStrWeekDay(strWeekDay);
			String strPm = weatherInfo.getString("pm");
			weatherBean.setStrPm(strPm);
			String strPmLevel = weatherInfo.getString("pm-level");
			weatherBean.setStrPmLevel(strPmLevel);
			String sysUpdateTime = jsonObject.getString("update_time");
			weatherBean.setSysUpdateTime(sysUpdateTime);
			ArrayList<WeatherOtherBean> weatherOtherList = new ArrayList<WeatherOtherBean>();
			ArrayList<Future5WeatherBean> future5WeatherList = new ArrayList<Future5WeatherBean>();
			for(int i=0;i<5;i++){
				Future5WeatherBean mFuture5WeatherBean = new Future5WeatherBean();
				String strFutureTemperature = weatherInfo.getString("temp"+(i+2));
				mFuture5WeatherBean.setStrTemperature(strFutureTemperature);
				String strFutureWeather= weatherInfo.getString("weather"+(i+2));
				mFuture5WeatherBean.setStrWeather(strFutureWeather);
				String strFutureWindLevel = weatherInfo.getString("fl"+(i+2));
				mFuture5WeatherBean.setStrWindLevel(strFutureWindLevel);
				String []weeks = getFuture5Week(strWeekDay);
				mFuture5WeatherBean.setStrWeek(weeks[i]);
				String strFutureWind = weatherInfo.getString("wind"+(i+2));
				mFuture5WeatherBean.setStrWind(strFutureWind);
				future5WeatherList.add(mFuture5WeatherBean);
			}
			weatherBean.setFuture5WeatherList(future5WeatherList);
			
			String strTemperature1 = weatherInfo.getString("temp1");
			weatherBean.setStrTemperature1(strTemperature1);
			
			String strWeather1 = weatherInfo.getString("weather1");
			weatherBean.setStrWeather1(strWeather1);
			
			String strWind1 = weatherInfo.getString("wd");//风向
			weatherBean.setStrWind1(strWind1);
			String strWindLevel1 = weatherInfo.getString("wind1");//风力级别
			weatherBean.setStrWindLevel1(strWindLevel1);
			weatherBean.setOtherBeanList(weatherOtherList);
			long strLastUpdateTime = jsonObject.getLong("t");
			weatherBean.setStrLastUpdateTime(strLastUpdateTime);
			PreferenceUtils.setPrefLong(HConstants.SP_UPDATE_WEATHER_TIME, strLastUpdateTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return weatherBean;
	}
	 public static String[] getFuture5Week(String week){
	    	String []weeks = new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
	    	String [] future5Weeks = new String[5];
	    	int position =0;
	    		for(int i = 0;i<weeks.length;i++){
	    			if(weeks[i].equals(week)){
	    				position = i;
	    				break;
	    			}
	    		}
	    		int a = 0;
	    		for(int j = (position==6)?(position-6):(position+1);j!=position;j =(j+1)%7){
	    			future5Weeks[a]=weeks[j];
	    			a++;
	    			if(a==5){
	    				break;
	    			}
	    		}
	    	return future5Weeks;
	    }
	private static void addBean2List(
			ArrayList<WeatherOtherBean> weatherOtherList,String strAdvice,String strTitle, String strSubTitle,
			String strDescription) {
		WeatherOtherBean mWeatherOtherBean = new WeatherOtherBean();
		mWeatherOtherBean.setStrAdvice(strAdvice);
		mWeatherOtherBean.setStrDescription(strDescription);
		mWeatherOtherBean.setStrTitle(strTitle);
		mWeatherOtherBean.setStrSubTitle(strSubTitle);
		weatherOtherList.add(mWeatherOtherBean);
	}

    /**
     * 获取指定城市CID编码。
     * 
     * @param city
     *            城市
     * @return 返回城市CID编码。错误或者城市不正确等则返回空字符串。
     * @throws Exception
     */
    public static String getCityCode(String city) {
    	if(TextUtils.isEmpty(city)){
    		return "";
    	}
        Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance()
			        .newDocumentBuilder().parse(BaseApplication.getInstance().getAssets().open("city_code.xml"));
		} catch (FileNotFoundException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		} catch (ParserConfigurationException e) {
		}
        NodeList cityNameList = doc.getElementsByTagName("key");
        NodeList cityCodeList = doc.getElementsByTagName("string");
        for (int i = 0; i < cityNameList.getLength(); i++) {
            Element ele = (Element) cityNameList.item(i);
            if (ele.getTextContent().equals(city)) {
        		String strCid = cityCodeList.item(i).getTextContent();
        		return strCid;
        	}
//            if (!TextUtils.isEmpty("cid") && !TextUtils.isEmpty("name")) {
//            	if (ele.getAttribute("name").equals(city)) {
//            		String strCid = ele.getAttribute("cid");
//            		return strCid;
//            	}
//            }
        }
        return "";
    }
    /**
     * 获取天气数据bean
     * @param strCityCode 城市编码
     * @param updateTime 更新天气数据间隔时间
     * @return WeatherBean
     */
    public static WeatherBean getWeatherBean(String strCityCode,long updateTime){
    	WeatherBean  mWeatherBean =null;
    	if(updateTime == 0){
    		updateTime =1000*60*60;//1小时;
    	}
		if(!TextUtils.isEmpty(strCityCode)){
			long lastUpdateWeatherTime = PreferenceUtils.getPrefLong(HConstants.SP_UPDATE_WEATHER_TIME, 0);
			String strJson = PreferenceUtils.getPrefString(HConstants.SP_WEATHER_JSON, "");
			if(lastUpdateWeatherTime!=0){
				long currentTime = System.currentTimeMillis();
				long tempTime = currentTime - lastUpdateWeatherTime;
				Log.d("tempTime:::::",tempTime+"ms");
				if(tempTime>updateTime){
					mWeatherBean = getWeatherJson(strCityCode, mWeatherBean,
							strJson);
					
				}else{
					mWeatherBean = getWeatherBeanForJson(mWeatherBean, strJson);
				}
			}else{
				mWeatherBean = getWeatherJson(strCityCode, mWeatherBean,strJson);
			}
		}
		return mWeatherBean;
    }

	private static WeatherBean getWeatherJson(String strCityCode,
			WeatherBean mWeatherBean, String strJson) {
		if(SystemUtils.checkAllNet(BaseApplication.getInstance().getApplicationContext())){
			String strReqUrl = weatherServiceUrl+strCityCode+".html";
			 strJson = HttpNetUtils.get(strReqUrl,null);
			mWeatherBean = getWeatherBeanForJson(mWeatherBean,strJson);
			PreferenceUtils.setPrefString(HConstants.SP_WEATHER_JSON, strJson);
		}else{
			mWeatherBean = getWeatherBeanForJson(mWeatherBean,strJson);
		}
		return mWeatherBean;
	}

	private static WeatherBean getWeatherBeanForJson(WeatherBean mWeatherBean,
			String strJson) {
		if(!TextUtils.isEmpty(strJson)){
			mWeatherBean  = WeatherManager.parserWeatherJson(strJson);
			Log.d("WeatherBean:::::", mWeatherBean.toString());
		}
		return mWeatherBean;
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
    		intScanSpan=1000*60*60;//1小时更新一次
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
