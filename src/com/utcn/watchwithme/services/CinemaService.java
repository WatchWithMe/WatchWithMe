package com.utcn.watchwithme.services;

import java.util.ArrayList;

import com.utcn.watchwithme.objects.Cinema;
import com.utcn.watchwithme.repository.RemoteCinemaRepository;
import com.utcn.watchwithme.repository.StaticCinemaRepository;

/**
 * 
 * @author Vlad
 * 
 */
public class CinemaService {

	private static ArrayList<Cinema> cinemaList = new ArrayList<Cinema>();
	private static ArrayList<Cinema> cinemaFavList = new ArrayList<Cinema>();
	private static Cinema selected;
	private static boolean flag;

	public static void eraseData() {
		cinemaList = new ArrayList<Cinema>();
		cinemaFavList = new ArrayList<Cinema>();
		selected = null;
	}

	public static boolean updated() {
		return flag;
	}

	public static Cinema getCinema(int id) {
		for (Cinema c : cinemaList) {
			if (c.getId() == id) {
				return c;
			}
		}

		Cinema c = RemoteCinemaRepository.getCinema(id);
		if (c != null) {
			return c;
		}
		return StaticCinemaRepository.getCinema(id);
	}

	public static ArrayList<Cinema> getAllCinemas() {
		if (cinemaList.size() == 0) {
			cinemaList = RemoteCinemaRepository.getAllCinemas();
			if (cinemaList == null) {
				cinemaList = StaticCinemaRepository.getAllCinemas();
				flag = false;
			} else {
				flag = true;
			}

			for (Cinema cinema : cinemaList) {
				if (cinema.isFavorite()) {
					cinemaFavList.add(cinema);
				}
			}
		}
		return cinemaList;
	}

	public static ArrayList<Cinema> getFavoriteCinemas() {
		ArrayList<Cinema> all = getAllCinemas();
		cinemaFavList.clear();

		for (Cinema cinema : all) {
			if (cinema.isFavorite()) {
				cinemaFavList.add(cinema);
			}
		}

		return cinemaFavList;
	}

	public static Cinema getSelected() {
		return selected;
	}

	public static void setSelected(Cinema selectedCinema) {
		selected = selectedCinema;
	}

	public static void changeCinemaFavoriteStatus(int id) {
		Cinema cinema = getCinema(id);
		if (cinema.isFavorite()) {
			cinema.setFavorite(false);
			cinemaFavList.remove(cinema);
		} else {
			cinema.setFavorite(true);
			cinemaFavList.clear();
			for (Cinema c : cinemaList) {
				if (c.isFavorite()) {
					cinemaFavList.add(c);
				}
			}
		}
	}
}
