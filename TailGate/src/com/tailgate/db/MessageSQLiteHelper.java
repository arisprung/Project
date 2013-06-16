package com.tailgate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MessageSQLiteHelper extends SQLiteOpenHelper
{

	public static final String TABLE_MESSAGE = "message";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_IMEI = "user_imei";
	public static final String COLUMN_USERNAME = "user_name";
	public static final String COLUMN_MESSAGE = "user_message";
	public static final String COLUMN_TIME = "time_message";
	

	private static final String DATABASE_NAME = "message.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_MESSAGE + "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_USERNAME + " text not null, "+ COLUMN_IMEI + " text not null, "+ COLUMN_TIME + " text not null, "  + COLUMN_MESSAGE + " text);";

	public MessageSQLiteHelper(Context context)
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
		Log.w(LocationSQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
		onCreate(db);

	}

}
