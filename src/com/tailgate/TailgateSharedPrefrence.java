package com.tailgate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class TailgateSharedPrefrence
{

	public static final String SHARED_PREFRENCE_NAME = "Tailgate_Shared_Prefrence";

	private static SharedPreferences getSharedPreferencesFile(Context context)
	{
		SharedPreferences sharedPreferencesFile = context.getSharedPreferences(SHARED_PREFRENCE_NAME, Context.MODE_WORLD_READABLE);
		return sharedPreferencesFile;
	}

	public void setBooleanSharedPreferences(Context context, String key, boolean keyValue)
	{

		SharedPreferences sharedPreferencesFile = getSharedPreferencesFile(context);
		if (sharedPreferencesFile != null)
		{
			Editor editor = sharedPreferencesFile.edit();
			editor.putBoolean(key, keyValue);
			editor.commit();
		}
	}

	public void setStringSharedPreferences(Context context, String key, String keyValue)
	{

		SharedPreferences sharedPreferencesFile = getSharedPreferencesFile(context);
		if (sharedPreferencesFile != null)
		{
			Editor editor = sharedPreferencesFile.edit();
			editor.putString(key, keyValue);
			editor.commit();
		}
	}
	
	public void setArayStringSharedPreferences(Context context,String  key,  ArrayList<String> keyValue)
	{

		SharedPreferences sharedPreferencesFile = getSharedPreferencesFile(context);
		if (sharedPreferencesFile != null)
		{
			
			Set<String> set = new HashSet<String>();
			set.addAll(keyValue);
			Editor editor = sharedPreferencesFile.edit();
			editor.putStringSet(key, set);
			editor.commit();
		}
	}

	public String getStringSharedPreferences(Context context, String key, String defValue)
	{

		SharedPreferences sharedPreferencesFile = getSharedPreferencesFile(context);
		if (sharedPreferencesFile != null)
			return sharedPreferencesFile.getString(key, defValue);

		return defValue;
	}
	
	public ArrayList<String> getArrayStringSharedPreferences(Context context, String key, String defValue)
	{
		ArrayList<String> list = null;
		SharedPreferences sharedPreferencesFile = getSharedPreferencesFile(context);
		if (sharedPreferencesFile != null)
		{
			Set<String> set = new HashSet<String>();
			set = sharedPreferencesFile.getStringSet("key", null);
			if(set == null)
			{
				return null;
			}
			 list = new ArrayList<String>(set);
		}
		
			return list;

	
	}

	public boolean getBooleanSharedPreferences(Context context, String key, boolean defValue)
	{

		SharedPreferences sharedPreferencesFile = getSharedPreferencesFile(context);
		if (sharedPreferencesFile != null)
			return sharedPreferencesFile.getBoolean(key, defValue);

		return defValue;
	}

	public boolean checkifPrefrenceExist(Context context)
	{

		SharedPreferences sharedPreferencesFile = getSharedPreferencesFile(context);
		if (sharedPreferencesFile != null)
		{
			return true;
		}
		else
		{
			return false;
		}

	}

}
