package com.tailgate.list;

import java.util.ArrayList;
import java.util.List;

import com.tailgate.R;
import com.tailgate.TailgateConstants;
import com.tailgate.UserBean;
import com.tailgate.xmpp.TailGateMultiUserChatManager;
import com.tailgate.xmpp.XMPPTailGateManager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatMembersListAdapter extends ArrayAdapter<String>
{

	private final Context context;
	private ArrayList<String> values;
	private TextView nameTextView;
	private TextView messageTextView;
	private TextView timeTextView;
	private ImageView statusImageView;
	private Context mContext;

	//
	// public UserListAdapter(Context context, ArrayList<UserBean> values)
	// {
	//
	// this.context = context;
	// this.values = values;
	// }
	//

	public ChatMembersListAdapter(Context context, int textViewResourceId, ArrayList<String> objects)
	{
		super(context, textViewResourceId);
		this.context = context;
		this.values = objects;
		mContext = context;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_item_white, parent, false);

		nameTextView = (TextView) rowView.findViewById(R.id.username);
		messageTextView = (TextView) rowView.findViewById(R.id.message);
		timeTextView = (TextView) rowView.findViewById(R.id.time);
		statusImageView = (ImageView) rowView.findViewById(R.id.avatar);

		nameTextView.setVisibility(View.INVISIBLE);

		timeTextView.setVisibility(View.INVISIBLE);
		messageTextView.setText(values.get(position));
//		if(!values.get(position).equals(XMPPTailGateManager.parseXMPPName(XMPPTailGateManager.m_connection.getUser())))
//		{
//			
//		}

		return rowView;
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

	@Override
	public void notifyDataSetChanged()
	{
		try
		{
			TailGateMultiUserChatManager muc = new TailGateMultiUserChatManager(mContext);
			values = muc.getRoomOccupants();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		super.notifyDataSetChanged();
	}

}
