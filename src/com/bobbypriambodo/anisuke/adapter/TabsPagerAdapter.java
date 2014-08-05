package com.bobbypriambodo.anisuke.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.bobbypriambodo.anisuke.BucketFragment;
import com.bobbypriambodo.anisuke.FollowingFragment;

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
			case 0: return new FollowingFragment();
			case 1: return new BucketFragment();
			default: return null;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}
}
