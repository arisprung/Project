//package com.tailgate.list;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import com.tailgate.MessageBean;
//import com.tailgate.R;
//import com.tailgate.TailgateConstants;
//import com.tailgate.UserBean;
//
//import com.tailgate.xmpp.XMPPTailGateManager;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.graphics.Color;
//import android.text.format.DateFormat;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class MessageListAdapter extends ArrayAdapter<MessageBean>
//{
//
//	private final Context context;
//	private ArrayList<MessageBean> values;
//	private String restFormat = "MMM dd, hh:mm:ss AA";
//
//
//	//
//	// public UserListAdapter(Context context, ArrayList<UserBean> values)
//	// {
//	//
//	// this.context = context;
//	// this.values = values;
//	// }
//	//
//
//	public MessageListAdapter(Context context, int textViewResourceId, ArrayList<MessageBean> objects)
//	{
//		super(context, textViewResourceId);
//		this.context = context;
//		this.values = objects;
//
//	}
//
//
//	@SuppressLint("ResourceAsColor")
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent)
//	{
//		
//		
//		
//		
//		View res = convertView;
//		if (res == null)
//		{
//			// LAYOUT FOR PHOTOS IN CALLLOG
//			// res = getLayoutInflater().inflate(R.layout.item_composer, null);
//			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			res =inflater.inflate(R.layout.list_item_white, parent, false);
//			ViewHolder holder = new ViewHolder();
//			holder.nameTextView =  (TextView) res.findViewById(R.id.username);
//			holder.messageTextView = (TextView) res.findViewById(R.id.message);
//			holder.timeTextView = (TextView) res.findViewById(R.id.time);
//			holder.statusImageView = (ImageView) res.findViewById(R.id.avatar);
//			
//		
//			res.setTag(holder);
//
//		}
//		else
//		{
//
//		}
//		
//
//		ViewHolder holder = (ViewHolder) res.getTag();
//		long timestamp = Long.valueOf(values.get(position).getTime());
//		
//		holder.messageTextView.setText(values.get(position).getMessage());
//		holder.nameTextView.setText(XMPPTailGateManager.parseXMPPGroupName(values.get(position).getUsername()));
//		holder.timeTextView.setText(DateFormat.format(restFormat, timestamp));
//		holder.nameTextView.setTextColor(context.getResources().getColor(R.color.pink));
//		if(XMPPTailGateManager.parseXMPPGroupName(values.get(position).getUsername()).equals(XMPPTailGateManager.mUserName))
//		{
//			holder.statusImageView.setImageResource(R.drawable.play);
//		}
//		else
//		{
//			holder.statusImageView.setImageResource(R.drawable.stop);
//		}
//	
//
//
//		return res;
//	}
//
//	@Override
//	public int getCount()
//	{
//
//		return values.size();
//	}
//
//	@Override
//	public MessageBean getItem(int position)
//	{
//
//		return values.get(position);
//	}
//
//	@Override
//	public long getItemId(int position)
//	{
//
//		return position;
//	}
//
//	public void swapItems(ArrayList<MessageBean> values)
//	{
//		this.values = values;
//		notifyDataSetChanged();
//	}
//	
//	@Override
//	public void notifyDataSetChanged()
//	{	
////		messageDataSource = new MessageDataSource(context);
////		messageDataSource.open();
////		values = messageDataSource.getAllMessageBeans();
////		messageDataSource.close();
//		super.notifyDataSetChanged();
//	}
//	
//	static class ViewHolder
//	{
//
//		private TextView nameTextView;
//		private TextView messageTextView;
//		private TextView timeTextView;		
//		private ImageView statusImageView;
//
//		
//
//	}
//
//}
