package com.utcn.watchwithme.repository;

import java.util.ArrayList;

import com.utcn.watchwithme.activities.WatchWithMeActivity;
import com.utcn.watchwithme.internet.NetworkUtilities;
import com.utcn.watchwithme.internet.WebServices;
import com.utcn.watchwithme.objects.Cinema;
import com.utcn.watchwithme.parsers.CinemaParser;

/**
 * 
 * @author Vlad
 * 
 */
public class RemoteCinemaRepository {

	public static ArrayList<Cinema> getAllCinemas() {
		if (NetworkUtilities.internetConection(WatchWithMeActivity
				.getInstance())) {
			WebServices ws = WebServices.getInstance();
			try {
				CinemaParser cinemaParser = CinemaParser.getInstance();
				return cinemaParser.getAllCinemas(ws.getAllCinemas());
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	public static Cinema getCinema(int id) {
		if (NetworkUtilities.internetConection(WatchWithMeActivity
				.getInstance())) {
			WebServices ws = WebServices.getInstance();
			try {
				CinemaParser cinemaParser = CinemaParser.getInstance();
				return cinemaParser.getCinema(ws.getCinema(id));
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}
}
