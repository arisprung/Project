package com.tailgate.reciever;

import org.jivesoftware.smack.XMPPConnection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tailgate.TailGateService;
import com.tailgate.activities.MainActivity;
import com.tailgate.xmpp.XMPPTailGateManager;

public class TailGateConnectionReciever extends BroadcastReceiver
{

	private ConnectivityManager connManager;
	private Context mContext;
	private XMPPConnection connection;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		mContext = context;
		
		
		connection = XMPPTailGateManager.getM_connection();

		
		if (connection.isConnected())
		{
			
			return;
			
		}
		if(getConnectivityStatus())
		{
			Intent service = new Intent(context, TailGateService.class);
			context.startService(service);
		}

	}

	public boolean getConnectivityStatus()
	{
		connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info != null)
			return info.isConnected(); // WIFI connected
		else
			return false; // no info object implies no connectivity
	}

}
