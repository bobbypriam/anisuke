package com.bobbypriambodo.anisuke.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.bobbypriambodo.anisuke.database.DatabaseHelper;
import com.bobbypriambodo.anisuke.database.SeriesTable;

/**
 * @author Bobby Priambodo
 */
public class AnisukeContentProvider extends ContentProvider {

	// Helper constants for UriMatcher.
	private static final int FOLLOWING_LIST	= 1;
	private static final int FOLLOWING_ID	= 2;
	private static final int BUCKET_LIST	= 3;

	// Authority for this provider.
	private static final String AUTHORITY = "com.bobbypriambodo.anisuke.contentprovider";

	// Paths for ContentURIs.
	private static final String FOLLOWING_PATH = "following";
	private static final String BUCKET_PATH = "bucket";

	// The content provider's ContentURI.
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

	// UriMatcher initialization and definitions.
	private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		URI_MATCHER.addURI(AUTHORITY, FOLLOWING_PATH, FOLLOWING_LIST);
		URI_MATCHER.addURI(AUTHORITY, FOLLOWING_PATH + "/#", FOLLOWING_ID);
		URI_MATCHER.addURI(AUTHORITY, BUCKET_PATH, BUCKET_LIST);
	}

	private DatabaseHelper mDbHelper;

	@Override
	public boolean onCreate() {
		mDbHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		switch (URI_MATCHER.match(uri)) {
			case FOLLOWING_LIST:
				return ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.bobbypriambodo.anisuke.anisuke_following";
			case FOLLOWING_ID:
				return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.bobbypriambodo.anisuke.anisuke_following";
			case BUCKET_LIST:
				return ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.bobbypriambodo.anisuke.anisuke_bucket";
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(SeriesTable.TABLE_NAME);

		switch (URI_MATCHER.match(uri)) {
			case FOLLOWING_LIST:
				break;
			case BUCKET_LIST:
				queryBuilder.appendWhere(SeriesTable.COL_BUCKET + "=" + 1);
				break;
			case FOLLOWING_ID:
				String id = uri.getLastPathSegment();
				queryBuilder.appendWhere(SeriesTable.COL_ID + "=" + id);
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		switch (URI_MATCHER.match(uri)) {
			case FOLLOWING_LIST:
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		long id = db.insert(SeriesTable.TABLE_NAME, null, values);
		Uri insertUri = ContentUris.withAppendedId(uri, id);
		getContext().getContentResolver().notifyChange(insertUri, null);
		return insertUri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		switch (URI_MATCHER.match(uri)) {
			case FOLLOWING_LIST:
				break;
			case FOLLOWING_ID:
				String id = uri.getLastPathSegment();
				selection = SeriesTable.COL_ID + "=" + id + (TextUtils.isEmpty(selection) ? " and (" + selection + ")" : "");
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		int deleteCount = db.delete(SeriesTable.TABLE_NAME, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return deleteCount;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		switch (URI_MATCHER.match(uri)) {
			case FOLLOWING_LIST:
				break;
			case FOLLOWING_ID:
				String id = uri.getLastPathSegment();
				selection = SeriesTable.COL_ID + "=" + id + (TextUtils.isEmpty(selection) ? " and (" + selection + ")" : "");
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		int updateCount = db.update(SeriesTable.TABLE_NAME, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return updateCount;
	}
}
