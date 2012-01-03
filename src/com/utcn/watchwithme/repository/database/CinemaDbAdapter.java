package com.utcn.watchwithme.repository.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.utcn.watchwithme.objects.Cinema;

public class CinemaDbAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_IMAGEURL = "imageURL";
	public static final String KEY_LATE6 = "latE6";
	public static final String KEY_LNGE6 = "lngE6";
	public static final String KEY_FAVORITE = "favorite";

	private static final String DB_TABLE = "cinemas";

	private Context context;
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;

	public CinemaDbAdapter(Context context) {
		this.context = context;
	}

	public CinemaDbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long createCinema(Cinema cinema) {
		ContentValues values = createContentValues(cinema.getId(),
				cinema.getName(), cinema.getLocation(), cinema.getImageURL(),
				cinema.getGeoPoint().getLatitudeE6(), cinema.getGeoPoint()
						.getLongitudeE6(), cinema.isFavorite());

		return db.insertWithOnConflict(DB_TABLE, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
	}

	public int updateCinema(Cinema cinema) {
		ContentValues values = createContentValues(cinema.getId(),
				cinema.getName(), cinema.getLocation(), cinema.getImageURL(),
				cinema.getGeoPoint().getLatitudeE6(), cinema.getGeoPoint()
						.getLongitudeE6(), cinema.isFavorite());

		return db.updateWithOnConflict(DB_TABLE, values, KEY_ROWID + "="
				+ cinema.getId(), null, SQLiteDatabase.CONFLICT_IGNORE);
	}

	public boolean deleteCinema(Cinema cinema) {
		return db.delete(DB_TABLE, KEY_ROWID + "=" + cinema.getId(), null) > 0;
	}

	public int deleteAll() {
		try {
			return db.delete(DB_TABLE, null, null);
		} catch (Exception e) {
			return 0;
		}
	}

	public Cursor fetchAllCinemas() {
		return db.query(DB_TABLE,
				new String[] { KEY_ROWID, KEY_NAME, KEY_ADDRESS, KEY_IMAGEURL,
						KEY_LATE6, KEY_LNGE6, KEY_FAVORITE }, null, null, null,
				null, null);
	}

	public Cursor fetchCinema(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DB_TABLE, new String[] { KEY_ROWID,
				KEY_NAME, KEY_ADDRESS, KEY_IMAGEURL, KEY_LATE6, KEY_LNGE6,
				KEY_FAVORITE }, KEY_ROWID + "=" + rowId, null, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(long id, String name,
			String address, String imageURL, int latE6, int lngE6, boolean fav) {
		ContentValues values = new ContentValues();
		values.put(KEY_ROWID, id);
		values.put(KEY_NAME, name);
		values.put(KEY_ADDRESS, address);
		values.put(KEY_IMAGEURL, imageURL);
		values.put(KEY_LATE6, latE6);
		values.put(KEY_LNGE6, lngE6);
		values.put(KEY_FAVORITE, fav ? 1 : 0);
		return values;
	}
}
