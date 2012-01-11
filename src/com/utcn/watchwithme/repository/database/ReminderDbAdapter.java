package com.utcn.watchwithme.repository.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.utcn.watchwithme.objects.Reminder;

public class ReminderDbAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_MID = "mid";
	public static final String KEY_CID = "cid";
	public static final String KEY_DATE = "date";

	private static final String DB_TABLE = "reminders";

	private Context context;
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;

	public ReminderDbAdapter(Context context) {
		this.context = context;
	}

	public ReminderDbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long createReminder(Reminder reminder) {
		ContentValues values = createContentValues(reminder.getMovie().getId(),
				reminder.getCinema().getId(), reminder.getDate());

		return db.insertWithOnConflict(DB_TABLE, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
	}

	public Cursor fetchAllReminders() {
		return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_MID, KEY_CID,
				KEY_DATE }, null, null, null, null, null);
	}

	public int deleteAll() {
		return db.delete(DB_TABLE, null, null);
	}

	private ContentValues createContentValues(int mid, int cid, String date) {
		ContentValues values = new ContentValues();
		values.put(KEY_MID, mid);
		values.put(KEY_CID, cid);
		values.put(KEY_DATE, date);
		return values;
	}
}
