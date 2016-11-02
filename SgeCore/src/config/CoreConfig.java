package config;

import android.content.Context;
/**
 * 外部初始化上下文对象类
 * @author liyusheng
 */
public final class CoreConfig {
	private static Context mContext;
	public static void init(Context context){
		mContext = context;
	}
	public static Context getContext() {
		return mContext;
	}
}
