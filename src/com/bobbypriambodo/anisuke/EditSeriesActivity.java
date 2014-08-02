package com.bobbypriambodo.anisuke;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * @author Bobby Priambodo
 */
public class EditSeriesActivity extends Activity {
	/*
	 * Menu constant IDs.
	 */
	private static final int DONE_ID = Menu.FIRST;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_series);
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
		Toast.makeText(this, "Series successfully saved.", Toast.LENGTH_SHORT).show();
		setResult(RESULT_OK);
		finish();
	}
}