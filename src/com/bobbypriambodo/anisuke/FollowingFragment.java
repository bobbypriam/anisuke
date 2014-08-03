package com.bobbypriambodo.anisuke;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bobbypriambodo.anisuke.adapter.AnisukeCursorAdapter;
import com.bobbypriambodo.anisuke.contentprovider.AnisukeContentProvider;
import com.bobbypriambodo.anisuke.database.SeriesTable;

/**
 * @author Bobby Priambodo
 */
public class FollowingFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private Context mCtx;
	private AnisukeCursorAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_following_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mCtx = getActivity();
		fillData();
	}

	private void fillData() {
		mAdapter = new AnisukeCursorAdapter(mCtx, R.layout.series_following_row, null, 0);
		setListAdapter(mAdapter);
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	 public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		String[] projection = { SeriesTable.COL_ID, SeriesTable.COL_TITLE, SeriesTable.COL_EPISODE };
		CursorLoader cursorLoader = new CursorLoader(mCtx, AnisukeContentProvider.CONTENT_URI_FOLLOWING, projection, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {
		mAdapter.swapCursor(null);
	}
}