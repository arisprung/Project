package com.tailgate.xmpp.listner;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.json.JSONException;
import org.json.JSONObject;

import com.tailgate.LocationBean;
import com.tailgate.MessageBean;
import com.tailgate.TailGateApplication;
import com.tailgate.TailgateConstants;
import com.tailgate.activities.MainActivity;
import com.tailgate.activities.RegistrationActivity;
import com.tailgate.db.LocationDataSource;

import com.tailgate.db.MessageSQLiteHelper;
import com.tailgate.db.TailGateMessageContentProvider;
import com.tailgate.fragments.MessageFrament;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class XMPPTailgatePacketListner implements PacketListener
{

	private static Message message;
	private static final String TAG = XMPPTailgatePacketListner.class.toString();
	private String mMessageFrom = null;

	private LocationDataSource locationDataSource;


	@Override
	public void processPacket(Packet packet)
	{
		
		try
		{
			message = (Message) packet;
			
			Log.i(TAG, " ***********************Message Recieved : " + message.getBody() + "**********************");
			mMessageFrom = message.getFrom();
			String strMessageID = message.getPacketID();
			if(message.getBody() == null)
			{
				return;
			}
			Runnable r = new MyThread(message.getBody());
			new Thread(r).start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		

	}

	private synchronized void ParseMessage(String body)
	{

		try
		{
			JSONObject json = new JSONObject(body);

			if (json.has(TailgateConstants.COMMAND))
			{
				String command = json.getString(TailgateConstants.COMMAND);
				if (command.equalsIgnoreCase(TailgateConstants.LOCATION))
				{
					ParseLocationMessage(json);
				}
				else if (command.equalsIgnoreCase(TailgateConstants.CHAT_MESSAGE))
				{
					ParseChatMessage(json);
				}
			}

		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	private void ParseChatMessage(JSONObject jObject)
	{
		try
		{
			if (jObject.has(TailgateConstants.COMMAND))
			{
				String strMessage = "";
				String strImei = "";
				String strUserName = "";
				String strTime = "";

				JSONObject JMessage = jObject.getJSONObject(TailgateConstants.MESSAGE);
				if (JMessage.has(TailgateConstants.CHAT_MESSAGE_BODY))
				{
					strMessage = JMessage.getString(TailgateConstants.CHAT_MESSAGE_BODY);
				}
				if (JMessage.has(TailgateConstants.IMEI))
				{
					strImei = JMessage.getString(TailgateConstants.IMEI);
				}
				if (JMessage.has(TailgateConstants.USER_NAME))
				{
					strUserName = JMessage.getString(TailgateConstants.USER_NAME);
				}
				if (JMessage.has(TailgateConstants.MESSAGE_TIME))
				{
					strTime = JMessage.getString(TailgateConstants.MESSAGE_TIME);
				}
				
				ContentValues values = new ContentValues();
				values.put(MessageSQLiteHelper.COLUMN_IMEI, strImei);
				values.put(MessageSQLiteHelper.COLUMN_MESSAGE, strMessage);
				values.put(MessageSQLiteHelper.COLUMN_USERNAME, strUserName);
				values.put(MessageSQLiteHelper.COLUMN_TIME, strTime);
				Uri todoUri = TailGateApplication.mContext.getContentResolver().insert(TailGateMessageContentProvider.CONTENT_URI, values);
//				messageDataSource = new MessageDataSource(TailGateApplication.getContext());
//				messageDataSource.open();
//				messageDataSource.createMessageEntry(new MessageBean(mMessageFrom, strImei, strMessage, strTime));
//				messageDataSource.close();
//				
				 TailGateApplication.getContext().sendBroadcast(new Intent("new_Message"));
			}

		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	private void ParseLocationMessage(JSONObject jObject)
	{
		try
		{
			if (jObject.has(TailgateConstants.COMMAND))
			{
				String strLatitude = "";
				String strLongtidude = "";
				String strImei = "";
				String strUserName = "";
				JSONObject JMessage = jObject.getJSONObject(TailgateConstants.MESSAGE);
				if (JMessage.has(TailgateConstants.LOCATION_LATITUDE))
				{
					strLatitude = JMessage.getString(TailgateConstants.LOCATION_LATITUDE);
				}
				if (JMessage.has(TailgateConstants.LOCATION_LONGNITUDE))
				{
					strLongtidude = JMessage.getString(TailgateConstants.LOCATION_LONGNITUDE);
				}
				if (JMessage.has(TailgateConstants.IMEI))
				{
					strImei = JMessage.getString(TailgateConstants.IMEI);
				}
				if (JMessage.has(TailgateConstants.USER_NAME))
				{
					strUserName = JMessage.getString(TailgateConstants.USER_NAME);
				}
				locationDataSource = new LocationDataSource(TailGateApplication.getContext());
				locationDataSource.open();
				locationDataSource.createLocationEntry(new LocationBean(mMessageFrom, strImei, strLatitude, strLongtidude));
				locationDataSource.close();
			

			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	public class MyThread implements Runnable
	{
		private String threadMessage;

		public MyThread(String message)
		{
			this.threadMessage = message;
		}

		public void run()
		{
			ParseMessage(threadMessage);
		}
	}

}
