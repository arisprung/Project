//package com.tailgate.db;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.tailgate.MessageBean;
//
//public class MessageDataSource
//{
//
//	// Database fields
//	private SQLiteDatabase database;
//	private MessageSQLiteHelper dbHelper;
//	public static  String[] allColumns = { MessageSQLiteHelper.COLUMN_ID, MessageSQLiteHelper.COLUMN_USERNAME,MessageSQLiteHelper.COLUMN_IMEI,MessageSQLiteHelper.COLUMN_TIME, MessageSQLiteHelper.COLUMN_MESSAGE };
//
//	public MessageDataSource(Context context)
//	{
//		dbHelper = new MessageSQLiteHelper(context);
//	}
//
//	public void open() throws SQLException
//	{
//		database = dbHelper.getWritableDatabase();
//	}
//
//	public void close()
//	{
//		dbHelper.close();
//	}
//
//	public MessageBean createMessageEntry(MessageBean message)
//	{
//		ContentValues values = new ContentValues();
//		values.put(MessageSQLiteHelper.COLUMN_USERNAME, message.getUsername());
//		values.put(MessageSQLiteHelper.COLUMN_IMEI, message.getImei());
//		values.put(MessageSQLiteHelper.COLUMN_MESSAGE, message.getMessage());
//		values.put(MessageSQLiteHelper.COLUMN_TIME, message.getTime());
//
//		long insertId = database.insert(MessageSQLiteHelper.TABLE_MESSAGE, null, values);
//		Cursor cursor = database.query(MessageSQLiteHelper.TABLE_MESSAGE, allColumns, MessageSQLiteHelper.COLUMN_ID + " = " + insertId, null, null,
//				null, null);
//		cursor.moveToFirst();
//		MessageBean newMessageBean = cursorToMessage(cursor);
//		cursor.close();
//		return newMessageBean;
//	}
//
//	public void deleteLocation(String username)
//	{
//		database.delete(MessageSQLiteHelper.TABLE_MESSAGE, MessageSQLiteHelper.COLUMN_USERNAME + " = " + username, null);
//	}
//
//	public ArrayList<MessageBean> getAllMessageBeans()
//	{
//		ArrayList<MessageBean> messagelist = new ArrayList<MessageBean>();
//		Cursor cursor = database.query(MessageSQLiteHelper.TABLE_MESSAGE, allColumns, null, null, null, null, MessageSQLiteHelper.COLUMN_TIME);
//
//		cursor.moveToFirst();
//		while (!cursor.isAfterLast())
//		{
//			MessageBean MessageBean = cursorToMessage(cursor);
//			messagelist.add(MessageBean);
//			cursor.moveToNext();
//		}
//		// Make sure to close the cursor
//		cursor.close();
//		return messagelist;
//	}
//
//	private MessageBean cursorToMessage(Cursor cursor)
//	{
//		MessageBean message = new MessageBean();
//		message.setId(cursor.getLong(0));
//		message.setUsername(cursor.getString(1));
//		message.setImei(cursor.getString(2));
//		message.setTime(cursor.getString(3));
//		message.setMessage(cursor.getString(4));
//
//		return message;
//	}
//	
//	public void deleteMessageTable()
//	{
//		database.delete(MessageSQLiteHelper.TABLE_MESSAGE, null, null);
//	}
//	
//	public Cursor fetchListItems()
//	{
//
//		Cursor cursor = database.query(MessageSQLiteHelper.TABLE_MESSAGE,allColumns, null, null, null, null, null);
//
//		if (cursor != null)
//		{
//			cursor.moveToFirst();
//		}
//		return cursor;
//	}
//
//}
