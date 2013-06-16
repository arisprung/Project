package com.tailgate.fragments;

import com.tailgate.R;
import com.tailgate.TailgateConstants;
import com.tailgate.activities.MainActivity;
import com.tailgate.db.TeamDataSource;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TeamListFragment extends Fragment
{
	
	
	private TeamDataSource datasource;
	private ListView listView;
	private String [] mTeamList;
	private static final String TAG = LeagueListFragment.class.toString();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		if (container == null)
		{
			return null;
		}
		
		if(MainActivity.mLeagueSelected.equals(TailgateConstants.NFL))
		{
			mTeamList = TailgateConstants.NFL_LIST;
		}
		else if(MainActivity.mLeagueSelected.equals(TailgateConstants.NBA))
		{
			mTeamList = TailgateConstants.NBA_LIST;
		}
		else if(MainActivity.mLeagueSelected.equals(TailgateConstants.NHL))
		{
			mTeamList = TailgateConstants.NHL_LIST;
		}
		else if(MainActivity.mLeagueSelected.equals(TailgateConstants.MLB))
		{
			mTeamList = TailgateConstants.MLB_LIST;
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

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),  android.R.layout.simple_list_item_1, mTeamList);
		listView = (ListView) getView().findViewById(R.id.list);
		listView.setAdapter(adapter);

		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int i, long l)
			{
				
			
				datasource = new TeamDataSource(getActivity());
				datasource.open();
				datasource.createTeamEntry(mTeamList[i]);
				datasource.close();
				
				

			}
		});

	}

}
