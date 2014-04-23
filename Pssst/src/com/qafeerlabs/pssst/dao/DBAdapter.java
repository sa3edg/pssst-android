package com.qafeerlabs.pssst.dao;

import java.util.List;

import com.qafeerlabs.pssst.common.model.IModel;
import com.qafeerlabs.pssst.common.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DBAdapter {

	/**
	 * if debug is set true then it will show all Logcat message
	 **/
	public static final boolean DEBUG = true;

	/**
	 * Logcat TAG
	 */
	public static final String LOG_TAG = DBAdapter.class.getSimpleName();
	
	/**
	 * Database Name
	 */
	public static final String DATABASE_NAME = "pssst.db";

	/**
	 * Database Version (Increase one if want to also upgrade your database)
	 */
	public static final int DATABASE_VERSION = 1;// started at 1

	/**
	 * Tables names
	 */
	public static final String USER_TABLE = "USER_TABLE";

	/**
	 * Set all table with comma seperated like SAVED_COMPLAINTS_TABLE,ABC_TABLE
	 */
	private static final String[] ALL_TABLES = { USER_TABLE};

	/**
	 * Used to open database in syncronized way
	 */
	private static DataBaseHelper DBHelper = null;

	protected DBAdapter() {

	}

	/**
	 * Initialize database
	 */
	public static void init(Context context) {
		if (DBHelper == null) {
			if (DEBUG)
				Log.i("DBAdapter", context.toString());
			DBHelper = new DataBaseHelper(context);
		}
	}

	/**
	 * Main Database creation INNER class
	 */
	private static class DataBaseHelper extends SQLiteOpenHelper {
		public DataBaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			if (DEBUG)
				Log.i(LOG_TAG, "new create");
			try {
				String userCreate = "create table "
						+ USER_TABLE + " ( "
						+ User.ACCESS_TOKEN
						+ " text primary key , "
						+ User.ACCESS_TOKEN + " text," + User.ACCESS_TOKEN
						+ " text," + User.ACCESS_TOKEN + " text);";
				db.execSQL(userCreate);

			} catch (Exception exception) {
				if (DEBUG)
					Log.i(LOG_TAG, "Exception onCreate() exception");
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (DEBUG)
				Log.w(LOG_TAG, "Upgrading database from version" + oldVersion
						+ "to" + newVersion + "...");

			for (String table : ALL_TABLES) {
				db.execSQL("DROP TABLE IF EXISTS " + table);
			}
			onCreate(db);
		}

	} // Inner class closed

	/**
	 * Open database for insert,update,delete in syncronized manner
	 */
	private static synchronized SQLiteDatabase open() throws SQLException {
		return DBHelper.getWritableDatabase();
	}

	

	public static void insertObject(IModel mdata) {

		// Open database for Read / Write
		final SQLiteDatabase db = open();
		ContentValues cVal = mdata.getContentValues();
		// Insert user values in database
		db.insert(mdata.getTableName(), null, cVal);
		// Closing database connection
		db.close(); 
	}
	

	// Updating single row
	public static int updateObject(IModel mdata, String keyId, String keyValue) {

		final SQLiteDatabase db = open();
		ContentValues cVal = mdata.getContentValues();
		// updating row
		int count = db.update(mdata.getTableName(), cVal, keyId
				+ " = ?", new String[] { keyValue });
		db.close();
		return count;
	}

	// Getting single attachment
	public static IModel getObject(IModel mdata, String keyId, String keyValue) {

		// Open database for Read / Write
		final SQLiteDatabase db = open();
		try{
		Cursor cursor = db.query(mdata.getTableName(), mdata.getColumns(), keyId + "=?",
				new String[] { keyValue }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();
		
		return mdata.createObjectFromCursor(cursor);
		}
		finally{
		db.close();
		}
	}

	// Getting All object list
	public static List<? extends IModel> getObjectList(IModel mdata, String keyId, String keyValue) {

		// Select All Query
		String selectQuery = "SELECT  * FROM " + mdata.getTableName() + " where " + keyId + "=?";

		String[] args={keyValue};
		// Open database for Read / Write
		final SQLiteDatabase db = open();
		try{
		Cursor cursor = db.rawQuery(selectQuery, args);
		return mdata.createObjectListFromCursor(cursor);
		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				// Adding complaints to list
//				contactList.add(mdata.createObjectFromCursor(cursor));
//			} while (cursor.moveToNext());
//		}
		}
		finally{
		db.close();
		}
	}
	
	// Getting All object list
		public static List<? extends IModel> getObjectList(IModel mdata) {

			// Select All Query
			String selectQuery = "SELECT  * FROM " + mdata.getTableName();

			// Open database for Read / Write
			final SQLiteDatabase db = open();
			try{
			Cursor cursor = db.rawQuery(selectQuery, null);
			return mdata.createObjectListFromCursor(cursor);
			// looping through all rows and adding to list
//			if (cursor.moveToFirst()) {
//				do {
//					// Adding complaints to list
//					contactList.add(mdata.createObjectFromCursor(cursor));
//				} while (cursor.moveToNext());
//			}
			}
			finally{
			db.close();
			}
		}

	// Deleting single object
	public static void deleteObject(IModel mdata, String keyId, String keyValue) {
		final SQLiteDatabase db = open();
		db.delete(mdata.getTableName(), keyId + " = ?",
				new String[] { keyValue });
		db.close();
	}
	
	// Deleting single object
		public static void deleteObject(IModel mdata) {
			final SQLiteDatabase db = open();
			db.delete(mdata.getTableName(), null, null );
			db.close();
		}


	// Getting dataCount

	public static int getSavedObjectCount(IModel mdata) {

		final SQLiteDatabase db = open();

		String countQuery = "SELECT  * FROM " + mdata.getTableName();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public static Bitmap getImageFromTable(IModel mdata, String keyId, String keyValue) {

		final SQLiteDatabase db = open();
		Cursor cursor = db.query(mdata.getTableName(), mdata.getColumns(), keyId + "=?",
				new String[] { keyValue }, null, null, null, null);

		if (cursor.moveToFirst()) {
			byte[] imgByte = cursor.getBlob(0);
			cursor.close();
			return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return null;
	}

} // CLASS CLOSED
