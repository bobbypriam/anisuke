package com.bobbypriambodo.anisuke;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.bobbypriambodo.anisuke.adapter.AnisukeCursorAdapter;
import com.bobbypriambodo.anisuke.contentprovider.AnisukeContentProvider;
import com.bobbypriambodo.anisuke.database.SeriesTable;
import com.bobbypriambodo.anisuke.service.AnisukeIntentService;

/**
 * @author Bobby Priambodo
 */
public class FollowingFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final int ACTIVITY_EDIT = 1;

	private static final int BUCKET_ID = Menu.FIRST + 2;
	private static final int EDIT_ID = Menu.FIRST + 3;
	private static final int DELETE_ID = Menu.FIRST + 4;

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
		registerForContextMenu(getListView());
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

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, BUCKET_ID, 0, R.string.menu_bucket);
		menu.add(0, EDIT_ID, 1, R.string.menu_edit);
		menu.add(0, DELETE_ID, 2, R.string.menu_delete);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		long id = info.id;
		switch (item.getItemId()) {
			case BUCKET_ID:
				// addToBucket(id);
				return true;
			case EDIT_ID:
				editSeries(id);
				return true;
			case DELETE_ID:
				deleteSeries(id);
				return true;
		}
		return super.onContextItemSelected(item);
	}

	private void editSeries(long id) {
		Intent i = new Intent(mCtx, EditSeriesActivity.class);
		i.putExtra(SeriesTable.COL_ID, id);
		startActivityForResult(i, ACTIVITY_EDIT);
	}

	private void deleteSeries(long id) {
		Intent i = new Intent(mCtx, AnisukeIntentService.class);
		i.putExtra(SeriesTable.COL_ID, id);
		i.setAction(AnisukeIntentService.ACTION_DELETE_SERIES);
		mCtx.startService(i);

		Toast.makeText(mCtx, "Successfully deleted series.", Toast.LENGTH_SHORT).show();
	}

	@Override
	 public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		String[] projection = { SeriesTable.COL_ID, SeriesTable.COL_TITLE, SeriesTable.COL_EPISODE };
		return new CursorLoader(mCtx, AnisukeContentProvider.CONTENT_URI_FOLLOWING, projection, null, null, SeriesTable.COL_TITLE);
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