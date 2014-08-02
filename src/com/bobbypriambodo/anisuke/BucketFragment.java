package com.bobbypriambodo.anisuke;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Bobby Priambodo
 */
public class BucketFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_bucket_list, container, false);

		// Do something meaningful.

		return rootView;
	}
}