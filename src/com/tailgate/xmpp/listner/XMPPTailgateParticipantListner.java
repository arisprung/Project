package com.tailgate.xmpp.listner;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.ParticipantStatusListener;

import com.tailgate.TailGateApplication;
import com.tailgate.xmpp.listner.XMPPTailgatePacketListner.MyThread;

import android.content.Intent;
import android.util.Log;

public class XMPPTailgateParticipantListner implements PacketListener
{

	private final static String TAG = XMPPTailgateParticipantListner.class.toString();

	@Override
	public void processPacket(Packet packet)
	{
		Presence presence = (Presence) packet;
		Log.i(TAG, "********Presence Recieved : " + presence.getFrom() + presence.getStatus() + "*********");

		Runnable r = new MyThread();
		new Thread(r).start();

	}

	public class MyThread implements Runnable
	{

		public void run()
		{
			sendBroadcastToUserList();
		}

		private void sendBroadcastToUserList()
		{

			try
			{
				Thread.sleep(2000);
				TailGateApplication.getContext().sendBroadcast(new Intent("presence_change"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}

	}

}
