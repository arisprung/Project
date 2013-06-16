package com.tailgate.fragments;

import java.util.ArrayList;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;
import android.content.IntentFilter;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.tailgate.MessageBean;
import com.tailgate.R;
import com.tailgate.activities.MainActivity;

import com.tailgate.db.MessageSQLiteHelper;
import com.tailgate.db.TailGateMessageContentProvider;
import com.tailgate.list.MessageCursorAdapter;

public class MessageFrament extends Fragment implements  LoaderManager.LoaderCallbacks<Cursor> 
{
	// public static MessageListAdapter adapter;

	private ListView listView;
	private ArrayList<MessageBean> userlist;

	private MessageCursorAdapter adapter;

	public static int[] allfields = { R.id.username, R.id.message, R.id.time, R.id.avatar };
	private static final String TAG = ChatListFragment.class.toString();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);
		if (container == null)
		{
			return null;
		}
		View view = inflater.inflate(R.layout.list_view, container, false);

		return view;

	}

	@Override
	public void onResume()
	{
		getActivity().registerReceiver(updateMessageListReciever, new IntentFilter("new_Message"));
		super.onResume();
	}

	@Override
	public void onPause()
	{
		getActivity().unregisterReceiver(updateMessageListReciever);
		super.onPause();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// loadParticipantsList();

		// adapter = new MessageListAdapter(getActivity().getApplicationContext(), R.layout.list_item, userlist);
		getLoaderManager().restartLoader(0, null, this);
		listView = (ListView) getView().findViewById(R.id.list);
		adapter = new MessageCursorAdapter(getActivity().getApplicationContext(), R.layout.list_item_white, null, TailGateMessageContentProvider.allColumns,
				allfields, 0);
		listView.setAdapter(adapter);
	
		

		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int i, long l)
			{
				Log.i(TAG, "Item Clicked");

			}
		});

		String myTag = getTag();

		((MainActivity) getActivity()).setTabFragManage(myTag);

	}

	// public static MessageListAdapter getAdapter()
	// {
	// return adapter;
	// }
	//
	// public static void setAdapter(MessageListAdapter adapter)
	// {
	// MessageFrament.adapter = adapter;
	// }

	private BroadcastReceiver updateMessageListReciever = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent)
		{

			 adapter.notifyDataSetChanged();
			Log.d("updateMessageListReciever", "Got message: ");
		}
	};

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{

		CursorLoader loader = new CursorLoader(getActivity(), TailGateMessageContentProvider.CONTENT_URI, TailGateMessageContentProvider.allColumns, null,
				null, MessageSQLiteHelper.COLUMN_TIME);
		return loader;
		
		
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor)
	{

		adapter.swapCursor(cursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0)
	{
		adapter.swapCursor(null);

	}

	



}