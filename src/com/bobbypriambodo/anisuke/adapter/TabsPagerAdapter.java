package com.bobbypriambodo.anisuke.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Bobby Priambodo on 8/2/2014.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		switch (i) {
			case 0: return null; // return FollowingFragment();
			case 1: return null; // return BucketFragment();
		}
	}

	@Override
	public int getCount() {
		return 2;
	}
}
