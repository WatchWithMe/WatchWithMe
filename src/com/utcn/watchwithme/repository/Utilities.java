package com.utcn.watchwithme.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.objects.Cinema;
import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.objects.Reminder;
import com.utcn.watchwithme.objects.Showtime;

public class Utilities {

	private static ArrayList<Cinema> mCinemasList = new ArrayList<Cinema>();
	private static ArrayList<Cinema> mCinemasListFav = new ArrayList<Cinema>();
	private static ArrayList<Movie> mMoviesList = new ArrayList<Movie>();
	private static ArrayList<Showtime> mShowtimesList = new ArrayList<Showtime>();
	private static ArrayList<Reminder> mRemindersList = new ArrayList<Reminder>();

	private static Cinema selectedCinema;
	private static Movie selectedMovie;

	public static ArrayList<Showtime> getShowtimesForCinema(Cinema cinema) {
		ArrayList<Showtime> list = new ArrayList<Showtime>();
		for (Showtime st : mShowtimesList) {
			if (st.getCinema().equals(cinema) && st.getMovie().isIgnored() == false) {
				list.add(st);
			}
		}
		return list;
	}

	public static void changeCinemaFavoriteStatus(int index, int all) {
		Cinema cinema;
		if (all == 1) {
			cinema = mCinemasList.get(index);
		} else {
			cinema = mCinemasListFav.get(index);
		}
		cinema.setFavorite(!cinema.isFavorite());
		mCinemasListFav.clear();
		for (Cinema c : mCinemasList) {
			if (c.isFavorite()) {
				mCinemasListFav.add(c);
			}
		}
	}

	public static void ignorShowtime(Showtime st) {
		mShowtimesList.remove(st);
	}

	public static void ignoreMovie(Movie m) {
		m.setIgnored(true);
	}

	public static void unignoreMovies() {
		for (Movie m : mMoviesList) {
			m.setIgnored(false);
		}
	}

	public static void addReminder(Reminder reminder) {
		mRemindersList.add(reminder);
	}

	public static ArrayList<Cinema> getAllCinemas() {
		return mCinemasList;
	}

	public static ArrayList<Cinema> getFavoriteCinemas() {
		return mCinemasListFav;
	}

	public static Cinema getSelectedCinema() {
		return selectedCinema;
	}

	public static void setSelectedCinema(Cinema selectedCinema) {
		Utilities.selectedCinema = selectedCinema;
	}

	public static Movie getSelectedMovie() {
		return selectedMovie;
	}

	public static void setSelectedMovie(Movie selectedMovie) {
		Utilities.selectedMovie = selectedMovie;
	}

	public static ArrayList<Movie> getAllMovies() {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		for (Movie m : mMoviesList) {
			if (m.isIgnored() == false) {
				movies.add(m);
			}
		}
		return movies;
	}

	public static ArrayList<Movie> searchForMovie(String s) {
		if (s == null) {
			return getAllMovies();
		} else {
			s = s.trim().toLowerCase();
			ArrayList<Movie> result = new ArrayList<Movie>();
			for (Movie m : mMoviesList) {
				if ((m.isIgnored() == false) && (m.getTitle().toLowerCase().contains(s))) {
					result.add(m);
				}
			}
			return result;
		}
	}

	public static List<Movie> findMoviesByTitle(String title) {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		for (Movie m : mMoviesList) {
			if (m.getTitle().contains(title)) {
				movies.add(m);
			}
		}
		return movies;
	}

	public static void sortShowtimesByMovieTitle() {
		for (int i = 0; i < mShowtimesList.size(); i++) {
			for (int j = i + 1; j < mShowtimesList.size(); j++) {
				Showtime si = mShowtimesList.get(i);
				Showtime sj = mShowtimesList.get(j);
				if (si.getMovie().getTitle().compareToIgnoreCase(sj.getMovie().getTitle()) > 0) {
					mShowtimesList.set(i, sj);
					mShowtimesList.set(j, si);
				}
			}
		}
	}

	public static void sortShowtimesByMoviePrice() {
		for (int i = 0; i < mShowtimesList.size(); i++) {
			for (int j = i + 1; j < mShowtimesList.size(); j++) {
				Showtime si = mShowtimesList.get(i);
				Showtime sj = mShowtimesList.get(j);
				if (si.getPrice() > sj.getPrice()) {
					mShowtimesList.set(i, sj);
					mShowtimesList.set(j, si);
				}
			}
		}
	}

