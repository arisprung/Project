package com.tailgate.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.tailgate.R;
import com.tailgate.TailgateConstants;
import com.tailgate.TailgateSharedPrefrence;
import com.tailgate.activities.MainActivity;
import com.tailgate.db.LocationDataSource;
import com.tailgate.db.TeamDataSource;
import com.tailgate.list.MenuListAdapter;

public class MenuFragment extends Fragment
{

	private ListView listView;
	MenuListAdapter adapter;
	MenuListAdapter teamAdapter;
	private ListView teamlistView;
	public static String[] mMenuList = { "Messages", "Map", "Online", "Add Message" };
	public static String[] mteamlist = { "Dolphins", "Heat", "Marlins", "Panthers" };
	private static TailgateSharedPrefrence tailgateSharedPrefs = new TailgateSharedPrefrence();
	private View header;
	private Button headerButton;
	ArrayList<String> chatlist;
	private TeamDataSource datasource;
	private ArrayList<String> teamlist;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.list_view, null);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		listView = (ListView) getView().findViewById(R.id.list);
		teamlistView = (ListView) getView().findViewById(R.id.team_list);
		super.onActivityCreated(savedInstanceState);
		chatlist = new ArrayList<String>();
		chatlist.add("Messages");
		chatlist.add("Map");
		chatlist.add("Online");
		chatlist.add("Add Message");

		//

	}

	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		
		datasource = new TeamDataSource(getActivity());
		datasource.open();
		teamlist = datasource.getAllTeamBeans();
		datasource.close();
		 
		LayoutInflater inflater = getActivity().getLayoutInflater();
		header = inflater.inflate(R.layout.listview_header, (ViewGroup) getActivity().findViewById(R.id.header_layout_root));
		headerButton = (Button) header.findViewById(R.id.header_text);

		if (teamlist.size() > 0)
		{

			headerButton.setText(TailgateConstants.MENU_YOUR_TEAM);

		}
		else
		{
			teamlist = new ArrayList<String>();
			headerButton.setText(TailgateConstants.MENU_ADD_TEAM);

		}

		teamlistView.addHeaderView(header, null, false);
		teamAdapter = new MenuListAdapter(getActivity().getApplicationContext(), R.layout.list_item, teamlist);
		// UserListAdapter chatAdapter = new UserListAdapter(context, textViewResourceId, objects)

		adapter = new MenuListAdapter(getActivity().getApplicationContext(), R.layout.list_item, chatlist);

		listView.setAdapter(adapter);
		teamlistView.setAdapter(teamAdapter);

		setOnItemListner();
		setHeaderListner();
	}

	private void setHeaderListner()
	{
		headerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{

				Fragment fragment = new LeagueListFragment();
				if (fragment != null)
					switchFragment(fragment);
			}
		});

	}

	private void setOnItemListner()
	{
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				Fragment newContent = null;
				switch (position)
				{
					case 0:
						newContent = new MessageFrament();
						break;
					case 1:
						newContent = new MapTailgateFragment();
						break;
					case 2:
						newContent = new ChatListFragment();
						break;
					case 3:
						newContent = new AddMessageFragment();
						break;
					case 4:
						newContent = new TeamListFragment();
						break;
					case 5:
						// newContent = new ColorFragment(android.R.color.black);
						break;
				}
				if (newContent != null)
					switchFragment(newContent);

			}

		});

	}

	// @Override
	// public void onListItemClick(ListView lv, View v, int position, long id)
	// {
	// Fragment newContent = null;
	// switch (position)
	// {
	// case 0:
	// newContent = new MessageFrament();
	// break;
	// case 1:
	// newContent = new MapTailgateFragment();
	// break;
	// case 2:
	// newContent = new ChatListFragment();
	// break;
	// case 3:
	// //newContent = new ColorFragment(android.R.color.white);
	// break;
	// case 4:
	// //newContent = new ColorFragment(android.R.color.black);
	// break;
	// }
	// if (newContent != null)
	// switchFragment(newContent);
	// }

	// the meat of switching the above fragment
	public void switchFragment(Fragment fragment)
	{
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity)
		{
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment);
		}
		// else if (getActivity() instanceof ResponsiveUIActivity)
		// {
		// ResponsiveUIActivity ra = (ResponsiveUIActivity) getActivity();
		// ra.switchContent(fragment);
		// }
	}

}
