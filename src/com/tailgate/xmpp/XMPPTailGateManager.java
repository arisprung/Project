package com.tailgate.xmpp;

import java.util.Iterator;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.provider.PrivacyProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.GroupChatInvitation;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.ReportedData.Row;
import org.jivesoftware.smackx.bytestreams.ibb.provider.CloseIQProvider;
import org.jivesoftware.smackx.bytestreams.ibb.provider.DataPacketProvider;
import org.jivesoftware.smackx.bytestreams.ibb.provider.OpenIQProvider;
import org.jivesoftware.smackx.bytestreams.socks5.provider.BytestreamsProvider;
import org.jivesoftware.smackx.packet.ChatStateExtension;
import org.jivesoftware.smackx.packet.LastActivity;
import org.jivesoftware.smackx.packet.OfflineMessageInfo;
import org.jivesoftware.smackx.packet.OfflineMessageRequest;
import org.jivesoftware.smackx.packet.SharedGroupsInfo;
import org.jivesoftware.smackx.provider.AdHocCommandDataProvider;
import org.jivesoftware.smackx.provider.DataFormProvider;
import org.jivesoftware.smackx.provider.DelayInformationProvider;
import org.jivesoftware.smackx.provider.DiscoverInfoProvider;
import org.jivesoftware.smackx.provider.DiscoverItemsProvider;
import org.jivesoftware.smackx.provider.MUCAdminProvider;
import org.jivesoftware.smackx.provider.MUCOwnerProvider;
import org.jivesoftware.smackx.provider.MUCUserProvider;
import org.jivesoftware.smackx.provider.MessageEventProvider;
import org.jivesoftware.smackx.provider.MultipleAddressesProvider;
import org.jivesoftware.smackx.provider.RosterExchangeProvider;
import org.jivesoftware.smackx.provider.StreamInitiationProvider;
import org.jivesoftware.smackx.provider.VCardProvider;
import org.jivesoftware.smackx.provider.XHTMLExtensionProvider;
import org.jivesoftware.smackx.search.UserSearch;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.json.JSONException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.tailgate.R;

import com.tailgate.TailgateConstants;
import com.tailgate.TailgateSharedPrefrence;
import com.tailgate.activities.MainActivity;
import com.tailgate.xmpp.listner.TailGateRosterListner;
import com.tailgate.xmpp.listner.XMPPTailgateConnectionListner;
import com.tailgate.xmpp.listner.XMPPTailgatePacketListner;

public class XMPPTailGateManager
{

	public static XMPPConnection m_connection;
	private static final String TAG = XMPPTailGateManager.class.toString();
	public static boolean m_bFlagPingThread = true;
	private static TailgateSharedPrefrence tailgateSharedPrefs = new TailgateSharedPrefrence();
	private static Context mContext;
	public static String mPassword ;
	public static String mUserName ;
	public static String mConfrenceName = "two";
	private ProviderManager pm;
	private static Roster mRoster;
	public static TailGateMultiUserChatManager muc;

	public XMPPTailGateManager(Context context)
	{
		if (mContext == null)
		{
			mContext = context;
		}


	}

	public XMPPTailGateManager()
	{

	}

	public boolean StartXMPPLoginRuningProgress() throws JSONException
	{
		
		setXMPPCredentials();
		
	

		pm = ProviderManager.getInstance();
		configure(pm);
		
		TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		tailgateSharedPrefs.setStringSharedPreferences(mContext.getApplicationContext(), TailgateConstants.IMEI, imei);
		
		// GET LOGIN AND PASSWORD AND FOTA NAME FROM SHARED PREFRENCES
		// getSharedPrefrencesData();

		ConnectionConfiguration connConfig = new ConnectionConfiguration(TailgateConstants.XMPP_SERVER_URL/* "talk.google.com" */,
				TailgateConstants.XMPP_PORT/* 5222 */);

		m_connection = new XMPPConnection(connConfig);

		int iIndex = 0;
		boolean bFlagConnection = false;
		while (!bFlagConnection)
		{
			Log.d(TAG, "XMPP Login Attemped: " + iIndex + 1);

			bFlagConnection = XMPPConnectingToServer();
			iIndex++;
			if (iIndex == 3)
			{
				Log.d(TAG, "Failed to connect to XMPP");
				XMPPTailGateManager.setConnectNotification(R.drawable.stop, "TailGate is OFF!");
				// runOnUI(InstallerActivity.loginText, "Failed to connect to XMPP");
				return bFlagConnection;
			}
		}

		if (bFlagConnection)
		{
			boolean bFlagLogin = XMPPLogingToServer();
			return bFlagLogin;
		}

		return bFlagConnection;
	}

