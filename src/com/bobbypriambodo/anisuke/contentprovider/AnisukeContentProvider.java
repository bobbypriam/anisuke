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
import com.bobbypriambodo.anisuke.database.BucketTable;
import com.bobbypriambodo.anisuke.database.DatabaseHelper;
import com.bobbypriambodo.anisuke.database.FollowingTable;

/**
 * @author Bobby Priambodo
 */
public class AnisukeContentProvider extends ContentProvider {

	// Helper constants for UriMatcher.
	private static final int FOLLOWING_LIST	= 1;
	private static final int FOLLOWING_ID	= 2;
	private static final int BUCKET_LIST	= 3;
	private static final int BUCKET_ID		= 4;

	// Authority for this provider.
	private static final String AUTHORITY = "com.bobbypriambodo.anisuke.contentprovider";

	// Paths for ContentURIs.
	private static final String FOLLOWING_PATH = "following";
	private static final String BUCKET_PATH = "bucket";

	// The content provider's ContentURI.
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
	public static final Uri CONTENT_URI_FOLLOWING = Uri.parse("content://" + AUTHORITY + "/" + FOLLOWING_PATH);
	public static final Uri CONTENT_URI_BUCKET = Uri.parse("content://" + AUTHORITY + "/" + BUCKET_PATH);

	// UriMatcher initialization and definitions.
	private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		URI_MATCHER.addURI(AUTHORITY, FOLLOWING_PATH, FOLLOWING_LIST);
		URI_MATCHER.addURI(AUTHORITY, FOLLOWING_PATH + "/#", FOLLOWING_ID);
		URI_MATCHER.addURI(AUTHORITY, BUCKET_PATH, BUCKET_LIST);
		URI_MATCHER.addURI(AUTHORITY, BUCKET_PATH + "/#", BUCKET_ID);
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
			case BUCKET_ID:
				return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.bobbypriambodo.anisuke.anisuke_bucket";
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		String id;
		switch (URI_MATCHER.match(uri)) {
			case FOLLOWING_ID:
				id = uri.getLastPathSegment();
				queryBuilder.appendWhere(FollowingTable.COL_ID + "=" + id);
			case FOLLOWING_LIST:
				queryBuilder.setTables(FollowingTable.TABLE_NAME);
				break;
			case BUCKET_ID:
				id = uri.getLastPathSegment();
				queryBuilder.appendWhere(BucketTable.COL_ID + "=" + id);
			case BUCKET_LIST:
				queryBuilder.setTables(BucketTable.TABLE_NAME);
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
		String tableName;
		switch (URI_MATCHER.match(uri)) {
			case FOLLOWING_LIST:
				tableName = FollowingTable.TABLE_NAME;
				break;
			case BUCKET_LIST:
				tableName = BucketTable.TABLE_NAME;
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		long id = db.insert(tableName, null, values);
		Uri insertUri = ContentUris.withAppendedId(uri, id);
		getContext().getContentResolver().notifyChange(insertUri, null);
		return insertUri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		String id, tableName;
		switch (URI_MATCHER.match(uri)) {
			case FOLLOWING_ID:
				id = uri.getLastPathSegment();
				selection = FollowingTable.COL_ID + "=" + id + (!TextUtils.isEmpty(selection) ? " and (" + selection + ")" : "");
			case FOLLOWING_LIST:
				tableName = FollowingTable.TABLE_NAME;
				break;
			case BUCKET_ID:
				id = uri.getLastPathSegment();
				selection = BucketTable.COL_ID + "=" + id + (!TextUtils.isEmpty(selection) ? " and (" + selection + ")" : "");
				break;
			case BUCKET_LIST:
				tableName = BucketTable.TABLE_NAME;
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		int deleteCount = db.delete(tableName, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return deleteCount;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		String id, tableName;

		switch (URI_MATCHER.match(uri)) {
			case FOLLOWING_ID:
				id = uri.getLastPathSegment();
				selection = FollowingTable.COL_ID + "=" + id + (!TextUtils.isEmpty(selection) ? " and (" + selection + ")" : "");
			case FOLLOWING_LIST:
				tableName = FollowingTable.TABLE_NAME;
				break;
			case BUCKET_ID:
				id = uri.getLastPathSegment();
				selection = BucketTable.COL_ID + "=" + id + (!TextUtils.isEmpty(selection) ? " and (" + selection + ")" : "");
			case BUCKET_LIST:
				tableName = BucketTable.TABLE_NAME;
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		int updateCount = db.update(tableName, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return updateCount;
	}
}
