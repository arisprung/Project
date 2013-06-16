package com.tailgate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocationSQLiteHelper extends SQLiteOpenHelper
{

	public static final String TABLE_LOCATION = "location";
	public static final String COLUMN_ID = "_id";
	
	public static final String COLUMN_USERNAME = "user_name";
	public static final String COLUMN_IMEI = "user_imei";
	public static final String COLUMN_LONGITUDE = "user_longitude";
	public static final String COLUMN_LATITUDE = "user_latitude";

	private static final String DATABASE_NAME = "location.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_LOCATION + "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_USERNAME + " text not null, "+ COLUMN_IMEI + " text not null, " + COLUMN_LONGITUDE + " text, " + COLUMN_LATITUDE + " text);";

	public LocationSQLiteHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		database.execSQL(DATABASE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(LocationSQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
		onCreate(db);

	}

}
