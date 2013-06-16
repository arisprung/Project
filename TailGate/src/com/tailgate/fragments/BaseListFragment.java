//package com.tailgate.fragments;
//
//import java.util.ArrayList;
//
//import com.tailgate.R;
//import com.tailgate.list.MessageListAdapter;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ListView;
//
//public abstract class BaseListFragment extends Fragment
//{
//	public static MessageListAdapter adapter;
//	private ListView listView;
//	private ArrayList<String> userlist;
//	private static final String TAG = BaseListFragment.class.toString();
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//	{
//
//		if (container == null)
//		{
//			return null;
//		}
//		View view = inflater.inflate(R.layout.list_view, container, false);
//		userlist = getlist();
//		return view;
//	
//	}
//	
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState)
//	{
//		super.onActivityCreated(savedInstanceState);
//		
//		//loadParticipantsList();
//	
//		//adapter = new MessageListAdapter(getActivity().getApplicationContext(), R.layout.list_item, userlist);
//		listView = (ListView) getView().findViewById(R.id.list);
//		listView.setAdapter(adapter);
//		
//		listView.setTextFilterEnabled(true);
//		
//		listView.setOnItemClickListener(new OnItemClickListener()
//		{
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View v, int i, long l)
//			{
//				Log.i(TAG,"Item Clicked");
//				
//				
//			}
//		});
//	
//
//	}
//	
//	
//
//	protected abstract ArrayList<String> getlist();
//
//	
//	
//
//	
//
//}
