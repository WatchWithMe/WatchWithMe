package com.utcn.watchwithme.objects;

public class Reminder {

	private Movie movie;
	private Cinema cinema;
	private String date;

	public Reminder() {
	}

	public Reminder(Movie m, Cinema c, String date) {
		this.movie = m;
		this.cinema = c;
		this.date = date;
	}

	public Reminder(Showtime showtime, String date) {
		this.movie = showtime.getMovie();
		this.cinema = showtime.getCinema();
		this.date = date;
	}

	@Override
	public String toString() {
		return movie.getTitle() + ", " + cinema.getName() + ": " + date;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Reminder) {
			Reminder x = (Reminder) o;
			return this.movie.equals(x.movie) && this.cinema.equals(x.cinema)
					&& this.date.equals(x.date);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return movie.hashCode() * 37 + cinema.hashCode() * 51 + date.hashCode();
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
