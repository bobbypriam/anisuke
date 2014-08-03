package com.bobbypriambodo.anisuke;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.bobbypriambodo.anisuke.contentprovider.AnisukeContentProvider;
import com.bobbypriambodo.anisuke.database.SeriesTable;
import com.bobbypriambodo.anisuke.service.AnisukeIntentService;

/**
 * @author Bobby Priambodo
 */
public class EditSeriesActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
	/*
	 * Menu constant IDs.
	 */
	private static final int DONE_ID = Menu.FIRST;

	private long mSeriesId = -1;
	private int mBucket = 0;

	private EditText mTitle;
	private EditText mEpisode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_series);

		Intent i = getIntent();
		if (i.hasExtra(SeriesTable.COL_ID))
			mSeriesId = i.getLongExtra(SeriesTable.COL_ID, -1);

		Log.d(this.getClass().getName(), "Series ID: " + mSeriesId);

		if (mSeriesId != -1)
			getLoaderManager().initLoader(1, null, this);

		mTitle = (EditText) findViewById(R.id.edit_title);
		mEpisode = (EditText) findViewById(R.id.edit_episode);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Add action items
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actions_edit_series_activity, menu);

		// Add options menu
		menu.add(0, DONE_ID, 0, R.string.menu_done);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			// For done action item and menu
			case R.id.action_done:
			case DONE_ID:
				done();
				return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	public void done() {
		Intent intent = new Intent(this, AnisukeIntentService.class);
		intent.putExtra(SeriesTable.COL_TITLE, mTitle.getText().toString());
		intent.putExtra(SeriesTable.COL_EPISODE, mEpisode.getText().toString());
		intent.putExtra(SeriesTable.COL_BUCKET, mBucket);

		Log.d(this.getClass().getName(), "Series ID: " + mSeriesId);

		if (mSeriesId == -1) {
			Log.d(this.getClass().getName(), "CREATE");
			intent.setAction(AnisukeIntentService.ACTION_CREATE_SERIES);
		} else {
			Log.d(this.getClass().getName(), "UPDATE");
			intent.putExtra(SeriesTable.COL_ID, mSeriesId);
			intent.setAction(AnisukeIntentService.ACTION_UPDATE_SERIES);
		}
		startService(intent);

		Toast.makeText(this, "Series successfully saved.", Toast.LENGTH_SHORT).show();
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		Uri loaderUri = ContentUris.withAppendedId(AnisukeContentProvider.CONTENT_URI_FOLLOWING, mSeriesId);
		return new CursorLoader(this, loaderUri, SeriesTable.PROJECTION_ALL, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		if (cursor != null && cursor.moveToFirst()) {
			mTitle.setText(cursor.getString(cursor.getColumnIndex(SeriesTable.COL_TITLE)));
			mEpisode.setText(cursor.getString(cursor.getColumnIndex(SeriesTable.COL_EPISODE)));
			mBucket = cursor.getInt(cursor.getColumnIndex(SeriesTable.COL_BUCKET));
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}
}