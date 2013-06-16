package com.tailgate.db;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Comment;

import com.tailgate.TeamBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TeamDataSource
{

	// Database fields
	private SQLiteDatabase database;
	private TeamSQLiteHelper dbHelper;
	private String[] allColumns = { TeamSQLiteHelper.COLUMN_ID, TeamSQLiteHelper.COLUMN_TEAM_NAME };

	public TeamDataSource(Context context)
	{
		dbHelper = new TeamSQLiteHelper(context);
	}

	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}

	public void close()
	{
		dbHelper.close();
	}

	public boolean checkIfTeamExist(String team)
	{
		Cursor cursor;

		cursor = database.rawQuery("SELECT * FROM " + TeamSQLiteHelper.TABLE_TEAM + " WHERE " + TeamSQLiteHelper.COLUMN_TEAM_NAME + " = '" + team
				+ "'", null);
		if (cursor.getCount() > 0)
		{
			return true;
		}
		return false;
	}

	public int checkHowManyTeamsExist()
	{
		Cursor cursor;

		cursor = database.rawQuery("SELECT * FROM " + TeamSQLiteHelper.TABLE_TEAM, null);

		return cursor.getCount();
	}

	public TeamBean createTeamEntry(String team)
	{

		ContentValues values = new ContentValues();
		values.put(TeamSQLiteHelper.COLUMN_TEAM_NAME, team);

		long insertId = database.insert(TeamSQLiteHelper.TABLE_TEAM, null, values);
		Cursor cursor = database
				.query(TeamSQLiteHelper.TABLE_TEAM, allColumns, TeamSQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		TeamBean newTeamBean = cursorToTeam(cursor);
		cursor.close();
		return newTeamBean;
	}

	public void deleteTeam(String teamname)
	{
		try	
		{
			database.delete(TeamSQLiteHelper.TABLE_TEAM, TeamSQLiteHelper.COLUMN_TEAM_NAME + "='"+teamname+"'", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public ArrayList<String> getAllTeamBeans()
	{
		ArrayList<String> teamlist = new ArrayList<String>();
		Cursor cursor = database.query(TeamSQLiteHelper.TABLE_TEAM, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			TeamBean teambean = cursorToTeam(cursor);
			teamlist.add(teambean.getTeamname());
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return teamlist;
	}

	private TeamBean cursorToTeam(Cursor cursor)
	{
		TeamBean team = new TeamBean();
		team.setId(cursor.getLong(0));
		team.setTeamname(cursor.getString(1));

		return team;
	}

}
