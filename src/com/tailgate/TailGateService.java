package com.tailgate;

import org.json.JSONException;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.tailgate.activities.MainActivity;
import com.tailgate.xmpp.XMPPTailGateManager;

public class TailGateService extends IntentService
{

	private static final String TAG = TailGateService.class.toString();
	private static Context mContext;

	public TailGateService()
	{
		super("TailGateService");

	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		mContext = getApplicationContext();
		// createXMPPThread();

		XMPPTailGateManager xmppconnext = new XMPPTailGateManager(mContext);
		try
		{
			boolean bXmppConnect = xmppconnext.StartXMPPLoginRuningProgress();
			// MainActivity.progressDialog.dismiss();
			// sendBroadcast(new Intent(TailgateConstants.INIT_BROADCAST_RECIEVER));
			// MainActivity.init();
			MainActivity.bXMPPLoginFinished = false;
			Log.d(TAG, " Connection  Succeed : " + bXmppConnect);
		}
		catch (Exception e)
		{
			XMPPTailGateManager.m_connection.disconnect();
			e.printStackTrace();
		}

	}

	// @Override
	// public IBinder onBind(Intent intent)
	// {
	//
	// return null;
	// }
	//
	// @Override
	// public void onCreate()
	// {
	// super.onCreate();
	//
	// // mContext = getApplicationContext();
	// // //createXMPPThread();
	// //
	// // XMPPTailGateManager xmppconnext = new XMPPTailGateManager(mContext);
	// // try
	// // {
	// // boolean bXmppConnect = xmppconnext.StartXMPPLoginRuningProgress();
	// // // MainActivity.progressDialog.dismiss();
	// // // sendBroadcast(new Intent(TailgateConstants.INIT_BROADCAST_RECIEVER));
	// // // MainActivity.init();
	// // MainActivity.bXMPPLoginFinished = false;
	// // Log.d(TAG, " Connection  Succeed : " + bXmppConnect);
	// // }
	// // catch (Exception e)
	// // {
	// // e.printStackTrace();
	// // }
	//
	// // new TailGateServiceAsyncTask().execute();
	// }
	//
	// public static void createXMPPThread()
	// {
	// thread.start();
	//
	// }
	//
	// static Thread thread = new Thread() {
	// @Override
	// public void run()
	// {
	// XMPPTailGateManager xmppconnext = new XMPPTailGateManager(mContext);
	// try
	// {
	// boolean bXmppConnect = xmppconnext.StartXMPPLoginRuningProgress();
	// // MainActivity.progressDialog.dismiss();
	// // sendBroadcast(new Intent(TailgateConstants.INIT_BROADCAST_RECIEVER));
	// // MainActivity.init();
	// MainActivity.bXMPPLoginFinished = false;
	// Log.d(TAG, " Connection  Succeed : " + bXmppConnect);
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	// };
	//
	// public class TailGateServiceAsyncTask extends AsyncTask<Void, Void, Void>
	//
	// {
	//
	// @Override
	// protected void onPostExecute(Void result)
	// {
	//
	// super.onPostExecute(result);
	// }
	//
	// @Override
	// protected void onPreExecute()
	// {
	//
	// super.onPreExecute();
	// }
	//
	// @Override
	// protected Void doInBackground(Void... params)
	// {
	//
	// try
	// {
	// XMPPTailGateManager xmppconnext = new XMPPTailGateManager(getApplicationContext());
	// try
	// {
	//
	// boolean bXmppConnect = xmppconnext.StartXMPPLoginRuningProgress();
	// // MainActivity.progressDialog.dismiss();
	// // sendBroadcast(new Intent(TailgateConstants.INIT_BROADCAST_RECIEVER));
	// // MainActivity.init();
	// MainActivity.bXMPPLoginFinished = false;
	// Log.d(TAG, " Connection  Succeed : " + bXmppConnect);
	// }
	// catch (JSONException e)
	// {
	//
	// e.printStackTrace();
	// }
	// }
	// catch (Exception e)
	// {
	//
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// }

}
