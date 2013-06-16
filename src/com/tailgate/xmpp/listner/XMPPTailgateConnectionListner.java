package com.tailgate.xmpp.listner;

import org.jivesoftware.smack.ConnectionListener;

import com.tailgate.R;
import com.tailgate.TailGateApplication;
import com.tailgate.TailGateService;
import com.tailgate.xmpp.XMPPTailGateManager;

import android.content.Intent;
import android.util.Log;

public class XMPPTailgateConnectionListner implements ConnectionListener
{
	
	private static final String TAG = XMPPTailgateConnectionListner.class.toString();

	@Override
	public void connectionClosed()
	{
		Log.e(TAG, "********************** connectionClosed !! ******************");
		XMPPTailGateManager.m_connection.disconnect();
		XMPPTailGateManager.setConnectNotification(R.drawable.stop,"TailGate is OFF!");
//		Intent service = new Intent(TailGateApplication.mContext, TailGateService.class);
//		TailGateApplication.mContext.startService(service);
		
	}

	@Override
	public void connectionClosedOnError(Exception exception)
	{
		exception.printStackTrace();
		XMPPTailGateManager.m_connection.disconnect();
		Log.e(TAG, "********************** connectionClosedOnError !! ******************");
		XMPPTailGateManager.setConnectNotification(R.drawable.stop,"TailGate is OFF!");
//		Intent service = new Intent(TailGateApplication.mContext, TailGateService.class);
//		TailGateApplication.mContext.startService(service);
	}

	@Override
	public void reconnectingIn(int number)
	{
		Log.e(TAG, "********************** reconnectingIn !! ****************** Reconnectin int :"+number);
		
	}

	@Override
	public void reconnectionFailed(Exception exception)
	{
		
		exception.printStackTrace();
		Log.e(TAG, "********************** reconnectionFailed !! ******************");
		
	}

	@Override
	public void reconnectionSuccessful()
	{
		Log.e(TAG, "********************** reconnectionSuccessful !! ******************");
		
	}

}
