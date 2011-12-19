package com.utcn.watchwithme.repository;

import java.util.ArrayList;

import com.utcn.watchwithme.activities.WatchWithMeActivity;
import com.utcn.watchwithme.internet.NetworkUtilities;
import com.utcn.watchwithme.internet.WebServices;
import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.parsers.MovieParser;

/**
 * 
 * @author Vlad
 * 
 */
public class RemoteMovieRepository {

	public static ArrayList<Movie> getAllMovies() {
		if (NetworkUtilities.internetConection(WatchWithMeActivity
				.getInstance())) {
			WebServices ws = WebServices.getInstance();
			try {
				MovieParser movieParser = MovieParser.getInstance();
				return movieParser.getAllMovies(ws.getAllMovies());
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	public static Movie getMovie(int id) {
		if (NetworkUtilities.internetConection(WatchWithMeActivity
				.getInstance())) {
			WebServices ws = WebServices.getInstance();
			try {
				MovieParser movieParser = MovieParser.getInstance();
				return movieParser.getMovie(ws.getMovie(id));
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}
}
