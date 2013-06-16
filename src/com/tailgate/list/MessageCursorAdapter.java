package com.tailgate.list;

import com.tailgate.R;
import com.tailgate.db.MessageSQLiteHelper;

import com.tailgate.xmpp.XMPPTailGateManager;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageCursorAdapter extends SimpleCursorAdapter
{

	private String restFormat = "MMM dd, hh:mm:ss AA";
	private int layout;
	private LayoutInflater inflater;
	private Context context;
	private Cursor c;
	private String[] from;
	private int[] to;

	public MessageCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags)
	{
		super(context, layout, c, from, to, flags);
		this.layout = layout;
		this.context = context;
		this.c = c;
		this.from = from;
		this.to = to;
		// inflater = LayoutInflater.from(context);
	}

	// public MessageCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to)
	// {
	// super(context, layout, c, from, to);
	// this.layout = layout;
	// this.context = context;
	// inflater = LayoutInflater.from(context);
	//
	// }

	@Override
	public void setViewText(TextView v, String text)
	{

		super.setViewText(v, text);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{

		View res = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.list_item_white, parent, false);
		ViewHolder holder = new ViewHolder();
		holder.nameTextView = (TextView) res.findViewById(R.id.username);
		holder.messageTextView = (TextView) res.findViewById(R.id.message);
		holder.timeTextView = (TextView) res.findViewById(R.id.time);
		holder.statusImageView = (ImageView) res.findViewById(R.id.avatar);
		res.setTag(holder);
		return res;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2)
	{

		return super.getView(arg0, arg1, arg2);
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor)
	{
		View res = convertView;
		if (res == null)
		{
			// LAYOUT FOR PHOTOS IN CALLLOG
			// res = getLayoutInflater().inflate(R.layout.item_composer, null);

			ViewHolder holder = new ViewHolder();
			holder.nameTextView = (TextView) res.findViewById(R.id.username);
			holder.messageTextView = (TextView) res.findViewById(R.id.message);
			holder.timeTextView = (TextView) res.findViewById(R.id.time);
			holder.statusImageView = (ImageView) res.findViewById(R.id.avatar);

			res.setTag(holder);

		}
		else
		{

		}
		String strName = XMPPTailGateManager.parseXMPPGroupName(cursor.getString(cursor.getColumnIndexOrThrow(MessageSQLiteHelper.COLUMN_USERNAME)));
		ViewHolder holder = (ViewHolder) res.getTag();
		long timestamp = Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(MessageSQLiteHelper.COLUMN_TIME)));

		holder.messageTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(MessageSQLiteHelper.COLUMN_MESSAGE)));
		holder.nameTextView.setText(strName);
		holder.timeTextView.setText(DateFormat.format(restFormat, timestamp));
		holder.nameTextView.setTextColor(context.getResources().getColor(R.color.pink));
		if (strName.equals(XMPPTailGateManager.mUserName))
		{
			holder.nameTextView.setTextColor(context.getResources().getColor(R.color.pink));
		}
		else
		{
			holder.nameTextView.setTextColor(context.getResources().getColor(R.color.dark_blue));
		}

		// return res;

	}

	static class ViewHolder
	{

		private TextView nameTextView;
		private TextView messageTextView;
		private TextView timeTextView;
		private ImageView statusImageView;

	}
}
