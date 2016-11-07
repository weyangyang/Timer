package com.qsvxin.utils;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;

public class ActivityManager {
	private static List<Activity> activityList = new ArrayList<Activity>();
	
	public static void add(Activity activity) {
		activityList.add(activity);
	}
	
	public static void remove(Activity activity) {
		activityList.remove(activity);
	}
	
	public static void finishAllActivities() {
		for (Activity activity : activityList) {
			activity.finish();
		}
	}
	
	public static Activity getCurrActivity() {
		if (activityList.size() == 0) {
			return null;
		}
		return activityList.get(activityList.size()-1);
	}
}
