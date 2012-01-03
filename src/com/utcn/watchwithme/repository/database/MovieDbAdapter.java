package com.utcn.watchwithme.repository.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.utcn.watchwithme.objects.Movie;

/**
 * 
 * @author Vlad
 * 
 */
public class MovieDbAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_DETAILS = "details";
	public static final String KEY_LENGTH = "length";
	public static final String KEY_TRAILERURL = "trailerURL";
	public static final String KEY_IMAGEURL = "imageURL";
	public static final String KEY_RATING = "rating";
	public static final String KEY_IGNORED = "ignored";

	private static final String DB_TABLE = "movies";

	private Context context;
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;

	public MovieDbAdapter(Context context) {
		this.context = context;
	}

	public MovieDbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long createMovie(Movie movie) {
		ContentValues values = createContentValues(movie.getId(),
				movie.getTitle(), movie.getDetails(), movie.getLength(),
				movie.getTrailerURL(), movie.getImageURL(), movie.getRating(),
				movie.isIgnored() == true ? 1 : 0);

		return db.insertWithOnConflict(DB_TABLE, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
	}

	public int updateMovie(Movie movie) {
		ContentValues values = createContentValues(movie.getId(),
				movie.getTitle(), movie.getDetails(), movie.getLength(),
				movie.getTrailerURL(), movie.getImageURL(), movie.getRating(),
				movie.isIgnored() == true ? 1 : 0);

		return db.updateWithOnConflict(DB_TABLE, values, KEY_ROWID + "="
				+ movie.getId(), null, SQLiteDatabase.CONFLICT_IGNORE);
	}

	public boolean deleteMovie(Movie movie) {
		return db.delete(DB_TABLE, KEY_ROWID + "=" + movie.getId(), null) > 0;
	}

	public int deleteAll() {
		return db.delete(DB_TABLE, null, null);
	}

	public Cursor fetchAllMovies() {
		return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_TITLE,
				KEY_DETAILS, KEY_LENGTH, KEY_TRAILERURL, KEY_IMAGEURL,
				KEY_RATING, KEY_IGNORED }, null, null, null, null, null);
	}

	public Cursor fetchMovie(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DB_TABLE, new String[] { KEY_ROWID,
				KEY_TITLE, KEY_DETAILS, KEY_LENGTH, KEY_TRAILERURL,
				KEY_IMAGEURL, KEY_RATING, KEY_IGNORED }, KEY_ROWID + "="
				+ rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(long id, String title,
			String details, int length, String trailerURL, String imageURL,
			double rating, int ignored) {
		ContentValues values = new ContentValues();
		values.put(KEY_ROWID, id);
		values.put(KEY_TITLE, title);
		values.put(KEY_DETAILS, details);
		values.put(KEY_LENGTH, length);
		values.put(KEY_TRAILERURL, trailerURL);
		values.put(KEY_IMAGEURL, imageURL);
		values.put(KEY_RATING, rating);
		values.put(KEY_IGNORED, ignored);
		return values;
	}
}
