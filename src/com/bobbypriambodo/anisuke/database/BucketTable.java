package com.bobbypriambodo.anisuke.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author Bobby Priambodo
 */
public class BucketTable {

	/** Table definitions */
	public static final String TABLE_NAME		= "bucket";
	public static final String COL_ID			= "_id";
	public static final String COL_TITLE		= "title";
	public static final String COL_EPISODE		= "episode";

	public static final String[] PROJECTION_ALL = new String[] { COL_ID, COL_TITLE, COL_EPISODE, };

	/** Database creation statement */
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME
			+ "( "
			+ COL_ID		+ " integer primary key autoincrement,"
			+ COL_TITLE		+ " text not null,"
			+ COL_EPISODE	+ " integer not null"
			+ " );";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(FollowingTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data.");
		database.execSQL("drop table if exists " + TABLE_NAME);
		onCreate(database);
	}
}