	private void setXMPPCredentials()
	{
		if(tailgateSharedPrefs != null)
		{
			mUserName = tailgateSharedPrefs.getStringSharedPreferences(mContext,TailgateConstants.XMPP_USER_NAME, "");
			mPassword = tailgateSharedPrefs.getStringSharedPreferences(mContext,TailgateConstants.XMPP_PASSWORD, "");
		}
		
		
	}

	private boolean XMPPConnectingToServer()
	{
		try
		{
			SASLAuthentication.supportSASLMechanism("PLAIN", 0);

			Log.d(TAG, "XMPP - Connecting...");

			m_connection.connect();
		}
		catch (XMPPException ex)
		{
			Log.e(TAG, "XMPP - Connecting Fail: " + ex.getMessage());
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e(TAG, "XMPP - Connecting Fail: " + e.getMessage());
			return false;
		}

		Log.i(TAG, "XMPP - Connection Succeed");

		return true;
	}

	private boolean XMPPLogingToServer() throws JSONException
	{

		try
		{

			m_connection.login(TailgateConstants.XMPP_ADMIN, TailgateConstants.XMPP_ADMIN_PASSWORD);
			Log.d(TAG, "XMPPLogingToServer - Login is admin Succeed checking if user exist");

			if (checkIfUserExistInXMPP())
			{
				Log.i(TAG, "user  exist: " + mUserName + "Starting relogin....");
				m_connection.disconnect();
				if (XMPPConnectingToServer())
				{
					m_connection.login(mUserName, mPassword);
					Log.i(TAG, "Login Succeed" + mUserName);
				}

			}
			else
			{
				Log.i(TAG, "user doesnt exist: " + mUserName + "Starting to create account....");

				AccountManager am = new AccountManager(m_connection);
				am.createAccount(mUserName, mPassword);
				if (checkIfUserExistInXMPP())
				{
					m_connection.disconnect();
					if (XMPPConnectingToServer())
					{

						Log.i(TAG, "Created New Account Succeed" + mUserName);

						m_connection.login(mUserName, mPassword);
						Log.i(TAG, "Login  new Account Succeed" + mUserName);

					}
				}
				else
				{
					Log.e(TAG, "Problem creating new Account");
					
				}

				

			}
			// String userKey = tailgateSharedPrefs.getStringSharedPreferences(mContext, TailgateConstants.XMPP_USER_NAME, "");
			// String passWordKey = tailgateSharedPrefs.getStringSharedPreferences(mContext, TailgateConstants.XMPP_PASSWORD, "");
			// Log.i("InstallerCommunication", "login user : " + userKey);
			// Log.i("InstallerCommunication", "login password: " + passWordKey);
			//
			// AccountManager am = new AccountManager(m_connection);
			// am.createAccount(mUserName, "1234");

		}
		catch (XMPPException ex)
		{

			Log.w(TAG, "XMPPLoginToServer Error: " + ex.getMessage());
			MainActivity.bXMPPLoginFinished = false;

			Log.w(TAG, "XMPPLoginToServer Error: " + ex.getMessage());

			return false;
		}
		catch (Exception e)
		{
			MainActivity.bXMPPLoginFinished = false;
			Log.e(TAG, "XMPPLoginToServer Error: " + e.getMessage());
			return false;
		}

		Presence presence = new Presence(Presence.Type.available);
		m_connection.sendPacket(presence);

		if (m_connection.isAuthenticated())
		{

			// runOnUI(InstallerActivity.loginText, "Login Succeed");
			Log.d(TAG, "XMPPLogingToServer - Login Succeed");
			setConnectNotification(R.drawable.play, "TailGate is ON! User : " + parseXMPPName(m_connection.getUser().toString()) + " is connected");
			// setMessageConnection();
			setConectionListner();
			// setPresenceListner();
			setMultiChatRoom();
			startLocationTaskManager();
		}

		// Sending Ping to XMPP Server is Alive

		new Thread() {
			public void run()
			{
				try
				{
					while (m_bFlagPingThread)
					{

						boolean bconnect = m_connection.isConnected();
						if (!bconnect)
						{
							XMPPTailGateManager.setConnectNotification(R.drawable.stop, "TailGate is OFF!");
						}

						Thread.sleep(10000);
					}

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			};
		}.start();

		return true;
	}

	private void startLocationTaskManager()
	{
		
		
	}

	public static void setConnectNotification(int icon, String message)
	{

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext).setSmallIcon(icon)
				.setContentTitle(mContext.getString(R.string.app_name)).setContentText(message);
		// Creates an explicit intent for an Activity in your app
		mBuilder.setOngoing(true);

		Intent resultIntent = new Intent(mContext, MainActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);

		NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(5, mBuilder.build());

	}

