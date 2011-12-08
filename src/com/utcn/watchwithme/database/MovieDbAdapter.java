package com.utcn.watchwithme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MovieDbAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_DETAILS = "details";
	public static final String KEY_LENGTH = "length";
	public static final String KEY_TRAILERURL = "trailerURL";
	public static final String KEY_IMAGE = "image";
	public static final String KEY_IGNORED = "ignored";
	private static final String DB_TABLE = "movie";

	private Context context;
	private SQLiteDatabase db;
	private MovieDatabaseHelper dbHelper;

	public MovieDbAdapter(Context context) {
		this.context = context;
	}

	public MovieDbAdapter open() throws SQLException {
		dbHelper = new MovieDatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long createMovie(long id, String title, String details, int length,
			String trailerURL, String image, int ignored) {
		ContentValues values = createContentValues(id, title, details, length,
				trailerURL, image, ignored);

		return db.insert(DB_TABLE, null, values);
	}

	public boolean updateMovie(long id, String title, String details,
			int length, String trailerURL, String image, int ignored) {
		ContentValues values = createContentValues(id, title, details, length,
				trailerURL, image, ignored);

		return db.update(DB_TABLE, values, KEY_ROWID + "=" + id, null) > 0;
	}

	public boolean deleteMovie(long id) {
		return db.delete(DB_TABLE, KEY_ROWID + "=" + id, null) > 0;
	}

	public Cursor fetchAllMovies() {
		return db.query(DB_TABLE,
				new String[] { KEY_ROWID, KEY_TITLE, KEY_DETAILS, KEY_LENGTH,
						KEY_TRAILERURL, KEY_IMAGE, KEY_IGNORED }, null, null,
				null, null, null);
	}

	public Cursor fetchMovie(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DB_TABLE, new String[] { KEY_ROWID,
				KEY_TITLE, KEY_DETAILS, KEY_LENGTH, KEY_TRAILERURL, KEY_IMAGE,
				KEY_IGNORED }, KEY_ROWID + "=" + rowId, null, null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(long id, String title,
			String details, int length, String trailerURL, String image,
			int ignored) {
		ContentValues values = new ContentValues();
		values.put(KEY_ROWID, id);
		values.put(KEY_TITLE, title);
		values.put(KEY_DETAILS, details);
		values.put(KEY_LENGTH, length);
		values.put(KEY_TRAILERURL, trailerURL);
		values.put(KEY_IMAGE, image);
		values.put(KEY_IGNORED, ignored);
		return values;
	}
}
