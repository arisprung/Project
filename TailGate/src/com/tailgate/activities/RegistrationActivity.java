package com.tailgate.activities;

import java.util.ArrayList;
import java.util.List;

import com.tailgate.LocationBean;
import com.tailgate.MessageBean;
import com.tailgate.R;
import com.tailgate.TailgateConstants;
import com.tailgate.TailgateSharedPrefrence;

import com.tailgate.TailGateService;
import com.tailgate.db.LocationDataSource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegistrationActivity extends Activity
{

	private TailgateSharedPrefrence tailgateSharedPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		
		Button signinBut = (Button) findViewById(R.id.buttonSignIN);
		Button signupBut = (Button) findViewById(R.id.buttonSignUP);

		tailgateSharedPrefs = new TailgateSharedPrefrence();
		if (checkIfToDisplayCredentials())
		{
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
		}
		signinBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
				startActivity(intent);
				finish();

			}
		});

		signupBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}

	private boolean checkIfToDisplayCredentials()
	{
		
		if(tailgateSharedPrefs != null)
		{
			if (tailgateSharedPrefs.checkifPrefrenceExist(getApplicationContext()))
			{
				boolean bDisplay = tailgateSharedPrefs.getBooleanSharedPreferences(getApplicationContext(), TailgateConstants.XMPP_SAVE_CREDENTIALS,
						false);
				if (bDisplay)
				{
					return true;
				}
			}
			return false;
		}
		return false;
		

	}

}
