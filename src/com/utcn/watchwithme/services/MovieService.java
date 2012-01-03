package com.utcn.watchwithme.services;

import java.util.ArrayList;

import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.repository.InternalMovieRepository;
import com.utcn.watchwithme.repository.RemoteMovieRepository;

/**
 * 
 * @author Vlad
 * 
 */
public class MovieService {

	private static ArrayList<Movie> movieList = new ArrayList<Movie>();
	private static Movie selected;
	private static boolean flag;

	private static InternalMovieRepository internalMovieRepository = InternalMovieRepository
			.getInstance();

	public static void eraseData() {
		movieList.clear();
	}

	public static boolean updated() {
		return flag;
	}

	public static Movie getMovie(int id) {
		for (Movie m : movieList) {
			if (m.getId() == id) {
				return m;
			}
		}
		Movie m = RemoteMovieRepository.getMovie(id);
		if (m != null) {
			internalMovieRepository.saveMovie(m);
			return m;
		}
		return internalMovieRepository.getMovie(id);
	}

	public static ArrayList<Movie> getAllMovies() {
		if (movieList.size() == 0 || flag == false) {
			movieList = RemoteMovieRepository.getAllMovies();
			if (movieList == null) {
				movieList = internalMovieRepository.getAllMovies();
				flag = false;
			} else {
				internalMovieRepository.saveAllMovies(movieList);
				flag = true;
			}
			for (int i = 0; i < movieList.size(); i++)
				for (int j = i + 1; j < movieList.size(); j++) {
					Movie mi = movieList.get(i);
					Movie mj = movieList.get(j);
					if (mi.getTitle().compareToIgnoreCase(mj.getTitle()) > 0) {
						movieList.set(i, mj);
						movieList.set(j, mi);
					}
				}
		}

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
		for (Movie movie : movieList) {
			if (movie.getId() == id) {
				movie.setIgnored(true);
				internalMovieRepository.updateMovie(movie);
			}
		}
	}

	public static void unignoreMovies() {
		for (Movie movie : movieList) {
			if (movie.isIgnored()) {
				movie.setIgnored(false);
				internalMovieRepository.updateMovie(movie);
			}
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
}
