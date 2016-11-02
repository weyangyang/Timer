package com.timer.weather;

import java.io.Serializable;

public class WeatherOtherBean implements Serializable {
	private static final long serialVersionUID = 1458500990074459625L;
	private String strTitle;//如，晨练、路况。。。
	private String strSubTitle;//如，适宜。。。
	private String strAdvice;//如，建议、详情。。。
	private String strDescription;//如，天气稍热会对垂 钓产生一定影响
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	public String getStrSubTitle() {
		return strSubTitle;
	}
	public void setStrSubTitle(String strSubTitle) {
		this.strSubTitle = strSubTitle;
	}
	public String getStrAdvice() {
		return strAdvice;
	}
	public void setStrAdvice(String strAdvice) {
		this.strAdvice = strAdvice;
	}
	public String getStrDescription() {
		return strDescription;
	}
	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}
	@Override
	public String toString() {
		return "WeatherOtherBean [strTitle=" + strTitle + ", strSubTitle="
				+ strSubTitle + ", strAdvice=" + strAdvice
				+ ", strDescription=" + strDescription + "]";
	}
	
}