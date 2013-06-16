package com.tailgate.fragments;

import java.util.ArrayList;
import java.util.List;

import com.tailgate.R;
import com.tailgate.R.id;
import com.tailgate.R.layout;
import com.tailgate.list.ChatMembersListAdapter;

import com.tailgate.xmpp.TailGateMultiUserChatManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChatListFragment extends Fragment
{

	public static ChatMembersListAdapter adapter;
	private ListView listView;
	private ArrayList<String> userlist;
	private static final String TAG = ChatListFragment.class.toString();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		if (container == null)
		{
			return null;
		}

		try
		{
			View view = inflater.inflate(R.layout.list_view, container, false);
			TailGateMultiUserChatManager muc = new TailGateMultiUserChatManager(getActivity().getApplicationContext());
			userlist = muc.getRoomOccupants();
			// userlist = new ArrayList<String>();
			// userlist.add("Ari");
			// userlist.add("Moshe");
			// userlist.add("Motti");
			return view;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return container;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// loadParticipantsList();

		adapter = new ChatMembersListAdapter(getActivity().getApplicationContext(), R.layout.list_item, userlist);
		listView = (ListView) getView().findViewById(R.id.list);
		listView.setAdapter(adapter);

		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int i, long l)
			{
				Log.i(TAG, "Item Clicked");

			}
		});

	}

	private BroadcastReceiver updatGroupListReciever = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent)
		{

			adapter.notifyDataSetChanged();
			Log.d("updatGroupListReciever", "Got message: ");
		}
	};

	@Override
	public void onResume()
	{
		getActivity().registerReceiver(updatGroupListReciever, new IntentFilter("presence_change"));
		super.onResume();
	}

	@Override
	public void onPause()
	{
		getActivity().unregisterReceiver(updatGroupListReciever);
		super.onPause();
	}

}