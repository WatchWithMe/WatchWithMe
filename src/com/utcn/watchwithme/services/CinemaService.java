package com.utcn.watchwithme.services;

import java.util.ArrayList;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.objects.Cinema;

public class CinemaService {

	private static ArrayList<Cinema> cinemaList = new ArrayList<Cinema>();
	private static ArrayList<Cinema> cinemaFavList = new ArrayList<Cinema>();
	private static Cinema selected;

	public static Cinema getCinema(int id) {
		// start loading if cinema not available in the list
		for (Cinema c : cinemaList) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}

	public static ArrayList<Cinema> getAllCinemas() {
		// start loading if cinemas not available in the list
		return cinemaList;
	}

	public static ArrayList<Cinema> getFavoriteCinemas() {
		// start loading if cinemas not available in the list
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

	static {
		cinemaList
				.add(new Cinema(
						"Odeon Cineplex",
						"Polus Center, Str. Avram Iancu, nr. 492 - 500, comuna Floresti, Cluj-Napoca",
						true, R.drawable.cinema_odeon, 46749267, 23530730));
		cinemaList.add(new Cinema("Cinema City",
				"Str Alexandru Vaida Voievod, Nr. 53-55 (Iulius Mall)", false,
				R.drawable.cinema_city, 46771761, 23625906));
		cinemaList.add(new Cinema("Florin Piersic",
				"P-ta Mihai Viteazul nr.11", true,
				R.drawable.cinema_florin_piersic, 46772217, 23587689));
		cinemaList.add(new Cinema("Cinema Victoria", "B-dul Eroilor nr.51",
				false, R.drawable.cinema_victoria, 46770659, 23596112));
		cinemaList.add(new Cinema("Arta-Eurimages - Cluj",
				"Str. Universitatii, nr.3", false, R.drawable.cinema_arta,
				46768080, 23590165));

		for (Cinema cinema : cinemaList) {
			if (cinema.isFavorite()) {
				cinemaFavList.add(cinema);
			}
		}
	}
}
