package com.bobbypriambodo.anisuke;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.bobbypriambodo.anisuke.adapter.TabsPagerAdapter;

/**
 * @author Bobby Priambodo
 */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	private TabsPagerAdapter mTabsPagerAdapter;

	private ViewPager mViewPager;

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
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}
}