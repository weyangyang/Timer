package db.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

public class BaseDbBean implements Cloneable {
	public static final String _ID = "_id";
	@ColumnAnnotation(column = _ID, info = _ID)
	public long _id = -1;
	private static Context mContext ;
	private static final int DB_TYPE_LONG = 11;
	private static final int DB_TYPE_STRING = 12;
	private static final int DB_TYPE_BLOB = 13;
	private static final int DB_TYPE_DOUBLE = 14;
	private static final int DB_TYPE_SHORT = 15;
	private static final int DB_TYPE_INTEGER = 16;
	private static final int DB_TYPE_FLOAT = 17;
	
	public static void init(Context context,
			Class<? extends BaseDbBean>... dbBeans) {
		mContext = context;
		for (int i = 0; i < dbBeans.length; i++) {
			mDbBeans.add(dbBeans[i]);
		}
	}

	public static void setDBConfig(String dbName, int dbVersion) {
		DBConfig.getInstance().setDbName(dbName);
		DBConfig.getInstance().setDbVersion(dbVersion);
	}

	private static List<Class<? extends BaseDbBean>> mDbBeans = new ArrayList<Class<? extends BaseDbBean>>();

	static List<Class<? extends BaseDbBean>> getDbBeans() {
		return mDbBeans;
	}

	 static HashMap<String, List<TableDataListener>> observersMap = new HashMap<String, List<TableDataListener>>();

	public static void registerContentObserver(String tableName,
			TableDataListener observer) {
		List<TableDataListener> observerList = observersMap.get(tableName);
		if (observerList == null) {
			observerList = new ArrayList<TableDataListener>();
			observersMap.put(tableName, observerList);
		}
		observerList.add(observer);
	}

	public static void unregisterContentObserver(String tableName,
			TableDataListener observer) {
		List<TableDataListener> observerList = observersMap.get(tableName);
		if (observerList != null) {
			observerList.remove(observer);
		}
	}
	