	private void setMultiChatRoom()
	{
		muc = new TailGateMultiUserChatManager(m_connection);
		muc.setMultiChat(mContext);

		// mMuc = new MultiUserChat(m_connection, "dolphins@conference."+TailgateConstants.XMPP_SERVER_URL);
		// try
		// {
		// mMuc.join(mUserName);
		// mMuc.addMessageListener(new Tail)
		// }
		// catch (XMPPException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private void setPresenceListner()
	{
		mRoster = m_connection.getRoster();
		mRoster.addRosterListener(new TailGateRosterListner());

	}

	public static Roster getTailGateRoster()
	{
		if (mRoster != null)
		{
			return mRoster;
		}
		return null;
	}

	private static void setConectionListner()
	{
		m_connection.addConnectionListener(new XMPPTailgateConnectionListner());

	}

	private static void setMessageConnection()
	{
		// Add a packet listener to get messages sent to us
		PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
		m_connection.addPacketListener(new XMPPTailgatePacketListner(), filter);

	}

	private static void configure(ProviderManager pm)
	{
		// CONFIGURE SMACK

		// Private Data Storage
		pm.addIQProvider("query", "jabber:iq:private", new PrivateDataManager.PrivateDataIQProvider());

		// Time
		try
		{
			pm.addIQProvider("query", "jabber:iq:time", Class.forName("org.jivesoftware.smackx.packet.Time"));
		}
		catch (ClassNotFoundException e)
		{
			Log.w("TestClient", "Can't load class for org.jivesoftware.smackx.packet.Time");
		}

		// Roster Exchange
		pm.addExtensionProvider("x", "jabber:x:roster", new RosterExchangeProvider());

		// Message Events
		pm.addExtensionProvider("x", "jabber:x:event", new MessageEventProvider());

		// Chat State
		pm.addExtensionProvider("active", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
		pm.addExtensionProvider("composing", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
		pm.addExtensionProvider("paused", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
		pm.addExtensionProvider("inactive", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
		pm.addExtensionProvider("gone", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());

		// XHTML
		pm.addExtensionProvider("html", "http://jabber.org/protocol/xhtml-im", new XHTMLExtensionProvider());

		// Group Chat Invitations
		pm.addExtensionProvider("x", "jabber:x:conference", new GroupChatInvitation.Provider());

		// Service Discovery # Items
		pm.addIQProvider("query", "http://jabber.org/protocol/disco#items", new DiscoverItemsProvider());

		// Service Discovery # Info
		pm.addIQProvider("query", "http://jabber.org/protocol/disco#info", new DiscoverInfoProvider());

		// Data Forms
		pm.addExtensionProvider("x", "jabber:x:data", new DataFormProvider());

		// MUC User
		pm.addExtensionProvider("x", "http://jabber.org/protocol/muc#user", new MUCUserProvider());

		// MUC Admin
		pm.addIQProvider("query", "http://jabber.org/protocol/muc#admin", new MUCAdminProvider());

		// MUC Owner
		pm.addIQProvider("query", "http://jabber.org/protocol/muc#owner", new MUCOwnerProvider());

		// Delayed Delivery
		pm.addExtensionProvider("x", "jabber:x:delay", new DelayInformationProvider());

		// Version
		try
		{
			pm.addIQProvider("query", "jabber:iq:version", Class.forName("org.jivesoftware.smackx.packet.Version"));
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		// VCard
		pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());

		// Offline Message Requests
		pm.addIQProvider("offline", "http://jabber.org/protocol/offline", new OfflineMessageRequest.Provider());

		// Offline Message Indicator
		pm.addExtensionProvider("offline", "http://jabber.org/protocol/offline", new OfflineMessageInfo.Provider());

		// Last Activity
		pm.addIQProvider("query", "jabber:iq:last", new LastActivity.Provider());

		// User Search
		pm.addIQProvider("query", "jabber:iq:search", new UserSearch.Provider());

		// SharedGroupsInfo
		pm.addIQProvider("sharedgroup", "http://www.jivesoftware.org/protocol/sharedgroup", new SharedGroupsInfo.Provider());

		// JEP-33: Extended Stanza Addressing
		pm.addExtensionProvider("addresses", "http://jabber.org/protocol/address", new MultipleAddressesProvider());

		// FileTransfer
		pm.addIQProvider("si", "http://jabber.org/protocol/si", new StreamInitiationProvider());
		pm.addIQProvider("query", "http://jabber.org/protocol/bytestreams", new BytestreamsProvider());
		pm.addIQProvider("open", "http://jabber.org/protocol/ibb", new OpenIQProvider());
		pm.addIQProvider("data", "http://jabber.org/protocol/ibb", new DataPacketProvider());
		pm.addIQProvider("close", "http://jabber.org/protocol/ibb", new CloseIQProvider());
		pm.addExtensionProvider("data", "http://jabber.org/protocol/ibb", new DataPacketProvider());

		// pm.addIQProvider("open", "http://jabber.org/protocol/ibb", new
		// IBBProviders.Open());
		// pm.addIQProvider("close", "http://jabber.org/protocol/ibb", new
		// IBBProviders.Close());
		// pm.addExtensionProvider("data", "http://jabber.org/protocol/ibb", new
		// IBBProviders.Data());

		// Privacy
		pm.addIQProvider("query", "jabber:iq:privacy", new PrivacyProvider());
		pm.addIQProvider("command", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider());
		pm.addExtensionProvider("malformed-action", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.MalformedActionError());
		pm.addExtensionProvider("bad-locale", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.BadLocaleError());
		pm.addExtensionProvider("bad-payload", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.BadPayloadError());
		pm.addExtensionProvider("bad-sessionid", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.BadSessionIDError());
		pm.addExtensionProvider("session-expired", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.SessionExpiredError());
	}

	public static String parseXMPPName(String name)
	{
		String strName = "";
		if (name.contains("@"))
		{
			String currentString[] = name.split("@");
			strName = currentString[0];
			return strName;
		}
		return name;

	}

	public static String parseXMPPGroupName(String name)
	{
		String strName = "";
		if (name.contains("/"))
		{
			String currentString[] = name.split("/");
			strName = currentString[1];
			return strName;
		}
		return name;

	}

	public TailGateMultiUserChatManager getMuc()
	{
		return muc;
	}

	public void setMuc(TailGateMultiUserChatManager muc)
	{
		this.muc = muc;
	}

	public static XMPPConnection getM_connection()
	{
		return m_connection;
	}

	public static void setM_connection(XMPPConnection m_connection)
	{
		XMPPTailGateManager.m_connection = m_connection;
	}

	private boolean checkIfUserExistInXMPP()
	{

		try
		{

			UserSearchManager search = new UserSearchManager(m_connection);

			Form searchForm = search.getSearchForm("search." + m_connection.getServiceName());

			Form answerForm = searchForm.createAnswerForm();
			answerForm.setAnswer("Username", true);

			answerForm.setAnswer("search", mUserName);

			org.jivesoftware.smackx.ReportedData data = search.getSearchResults(answerForm, "search." + m_connection.getServiceName());

			if (data.getRows() != null)
			{
				Iterator<Row> it = data.getRows();
				while (it.hasNext())
				{
					Row row = it.next();
					Iterator iterator = row.getValues("jid");
					if (iterator.hasNext())
					{
						String value = iterator.next().toString();
						Log.i(TAG, " " + value);
						return true;
					}
					else
					{
						return false;
					}
					// Log.i("Iteartor values......"," "+value);
				}

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

}
