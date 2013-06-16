package com.tailgate.xmpp;

import java.util.ArrayList;
import java.util.Iterator;

import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.tailgate.TailGateApplication;
import com.tailgate.TailgateConstants;

import com.tailgate.db.TailGateMessageContentProvider;
import com.tailgate.json.TailGateJSONParser;
import com.tailgate.xmpp.listner.XMPPTailgatePacketListner;
import com.tailgate.xmpp.listner.XMPPTailgateParticipantListner;

public class TailGateMultiUserChatManager
{
	private static final String TAG = TailGateMultiUserChatManager.class.toString();
	private XMPPConnection mConnection;
	private static MultiUserChat mMuc;
	private static ArrayList<String> chatList;


	public TailGateMultiUserChatManager(XMPPConnection connection)
	{
		if (mConnection == null)
		{
			mConnection = connection;
		}

	}

	public TailGateMultiUserChatManager(Context context)
	{

		initMUCRoom(context);

	}

	public void sendMessageToGroup(String message)
	{

		try
		{
			mMuc.sendMessage(message);
		}
		catch (XMPPException e)
		{

			e.printStackTrace();
		}
	}

	public void setMultiChat(Context context)
	{
		try
		{
			initMUCRoom(context);
//			LocationInfo latestInfo = new LocationInfo(context);
//			double latitude = latestInfo.lastLat;
//			double longitude = latestInfo.lastLong;
//			String strLatitude = String.valueOf(latitude);
//			String strLongitude = String.valueOf(longitude);
//			
//			TailGateMultiUserChatManager muc = new TailGateMultiUserChatManager(context);
//			muc.addLocationToMultiChat(strLongitude, strLatitude);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		
	}

	public ArrayList<String> getRoomOccupants() throws XMPPException
	{
		chatList = new ArrayList<String>();
		Iterator<String> occupants = mMuc.getOccupants();
		int iOccupents = mMuc.getOccupantsCount();
		while (occupants.hasNext())
		{
			String jidOccupant = occupants.next();
			chatList.add((XMPPTailGateManager.parseXMPPGroupName(jidOccupant)));
		}
		return chatList;
	}

	public static ArrayList<String> getChatList()
	{
		return chatList;
	}

	public void setChatList(ArrayList<String> chatList)
	{
		this.chatList = chatList;
	}

	public void addMessageToMultiChat(String message)
	{

		try
		{

			if (mMuc != null)
			{
				TailGateJSONParser parser = new TailGateJSONParser(TailGateApplication.getContext());
				String strMessage = parser.setMessageJSON(message);
				mMuc.sendMessage(strMessage);
			}
			else
			{
				Log.e(TAG, "MUC is empty!!!!!!!!!!!!!!!!!!!!!!!");
			}

		}
		catch (XMPPException e)
		{

			e.printStackTrace();
		}

	}

	public void addLocationToMultiChat(String longnitude, String latinude)
	{

		try
		{

			if (mMuc != null)
			{
				TailGateJSONParser parser = new TailGateJSONParser(TailGateApplication.getContext());
				String strLocation = parser.setLocationJSON(longnitude, latinude);
				mMuc.sendMessage(strLocation);
			}
			else
			{
				Log.e(TAG, "MUC is empty!!!!!!!!!!!!!!!!!!!!!!!");
			}

		}
		catch (Exception e)
		{

			e.printStackTrace();
		}

	}

	private void initMUCRoom(Context context)

	{
		if (mMuc == null)
		{

			try
			{


				int rows = context.getContentResolver().delete(TailGateMessageContentProvider.CONTENT_URI, null, null);
				Log.i(TAG, "Rows deleted in DB : "+rows);
				mMuc = new MultiUserChat(mConnection, XMPPTailGateManager.mConfrenceName + TailgateConstants.XMPP_CONFRENCE_NAME);
				mMuc.join(XMPPTailGateManager.mUserName);
				mMuc.addMessageListener(new XMPPTailgatePacketListner());
				mMuc.addParticipantListener(new XMPPTailgateParticipantListner());

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
