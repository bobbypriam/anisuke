package com.bobbypriambodo.anisuke;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.*;
import android.widget.AdapterView;
import android.widget.Toast;
import com.bobbypriambodo.anisuke.adapter.AnisukeCursorAdapter;
import com.bobbypriambodo.anisuke.contentprovider.AnisukeContentProvider;
import com.bobbypriambodo.anisuke.database.BucketTable;
import com.bobbypriambodo.anisuke.database.FollowingTable;
import com.bobbypriambodo.anisuke.service.AnisukeIntentService;

/**
 * @author Bobby Priambodo
 */
public class BucketFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private Context mCtx;
	private AnisukeCursorAdapter mAdapter;

	private static final int DELETE_ID = Menu.FIRST + 5;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bucket_list, container, false);
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
		getLoaderManager().initLoader(1, null, this);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete_bucket);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		long id = info.id;
		switch (item.getItemId()) {
			case DELETE_ID:
				deleteEntry(id);
				return true;
		}
		return super.onContextItemSelected(item);
	}

	private void deleteEntry(long id) {
		Intent i = new Intent(mCtx, AnisukeIntentService.class);
		i.putExtra(BucketTable.COL_ID, id);
		i.setAction(AnisukeIntentService.ACTION_DELETE_FROM_BUCKET);
		mCtx.startService(i);

		Toast.makeText(mCtx, "Successfully deleted entry.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		String[] projection = { BucketTable.COL_ID, BucketTable.COL_TITLE, BucketTable.COL_EPISODE };
		return new CursorLoader(mCtx, AnisukeContentProvider.CONTENT_URI_BUCKET, projection, null, null, BucketTable.COL_TITLE + "," + BucketTable.COL_EPISODE);
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