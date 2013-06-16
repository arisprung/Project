package com.tailgate.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;
import com.tailgate.LocationBean;
import com.tailgate.MyLocation;
import com.tailgate.R;
import com.tailgate.UserBean;
import com.tailgate.MyLocation.LocationResult;
import com.tailgate.R.drawable;
import com.tailgate.R.id;
import com.tailgate.R.layout;
import com.tailgate.db.LocationDataSource;
import com.tailgate.xmpp.XMPPTailGateManager;

public class MapTailgateFragment extends Fragment
{
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	private MapView mMapView;
	private GoogleMap mMap;
	private Bundle mBundle;
	private UiSettings mUiSettings;
	private Context mContext;
	private MyLocationOverlay me = null;
	public static double mlongitude;
	public static double mlatitude;
	private static MyLocation myLocation = new MyLocation();
	private final static String TAG = MapTailgateFragment.class.toString();
	private List<LocationBean> userlist;
	View inflatedView;
	private LocationDataSource datasource;
	LocationManager manager;
	int mCurCheckPosition = 0;

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", mCurCheckPosition);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{

		mContext = getActivity().getApplicationContext();
		super.onActivityCreated(savedInstanceState);
		// mContext=getApplicationContext();
		if (savedInstanceState != null)
		{
			// Restore last state for checked position.
			mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		inflatedView = inflater.inflate(R.layout.map_fragment, container, false);

		try
		{
			MapsInitializer.initialize(getActivity());
		}
		catch (GooglePlayServicesNotAvailableException e)
		{
			e.printStackTrace();
		}
		manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			buildAlertMessageNoGps();
		}
		mMapView = (MapView) inflatedView.findViewById(R.id.map);
		mMapView.setCameraDistance(10);

		// mMapView.getOverlays().add(new SitesOverlay(marker));
		// mMap = ((MapFragment) getSupportFragmentManager().findFragmentById(android.R.id.content)).getMap();
		mMapView.onCreate(mBundle);

		return inflatedView;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		MapTailgateFragment f = (MapTailgateFragment) getFragmentManager().findFragmentById(R.id.map);
		if (f != null)
		{
			getFragmentManager().beginTransaction().remove(f).commit();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mBundle = savedInstanceState;
	}

	private void setUpMapIfNeeded(View inflatedView)
	{
		if (mMap == null)
		{
			mMap = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
			if (mMap != null)
			{
				setUpMap();
			}
		}
	}

	private void setUpMap()
	{
		userlist = new ArrayList<LocationBean>();
		GetDeviceLocation();

	}

	@Override
	public void onResume()
	{
		super.onResume();
		mMapView.onResume();
		setUpMapIfNeeded(inflatedView);
	}

	@Override
	public void onPause()
	{
		super.onPause();
		mMapView.onPause();
		mMap = null;
	}

	@Override
	public void onDestroy()
	{
		mMapView.onDestroy();
		super.onDestroy();
	}

	private void buildAlertMessageNoGps()
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?").setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, final int id)
					{
						startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, final int id)
					{
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	private GeoPoint getPoint(double lat, double lon)
	{
		return (new GeoPoint((int) (lat * 1000000.0), (int) (lon * 1000000.0)));
	}

	public LocationResult locationResult = new LocationResult() {
		@Override
		public void gotLocation(final Location location)
		{
			if (location == null)
			{
				Log.i(TAG, "Cant get location = n");

				getActivity().runOnUiThread(new Runnable() {
					public void run()
					{
						buildAlertMessageNoGps();
					}
				});

				return;
			}

			mlongitude = location.getLongitude();
			mlatitude = location.getLatitude();
			// Got the location!

			String m_strLastKnownLocation = String.format("%s,%s", mlatitude, mlongitude);
			Log.i(TAG, "Last Location : " + m_strLastKnownLocation);

			Handler notificationHandle = new Handler(Looper.getMainLooper()) {
				public void handleMessage(Message msg)
				{
					setmyLocationInMap();

				}
			};

			notificationHandle.sendMessage(notificationHandle.obtainMessage());

		}

	};

	public void GetDeviceLocation()
	{

		// notificationHandle.sendMessage(notificationHandle.obtainMessage());
		myLocation.getLocation(getActivity(), locationResult);

	}

	private void setmyLocationInMap()
	{

		datasource = new LocationDataSource(mContext);
		datasource.open();
		userlist = datasource.getAllLocationBeans();
		Log.d(TAG, "Map Users List : " + userlist);
		updateMap();

	}

	private void updateMap()
	{

		if (mMap != null)
		{
			mMap = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
			if (mMap != null)
			{
				for (int i = 0; i < userlist.size(); i++)
				{

					double dLatitude = Double.valueOf(userlist.get(i).getLatitude());
					double dLongitude = Double.valueOf(userlist.get(i).getLongitude());
					LatLng ll = new LatLng(dLatitude, dLongitude);
					mMap.addMarker(new MarkerOptions().position(ll).title(XMPPTailGateManager.parseXMPPGroupName(userlist.get(i).getUsername()))
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

				}
				LatLng latLng = new LatLng(mlatitude, mlongitude);
				CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 7);
				mMap.animateCamera(cameraUpdate);
				mMap.setMyLocationEnabled(true);

				mUiSettings = mMap.getUiSettings();
				mUiSettings.setMyLocationButtonEnabled(true);
			}

		}
	}

}