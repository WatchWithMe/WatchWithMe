package com.utcn.watchwithme.repository;

import java.util.ArrayList;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.objects.Movie;

/**
 * 
 * @author Vlad
 * 
 */
public class StaticMovieRepository {

	private static ArrayList<Movie> movieList = new ArrayList<Movie>();

	public static ArrayList<Movie> getAllMovies() {
		return movieList;
	}

	public static Movie getMovie(int id) {
		for (Movie m : movieList) {
			if (m.getId() == id) {
				return m;
			}
		}
		return null;
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
