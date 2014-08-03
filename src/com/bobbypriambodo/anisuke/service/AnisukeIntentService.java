package com.bobbypriambodo.anisuke.service;

import android.app.IntentService;
import android.content.Intent;

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

	}

	private void onActionDeleteSeries(Intent intent) {

	}

	private void onActionUpdateSeries(Intent intent) {
		
	}
}
