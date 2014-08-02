package com.bobbypriambodo.anisuke;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.bobbypriambodo.anisuke.adapter.TabsPagerAdapter;

/**
 * @author Bobby Priambodo
 */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	/*
	 * Activity code constants.
	 */
	private static final int ACTIVITY_CREATE = 0;

	/*
	 * Menu constant IDs.
	 */
	private static final int ADD_ID = Menu.FIRST;
	private static final int ABOUT_ID = Menu.FIRST + 1;

	/*
	 * PagerAdapter for the tabs.
	 */
	private TabsPagerAdapter mTabsPagerAdapter;

	/*
	 * The ViewPager.
	 */
	private ViewPager mViewPager;

	/*
	 * Array of string containing the tabs' titles.
	 */
	private String[] tabs = { "Following", "Bucket" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter.
		mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener for swipes.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mTabsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each tabs, add the tab to the action bar.
		for (String title : tabs)
			actionBar.addTab(actionBar.newTab().setText(title).setTabListener(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Add Action Items
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actions_main_activity, menu);

		// Add options menu
		menu.add(0, ADD_ID, 0, R.string.menu_add);
		menu.add(0, ABOUT_ID, 1, R.string.menu_about);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			// For add action item and menu
			case R.id.action_add:
			case ADD_ID:
				addSeries();
				return true;

			// For about menu
			case ABOUT_ID:
				showAbout();
				return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}

	private void addSeries() {
		Intent i = new Intent(this, EditSeriesActivity.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}

	private void showAbout() {
		Intent i = new Intent(this, AboutActivity.class);
		startActivity(i);
	}
}