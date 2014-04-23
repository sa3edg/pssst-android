package com.qafeerlabs.pssst.common.model;

import java.io.Serializable;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

public interface IModel extends Serializable{
	public ContentValues getContentValues();
	public String getTableName();
	public String[] getColumns();
	public IModel createObjectFromCursor(Cursor cursor);
	public List<? extends IModel> createObjectListFromCursor(Cursor cursor);

}