	public static void loadApplicationData() {
		/* Hardcoded, replace with real thing */
		if (mCinemasList.isEmpty()) {
			/* Get cinemas list */
			mCinemasList.add(new Cinema("Odeon Cineplex", "Polus Center, Str. Avram Iancu, nr. 492 - 500, comuna Floresti, Cluj-Napoca", true,
					R.drawable.cinema_odeon, 46749267, 23530730));
			mCinemasList.add(new Cinema("Cinema City", "Str Alexandru Vaida Voievod, Nr. 53-55 (Iulius Mall)", false, R.drawable.cinema_city, 46771761,
					23625906));
			mCinemasList.add(new Cinema("Florin Piersic", "P-ta Mihai Viteazul nr.11", true, R.drawable.cinema_florin_piersic, 46772217, 23587689));
			mCinemasList.add(new Cinema("Cinema Victoria", "B-dul Eroilor nr.51", false, R.drawable.cinema_victoria, 46770659, 23596112));
			mCinemasList.add(new Cinema("Arta-Eurimages - Cluj", "Str. Universitatii, nr.3", false, R.drawable.cinema_arta, 46768080, 23590165));
			for (Cinema cinema : mCinemasList) {
				if (cinema.isFavorite()) {
					mCinemasListFav.add(cinema);
				}
			}
		}
		if (mMoviesList.isEmpty()) {
			/* Get movies list */
			mMoviesList.add(new Movie("The Twilight Saga: Breaking Dawn - Part 1 (2011)", R.drawable.movie_twilight,
					"http://www.youtube.com/watch?v=sIpeBi6SG4A"));
			mMoviesList.add(new Movie("Immortals (2011)", R.drawable.movie_imortals));
			mMoviesList.add(new Movie("In Time (2011)", R.drawable.movie_in_time));
			mMoviesList.add(new Movie("Cars 2 (2011)", R.drawable.movie_cars_2));
			mMoviesList.add(new Movie("A Dangerous Method (2011)", R.drawable.movie_dangerous_method));
			mMoviesList.add(new Movie("Arthur Christmas (2011)", R.drawable.movie_arthur_christmas));
			mMoviesList.add(new Movie("The Thing (2011)", R.drawable.movie_the_thing));
			mMoviesList.add(new Movie("Anonymous (2011)", R.drawable.movie_anonymous));
			mMoviesList.add(new Movie("Margin Call (2011)", R.drawable.movie_margin_call));
			mMoviesList.add(new Movie("Puss in Boots (2011)", R.drawable.movie_puss_in_boots));
			mMoviesList.add(new Movie("Amador (2010)", R.drawable.movie_amador, "http://www.youtube.com/watch?v=1TqWIkr5HSI"));
			mMoviesList.add(new Movie("Liceenii, �n 53 de ore si ceva (2010)", R.drawable.movie_liceenii));
			mMoviesList.add(new Movie("Admiral (2008)", R.drawable.movie_amiral));
			mMoviesList.add(new Movie("Kandagar (2010)", R.drawable.movie_kandagar));
		}
		if (mShowtimesList.isEmpty()) {
			/* Get Showtimess list */
			Random rand = new Random();

			mShowtimesList.add(new Showtime(mMoviesList.get(9), mCinemasList.get(0), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(4), mCinemasList.get(0), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(5), mCinemasList.get(0), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(0), mCinemasList.get(0), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(1), mCinemasList.get(0), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(2), mCinemasList.get(0), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(3), mCinemasList.get(0), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));

			mShowtimesList.add(new Showtime(mMoviesList.get(9), mCinemasList.get(1), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(4), mCinemasList.get(1), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(5), mCinemasList.get(1), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(0), mCinemasList.get(1), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(1), mCinemasList.get(1), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(7), mCinemasList.get(1), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(8), mCinemasList.get(1), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(2), mCinemasList.get(1), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));

			mShowtimesList.add(new Showtime(mMoviesList.get(9), mCinemasList.get(2), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(12), mCinemasList.get(2), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(13), mCinemasList.get(2), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));

			mShowtimesList.add(new Showtime(mMoviesList.get(6), mCinemasList.get(3), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
			mShowtimesList.add(new Showtime(mMoviesList.get(10), mCinemasList.get(3), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));

			mShowtimesList.add(new Showtime(mMoviesList.get(11), mCinemasList.get(4), rand.nextInt() % 5 + 10, "09.12;10.12;11.12 16:00;17:30;21:15"));
		}

		// sort movies by name
		for (int i = 0; i < mMoviesList.size(); i++) {
			for (int j = i + 1; j < mMoviesList.size(); j++) {
				Movie mi = mMoviesList.get(i);
				Movie mj = mMoviesList.get(j);
				if (mi.getTitle().compareToIgnoreCase(mj.getTitle()) > 0) {
					mMoviesList.set(i, mj);
					mMoviesList.set(j, mi);
				}
			}
		}

		// sort showtimes by movie title
		sortShowtimesByMovieTitle();
	}
}
