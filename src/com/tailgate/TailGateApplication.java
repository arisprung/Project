package com.tailgate;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;



@ReportsCrashes(formUri = "http://www.bugsense.com/api/acra?api_key=15ee5858", formKey="") 

public class TailGateApplication extends Application
{

	private TailgateSharedPrefrence tailgateSharedPrefs;
	public static Context mContext;

	@Override
	public void onCreate()
	{
		super.onCreate();
		ACRA.init(this);
		
		

		LocationLibrary.showDebugOutput(true);
		LocationLibrary.initialiseLibrary(getBaseContext(), 0, 2 * 60 * 1000, "com.tailgate");
		LocationLibrary.startAlarmAndListener(getApplicationContext());

		mContext = getApplicationContext();
		
	}

	public static Context getContext()
	{
		return mContext;
	}

}
