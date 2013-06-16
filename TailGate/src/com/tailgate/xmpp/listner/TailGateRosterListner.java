package com.tailgate.xmpp.listner;

import java.util.Collection;

import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import com.tailgate.UserBean;

import com.tailgate.xmpp.XMPPTailGateManager;

import android.util.Log;

public class TailGateRosterListner implements RosterListener
{
	
	private static final String TAG = TailGateRosterListner.class.toString();

	@Override
	public void entriesAdded(Collection<String> arg0)
	{
		Log.i(TAG, "Roster Listner - entriesAdded");
	}

	@Override
	public void entriesDeleted(Collection<String> arg0)
	{
		Log.i(TAG, "Roster Listner - entriesDeleted");
	}

	@Override
	public void entriesUpdated(Collection<String> arg0)
	{
		Log.i(TAG, "Roster Listner - entriesUpdated");
	}

	@Override
	public void presenceChanged(Presence presence)
	{
		
	
		String strName = presence.getFrom();
		String strStatus = presence.toString();
		Log.i(TAG, "Roster Listner - presenceChanged  Name : " + strName+" Status :"+ strStatus);
		String strParseName = XMPPTailGateManager.parseXMPPName(strName);
		//UserListView.updateRosterlistStatus(strName,strStatus);
	}

}
