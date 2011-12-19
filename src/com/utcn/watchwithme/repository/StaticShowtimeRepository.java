package com.utcn.watchwithme.repository;

import java.util.ArrayList;
import java.util.Random;

import com.utcn.watchwithme.objects.Showtime;
import com.utcn.watchwithme.services.CinemaService;
import com.utcn.watchwithme.services.MovieService;

/**
 * 
 * @author Vlad
 * 
 */
public class StaticShowtimeRepository {

	private static ArrayList<Showtime> showtimeList = new ArrayList<Showtime>();

	public static ArrayList<Showtime> getForCinema(int id) {
		ArrayList<Showtime> list = new ArrayList<Showtime>();
		for (Showtime st : showtimeList) {
			if (st.getCinema().getId() == id) {
				list.add(st);
			}
		}
		return list;
	}

	public static ArrayList<Showtime> getForMovie(int id) {
		ArrayList<Showtime> list = new ArrayList<Showtime>();
		for (Showtime st : showtimeList) {
			if (st.getMovie().getId() == id) {
				list.add(st);
			}
		}
		return list;
	}

	public static void sortByTitle() {
		for (int i = 0; i < showtimeList.size(); i++) {
			for (int j = i + 1; j < showtimeList.size(); j++) {
				Showtime si = showtimeList.get(i);
				Showtime sj = showtimeList.get(j);
				if (si.getMovie().getTitle()
						.compareToIgnoreCase(sj.getMovie().getTitle()) > 0) {
					showtimeList.set(i, sj);
					showtimeList.set(j, si);
				}
			}
		}
	}

	static {
		Random rand = new Random();

		showtimeList.add(new Showtime(MovieService.getMovie(9), CinemaService
				.getCinema(0), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(4), CinemaService
				.getCinema(0), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(5), CinemaService
				.getCinema(0), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(0), CinemaService
				.getCinema(0), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(1), CinemaService
				.getCinema(0), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(2), CinemaService
				.getCinema(0), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(3), CinemaService
				.getCinema(0), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));

		showtimeList.add(new Showtime(MovieService.getMovie(9), CinemaService
				.getCinema(1), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(4), CinemaService
				.getCinema(1), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(5), CinemaService
				.getCinema(1), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(0), CinemaService
				.getCinema(1), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(1), CinemaService
				.getCinema(1), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(7), CinemaService
				.getCinema(1), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(8), CinemaService
				.getCinema(1), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(2), CinemaService
				.getCinema(1), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));

		showtimeList.add(new Showtime(MovieService.getMovie(9), CinemaService
				.getCinema(2), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(12), CinemaService
				.getCinema(2), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(13), CinemaService
				.getCinema(2), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));

		showtimeList.add(new Showtime(MovieService.getMovie(6), CinemaService
				.getCinema(3), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));
		showtimeList.add(new Showtime(MovieService.getMovie(10), CinemaService
				.getCinema(3), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));

		showtimeList.add(new Showtime(MovieService.getMovie(11), CinemaService
				.getCinema(4), rand.nextInt() % 5 + 10,
				"09.12;10.12;11.12 16:00;17:30;21:15"));

		sortByTitle();
	}
}
