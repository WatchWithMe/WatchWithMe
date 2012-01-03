package com.utcn.watchwithme.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.database.Cursor;
import android.util.Log;

import com.utcn.watchwithme.activities.WatchWithMeActivity;
import com.utcn.watchwithme.objects.Showtime;
import com.utcn.watchwithme.repository.database.ShowtimeDbAdapter;
import com.utcn.watchwithme.services.CinemaService;
import com.utcn.watchwithme.services.MovieService;

public class InternalShowtimeRepository {

	private ShowtimeDbAdapter helper;
	private Set<Showtime> set;
	private static InternalShowtimeRepository instance;

	public static InternalShowtimeRepository getInstance() {
		if (instance == null) {
			instance = new InternalShowtimeRepository();
		}
		return instance;
	}

	private InternalShowtimeRepository() {
		helper = new ShowtimeDbAdapter(WatchWithMeActivity.getInstance());
		helper.open();
		set = new HashSet<Showtime>();

		Cursor cursor = helper.fetchAllShowtimes();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int mid = cursor.getInt(cursor
						.getColumnIndexOrThrow(ShowtimeDbAdapter.KEY_MID));
				int cid = cursor.getInt(cursor
						.getColumnIndexOrThrow(ShowtimeDbAdapter.KEY_CID));
				String st = cursor
						.getString(cursor
								.getColumnIndexOrThrow(ShowtimeDbAdapter.KEY_SHOWTIMES));
				float price = cursor.getFloat(cursor
						.getColumnIndexOrThrow(ShowtimeDbAdapter.KEY_PRICE));
				try {
					Showtime showtime = new Showtime(
							MovieService.getMovie(mid),
							CinemaService.getCinema(cid), price, st);
					set.add(showtime);
				} catch (Exception e) {
					Log.e("InternalShowtimeRepository", "Exception thrown.");
				}
			}
		}
	}

	public ArrayList<Showtime> getForCinema(int id) {
		ArrayList<Showtime> sts = new ArrayList<Showtime>();
		for (Showtime st : set) {
			if (st.getCinema().getId() == id) {
				sts.add(st);
			}
		}
		return sts;
	}

	public ArrayList<Showtime> getForMovie(int id) {
		ArrayList<Showtime> sts = new ArrayList<Showtime>();
		for (Showtime st : set) {
			if (st.getMovie().getId() == id) {
				sts.add(st);
			}
		}
		return sts;
	}

	public int deleteAll() {
		return helper.deleteAll();
	}

	public void addAll(ArrayList<Showtime> sts) {
		for (Showtime st : sts) {
			if (set.add(st)) {
				helper.createShowtime(st);
			}
		}
	}
}
