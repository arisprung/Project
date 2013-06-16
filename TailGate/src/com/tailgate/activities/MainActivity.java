package com.tailgate.activities;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.tailgate.LocationBean;
import com.tailgate.R;
import com.tailgate.TailGateService;
import com.tailgate.TailgateConstants;
import com.tailgate.TailgateSharedPrefrence;
import com.tailgate.db.LocationDataSource;
import com.tailgate.db.TailGateMessageContentProvider;
import com.tailgate.fragments.ChatListFragment;
import com.tailgate.fragments.MenuFragment;
import com.tailgate.fragments.MessageFrament;
import com.tailgate.menu.BaseActivity;

public class MainActivity extends BaseActivity
{

	private Fragment mContent;
	private LocationDataSource locationDataSource;
	public static String mLeagueSelected;

	private final static String TAG = MainActivity.class.toString();
	private String imei;
	private String FragManage;

	public MainActivity()
	{
		super(R.string.hello_world);
	}

	public static Context appContext;
	public static boolean bXMPPLoginFinished = true;

	public static ArrayList<String> arraylist;
	TailgateSharedPrefrence tailgateSharedPrefs;
	View menu;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		new XMPPProgressDialog().execute();

		Intent service = new Intent(getApplicationContext(), TailGateService.class);
		startService(service);

		tailgateSharedPrefs = new TailgateSharedPrefrence();
		imei = tailgateSharedPrefs.getStringSharedPreferences(getApplicationContext(), TailgateConstants.IMEI, "");
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new ChatListFragment();

		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment()).commit();

		// /////////////////////////////////////////////////////////////////
		// FAKING DB ENTRIES!!!
		// ////////////////////////////////////////////////////////////////

		// createFakeLocationDB();

		// createFakeMessageDB();

	}

	private void createFakeMessageDB()
	{

		int todoUri = getContentResolver().delete(TailGateMessageContentProvider.CONTENT_URI, null, null);

		// ContentValues values = new ContentValues();
		// values.put(MessageSQLiteHelper.COLUMN_IMEI, "457383983");
		// values.put(MessageSQLiteHelper.COLUMN_MESSAGE, "message 1");
		// values.put(MessageSQLiteHelper.COLUMN_USERNAME, "ari");
		// values.put(MessageSQLiteHelper.COLUMN_TIME, "32323222222");
		// Uri todoUri = getContentResolver().insert(TailGateMessageContentProvider.CONTENT_URI, values);
		// if (todoUri == null)
		// {
		// // New todo
		// todoUri = getContentResolver().insert(TailGateMessageContentProvider.CONTENT_URI, values);
		// }
		// else
		// {
		// // Update todo
		// getContentResolver().update(todoUri, values, null, null);
		// }
		// messageDataSource = new MessageDataSource(this);
		// messageDataSource.open();
		//
		//
		// ArrayList<MessageBean> messagelist = new ArrayList<MessageBean>();
		// long time = System.currentTimeMillis();
		// String strTime = String.valueOf(time);
		//
		// messagelist.add(new MessageBean("ME", imei, "Hello",strTime));
		// messagelist.add(new MessageBean("User1", imei, "Whats Up?",strTime ));
		// messagelist.add(new MessageBean("User2", imei, "Good!",strTime));
		// messagelist.add(new MessageBean("User3", imei, "Awesome!",strTime));
		//
		// for (int i = 0; i < messagelist.size(); i++)
		// {
		// messageDataSource.createMessageEntry(messagelist.get(i));
		// }
		//
		// messageDataSource.close();

	}

	private void createFakeLocationDB()
	{
		locationDataSource = new LocationDataSource(this);
		locationDataSource.open();

		ArrayList<LocationBean> userlist = new ArrayList<LocationBean>();
		LocationInfo latestInfo = new LocationInfo(getBaseContext());

		double latitude = latestInfo.lastLat;
		double longitude = latestInfo.lastLong;

		double latitude1 = latestInfo.lastLat + 0.25;
		double longitude1 = latestInfo.lastLong + 0.25;

		double latitude2 = latestInfo.lastLat + 0.5;
		double longitude2 = latestInfo.lastLong + 0.5;

		double latitude3 = latestInfo.lastLat + 0.75;
		double longitude3 = latestInfo.lastLong + 0.75;

		String strLatitude = String.valueOf(latitude);
		String strLongitude = String.valueOf(longitude);

		String strLatitude1 = String.valueOf(latitude1);
		String strLongitude1 = String.valueOf(longitude1);

		String strLatitude2 = String.valueOf(latitude2);
		String strLongitude2 = String.valueOf(longitude2);

		String strLatitude3 = String.valueOf(latitude3);
		String strLongitude3 = String.valueOf(longitude3);

		userlist.add(new LocationBean("ME", imei, strLatitude, strLongitude));
		userlist.add(new LocationBean("User1", imei, strLatitude1, strLongitude1));
		userlist.add(new LocationBean("User2", imei, strLatitude2, strLongitude2));
		userlist.add(new LocationBean("User3", imei, strLatitude3, strLongitude3));

		for (int i = 0; i < userlist.size(); i++)
		{
			locationDataSource.createLocationEntry(userlist.get(i));
		}

		locationDataSource.close();

	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

	}

	public class XMPPProgressDialog extends AsyncTask<Void, Void, Void>

	{

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute()
		{
			progressDialog = ProgressDialog.show(MainActivity.this, "", "Connecting to Tailgate Service...");
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params)
		{

			while (bXMPPLoginFinished)
			{
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{

					e.printStackTrace();
				}

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			progressDialog.dismiss();
			super.onPostExecute(result);
		}

	}

	public void switchContent(Fragment fragment)
	{
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();

	}

	public void setTabFragManage(String t)
	{
		FragManage = t;
	}

	public String getTabFragManage()
	{
		return FragManage;
	}

	@Override
	protected void onDestroy()
	{
		unregisterReceiver(UpdateMenuListReciever);
		super.onDestroy();
	}

	@Override
	protected void onResume()
	{
		registerReceiver(UpdateMenuListReciever, new IntentFilter("Refresh_Adapter"));
		super.onResume();
	}

	private BroadcastReceiver UpdateMenuListReciever = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent)
		{
			MenuFragment.teamAdapter.notifyDataSetChanged();
		}
	};

}