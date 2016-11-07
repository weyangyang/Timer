package com.qsvxin.db;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.qsvxin.MyApplication;

public class BaseDbBean implements Cloneable {
	public static final String _ID = "_id";
	@ColumnAnnotation(column = _ID, info = _ID)
	public long _id = -1;

	private SQLiteDatabase getDb() {
		return new CommonDbHelper(MyApplication.getApplication())
				.getReadableDatabase();
	}

	public ContentValues contentValues() {
		ContentValues values = new ContentValues();

		Field fields[] = getClass().getFields();
		for (Field f : fields) {
			ColumnAnnotation anno = f.getAnnotation(ColumnAnnotation.class);
			if (anno != null && !f.getName().equals(_ID)) {
				Type type = f.getGenericType();
				try {
					if (type.equals(int.class) || type.equals(Integer.class)) {
						values.put(anno.column(), f.getInt(this));
					} else if (type.equals(short.class)
							|| type.equals(Short.class)) {
						values.put(anno.column(), f.getShort(this));
					} else if (type.equals(byte.class)
							|| type.equals(Byte.class)) {
						values.put(anno.column(), f.getByte(this));
					} else if (type.equals(long.class)
							|| type.equals(Long.class)) {
						values.put(anno.column(), f.getLong(this));
					} else if (type.equals(boolean.class)
							|| type.equals(Boolean.class)) {
						values.put(anno.column(), f.getBoolean(this) ? 1 : 0);
					} else if (type.equals(float.class)
							|| type.equals(Float.class)) {
						values.put(anno.column(), f.getFloat(this));
					} else if (type.equals(double.class)
							|| type.equals(Double.class)) {
						values.put(anno.column(), f.getDouble(this));
					} else if (type.equals(String.class)) {
						values.put(anno.column(), (String) f.get(this));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return values;
	}

	public BaseDbBean parseCursor(Cursor cursor) {
		Field fields[] = getClass().getFields();
		for (Field f : fields) {
			ColumnAnnotation anno = f.getAnnotation(ColumnAnnotation.class);
			if (anno != null) {
				Type type = f.getGenericType();
				try {
					if (type.equals(int.class) || type.equals(Integer.class)) {
						f.set(this, cursor.getInt(cursor.getColumnIndex(anno
								.column())));
					} else if (type.equals(short.class)
							|| type.equals(Short.class)) {
						f.set(this, cursor.getShort(cursor.getColumnIndex(anno
								.column())));
					} else if (type.equals(byte.class)
							|| type.equals(Byte.class)) {
						f.set(this, (byte) cursor.getInt(cursor
								.getColumnIndex(anno.column())));
					} else if (type.equals(long.class)
							|| type.equals(Long.class)) {
						f.set(this, cursor.getLong(cursor.getColumnIndex(anno
								.column())));
					} else if (type.equals(boolean.class)
							|| type.equals(Boolean.class)) {
						f.set(this, cursor.getInt(cursor.getColumnIndex(anno
								.column())) == 1 ? true : false);
					} else if (type.equals(float.class)
							|| type.equals(Float.class)) {
						f.set(this, cursor.getFloat(cursor.getColumnIndex(anno
								.column())));
					} else if (type.equals(double.class)
							|| type.equals(Double.class)) {
						f.set(this, cursor.getDouble(cursor.getColumnIndex(anno
								.column())));
					} else if (type.equals(String.class)) {
						f.set(this, cursor.getString(cursor.getColumnIndex(anno
								.column())));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return this;
	}

	public boolean deleteAll() {
		boolean result = false;
		SQLiteDatabase db = getDb();

		try {
			int rows = db.delete(getClass().getField("TABLE_NAME").get(null)
					.toString(), null, null);
			if (rows > 0) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close();
		return result;
	}

	public boolean delete() {
		boolean result = false;
		SQLiteDatabase db = getDb();

		try {
			int rows = db.delete(getClass().getField("TABLE_NAME").get(null)
					.toString(), _ID + "=?",
					new String[] { String.valueOf(_id) });
			if (rows > 0) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		db.close();
		return result;
	}

	public List query(String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		List results = new ArrayList();
		SQLiteDatabase db = getDb();

		try {
			Cursor cursor = db.query(getClass().getField("TABLE_NAME")
					.get(null).toString(), columns, selection, selectionArgs,
					groupBy, having, orderBy);
			while (cursor.moveToNext()) {
				results.add(((BaseDbBean) clone()).parseCursor(cursor));
			}
			cursor.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		db.close();

		return results;
	}

	public boolean update() {
		boolean result = false;
		SQLiteDatabase db = getDb();

		try {
			int rows = db.update(getClass().getField("TABLE_NAME").get(null)
					.toString(), contentValues(), _ID + "=?",
					new String[] { String.valueOf(_id) });
			if (rows > 0) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		db.close();
		return result;
	}

	public boolean insert(String column, String value) {
		return insert(true, column, value);
	}

	public boolean insert() {
		return insert(true, _ID, String.valueOf(_id));
	}

	public boolean insert(boolean updateIfExist, String column, String value) {
		boolean result = false;
		SQLiteDatabase db = getDb();

		if (updateIfExist) {
			try {
				Cursor cursor = db.query(
						getClass().getField("TABLE_NAME").get(null).toString(),
						new String[] { _ID }, column + "=?",
						new String[] { value }, null, null, null);
				if (cursor.getCount() > 0) {
					int rows = db.update(
							getClass().getField("TABLE_NAME").get(null)
									.toString(), contentValues(),
							column + "=?", new String[] { value });
					if (rows > 0) {
						result = true;
					} else {
						result = false;
					}
				} else {
					long id = db.insert(
							getClass().getField("TABLE_NAME").get(null)
									.toString(), null, contentValues());
					if (id <= 0) {
						result = false;
					} else {
						_id = id;
						result = true;
					}
				}
				cursor.close();
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		} else {
			try {
				long id = db.insert(getClass().getField("TABLE_NAME").get(null)
						.toString(), null, contentValues());
				if (id <= 0) {
					result = false;
				} else {
					result = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		}

		db.close();

		return result;
	}
}
