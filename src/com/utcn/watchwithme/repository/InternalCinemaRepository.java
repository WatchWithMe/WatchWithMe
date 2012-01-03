package com.utcn.watchwithme.repository;

import java.util.ArrayList;

import android.database.Cursor;

import com.utcn.watchwithme.activities.WatchWithMeActivity;
import com.utcn.watchwithme.objects.Cinema;
import com.utcn.watchwithme.repository.database.CinemaDbAdapter;

public class InternalCinemaRepository {

	private CinemaDbAdapter helper;
	private static InternalCinemaRepository instance;

	public static InternalCinemaRepository getInstance() {
		if (instance == null) {
			instance = new InternalCinemaRepository();
		}
		return instance;
	}

	private InternalCinemaRepository() {
		helper = new CinemaDbAdapter(WatchWithMeActivity.getInstance());
		helper.open();
	}

	public ArrayList<Cinema> getAllCinemas() {
		ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
		Cursor cursor = helper.fetchAllCinemas();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor
						.getColumnIndexOrThrow(CinemaDbAdapter.KEY_ROWID));
				String name = cursor.getString(cursor
						.getColumnIndexOrThrow(CinemaDbAdapter.KEY_NAME));
				String address = cursor.getString(cursor
						.getColumnIndexOrThrow(CinemaDbAdapter.KEY_ADDRESS));
				String imageURL = cursor.getString(cursor
						.getColumnIndexOrThrow(CinemaDbAdapter.KEY_IMAGEURL));
				int latE6 = cursor.getInt(cursor
						.getColumnIndexOrThrow(CinemaDbAdapter.KEY_LATE6));
				int lngE6 = cursor.getInt(cursor
						.getColumnIndexOrThrow(CinemaDbAdapter.KEY_LNGE6));
				int fav = cursor.getInt(cursor
						.getColumnIndexOrThrow(CinemaDbAdapter.KEY_FAVORITE));
				Cinema c = new Cinema(id, name, address, imageURL, latE6, lngE6);
				c.setFavorite(fav == 1);
				cinemas.add(c);
			}
		}
		return cinemas;
	}

	public Cinema getCinema(int id) {
		Cursor cursor = helper.fetchCinema(id);

		String name = cursor.getString(cursor
				.getColumnIndexOrThrow(CinemaDbAdapter.KEY_NAME));
		String address = cursor.getString(cursor
				.getColumnIndexOrThrow(CinemaDbAdapter.KEY_ADDRESS));
		String imageURL = cursor.getString(cursor
				.getColumnIndexOrThrow(CinemaDbAdapter.KEY_IMAGEURL));
		int latE6 = cursor.getInt(cursor
				.getColumnIndexOrThrow(CinemaDbAdapter.KEY_LATE6));
		int lngE6 = cursor.getInt(cursor
				.getColumnIndexOrThrow(CinemaDbAdapter.KEY_LNGE6));
		int fav = cursor.getInt(cursor
				.getColumnIndexOrThrow(CinemaDbAdapter.KEY_FAVORITE));
		Cinema c = new Cinema(id, name, address, imageURL, latE6, lngE6);
		c.setFavorite(fav == 1);
		return c;
	}

	public long saveCinema(Cinema cinema) {
		return helper.createCinema(cinema);
	}

	public long saveAllCinemas(ArrayList<Cinema> cinemas) {
		long nr = 0;
		for (Cinema cinema : cinemas) {
			try {
				Cinema c = getCinema(cinema.getId());
				cinema.setFavorite(c.isFavorite());
			} catch (Exception e) {
			}
		}
		helper.deleteAll();
		for (Cinema cinema : cinemas) {
			if (saveCinema(cinema) == cinema.getId()) {
				nr++;
			}
		}
		return nr;
	}

	public int updateCinema(Cinema cinema) {
		return helper.updateCinema(cinema);
	}

	public boolean deleteCinema(Cinema cinema) {
		return helper.deleteCinema(cinema);
	}
}
