package com.utcn.watchwithme.repository.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "watchwithme";

	private static final int DATABASE_VERSION = 2;

	private static final String CREATE_MOVIES_TABLE = "create table movies "
			+ "(_id integer primary key," + "title text not null,"
			+ "details text not null," + "length integer not null,"
			+ "trailerURL text not null," + "imageURL text not null,"
			+ "rating real not null," + "ignored integer not null);";
	private static final String CREATE_CINEMAS_TABLE = "create table cinemas "
			+ "(_id integer primary key," + "name text not null,"
			+ "address text not null," + "imageURL text not null,"
			+ "latE6 integer not null," + "lngE6 integer not null,"
			+ "favorite integer not null);";
	private static final String CREATE_SHOWTIMES_TABLE = "create table showtimes "
			+ "(_id integer primary key autoincrement,"
			+ "mid integer not null,"
			+ "cid integer not null,"
			+ "showtimes text not null," + "price real not null);";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_MOVIES_TABLE);
		db.execSQL(CREATE_CINEMAS_TABLE);
		db.execSQL(CREATE_SHOWTIMES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS movies");
		db.execSQL("DROP TABLE IF EXISTS cinemas");
		db.execSQL("DROP TABLE IF EXISTS showtimes");
		onCreate(db);
	}
}
