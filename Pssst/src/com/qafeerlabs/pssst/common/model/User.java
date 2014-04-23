package com.qafeerlabs.pssst.common.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.facebook.model.GraphUser;
import com.qafeerlabs.pssst.dao.DBAdapter;


public class User extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2201099265970968322L;
	public static GraphUser profile;
	public static Setting mySetting = new Setting();
	public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
	@Override
	public ContentValues getContentValues() {
		// TODO Auto-generated method stub
		ContentValues cVal = new ContentValues();
		cVal.put(ACCESS_TOKEN, "");
		return cVal;
	}
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return DBAdapter.USER_TABLE;
	}
	@Override
	public String[] getColumns() {
		// TODO Auto-generated method stub
		return new String[]{ACCESS_TOKEN};
	}
	@Override
	public IModel createObjectFromCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		if (cursor != null)
			cursor.moveToFirst();
		User mdata = new User();
//		mdata.setId( cursor.getString(0));
		return mdata;
	}
	@Override
	public List<? extends IModel> createObjectListFromCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		List<User> mlist = new ArrayList<User>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				User data = new User();
//				data.setId( cursor.getString(0));
				// Adding complaints to list
				mlist.add(data);
			} while (cursor.moveToNext());
		}
		return mlist;
	}
}
