package com.tailgate.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibraryConstants;
import com.tailgate.json.TailGateJSONParser;
import com.tailgate.xmpp.TailGateMultiUserChatManager;

public class LocationReciever extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		final LocationInfo locationInfo = (LocationInfo) intent.getSerializableExtra(LocationLibraryConstants.LOCATION_BROADCAST_EXTRA_LOCATIONINFO);
		double latitude = locationInfo.lastLat;
		double longitude = locationInfo.lastLong;
		
		Log.e("LocationReciever", "*****************************************************************");
		Log.e("LocationReciever", "********************************** GOT LOCATION!!! **************");
		Log.e("LocationReciever", "*****************************************************************");
		
		
		Toast.makeText(context, "Location Changed" + "Lat : " + latitude + " Long :" + longitude, Toast.LENGTH_LONG).show();
		String strLatitude = String.valueOf(latitude);
		String strLongitude = String.valueOf(longitude);
		
		TailGateMultiUserChatManager muc = new TailGateMultiUserChatManager(context);
		muc.addLocationToMultiChat(strLongitude, strLatitude);
		
	}

}
