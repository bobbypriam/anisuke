package com.bobbypriambodo.anisuke.database;

/**
 * @author Bobby Priambodo
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	/** Database definitions */
	private static final String DATABASE_NAME = "anisuke.db";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		FollowingTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		FollowingTable.onUpgrade(database, oldVersion, newVersion);
	}
}
