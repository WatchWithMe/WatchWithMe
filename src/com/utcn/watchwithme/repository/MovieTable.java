package com.utcn.watchwithme.repository;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 
 * @author Vlad
 * 
 */
public class MovieTable {

	private static final String DATABASE_CREATE = "create table movie "
			+ "(_id integer primary key," + "title text not null,"
			+ "details text not null," + "length integer not null,"
			+ "trailerURL text not null," + "image text not null,"
			+ "ignored integer not null);";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(MovieTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS movie");
		onCreate(database);
	}
}
