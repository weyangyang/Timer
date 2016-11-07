package com.qsvxin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	public static String getHourMinute(long timeMillis) {
		if (timeMillis == 0) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(new Date(timeMillis));
	}
	
	public static String getYearMonthDay(long timeMillis) {
		if (timeMillis == 0) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(timeMillis));
	}
	
	public static String getMonth(long timeMillis) {
		if (timeMillis == 0) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM月");
		return sdf.format(new Date(timeMillis));
	}
	
	public static String getDay(long timeMillis) {
		if (timeMillis == 0) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd日");
		return sdf.format(new Date(timeMillis));
	}
	
	public static String getYearMonthDayHourMinuteSecond(long timeMillis) {
		if (timeMillis == 0) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(timeMillis));
	}
	public static String getYearMonthDayHourMinute(long timeMillis) {
		if (timeMillis == 0) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(new Date(timeMillis));
	}
	
	public static String getFencePeriodStr(String period) {
		String periodStr = "";
		if (period.contains("8")) {
			periodStr = "只设一次";
		} else {
			periodStr = "星期";
			if (period.contains("2")) {
				periodStr += "一";
			}
			if (period.contains("3")) {
				periodStr += "二";
			}
			if (period.contains("4")) {
				periodStr += "三";
			}
			if (period.contains("5")) {
				periodStr += "四";
			}
			if (period.contains("6")) {
				periodStr += "五";
			}
			if (period.contains("7")) {
				periodStr += "六";
			}
			if (period.contains("1")) {
				periodStr += "日";
			}
		}
		return periodStr;
	}
}
