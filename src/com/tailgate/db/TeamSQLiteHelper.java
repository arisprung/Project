package com.tailgate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TeamSQLiteHelper extends SQLiteOpenHelper
{

	public static final String TABLE_TEAM = "team";
	public static final String COLUMN_ID = "_id";
	
	public static final String COLUMN_TEAM_NAME = "team_name";
	;

	private static final String DATABASE_NAME = "team.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_TEAM + "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_TEAM_NAME + " text);";

	public TeamSQLiteHelper(Context context)
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
		Log.w(TeamSQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAM);
		onCreate(db);

	}

}
