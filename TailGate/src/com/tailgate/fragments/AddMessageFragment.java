package com.tailgate.fragments;

import java.util.ArrayList;

import org.jivesoftware.smack.XMPPConnection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.tailgate.R;
import com.tailgate.list.ChatMembersListAdapter;
import com.tailgate.xmpp.TailGateMultiUserChatManager;
import com.tailgate.xmpp.XMPPTailGateManager;

public class AddMessageFragment  extends Fragment
{

	public static ChatMembersListAdapter adapter;
	private ListView listView;
	private ArrayList<String> userlist;
	private static final String TAG = ChatListFragment.class.toString();
	private Button addButton;
	private EditText messageEditText;
	private  XMPPConnection m_connection; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		

		if (container == null)
		{
			return null;
		}
		View view = inflater.inflate(R.layout.add_message_layout, container, false);
		addButton = (Button)view.findViewById(R.id.button);
		messageEditText = (EditText)view.findViewById(R.id.editText);
		
		return view;
	
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				if (!messageEditText.getText().toString().equals("") && (messageEditText.getText().length() < 140))
				{
					
					sendMessage(messageEditText.getText().toString());
					messageEditText.setText("");
					
					
				}
				
			}

			private void sendMessage(String string)
			{
				
				TailGateMultiUserChatManager muc = new TailGateMultiUserChatManager(getActivity().getApplicationContext());
				muc.addMessageToMultiChat(string);
				
			}
		});
		
	

	}
	
}