package com.tailgate.list;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tailgate.R;
import com.tailgate.db.TeamDataSource;
import com.tailgate.xmpp.TailGateMultiUserChatManager;

public class TeamListAdapter extends ArrayAdapter<String>
{

	private final Context context;
	private String[] values;
	private CheckedTextView teamNameView;

	private TeamDataSource datasource;

	private Context mContext;

	//
	// public UserListAdapter(Context context, ArrayList<UserBean> values)
	// {
	//
	// this.context = context;
	// this.values = values;
	// }
	//

	public TeamListAdapter(Context context, int textViewResourceId, String[] objects)
	{
		super(context, textViewResourceId);
		this.context = context;
		this.values = objects;
		mContext = context;
		datasource = new TeamDataSource(context);
		

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		
		datasource.open();
		String teamName = values[position];
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_checkbox_item, parent, false);
		
		teamNameView = (CheckedTextView) rowView.findViewById(R.id.user_name_checked);
		teamNameView.setText(teamName);
	
		
	
		if(datasource.checkIfTeamExist(teamName))
		{
			
		}
		else
		{
			
		}
				
		
		
		datasource.close();
		
		
		
		

		return rowView;
	}

	@Override
	public int getCount()
	{

		return values.length;
	}

	@Override
	public String getItem(int position)
	{

		return values[position];
	}

	@Override
	public long getItemId(int position)
	{

		return position;
	}

	public void swapItems(String[] values)
	{
		this.values = values;
		notifyDataSetChanged();
	}

}
