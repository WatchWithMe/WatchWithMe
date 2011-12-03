package com.utcn.watchwithme.objects;

import java.util.ArrayList;

public class Showtime {

	private Movie movie;
	private Cinema cinema;
	private ArrayList<Time> dates;
	private double price;

	public Showtime() {
	}

	public Showtime(Movie movie, Cinema cinema, double price) {
		this.movie = movie;
		this.cinema = cinema;
		dates = new ArrayList<Time>();
		dates.add(new Time());
		dates.add(new Time(18, 30));
		dates.add(new Time(21, 15));
		this.price = price;
	}

	public String getPriceString() {
		int euro = (int) price;
		int cents = (int) ((price - euro) * 100);
		return euro + "." + ((cents > 9) ? cents : ("0" + cents)) + " RON";
	}

	public String getDateString() {
		String hours = "";
		for (Time d : dates) {
			hours += d.toString() + "; ";
		}
		return hours;
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

	public ArrayList<Time> getDate() {
		return dates;
	}

	public void setDate(ArrayList<Time> date) {
		this.dates = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
