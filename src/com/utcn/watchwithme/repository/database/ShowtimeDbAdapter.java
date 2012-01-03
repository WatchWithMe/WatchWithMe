package com.utcn.watchwithme.repository.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.utcn.watchwithme.objects.Showtime;

public class ShowtimeDbAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_MID = "mid";
	public static final String KEY_CID = "cid";
	public static final String KEY_SHOWTIMES = "showtimes";
	public static final String KEY_PRICE = "price";

	private static final String DB_TABLE = "showtimes";

	private Context context;
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;

	public ShowtimeDbAdapter(Context context) {
		this.context = context;
	}

	public ShowtimeDbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long createShowtime(Showtime showtime) {
		ContentValues values = createContentValues(showtime.getMovie().getId(),
				showtime.getCinema().getId(), showtime.getSt(),
				(float) showtime.getPrice());

		return db.insertWithOnConflict(DB_TABLE, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
	}

	public Cursor fetchAllShowtimes() {
		return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_MID, KEY_CID,
				KEY_SHOWTIMES, KEY_PRICE }, null, null, null, null, null);
	}

	public int deleteAll() {
		return db.delete(DB_TABLE, null, null);
	}

	private ContentValues createContentValues(int mid, int cid,
			String showtimes, float price) {
		ContentValues values = new ContentValues();
		values.put(KEY_MID, mid);
		values.put(KEY_CID, cid);
		values.put(KEY_SHOWTIMES, showtimes);
		values.put(KEY_PRICE, price);
		return values;
	}
}
