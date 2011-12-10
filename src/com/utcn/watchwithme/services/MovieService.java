package com.utcn.watchwithme.services;

import java.util.ArrayList;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.objects.Movie;

public class MovieService {

	private static ArrayList<Movie> movieList = new ArrayList<Movie>();
	private static Movie selected;

	public static Movie getMovie(int id) {
		// start loading if movie not available in the list
		for (Movie m : movieList) {
			if (m.getId() == id) {
				return m;
			}
		}
		return null;
	}

	public static Movie getMovieDetails(int id) {
		// start loading if movie not available in the list
		for (Movie m : movieList) {
			if (m.getId() == id) {
				return m;
			}
		}
		return null;
	}

	public static ArrayList<Movie> getAllMovies() {
		// start loading if movie not in the list
		ArrayList<Movie> movies = new ArrayList<Movie>();
		for (Movie m : movieList) {
			if (m.isIgnored() == false) {
				movies.add(m);
			}
		}
		return movies;
	}

	public static void setSelected(Movie movie) {
		selected = movie;
	}

	public static Movie getSelected() {
		return selected;
	}

	public static void ignoreMovie(int id) {
		for (Movie m : movieList) {
			if (m.getId() == id) {
				m.setIgnored(true);
			}
		}
	}

	public static void unignoreMovies() {
		for (Movie m : movieList) {
			m.setIgnored(false);
		}
	}

	public static ArrayList<Movie> searchForMovie(String s) {
		if (s == null) {
			return getAllMovies();
		} else {
			s = s.trim().toLowerCase();
			ArrayList<Movie> result = new ArrayList<Movie>();
			for (Movie m : movieList) {
				if ((m.isIgnored() == false)
						&& (m.getTitle().toLowerCase().contains(s))) {
					result.add(m);
				}
			}
			return result;
		}
	}

	static {
		movieList.add(new Movie(
				"The Twilight Saga: Breaking Dawn - Part 1 (2011)",
				R.drawable.movie_twilight,
				"http://www.youtube.com/watch?v=sIpeBi6SG4A"));
		movieList.add(new Movie("Immortals (2011)", R.drawable.movie_imortals));
		movieList.add(new Movie("In Time (2011)", R.drawable.movie_in_time));
		movieList.add(new Movie("Cars 2 (2011)", R.drawable.movie_cars_2));
		movieList.add(new Movie("A Dangerous Method (2011)",
				R.drawable.movie_dangerous_method));
		movieList.add(new Movie("Arthur Christmas (2011)",
				R.drawable.movie_arthur_christmas));
		movieList
				.add(new Movie("The Thing (2011)", R.drawable.movie_the_thing));
		movieList
				.add(new Movie("Anonymous (2011)", R.drawable.movie_anonymous));
		movieList.add(new Movie("Margin Call (2011)",
				R.drawable.movie_margin_call));
		movieList.add(new Movie("Puss in Boots (2011)",
				R.drawable.movie_puss_in_boots));
		movieList.add(new Movie("Amador (2010)", R.drawable.movie_amador,
				"http://www.youtube.com/watch?v=1TqWIkr5HSI"));
		movieList.add(new Movie("Liceenii, în 53 de ore si ceva (2010)",
				R.drawable.movie_liceenii));
		movieList.add(new Movie("Admiral (2008)", R.drawable.movie_amiral));
		movieList.add(new Movie("Kandagar (2010)", R.drawable.movie_kandagar));

		// sort movies by name
		for (int i = 0; i < movieList.size(); i++) {
			for (int j = i + 1; j < movieList.size(); j++) {
				Movie mi = movieList.get(i);
				Movie mj = movieList.get(j);
				if (mi.getTitle().compareToIgnoreCase(mj.getTitle()) > 0) {
					movieList.set(i, mj);
					movieList.set(j, mi);
				}
			}
		}
	}
}
