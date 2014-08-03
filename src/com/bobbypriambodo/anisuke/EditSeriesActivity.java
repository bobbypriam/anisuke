package com.bobbypriambodo.anisuke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.bobbypriambodo.anisuke.database.SeriesTable;
import com.bobbypriambodo.anisuke.service.AnisukeIntentService;

/**
 * @author Bobby Priambodo
 */
public class EditSeriesActivity extends Activity {
	/*
	 * Menu constant IDs.
	 */
	private static final int DONE_ID = Menu.FIRST;

	private long mSeriesId = -1;

	private EditText mTitle;
	private EditText mEpisode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_series);

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

		if (mSeriesId == -1) {
			intent.setAction(AnisukeIntentService.ACTION_CREATE_SERIES);
		} else {
			intent.putExtra(SeriesTable.COL_ID, mSeriesId);
			intent.setAction(AnisukeIntentService.ACTION_UPDATE_SERIES);
		}
		startService(intent);

		Toast.makeText(this, "Series successfully saved.", Toast.LENGTH_SHORT).show();
		setResult(RESULT_OK);
		finish();
	}
}