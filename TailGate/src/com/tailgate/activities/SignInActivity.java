package com.tailgate.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.tailgate.R;
import com.tailgate.TailGateService;
import com.tailgate.TailgateConstants;
import com.tailgate.TailgateSharedPrefrence;

public class SignInActivity extends Activity
{

	private TailgateSharedPrefrence tailgateSharedPrefs;
	private EditText userText;
	private EditText passwordText;
	private Button signInButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		tailgateSharedPrefs = new TailgateSharedPrefrence();
		setContentView(R.layout.sign_in_screen);
		userText = (EditText) findViewById(R.id.editTextUserNameToLogin);
		passwordText = (EditText) findViewById(R.id.editTextPasswordToLogin);
		signInButton = (Button) findViewById(R.id.buttonSignIn);
		checkIfToDisplayCredentials();

		signInButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
				finish();

			}
		});

	}

	private void checkIfToDisplayCredentials()
	{

		if (tailgateSharedPrefs != null)
		{

			if (tailgateSharedPrefs.checkifPrefrenceExist(getApplicationContext()))
			{
				boolean bDisplay = tailgateSharedPrefs.getBooleanSharedPreferences(getApplicationContext(), TailgateConstants.XMPP_SAVE_CREDENTIALS,
						false);
				if (bDisplay)
				{
					userText.setText(tailgateSharedPrefs.getStringSharedPreferences(getApplicationContext(), TailgateConstants.XMPP_USER_NAME, ""));
					passwordText
							.setText(tailgateSharedPrefs.getStringSharedPreferences(getApplicationContext(), TailgateConstants.XMPP_PASSWORD, ""));
				}
			}

		}
	}
}
