package com.bobbypriambodo.anisuke;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.bobbypriambodo.anisuke.adapter.AnisukeCursorAdapter;
import com.bobbypriambodo.anisuke.contentprovider.AnisukeContentProvider;
import com.bobbypriambodo.anisuke.database.SeriesTable;

/**
 * @author Bobby Priambodo
 */
public class FollowingFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final int ACTIVITY_EDIT = 1;

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
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		editSeries(id);
	}

	private void editSeries(long id) {
		Intent i = new Intent(mCtx, EditSeriesActivity.class);
		i.putExtra(SeriesTable.COL_ID, id);
		startActivityForResult(i, ACTIVITY_EDIT);
	}

	@Override
	 public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		String[] projection = { SeriesTable.COL_ID, SeriesTable.COL_TITLE, SeriesTable.COL_EPISODE };
		return new CursorLoader(mCtx, AnisukeContentProvider.CONTENT_URI_FOLLOWING, projection, null, null, null);
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