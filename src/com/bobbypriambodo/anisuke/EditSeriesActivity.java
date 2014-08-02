package com.bobbypriambodo.anisuke;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
		super.onCreateOptionsMenu(menu);
		menu.add(0, DONE_ID, 0, R.string.menu_done);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case DONE_ID:
				setResult(RESULT_OK);
				finish();
				return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
}