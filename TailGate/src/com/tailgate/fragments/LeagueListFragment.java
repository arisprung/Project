package com.tailgate.fragments;

import java.util.ArrayList;

import com.tailgate.R;
import com.tailgate.TailgateConstants;
import com.tailgate.activities.MainActivity;
import com.tailgate.list.ChatMembersListAdapter;
import com.tailgate.xmpp.TailGateMultiUserChatManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LeagueListFragment extends Fragment
{


	private ListView listView;
	public static String[] mLeagueList = { TailgateConstants.NFL, TailgateConstants.NBA, TailgateConstants.MLB,TailgateConstants.NHL };
	private static final String TAG = LeagueListFragment.class.toString();

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

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),  android.R.layout.simple_list_item_1, mLeagueList);
		listView = (ListView) getView().findViewById(R.id.list);
		listView.setAdapter(adapter);

		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int i, long l)
			{
				
				MainActivity.mLeagueSelected = mLeagueList[i];
				Fragment fragment = new TeamListFragment();
				if (getActivity() instanceof MainActivity)
				{
					MainActivity fca = (MainActivity) getActivity();
					fca.switchContent(fragment);
				}
					
				
				

			}
		});

	}


}
