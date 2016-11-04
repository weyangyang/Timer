package com.timer.weather;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 今天天气数据bean
 * @author liyusheng
 *
 */
public class WeatherBean implements Serializable{
	private static final long serialVersionUID = 495919066134127031L;
//	private static final WeatherBean mWeatherBean = new WeatherBean();
//	private WeatherBean(){};
//	public static final synchronized WeatherBean getInstance(){
//		return mWeatherBean;
//	}
	private long strLastUpdateTime;//上次获取天气时间
	private String strCity;//城市（区县）
	private String strDate;//日期（2012年2月16日）
	private String strWeekDay;//星期几
	private String strWeather1;//当前天气（天气描述：晴）
	private String strPm; //pm2.5值
	private String strPmLevel; //pm2.5级别
	private String strTemperature1;//温度
	private String strWind1;//风向
	private String strWindLevel1;//风速级别描述
	private String sysUpdateTime;//系统更新天气时间
	
	
//	private String strDressingIndex;//今天穿衣指数
//	//private String strDressingIndex_D;//今天穿衣指数详情
//	private String str48DressingIndex;//48小时穿衣指数
//	private String str48DressingIndex_D;//48小时穿衣指数详情
//	private String strUltravioletRays ;//紫外线
//	private String strUltravioletRays_D ;//紫外线详情
//	private String str48UltravioletRays ;//48小时紫外线
//	private String strClearCar;//洗车
//	private String strClearCar_D;//洗车详情
//	private String strTravel;//旅游
//	private String strTravel_D;//旅游详情
//	private String strComfortIndex;//舒适指数
//	private String strComfortIndex_D;//舒适指数详情
//	private String strMorningExercises;//晨练
//	private String strMorningExercises_D;//晨练详情
//	private String strDrying;//晾晒
//	private String strDrying_D;//晾晒详情
	
	private ArrayList<WeatherOtherBean> otherBeanList ;//天气其他相关信息数据集合
	private ArrayList<Future5WeatherBean> future5WeatherList ;//未来5天天气数据集合
	
	public ArrayList<WeatherOtherBean> getOtherBeanList() {
		return otherBeanList;
	}
	public void setOtherBeanList(ArrayList<WeatherOtherBean> otherBeanList) {
		this.otherBeanList = otherBeanList;
	}
	public ArrayList<Future5WeatherBean> getFuture5WeatherList() {
		return future5WeatherList;
	}
	public void setFuture5WeatherList(
			ArrayList<Future5WeatherBean> future5WeatherList) {
		this.future5WeatherList = future5WeatherList;
	}
	public String getStrCity() {
		return strCity;
	}
	public void setStrCity(String strCity) {
		this.strCity = strCity;
	}
	public String getStrDate() {
		return strDate;
	}
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}
	public String getStrWeekDay() {
		return strWeekDay;
	}
	public void setStrWeekDay(String strWeekDay) {
		this.strWeekDay = strWeekDay;
	}
	public String getStrWeather1() {
		return strWeather1;
	}
	public void setStrWeather1(String strWeather1) {
		this.strWeather1 = strWeather1;
	}
	public String getStrPm() {
		return strPm;
	}
	public void setStrPm(String strPm) {
		this.strPm = strPm;
	}
	public String getStrPmLevel() {
		return strPmLevel;
	}
	public void setStrPmLevel(String strPmLevel) {
		this.strPmLevel = strPmLevel;
	}
	public String getStrTemperature1() {
		return strTemperature1;
	}
	public void setStrTemperature1(String strTemperature1) {
		this.strTemperature1 = strTemperature1;
	}
	public String getStrWind1() {
		return strWind1;
	}
	public void setStrWind1(String strWind1) {
		this.strWind1 = strWind1;
	}
	public String getStrWindLevel1() {
		return strWindLevel1;
	}
	public void setStrWindLevel1(String strWindLevel1) {
		this.strWindLevel1 = strWindLevel1;
	}
	
public String getSysUpdateTime() {
		return sysUpdateTime;
	}
	public void setSysUpdateTime(String sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}
	/**
	 * 上次更新天气的时间
	 * @return
	 */
	public long getStrLastUpdateTime() {
		return strLastUpdateTime;
	}
	/**
	 * 上次更新天气的时间
	 * @param strLastUpdateTime
	 */
	public void setStrLastUpdateTime(long strLastUpdateTime) {
		this.strLastUpdateTime = strLastUpdateTime;
	}
	@Override
	public String toString() {
		return "WeatherBean [strLastUpdateTime=" + strLastUpdateTime
				+ ", strCity=" + strCity + ", strDate=" + strDate
				+ ", strWeekDay=" + strWeekDay + ", strWeather1=" + strWeather1
				+ ", strPm=" + strPm + ", strPmLevel=" + strPmLevel
				+ ", strTemperature1=" + strTemperature1 + ", strWind1="
				+ strWind1 + ", strWindLevel1=" + strWindLevel1
				+ ", sysUpdateTime=" + sysUpdateTime + ", otherBeanList="
				+ otherBeanList + ", future5WeatherList=" + future5WeatherList
				+ "]";
	}
	//	/**
//	 * 今天穿衣指数
//	 * @return
//	 */
//	public String getStrDressingIndex() {
//		return strDressingIndex;
//	}
//	/**
//	 * 今天穿衣指数
//	 * @param strDressingIndex
//	 */
//	public void setStrDressingIndex(String strDressingIndex) {
//		this.strDressingIndex = strDressingIndex;
//	}
	/**
	 * 今天穿衣指数详情
	 * @return
	 */
//	public String getStrDressingIndex_D() {
//		return strDressingIndex_D;
//	}
	/**
	 * 今天穿衣指数详情
	 * @param strDressingIndex_D
	 */
