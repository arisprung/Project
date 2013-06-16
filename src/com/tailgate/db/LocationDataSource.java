package com.tailgate.db;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Comment;

import com.tailgate.LocationBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LocationDataSource
{

	// Database fields
	private SQLiteDatabase database;
	private LocationSQLiteHelper dbHelper;
	private String[] allColumns = { LocationSQLiteHelper.COLUMN_ID, LocationSQLiteHelper.COLUMN_USERNAME, LocationSQLiteHelper.COLUMN_IMEI,
			LocationSQLiteHelper.COLUMN_LONGITUDE, LocationSQLiteHelper.COLUMN_LATITUDE };

	public LocationDataSource(Context context)
	{
		dbHelper = new LocationSQLiteHelper(context);
	}

	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}

	public void close()
	{
		dbHelper.close();
	}

	public LocationBean createLocationEntry(LocationBean location)
	{
		
		Cursor cursor;
		ContentValues values = new ContentValues();
		values.put(LocationSQLiteHelper.COLUMN_USERNAME, location.getUsername());
		values.put(LocationSQLiteHelper.COLUMN_IMEI, location.getImei());
		values.put(LocationSQLiteHelper.COLUMN_LONGITUDE, location.getLongitude());
		values.put(LocationSQLiteHelper.COLUMN_LATITUDE, location.getLatitude());
		
		cursor = database.rawQuery("SELECT * FROM " + LocationSQLiteHelper.TABLE_LOCATION + " WHERE "+ LocationSQLiteHelper.COLUMN_IMEI+
				" = '" + location.getImei() + "'", null);
		if (cursor.getCount() > 0) 
		{ 
			database.update(LocationSQLiteHelper.TABLE_LOCATION, values,  LocationSQLiteHelper.COLUMN_IMEI+"="+location.getImei(), null);
		}
		else
		{
			long insertId = database.insert(LocationSQLiteHelper.TABLE_LOCATION, null, values);
			cursor = database.query(LocationSQLiteHelper.TABLE_LOCATION, allColumns, LocationSQLiteHelper.COLUMN_ID + " = " + insertId, null,
					null, null, null);
		}
		
		
		 
		cursor.moveToFirst();
		LocationBean newLocationBean = cursorToLocation(cursor);
		cursor.close();
		return newLocationBean;
	}

	public void deleteLocation(String username)
	{
		database.delete(LocationSQLiteHelper.TABLE_LOCATION, LocationSQLiteHelper.COLUMN_USERNAME + " = " + username, null);
	}

	public List<LocationBean> getAllLocationBeans()
	{
		List<LocationBean> locationlist = new ArrayList<LocationBean>();

		Cursor cursor = database.query(LocationSQLiteHelper.TABLE_LOCATION, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			LocationBean locationbean = cursorToLocation(cursor);
			locationlist.add(locationbean);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return locationlist;
	}

	private LocationBean cursorToLocation(Cursor cursor)
	{
		LocationBean location = new LocationBean();
		location.setId(cursor.getLong(0));
		location.setUsername(cursor.getString(1));
		location.setImei(cursor.getString(2));
		location.setLongitude(cursor.getString(3));
		location.setLatitude(cursor.getString(4));
		return location;
	}

}
