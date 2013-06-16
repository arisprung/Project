package com.tailgate.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.tailgate.R;
import com.tailgate.TailgateConstants;
import com.tailgate.activities.MainActivity;
import com.tailgate.db.TeamDataSource;
import com.tailgate.list.TeamListAdapter;

public class TeamListFragment extends Fragment
{

	private TeamListAdapter adapter;
	private TeamDataSource datasource;
	private ListView listView;
	private String[] mTeamList;
	private static final String TAG = LeagueListFragment.class.toString();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		if (container == null)
		{
			return null;
		}

		if (MainActivity.mLeagueSelected.equals(TailgateConstants.NFL))
		{
			mTeamList = TailgateConstants.NFL_LIST;
		}
		else if (MainActivity.mLeagueSelected.equals(TailgateConstants.NBA))
		{
			mTeamList = TailgateConstants.NBA_LIST;
		}
		else if (MainActivity.mLeagueSelected.equals(TailgateConstants.NHL))
		{
			mTeamList = TailgateConstants.NHL_LIST;
		}
		else if (MainActivity.mLeagueSelected.equals(TailgateConstants.MLB))
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
		datasource = new TeamDataSource(getActivity());
		// loadParticipantsList();
		// adapter = new TeamListAdapter(getActivity().getApplicationContext(), R.layout.list_checkbox_item, mTeamList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
				android.R.layout.simple_list_item_multiple_choice, mTeamList);
		listView = (ListView) getView().findViewById(R.id.list);
		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setTextFilterEnabled(true);
		
		datasource.open();
		ArrayList<String> listteams = datasource.getAllTeamBeans();
		datasource.close();
		
		if(listteams.size() > 0)
		{
			for (int i = 0; i < mTeamList.length; i++)
			{
				if(mTeamList[i].equals(listteams.get(0)))
				{
					listView.setItemChecked(i, true);
				}
			}
		}
		
		

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long l)
			{

				datasource.open();
				CheckedTextView check = (CheckedTextView) v;
				//int icount = datasource.checkHowManyTeamsExist();
				
				ArrayList<String> dblist = datasource.getAllTeamBeans();
				if (dblist.size() > 0 || listView.getCheckedItemCount() > 1)
				{
					
					check.setChecked(!check.isChecked());
					boolean click = !check.isChecked();
					check.setChecked(click);
					if (click)
					{
						listView.setItemChecked(position, false);
						Toast.makeText(getActivity(), "You can only select one team. Please unselect "+dblist.get(0)+" the try again", Toast.LENGTH_LONG).show();
					}
					else
					{
						
						int icounti = datasource.checkHowManyTeamsExist();
						ArrayList<String> strList = datasource.getAllTeamBeans();
						for (int i = 0; i < strList.size(); i++)
						{
							datasource.deleteTeam(strList.get(i));
							
						}
						listView.setItemChecked(position, false);
					}
					
					
					
				}
				else
				{

					
					check.setChecked(!check.isChecked());
					boolean click = !check.isChecked();
					check.setChecked(click);
					if (click)
					{
						listView.setItemChecked(position, true);
						datasource.createTeamEntry(mTeamList[position]);
					}
					else
					{
						listView.setItemChecked(position, false);
						
					}
				}
				datasource.close();
				getActivity().getApplicationContext().sendBroadcast(new Intent("Refresh_Adapter"));
			}
		});
		
	}

}
