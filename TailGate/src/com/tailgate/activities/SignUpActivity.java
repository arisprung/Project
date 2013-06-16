package com.tailgate.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;

import com.tailgate.R;
import com.tailgate.TailGateService;
import com.tailgate.TailgateConstants;
import com.tailgate.TailgateSharedPrefrence;


public class SignUpActivity extends Activity
{
	private TailgateSharedPrefrence tailgateSharedPrefs; 
	private EditText userText;
	private EditText passwordText;
	private EditText verifyPasswordText; 
	private CheckBox checkbox;
	
	
	private final static String TAG = SignUpActivity.class.toString();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_screen);
		tailgateSharedPrefs = new TailgateSharedPrefrence();
		Button signBut = (Button)findViewById(R.id.buttonSignIn);
		
		 userText = (EditText)findViewById(R.id.editTextUserNameToLogin);
		 passwordText = (EditText)findViewById(R.id.editTextPasswordToLogin);
		 checkbox= (CheckBox)findViewById(R.id.checkbox);
		 verifyPasswordText = (EditText)findViewById(R.id.editTextPasswordToLoginVerify);
		
		signBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				
				if( verifyPasswordText.getText().toString().equals(passwordText.getText().toString()))
				{
					
					Log.i(TAG , "User Name : "+userText.getText().toString());
					Log.i(TAG , "Password : "+passwordText.getText().toString());
					saveUserPassword();
					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(intent);
				
				}
				else
				{
					Log.e(TAG , "Verfiy Password is not good try again!");
					Toast.makeText(getApplicationContext(), "Verfiy Password is not good try again!", Toast.LENGTH_LONG).show();
				}
				
				finish();
			}

			private void saveUserPassword()
			{
				tailgateSharedPrefs.setStringSharedPreferences(getApplicationContext(), TailgateConstants.XMPP_USER_NAME, userText.getText().toString());
				tailgateSharedPrefs.setStringSharedPreferences(getApplicationContext(), TailgateConstants.XMPP_PASSWORD, passwordText.getText().toString());
				tailgateSharedPrefs.setBooleanSharedPreferences(getApplicationContext(), TailgateConstants.XMPP_SAVE_CREDENTIALS, checkbox.isChecked());
			}
		});
	}
}
