package com.qafeerlabs.pssst.common.model;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;

public abstract class Model  implements IModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5817832184478117379L;
	/**
	 * Escape string for single quotes (Insert,Update)
	 */
	protected String sqlEscapeString(String aString) {
		String aReturn = "";
		if (null != aString) {
			// aReturn = aString.replace(", );
			aReturn = DatabaseUtils.sqlEscapeString(aString);
			// Remove the enclosing single quotes ...
			aReturn = aReturn.substring(1, aReturn.length() - 1);
		}

		return aReturn;
	}
	@Override
	public ContentValues getContentValues() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getColumns() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IModel createObjectFromCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<? extends IModel> createObjectListFromCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		return null;
	}
}
