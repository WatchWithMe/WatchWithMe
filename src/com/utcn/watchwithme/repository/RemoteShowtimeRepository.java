package com.utcn.watchwithme.repository;

import java.util.ArrayList;

import com.utcn.watchwithme.activities.WatchWithMeActivity;
import com.utcn.watchwithme.internet.NetworkUtilities;
import com.utcn.watchwithme.internet.WebServices;
import com.utcn.watchwithme.objects.Showtime;
import com.utcn.watchwithme.parsers.ShowtimeParser;

/**
 * 
 * @author Vlad
 * 
 */
public class RemoteShowtimeRepository {

	public static ArrayList<Showtime> getForCinema(int id) {
		if (NetworkUtilities.internetConection(WatchWithMeActivity
				.getInstance())) {
			WebServices ws = WebServices.getInstance();
			try {
				ShowtimeParser stParser = ShowtimeParser.getInstance();
				return stParser.getShowtimes(ws.getMoviesForCinemas(id));
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	public static ArrayList<Showtime> getForMovie(int id) {
		if (NetworkUtilities.internetConection(WatchWithMeActivity
				.getInstance())) {
			WebServices ws = WebServices.getInstance();
			try {
				ShowtimeParser stParser = ShowtimeParser.getInstance();
				return stParser.getShowtimes(ws.getCinemasForMovie(id));
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}
}
