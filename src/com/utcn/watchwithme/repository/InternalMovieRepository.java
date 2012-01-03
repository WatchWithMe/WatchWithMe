package com.utcn.watchwithme.repository;

import java.util.ArrayList;

import android.database.Cursor;

import com.utcn.watchwithme.activities.WatchWithMeActivity;
import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.repository.database.MovieDbAdapter;

public class InternalMovieRepository {

	private MovieDbAdapter helper;
	private static InternalMovieRepository instance;

	public static InternalMovieRepository getInstance() {
		if (instance == null) {
			instance = new InternalMovieRepository();
		}
		return instance;
	}

	private InternalMovieRepository() {
		helper = new MovieDbAdapter(WatchWithMeActivity.getInstance());
		helper.open();
	}

	public ArrayList<Movie> getAllMovies() {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		Cursor cursor = helper.fetchAllMovies();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor
						.getColumnIndexOrThrow(MovieDbAdapter.KEY_ROWID));
				String title = cursor.getString(cursor
						.getColumnIndexOrThrow(MovieDbAdapter.KEY_TITLE));
				String details = cursor.getString(cursor
						.getColumnIndexOrThrow(MovieDbAdapter.KEY_DETAILS));
				int length = cursor.getInt(cursor
						.getColumnIndexOrThrow(MovieDbAdapter.KEY_LENGTH));
				String trailerURL = cursor.getString(cursor
						.getColumnIndexOrThrow(MovieDbAdapter.KEY_TRAILERURL));
				String imageURL = cursor.getString(cursor
						.getColumnIndexOrThrow(MovieDbAdapter.KEY_IMAGEURL));
				float rating = cursor.getFloat(cursor
						.getColumnIndexOrThrow(MovieDbAdapter.KEY_RATING));
				int ignored = cursor.getInt(cursor
						.getColumnIndexOrThrow(MovieDbAdapter.KEY_IGNORED));
				Movie m = new Movie(id, title, details, length, trailerURL,
						imageURL, rating);
				if (ignored != 0) {
					m.setIgnored(true);
				}
				movies.add(m);
			}
		}
		return movies;
	}

	public Movie getMovie(int id) {
		Cursor cursor = helper.fetchMovie(id);

		String title = cursor.getString(cursor
				.getColumnIndexOrThrow(MovieDbAdapter.KEY_TITLE));
		String details = cursor.getString(cursor
				.getColumnIndexOrThrow(MovieDbAdapter.KEY_DETAILS));
		int length = cursor.getInt(cursor
				.getColumnIndexOrThrow(MovieDbAdapter.KEY_LENGTH));
		String trailerURL = cursor.getString(cursor
				.getColumnIndexOrThrow(MovieDbAdapter.KEY_TRAILERURL));
		String imageURL = cursor.getString(cursor
				.getColumnIndexOrThrow(MovieDbAdapter.KEY_IMAGEURL));
		float rating = cursor.getFloat(cursor
				.getColumnIndexOrThrow(MovieDbAdapter.KEY_RATING));
		int ignored = cursor.getInt(cursor
				.getColumnIndexOrThrow(MovieDbAdapter.KEY_IGNORED));
		Movie movie = new Movie(id, title, details, length, trailerURL,
				imageURL, rating);
		if (ignored != 0) {
			movie.setIgnored(true);
		}
		return movie;
	}

	public long saveMovie(Movie movie) {
		return helper.createMovie(movie);
	}

	public long saveAllMovies(ArrayList<Movie> movies) {
		long nr = 0;
		helper.deleteAll();
		for (Movie movie : movies) {
			if (saveMovie(movie) == movie.getId()) {
				nr++;
			}
		}
		return nr;
	}

	public int updateMovie(Movie movie) {
		return helper.updateMovie(movie);
	}

	public boolean deleteMovie(Movie movie) {
		return helper.deleteMovie(movie);
	}
}
