package com.tailgate.json;

import org.json.JSONObject;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.tailgate.TailgateConstants;
import com.tailgate.TailgateSharedPrefrence;
import com.tailgate.xmpp.XMPPTailGateManager;

public class TailGateJSONParser
{

	private Context mContext;
	private TailgateSharedPrefrence tailgateSharedPrefs;

	public TailGateJSONParser(Context context)
	{
		mContext = context;
		tailgateSharedPrefs = new TailgateSharedPrefrence();
	}

	public String setMessageJSON(String message)
	{

		try
		{
			
			String imei = tailgateSharedPrefs.getStringSharedPreferences(mContext, TailgateConstants.IMEI, "");
			JSONObject jobject = new JSONObject();
			jobject.put(TailgateConstants.COMMAND, TailgateConstants.CHAT_MESSAGE);
			jobject.put(TailgateConstants.REQUEST_TYPE, TailgateConstants.CHAT_MESSAGE);
			jobject.put(TailgateConstants.REQUEST_ID, "1234");

			JSONObject jsonMessage = new JSONObject();
			jsonMessage.put(TailgateConstants.IMEI, imei);
			jsonMessage.put(TailgateConstants.USER_NAME, XMPPTailGateManager.parseXMPPName(XMPPTailGateManager.m_connection.getUser()));
			jsonMessage.put(TailgateConstants.CHAT_MESSAGE_BODY, message);
			jsonMessage.put(TailgateConstants.MESSAGE_TIME, String.valueOf(System.currentTimeMillis()));

			jobject.put(TailgateConstants.MESSAGE, jsonMessage);
			return jobject.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;

	
			

	}

	public String setLocationJSON(String longnitude, String latitude)
	{

		

		try
		{

			String imei = tailgateSharedPrefs.getStringSharedPreferences(mContext, TailgateConstants.IMEI, "");
			JSONObject jobject = new JSONObject();
			jobject.put(TailgateConstants.COMMAND, TailgateConstants.LOCATION);
			jobject.put(TailgateConstants.REQUEST_TYPE, TailgateConstants.LOCATION);
			jobject.put(TailgateConstants.REQUEST_ID, "12345");

			JSONObject jsonMessage = new JSONObject();
			jsonMessage.put(TailgateConstants.IMEI, imei);
			jsonMessage.put(TailgateConstants.LOCATION_LATITUDE, latitude);
			jsonMessage.put(TailgateConstants.LOCATION_LONGNITUDE, longnitude);
			jsonMessage.put(TailgateConstants.USER_NAME, XMPPTailGateManager.parseXMPPName(XMPPTailGateManager.m_connection.getUser()));

			jobject.put(TailgateConstants.MESSAGE, jsonMessage);
			return jobject.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return "";

	}

}
