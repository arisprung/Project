package com.tailgate.menu;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.tailgate.R;
import com.tailgate.R.dimen;
import com.tailgate.R.drawable;
import com.tailgate.R.id;
import com.tailgate.R.layout;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class BaseActivity extends SlidingFragmentActivity
{

	private int mTitleRes;
	protected ListFragment mFrag;

	public BaseActivity(int titleRes)
	{
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null)
		{
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new SampleListFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		}
		else
		{
			mFrag = (ListFragment) this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setMenu(R.layout.row);
		ColorDrawable cd = new ColorDrawable((Color.parseColor("#7D0802")));
		getActionBar().setBackgroundDrawable(cd);
		getActionBar().setIcon(R.drawable.abs__ic_menu_moreoverflow_normal_holo_dark);
		// getActionBar().setTitle("Settings");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
			case android.R.id.home:
				toggle();
				return true;
				// case R.id.github:
				// Util.goToGitHub(this);
				// return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keycode, KeyEvent e)
	{
		switch (keycode)
		{
			case KeyEvent.KEYCODE_MENU:
				toggle();
				return true;
		}

		return super.onKeyDown(keycode, e);
	}
	
	

}
