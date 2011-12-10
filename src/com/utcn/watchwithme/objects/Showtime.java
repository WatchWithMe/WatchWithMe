package com.utcn.watchwithme.objects;

public class Showtime {

	private Movie movie;
	private Cinema cinema;
	private String[] dates;
	private String hours;
	private double price;

	public Showtime() {
	}

	/**
	 * 
	 * @param movie
	 * @param cinema
	 * @param price
	 * @param showtimes
	 *            showtimes DD.MM;DD.MM HH.MM;HH.MM (i.e. day.month;day.month;
	 *            and so on [space] hour:minute;hour:minute; and so on)
	 */
	public Showtime(Movie movie, Cinema cinema, double price, String showtimes) {
		this.movie = movie;
		this.cinema = cinema;
		this.price = price;
		String s[] = showtimes.split("[ ]", 2);
		this.hours = s[1];

		String d[] = s[0].split("[;]");
		String h[] = s[1].split("[;]");

		dates = new String[h.length * d.length];
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < h.length; j++) {
				dates[i * h.length + j] = d[i] + " - " + h[j];
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Showtime) {
			Showtime x = (Showtime) o;
			return this.movie.equals(x.movie) && this.cinema.equals(x.cinema);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return movie.hashCode() * 37 + cinema.hashCode();
	}

	public String[] getShowtimes() {
		return dates;
	}

	public String getHours() {
		return hours;
	}

	public String getPriceString() {
		int euro = (int) price;
		int cents = (int) ((price - euro) * 100);
		return euro + "." + ((cents > 9) ? cents : ("0" + cents)) + " RON";
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