//	public void setStrDressingIndex_D(String strDressingIndex_D) {
//		this.strDressingIndex_D = strDressingIndex_D;
//	}
//	/**
//	 * 48小时穿衣指数
//	 * @return
//	 */
//	public String getStr48DressingIndex() {
//		return str48DressingIndex;
//	}
//	/**
//	 * 48小时穿衣指数
//	 * @param str48DressingIndex
//	 */
//	public void setStr48DressingIndex(String str48DressingIndex) {
//		this.str48DressingIndex = str48DressingIndex;
//	}
//	/**
//	 *48小时穿衣指数详情
//	 * @return
//	 */
//	public String getStr48DressingIndex_D() {
//		return str48DressingIndex_D;
//	}
//	/**
//	 * 48小时穿衣指数详情
//	 * @param str48DressingIndex_D
//	 */
//	public void setStr48DressingIndex_D(String str48DressingIndex_D) {
//		this.str48DressingIndex_D = str48DressingIndex_D;
//	}
//	/**
//	 * 紫外线
//	 * @return
//	 */
//	public String getStrUltravioletRays() {
//		return strUltravioletRays;
//	}
//	/**
//	 * 紫外线
//	 * @param strUltravioletRays
//	 */
//	public void setStrUltravioletRays(String strUltravioletRays) {
//		this.strUltravioletRays = strUltravioletRays;
//	}
//	/**
//	 *48小时紫外线
//	 * @return
//	 */
//	public String getStr48UltravioletRays() {
//		return str48UltravioletRays;
//	}
//	/**
//	 * 48小时紫外线
//	 * @param str48UltravioletRays
//	 */
//	public void setStr48UltravioletRays(String str48UltravioletRays) {
//		this.str48UltravioletRays = str48UltravioletRays;
//	}
//	/**
//	 * 洗车
//	 * @return
//	 */
//	public String getStrClearCar() {
//		return strClearCar;
//	}
//	/**
//	 * 洗车
//	 * @param strClearCar
//	 */
//	public void setStrClearCar(String strClearCar) {
//		this.strClearCar = strClearCar;
//	}
//	/**
//	 * 旅游
//	 * @return
//	 */
//	public String getStrTravel() {
//		return strTravel;
//	}
//	/**
//	 * 旅游
//	 * @param strTravel
//	 */
//	public void setStrTravel(String strTravel) {
//		this.strTravel = strTravel;
//	}
//	/**
//	 * 舒适指数
//	 * @return
//	 */
//	public String getStrComfortIndex() {
//		return strComfortIndex;
//	}
//	/**
//	 * 舒适指数
//	 * @param strComfortIndex
//	 */
//	public void setStrComfortIndex(String strComfortIndex) {
//		this.strComfortIndex = strComfortIndex;
//	}
//	/**
//	 * 晨练
//	 * @return
//	 */
//	public String getStrMorningExercises() {
//		return strMorningExercises;
//	}
//	/**
//	 * 晨练
//	 * @param strMorningExercises
//	 */
//	public void setStrMorningExercises(String strMorningExercises) {
//		this.strMorningExercises = strMorningExercises;
//	}
//	/**
//	 *晾晒
//	 * @return
//	 */
//	public String getStrDrying() {
//		return strDrying;
//	}
//	/**
//	 * 晾晒
//	 * @param strDrying
//	 */
//	public void setStrDrying(String strDrying) {
//		this.strDrying = strDrying;
//	}
//	/**
//	 * 今天紫外线详情
//	 * @return
//	 */
//	public String getStrUltravioletRays_D() {
//		return strUltravioletRays_D;
//	}
//	/**
//	 * 今天紫外线详情
//	 * @param strUltravioletRays_D
//	 */
//	public void setStrUltravioletRays_D(String strUltravioletRays_D) {
//		this.strUltravioletRays_D = strUltravioletRays_D;
//	}
//	/**
//	 * 洗车详情
//	 * @return
//	 */
//	public String getStrClearCar_D() {
//		return strClearCar_D;
//	}
//	/**
//	 * 洗车详情
//	 * @param strClearCar_D
//	 */
//	public void setStrClearCar_D(String strClearCar_D) {
//		this.strClearCar_D = strClearCar_D;
//	}
//	/**
//	 * 旅游详情
//	 * @return
//	 */
//	public String getStrTravel_D() {
//		return strTravel_D;
//	}
//	/**
//	 * 旅游详情
//	 * @param strTravel_D
//	 */
//	public void setStrTravel_D(String strTravel_D) {
//		this.strTravel_D = strTravel_D;
//	}
//	/**
//	 * 舒适指数详情
//	 * @return
//	 */
//	public String getStrComfortIndex_D() {
//		return strComfortIndex_D;
//	}
//	/**
//	 * 舒适指数详情
//	 * @param strComfortIndex_D
//	 */
//	public void setStrComfortIndex_D(String strComfortIndex_D) {
//		this.strComfortIndex_D = strComfortIndex_D;
//	}
//	/**
//	 * 晨练详情
//	 * @return
//	 */
//	public String getStrMorningExercises_D() {
//		return strMorningExercises_D;
//	}
//	/**
//	 * 晨练详情
//	 * @param strMorningExercises_D
//	 */
//	public void setStrMorningExercises_D(String strMorningExercises_D) {
//		this.strMorningExercises_D = strMorningExercises_D;
//	}
//	/**
//	 * 晾晒详情
//	 * @return
//	 */
//	public String getStrDrying_D() {
//		return strDrying_D;
//	}
//	/**
//	 * 晾晒详情
//	 * @param strDrying_D
//	 */
//	public void setStrDrying_D(String strDrying_D) {
//		this.strDrying_D = strDrying_D;
//	}
	

	
	
}