	static android.database.sqlite.SQLiteDatabase mSQLiteDatabase ;
	static SQLiteDatabase getDb() {
		if(mSQLiteDatabase!=null&&mSQLiteDatabase.isOpen()){
				return mSQLiteDatabase;
		}else{
			mSQLiteDatabase = new CommonDbHelper(mContext).getReadableDatabase();
		}
		return mSQLiteDatabase;
	}
	 static SQLiteDatabase getSQLiteDatabaseInstance(){
		return mSQLiteDatabase;
	}
	 void setSQLiteDatabaseInstance(SQLiteDatabase db){
		mSQLiteDatabase = db;
	}
	 public ContentValues contentValues(){
		return contentValues(null);
	 }
	public ContentValues contentValues(String[] keys) {
		ContentValues values = new ContentValues();
		HashMap<String,Boolean> hashMap = null;
		if(keys!=null){
			hashMap = new HashMap<String, Boolean>();
			for(String key:keys){
				hashMap.put(key, true);
			}
		}
		Field fields[] = getClass().getFields();
		int typeNum = 0; 
		int []typeNums = new int[fields.length];
		 hashMapTemp = new HashMap<String, Object>();
		for (Field f : fields) {
			ColumnAnnotation anno = f.getAnnotation(ColumnAnnotation.class);
			if (anno != null && !f.getName().equals(_ID)) {
				Type type = f.getGenericType();
				if(hashMap!=null&&null == hashMap.get(anno.column())){
					continue;
				}
				try {
					if (type.equals(int.class) || type.equals(Integer.class)) {
						typeNum = DB_TYPE_INTEGER;
						values.put(anno.column(), f.getInt(this));
						hashMapTemp.put(anno.column(), typeNum);
					} else if (type.equals(short.class)
							|| type.equals(Short.class)) {
						typeNum = DB_TYPE_SHORT;
						hashMapTemp.put(anno.column(), typeNum);
						values.put(anno.column(), f.getShort(this));
					} else if (type.equals(byte.class)
							|| type.equals(Byte.class)) {
						typeNum = DB_TYPE_BLOB;
						hashMapTemp.put(anno.column(), typeNum);
						values.put(anno.column(), f.getByte(this));
					} else if (type.equals(long.class)
							|| type.equals(Long.class)) {
						typeNum = DB_TYPE_LONG;
						hashMapTemp.put(anno.column(), typeNum);
						values.put(anno.column(), f.getLong(this));
					} else if (type.equals(boolean.class)
							|| type.equals(Boolean.class)) {
						typeNum = DB_TYPE_INTEGER;
						hashMapTemp.put(anno.column(), typeNum);
						values.put(anno.column(), f.getBoolean(this) ? 1 : 0);
					} else if (type.equals(float.class)
							|| type.equals(Float.class)) {
						typeNum = DB_TYPE_FLOAT;
						hashMapTemp.put(anno.column(), typeNum);
						values.put(anno.column(), f.getFloat(this));
					} else if (type.equals(double.class)
							|| type.equals(Double.class)) {
						typeNum = DB_TYPE_DOUBLE;
						hashMapTemp.put(anno.column(), typeNum);
						values.put(anno.column(), f.getDouble(this));
					} else if (type.equals(String.class)) {
						typeNum = DB_TYPE_STRING;
						hashMapTemp.put(anno.column(), typeNum);
						values.put(anno.column(), (String) f.get(this));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(typeNum!=0){
				typeNum = 0;
			}
		}
		setTypeNums(typeNums,hashMapTemp);
		return values;
	}

	HashMap<String,Object> hashMapTemp;
	private HashMap<String,Object> getTypeNums() {
		return hashMapTemp;
	}

	private void setTypeNums(int[] typeNums,HashMap<String,Object> hashMapTemp) {
		this.hashMapTemp = hashMapTemp;
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
	/**
	 * Query the given table, returning a {@link Cursor} over the result set.
	 * 
	 * @param columns A list of which columns to return. Passing null will
     *            return all columns, which is discouraged to prevent reading
     *            data from storage that isn't going to be used.
     * @param selection A filter declaring which rows to return, formatted as an
     *            SQL WHERE clause (excluding the WHERE itself). Passing null
     *            will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *         replaced by the values from selectionArgs, in order that they
     *         appear in the selection. The values will be bound as Strings.
     * @param groupBy A filter declaring how to group rows, formatted as an SQL
     *            GROUP BY clause (excluding the GROUP BY itself). Passing null
     *            will cause the rows to not be grouped.
     * @param having A filter declare which row groups to include in the cursor,
     *            if row grouping is being used, formatted as an SQL HAVING
     *            clause (excluding the HAVING itself). Passing null will cause
     *            all row groups to be included, and is required when row
     *            grouping is not being used.
     * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause
     *            (excluding the ORDER BY itself). Passing null will use the
     *            default sort order, which may be unordered.
	 * 
	 * @return  A {@link Cursor} object, which is positioned before the first entry. Note that
     * {@link Cursor}s are not synchronized, see the documentation for more details.
     * @see Cursor
	 */
	public Cursor queryCursor(String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy){
		SQLiteDatabase db = getDb();
		setSQLiteDatabaseInstance(db);
		Cursor cursor = null;
		try{
		 cursor = db.query(getTableName(), columns, selection, selectionArgs,
					groupBy, having, orderBy);
		}catch(Exception e){
			
		}
		return cursor;
		
	}
	/**
	 * Query the given table
	 * @param columns A list of which columns to return. Passing null will
     *            return all columns, which is discouraged to prevent reading
     *            data from storage that isn't going to be used.
     * @param selection A filter declaring which rows to return, formatted as an
     *            SQL WHERE clause (excluding the WHERE itself). Passing null
     *            will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *         replaced by the values from selectionArgs, in order that they
     *         appear in the selection. The values will be bound as Strings.
     * @param groupBy A filter declaring how to group rows, formatted as an SQL
     *            GROUP BY clause (excluding the GROUP BY itself). Passing null
     *            will cause the rows to not be grouped.
     * @param having A filter declare which row groups to include in the cursor,
     *            if row grouping is being used, formatted as an SQL HAVING
     *            clause (excluding the HAVING itself). Passing null will cause
     *            all row groups to be included, and is required when row
     *            grouping is not being used.
     * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause
     *            (excluding the ORDER BY itself). Passing null will use the
     *            default sort order, which may be unordered.
	 * @return list contain  bean extends of BaseDbBean
	 */
	public List query(String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		List results = new ArrayList();
		SQLiteDatabase db = getDb();
		setSQLiteDatabaseInstance(db);
		try {
			Cursor cursor = db.query(getTableName(), columns, selection, selectionArgs,
					groupBy, having, orderBy);
			while (cursor.moveToNext()) {
				results.add(((BaseDbBean) clone()).parseCursor(cursor));
			}
			cursor.close();

		} catch (Exception e) {
		}

		//db.close();

		return results;
	}
	public BaseDbBean querySingle(String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		SQLiteDatabase db = getDb();
		setSQLiteDatabaseInstance(db);
		try {
			Cursor cursor = db.query(getClass().getField("TABLE_NAME")
					.get(null).toString(), columns, selection, selectionArgs,
					groupBy, having, orderBy,"1");
			if(cursor.moveToNext()){
				return parseCursor(cursor);
			}
			cursor.close();
			
		} catch (Exception e) {
			return null;
		}
		
		//db.close();
		return null;
	}
	
	
	/**
	 * Convenience method for deleting rows in the database.
	 * @param whereClause whereClause the optional WHERE clause to apply when deleting.
     *            Passing null will delete all rows.
	 * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
	 * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. To remove all rows and get a count pass "1" as the
     *         whereClause.
	 */
	public int statementDelete(String whereClause, String[] whereArgs){
		return statementDelete(getTableName(), whereClause, whereArgs);
	}
	/**
	 * Convenience method for deleting rows in the database.
	 * @param table the table to delete from
	 * @param whereClause whereClause the optional WHERE clause to apply when deleting.
     *            Passing null will delete all rows.
	 * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
	 * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. To remove all rows and get a count pass "1" as the
     *         whereClause.
	 */
	public int statementDelete(String table, String whereClause, String[] whereArgs){
		int rows = 0;
		 SQLiteDatabase db = getDb();
		 setSQLiteDatabaseInstance(db);
		 StringBuilder sql = new StringBuilder(120);
         sql.append("DELETE FROM ");
         sql.append(table);
		 if (!TextUtils.isEmpty(whereClause) && whereArgs != null && whereArgs.length > 0) {
				String[] temp = new String[whereArgs.length];
				for (int k = 0; k < whereArgs.length; k++) {
					temp[k] = "'" + whereArgs[k] + "'";
				}
				sql.append(" WHERE ");
				whereClause = whereClause.replace("?", "%s");
				whereClause = String.format(whereClause, temp);
				sql.append(whereClause);
			}
		 SQLiteStatement statement = null;
         try {
          statement = db.compileStatement(sql.toString());
         	db.beginTransaction();
         	rows =  statement.executeUpdateDelete();
         	if(rows>0){
         		db.setTransactionSuccessful();
         		notifyChange(TableDataListener.TYPE_ADD);
         	}
         }catch(Exception e){}
         finally {
         	if(statement!=null){
         		statement.close();
         		db.endTransaction();
         	}
         }
		return rows;
		
	}
	
	@Deprecated
	public boolean delete() {
		boolean result = false;
		SQLiteDatabase db = getDb();
		setSQLiteDatabaseInstance(db);
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

		//db.close();
		if (result) {
			notifyChange(TableDataListener.TYPE_DELETE);
		}
		return result;
	}

	public int rawDelete(String whereClause, String[] whereArgs) {
		int rows = 0;
		SQLiteDatabase db = getDb();
		setSQLiteDatabaseInstance(db);
		try {
			rows = db.delete(getClass().getField("TABLE_NAME").get(null)
					.toString(), whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//db.close();
		if (rows > 0) {
			notifyChange(TableDataListener.TYPE_RAW_DELETE);
		}
		return rows;
	}
	public int rawUpdate(String[]keys,String wherekey){
		if(TextUtils.isEmpty(wherekey)){
			return -1;
		}
		String value = null;
		try {
			value = getClass().getField(wherekey).get(this).toString();
		} catch (IllegalAccessException e) {
			return -1;
		} catch (IllegalArgumentException e) {
			return -1;
		} catch (NoSuchFieldException e) {
			return -1;
		}
		return rawUpdate(contentValues(keys), wherekey+"=?", new String[]{value});
		
	}
	
	public int rawUpdate(String[]keys,String whereClause,
			String[] whereArgs){
		int rows = 0;
		SQLiteDatabase db = getDb();
		setSQLiteDatabaseInstance(db);
		try {
			rows = db.update(getClass().getField("TABLE_NAME").get(null)
					.toString(), contentValues(keys),whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//db.close();
		if (rows > 0) {
			notifyChange(TableDataListener.TYPE_RAW_UPDATE);
		}
		
		return rows;
		
	}

	/**
	 * 按列名更新数据库
	 *         <p>
	 *         This method is not thread-safe.
	 *         </p>
	 * 
	 * @param columns
	 *            要更新的列名 ，该参数为null时，整列更新
	 * @param whereClause
	 *            the optional WHERE clause to apply when updating. Passing null
	 *            will update all rows.
	 * @param whereArgs
	 *            You may include ?s in the where clause, which will be replaced
	 *            by the values from whereArgs. The values will be bound as
	 *            Strings.
	 * @return the number of rows affected
	 */
	public int statementUpdate(String[]columns,String whereClause,
			String[] whereArgs){
			int rows = -1;
			ContentValues values = contentValues(columns);
			if (values == null || values.size() == 0) {
				throw new IllegalArgumentException("Empty values");
			}
			 StringBuilder sql = new StringBuilder(120);
	            sql.append("UPDATE ");
	            sql.append(getTableName());
	            sql.append(" SET ");
	            int i = 0;
	            if(columns!=null&&columns.length>0){
	            	for (String colName :columns) {
	            		sql.append((i > 0) ? "," : "");
	            		sql.append(colName);
	            		sql.append("=?");
	            		i++;
	            	}
	            }else{
	            	for (String colName :values.keySet()) {
	            		sql.append((i > 0) ? "," : "");
	            		sql.append(colName);
	            		sql.append("=?");
	            		i++;
	            	}
	            }
				if (!TextUtils.isEmpty(whereClause) && whereArgs != null && whereArgs.length > 0) {
					String[] temp = new String[whereArgs.length];
					for (int k = 0; k < whereArgs.length; k++) {
						temp[k] = "'" + whereArgs[k] + "'";
					}
					sql.append(" WHERE ");
					whereClause = whereClause.replace("?", "%s");
					whereClause = String.format(whereClause, temp);
					sql.append(whereClause);
				}
	            SQLiteDatabase db = getDb();
	            SQLiteStatement statement = null;
	            try {
	            	statement =  getSQLiteStatement (columns, db ,sql.toString(),values);
	            	db.beginTransaction();
	            	rows =  statement.executeUpdateDelete();
	            	if(rows>0){
	            		db.setTransactionSuccessful();
	            		notifyChange(TableDataListener.TYPE_ADD);
	            	}
	            }catch(Exception e){}
	            finally {
	            	if(statement!=null){
	            		statement.close();
	            		db.endTransaction();
	            	}
	            }
			return rows;
			
		}
	/**
	 * 按列名更新数据库
	 *         <p>
	 *         This method is not thread-safe.
	 *         </p>
	 * 
	 * @param columns
	 *            要更新的列名，Passing null will update all rows.
	 * @param wherekeys where条件里的column，在底层只拼接 “＝”,不能为null
	 * @return the number of rows affected
	 */
	public int statementUpdate(String[]columns,String ... wherekeys){
		String whereClause = "";
		String [] whereArgs = null;
		if(wherekeys!=null&&wherekeys.length>0){
			whereArgs = new String[wherekeys.length];
			for(int i=0;i<wherekeys.length;i++){
				whereClause += wherekeys[i]+"=?";
				String value =null;
				try {
				  value = getClass().getField(wherekeys[i]).get(this).toString();
				  whereArgs[i] = value;
				} catch (IllegalAccessException e) {
					continue;
				} catch (IllegalArgumentException e) {
					continue;
				} catch (NoSuchFieldException e) {
					continue;
				}
			}
		}
		return statementUpdate(columns, whereClause, whereArgs);
	}

	public int rawUpdate(ContentValues values, String whereClause,
			String[] whereArgs) {
		int rows = 0;
		SQLiteDatabase db = getDb();
		setSQLiteDatabaseInstance(db);
		try {
			rows = db.update(getClass().getField("TABLE_NAME").get(null)
					.toString(), values, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//db.close();
		if (rows > 0) {
			notifyChange(TableDataListener.TYPE_RAW_UPDATE);
		}
		return rows;
	}
	@Deprecated
	public boolean update() {
		boolean result = false;
		SQLiteDatabase db = getDb();
		setSQLiteDatabaseInstance(db);
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

		//db.close();
		if (result) {
			notifyChange(TableDataListener.TYPE_UPDATE);
		}
		return result;
	}
	@Deprecated
	public boolean insert() {
		return insert(false);
	}
	@Deprecated
	public boolean insert(boolean updateIfExist) {

		return insert(updateIfExist, null, null);

	}
	
	/**
	 * 插入单条数据
	 * <p>
	 * this method is not thread-safe.
	 * </p>
	 * 
	 * @return the row ID of the newly inserted row, or -1 if an error occurred
	 */
	public long statementInsert(){
		return statementInsert(null);
	}

	/**
	 * 插入单条数据
	 * <p>
	 * this method is not thread-safe.
	 * </p>
	 * 
	 * @param nullColumnHack optional; may be <code>null</code>.
     *            SQL doesn't allow inserting a completely empty row without
     *            naming at least one column name.  If your provided <code>values</code> is
     *            empty, no column names are known and an empty row can't be inserted.
     *            If not set to null, the <code>nullColumnHack</code> parameter
     *            provides the name of nullable column name to explicitly insert a NULL into
     *            in the case where your <code>values</code> is empty.
	 * @return the row ID of the newly inserted row, or -1 if an error occurred
	 */
	public long statementInsert(String nullColumnHack){
		long insertNum = -1;
		ContentValues cv = contentValues();
		 StringBuilder sql = new StringBuilder();
         sql.append("INSERT");
         sql.append(" INTO ");
         sql.append(getTableName());
         sql.append('(');
         int size = (cv != null && cv.size() > 0) ? cv.size() : 0;
         if (size > 0) {
             int i = 0;
             for (String colName : cv.keySet()) {
                 sql.append((i > 0) ? "," : "");
                 sql.append(colName);
             }
             sql.append(')');
             sql.append(" VALUES (");
             for (i = 0; i < size; i++) {
                 sql.append((i > 0) ? ",?" : "?");
             }
         } else {
             sql.append(nullColumnHack + ") VALUES (NULL");
         }
         sql.append(')');
		SQLiteDatabase db = getDb();
		  SQLiteStatement statement = null;
        try {
        	statement =  getSQLiteStatement (null, db ,sql.toString(),cv);
        	db.beginTransaction();
        	insertNum =  statement.executeInsert();
        	if(insertNum>=0){
        		db.setTransactionSuccessful();
        		notifyChange(TableDataListener.TYPE_ADD);
        	}
        }catch(Exception e){
        }
        finally {
        	if(statement!=null){
        		statement.close();
        		db.endTransaction();
        	}
        }
		return insertNum;
	}
	
	private SQLiteStatement getSQLiteStatement (String []keys,SQLiteDatabase db ,String sql,ContentValues cv){
		setSQLiteDatabaseInstance(db);
        SQLiteStatement statement = db.compileStatement(sql);
        hashMapTemp =  getTypeNums();
        if(keys!=null&&keys.length>0){
        	for(String key:keys){
        		if(!hashMapTemp.keySet().contains(key)){
        			hashMapTemp.remove(key);
        		}
        	}
        }
        int tempNum = 0;
        for(String key: hashMapTemp.keySet()){
        		tempNum ++;
        		if(hashMapTemp!=null&&hashMapTemp.size()>0){
        			int tempType = (Integer) hashMapTemp.get(key);
        			switch(tempType){
        			case DB_TYPE_BLOB:
        				statement.bindBlob(tempNum,(byte[]) cv.get(key) );
        				break;
        			case DB_TYPE_DOUBLE:
        				statement.bindDouble(tempNum, (Double) cv.get(key));
        				break;
        			case DB_TYPE_LONG:
        				statement.bindLong(tempNum, (Long) cv.get(key));
        				break;
        			case DB_TYPE_STRING:
        				String value = (String) cv.get(key);
        				if(null == value){
        					value = "";
        				}
        				statement.bindString(tempNum,value);
        				break;
        			case DB_TYPE_INTEGER:
        				statement.bindLong(tempNum,(Integer) cv.get(key) );
        				break;
        			case DB_TYPE_FLOAT:
        				statement.bindDouble(tempNum,(Float) cv.get(key) );
        				break;
        			case DB_TYPE_SHORT:
        				statement.bindDouble(tempNum,(Short) cv.get(key) );
        				break;
        		}
        }
        }
        return statement;
	}
	public boolean insert(boolean updateIfExist, String column,
			String selectionArg) {
		if (TextUtils.isEmpty(selectionArg)) {
			selectionArg = _id + "";
		}
		if(TextUtils.isEmpty(column)){
			column = _ID;
		}
		boolean result = false;
		int type = 0;
		//boolean isInsert = true;
		SQLiteDatabase db = getDb();
		setSQLiteDatabaseInstance(db);
		String tableName = getTableName();
		if (updateIfExist) {
				int rows = rawUpdate(contentValues(),column + "=?", new String[] { selectionArg });
				if(rows>0){
					result = true;
					type = TableDataListener.TYPE_UPDATE;
				}else{
					type = TableDataListener.TYPE_ADD;
					long id = db.insert(tableName, null, contentValues());
					if (id <= 0) {
						result = false;
					} else {
						_id = id;
						result = true;
					}
					
				}
		} else {
				type = TableDataListener.TYPE_ADD;
				long id = db.insert(tableName, null, contentValues());
				if (id <= 0) {
					result = false;
				} else {
					_id = id;
					result = true;
				}
		}

		//db.close();
		if (result) {
			notifyChange(type);
		}

		return result;
	}

	private String getTableName() {
		String tableName = "";
		try {
			tableName = getClass().getField("TABLE_NAME").get(null).toString();
		} catch (IllegalAccessException e1) {
		} catch (IllegalArgumentException e1) {
		} catch (NoSuchFieldException e1) {
		}
		return tableName;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private  void notifyChange(int type) {
		try {
			List<TableDataListener> observerList = observersMap.get(getTableName());
			if (observerList != null) {
				for (TableDataListener observer : observerList) {
					switch (type) {
					case TableDataListener.TYPE_ADD:
						observer.notifyDataChange(TableDataListener.TYPE_ADD, this);
						break;
					case TableDataListener.TYPE_UPDATE:
						observer.notifyDataChange(TableDataListener.TYPE_UPDATE, this);
						break;
					case TableDataListener.TYPE_RAW_UPDATE:
						observer.notifyDataChange(TableDataListener.TYPE_RAW_UPDATE, this);
						break;
					case TableDataListener.TYPE_RAW_DELETE:
						observer.notifyDataChange(TableDataListener.TYPE_RAW_DELETE, this);
						break;
					case TableDataListener.TYPE_DELETE:
						observer.notifyDataChange(TableDataListener.TYPE_DELETE, this);
						break;
					}
				}
			}
		} catch (Exception e) {
		}
	}
}
