package db.utils;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * 数据库配置类
 * @author liyusheng
 *
 */
public class DBConfig implements Serializable {
	private static final long serialVersionUID = -7682826928063213894L;
	private static final String DB_NAME = "cowboy.db";
	private static final int DB_VERSION = 1;
    private static DBConfig mDBConfig;

    private DBConfig() {

    }

    public  static DBConfig getInstance() {
        if (null == mDBConfig) {
        	mDBConfig = new DBConfig();
        }
        return mDBConfig;

    }
	private int dbVersion;
	private String dbName;
	protected int getDbVersion() {
		if(dbVersion<=0){
			return DB_VERSION;
		}
		return dbVersion;
	}
	protected void setDbVersion(int dbVersion) {
		this.dbVersion = dbVersion;
	}
	protected String getDbName() {
		if(TextUtils.isEmpty(dbName)){
			return DB_NAME;
		}
		return dbName;
	}
	protected void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
}
