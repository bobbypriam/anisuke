package com.bobbypriambodo.anisuke;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Bobby Priambodo
 */
public class FollowingFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_following_list, container, false);

		// Do something meaningful

		return rootView;
	}
}