package com.qsvxin.db;

import java.lang.reflect.Field;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qsvxin.bean.GetUserMsgListBean;
import com.qsvxin.bean.GroupBuysListBean;
import com.qsvxin.bean.MemberBean;
import com.qsvxin.bean.VorderFormBean;
import com.qsvxin.engine.GetUserMsgList;

public class CommonDbHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "fence_master.db";
	private static final int DB_VERSION = 1;
	
	public CommonDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createTableCmd(VorderFormBean.class));
		db.execSQL(createTableCmd(GetUserMsgListBean.class));
		db.execSQL(createTableCmd(GroupBuysListBean.class));
		db.execSQL(createTableCmd(MemberBean.class));
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion == newVersion) {
			return;
		}
		db.execSQL("DROP TABLE IF EXISTS " + VorderFormBean.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + GetUserMsgListBean.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + GroupBuysListBean.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + MemberBean.TABLE_NAME);
		onCreate(db);
	}
	
	private String createTableCmd(Class clazz) {
		StringBuilder cmd = new StringBuilder();
		
		try {
			cmd.append("create table if not exists ").append(clazz.getDeclaredField("TABLE_NAME").get(null)).append("(");
		
			Field fields[] = clazz.getFields();
			for (Field f : fields) {
				ColumnAnnotation anno = f.getAnnotation(ColumnAnnotation.class);
				if (anno != null) {
					String info = anno.info();
					if (info.equals("_id")) {
						info = "integer primary key autoincrement";
					}
					cmd.append(anno.column()).append(" ").append(info).append(",");
				}
			}
			cmd.deleteCharAt(cmd.length() - 1);
			cmd.append(")");
		} catch (Exception e) {
			e.printStackTrace();
			cmd = null;
		}
		
		return cmd.toString();
	}
}
