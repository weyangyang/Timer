package com.timer.weather;

import java.io.Serializable;
/**
 * 未来5天天气数据bean
 * @author liyusheng
 *
 */
public class Future5WeatherBean implements Serializable {
	private static final long serialVersionUID = 8517394246858083596L;
	private String strWeather;
	private String strWeek;
	private String strTemperature;
	private String strWind;
	private String strWindLevel;
	public String getStrWeather() {
		return strWeather;
	}
	public void setStrWeather(String strWeather) {
		this.strWeather = strWeather;
	}
	public String getStrWeek() {
		return strWeek;
	}
	public void setStrWeek(String strWeek) {
		this.strWeek = strWeek;
	}
	public String getStrTemperature() {
		return strTemperature;
	}
	public void setStrTemperature(String strTemperature) {
		this.strTemperature = strTemperature;
	}
	public String getStrWind() {
		return strWind;
	}
	public void setStrWind(String strWind) {
		this.strWind = strWind;
	}
	public String getStrWindLevel() {
		return strWindLevel;
	}
	public void setStrWindLevel(String strWindLevel) {
		this.strWindLevel = strWindLevel;
	}
	@Override
	public String toString() {
		return "Future5WeatherBean [strWeather=" + strWeather + ", strWeek="
				+ strWeek + ", strTemperature=" + strTemperature + ", strWind="
				+ strWind + ", strWindLevel=" + strWindLevel + "]";
	}
	
	
}
