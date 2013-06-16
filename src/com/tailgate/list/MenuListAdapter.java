package com.tailgate.list;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tailgate.R;

public class MenuListAdapter extends ArrayAdapter<String>
{

	private final Context context;
	private ArrayList<String>  values;
	private TextView menuNameTextView;
	private TextView menuMessageTextView;
	private TextView menuTimeTextView;
	private ImageView menuStatusImageView;

	//
	// public UserListAdapter(Context context, ArrayList<UserBean> values)
	// {
	//
	// this.context = context;
	// this.values = values;
	// }
	//

	public MenuListAdapter(Context context, int textViewResourceId, ArrayList<String> objects)
	{
		super(context, textViewResourceId);
		this.context = context;
		this.values = objects;

	}
	
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		
		if(values != null)
			
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.list_item_menu, parent, false);

			menuNameTextView = (TextView) rowView.findViewById(R.id.username);
			menuMessageTextView = (TextView) rowView.findViewById(R.id.message);
			menuTimeTextView = (TextView) rowView.findViewById(R.id.time);
			menuStatusImageView = (ImageView) rowView.findViewById(R.id.avatar);

			menuMessageTextView.setText(values.get(position));

			menuNameTextView.setVisibility(View.INVISIBLE);

			menuTimeTextView.setVisibility(View.INVISIBLE);
			return rowView;
		}
		else
		{
			return convertView;
		}

	
		// if (values.get(position).getStatus().equalsIgnoreCase(TailgateConstants.STATUS_AVAILABLE))
		// {
		// statusImageView.setImageResource(R.drawable.online);
		// }
		// else if (values.get(position).getStatus().equalsIgnoreCase(TailgateConstants.STATUS_UNAVAILABLE))
		// {
		// statusImageView.setImageResource(R.drawable.marker);
		// }

		
	}

	@Override
	public int getCount()
	{
		return values.size();
	}

	@Override
	public String getItem(int position)
	{
		return values.get(position);
	}

	@Override
	public long getItemId(int position)
	{

		return position;
	}

	public void swapItems(ArrayList<String> values)
	{
		this.values = values;
		notifyDataSetChanged();
	}

}
