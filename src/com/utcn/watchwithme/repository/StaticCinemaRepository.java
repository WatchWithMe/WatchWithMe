package com.utcn.watchwithme.repository;

import java.util.ArrayList;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.objects.Cinema;

/**
 * 
 * @author Vlad
 * 
 */
public class StaticCinemaRepository {

	private static ArrayList<Cinema> cinemaList = new ArrayList<Cinema>();

	public static ArrayList<Cinema> getAllCinemas() {
		return cinemaList;
	}

	public static Cinema getCinema(int id) {
		for (Cinema c : cinemaList) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
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
	}
}
