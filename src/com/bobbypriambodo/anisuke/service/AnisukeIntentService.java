package com.bobbypriambodo.anisuke.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.bobbypriambodo.anisuke.contentprovider.AnisukeContentProvider;
import com.bobbypriambodo.anisuke.database.SeriesTable;

/**
 * @author Bobby Priambodo
 */
public class AnisukeIntentService extends IntentService {

	// The actions for this service.
	public static final String ACTION_CREATE_SERIES = "actionCreateSeries";
	public static final String ACTION_DELETE_SERIES = "actionDeleteSeries";
	public static final String ACTION_UPDATE_SERIES = "actionUpdateSeries";

	public AnisukeIntentService() {
		super("AnisukeIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		final String action = intent.getAction();
		// note: the "==" comparison only works
		// if you're inside the same process!
		if (ACTION_CREATE_SERIES == action) {
			onActionCreateSeries(intent);
		} else if (ACTION_DELETE_SERIES == action) {
			onActionDeleteSeries(intent);
		} else if (ACTION_UPDATE_SERIES == action) {
			onActionUpdateSeries(intent);
		}
	}

	private void onActionCreateSeries(Intent intent) {
		String title = intent.getStringExtra(SeriesTable.COL_TITLE);
		String episode = intent.getStringExtra(SeriesTable.COL_EPISODE);
		int bucket = intent.getIntExtra(SeriesTable.COL_BUCKET, 0);

		if (TextUtils.isEmpty(title) && TextUtils.isEmpty(episode))
			return;

		ContentValues values = new ContentValues();
		values.put(SeriesTable.COL_TITLE, title);
		values.put(SeriesTable.COL_EPISODE, episode);
		values.put(SeriesTable.COL_BUCKET, bucket);

		ContentResolver resolver = getContentResolver();
		resolver.insert(AnisukeContentProvider.CONTENT_URI_FOLLOWING, values);
	}

	private void onActionDeleteSeries(Intent intent) {

	}

	private void onActionUpdateSeries(Intent intent) {
		long seriesId = intent.getLongExtra(SeriesTable.COL_ID, -1);
		if (seriesId == -1)
			throw new IllegalStateException("Cannot update record with seriesId == -1");

		String title = intent.getStringExtra(SeriesTable.COL_TITLE);
		String episode = intent.getStringExtra(SeriesTable.COL_EPISODE);
		int bucket = intent.getIntExtra(SeriesTable.COL_BUCKET, 0);

		ContentValues values = new ContentValues();
		values.put(SeriesTable.COL_TITLE, title);
		values.put(SeriesTable.COL_EPISODE, episode);
		values.put(SeriesTable.COL_BUCKET, bucket);

		ContentResolver resolver = getContentResolver();
		Uri updateUri = ContentUris.withAppendedId(AnisukeContentProvider.CONTENT_URI_FOLLOWING, seriesId);
		int updateCount = resolver.update(updateUri, values, null, null);
		Log.d(this.getClass().getName(), "Updated " + updateCount + " rows");
	}
}