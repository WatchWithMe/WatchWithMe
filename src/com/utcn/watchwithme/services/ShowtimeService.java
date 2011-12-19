package com.utcn.watchwithme.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.utcn.watchwithme.objects.Showtime;
import com.utcn.watchwithme.repository.RemoteShowtimeRepository;
import com.utcn.watchwithme.repository.StaticShowtimeRepository;

/**
 * 
 * @author Vlad
 * 
 */
public class ShowtimeService {

	private static ArrayList<Showtime> showtimeList = new ArrayList<Showtime>();
	private static HashSet<Integer> obtainedCinemas = new HashSet<Integer>();
	private static HashSet<Integer> obtainedMovies = new HashSet<Integer>();
	private static boolean flag;

	public static void eraseData() {
		showtimeList.clear();
		obtainedCinemas.clear();
		obtainedMovies.clear();
	}

	public static boolean updated() {
		return flag;
	}

	public static ArrayList<Showtime> getForCinema(int id) {
		MovieService.getAllMovies();
		ArrayList<Showtime> list = new ArrayList<Showtime>();
		if (obtainedCinemas.contains(id) && flag == true) {
			for (Showtime st : showtimeList) {
				if (st.getCinema().getId() == id) {
					list.add(st);
				}
			}
			return list;
		}
		list = RemoteShowtimeRepository.getForCinema(id);
		if (list == null) {
			list = StaticShowtimeRepository.getForCinema(id);
			flag = false;
		} else {
			flag = true;
		}
		if (list.size() > 0) {
			for (Showtime st : list) {
				if (!showtimeList.contains(st)) {
					showtimeList.add(st);
				} else {
					showtimeList.remove(st);
					showtimeList.add(st);
				}
			}
			obtainedCinemas.add(id);
		}

		Iterator<Showtime> it = list.iterator();
		while (it.hasNext()) {
			Showtime st = it.next();
			if (st.getMovie().isIgnored()) {
				it.remove();
			}
		}
		return list;
	}

	public static ArrayList<Showtime> getForMovie(int id) {
		CinemaService.getAllCinemas();
		ArrayList<Showtime> list = new ArrayList<Showtime>();
		if (obtainedMovies.contains(id) && flag == true) {
			for (Showtime st : showtimeList) {
				if (st.getMovie().getId() == id) {
					list.add(st);
				}
			}
			return list;
		}
		list = RemoteShowtimeRepository.getForMovie(id);
		if (list == null) {
			list = StaticShowtimeRepository.getForMovie(id);
			flag = false;
		} else {
			flag = true;
		}
		if (list.size() > 0) {
			for (Showtime st : list) {
				if (!showtimeList.contains(st)) {
					showtimeList.add(st);
				} else {
					showtimeList.remove(st);
					showtimeList.add(st);
				}
			}
			obtainedMovies.add(id);
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

	public static void sortByPrice() {
		for (int i = 0; i < showtimeList.size(); i++) {
			for (int j = i + 1; j < showtimeList.size(); j++) {
				Showtime si = showtimeList.get(i);
				Showtime sj = showtimeList.get(j);
				if (si.getPrice() > sj.getPrice()) {
					showtimeList.set(i, sj);
					showtimeList.set(j, si);
				}
			}
		}
	}
}
