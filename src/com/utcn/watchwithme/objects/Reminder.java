package com.utcn.watchwithme.objects;

public class Reminder {

	private Movie movie;
	private Cinema cinema;
	private String date;
	private String contact;

	public Reminder() {
	}

	public Reminder(Showtime showtime, String date) {
		this.movie = showtime.getMovie();
		this.cinema = showtime.getCinema();
		this.date = date;
		this.contact = "";
	}

	@Override
	public String toString() {
		return movie.getTitle() + ", " + cinema.getName() + ": " + date;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
