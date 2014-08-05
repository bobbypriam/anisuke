package com.bobbypriambodo.anisuke.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.widget.TextView;
import com.bobbypriambodo.anisuke.R;
import com.bobbypriambodo.anisuke.database.FollowingTable;

/**
 * @author Bobby Priambodo
 */
public class AnisukeCursorAdapter extends ResourceCursorAdapter {

	public AnisukeCursorAdapter(Context context, int layout, Cursor c, int flags) {
		super(context, layout, c, flags);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		String title = cursor.getString(cursor.getColumnIndex(FollowingTable.COL_TITLE));
		String description = "Episode " + cursor.getString(cursor.getColumnIndex(FollowingTable.COL_EPISODE));
		TextView titleView = (TextView) view.findViewById(R.id.title);
		TextView descriptionView = (TextView) view.findViewById(R.id.description);
		titleView.setText(title);
		descriptionView.setText(description);
	}
}
