package com.utcn.watchwithme.objects;

import java.io.Serializable;

public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private String details;
	private String trailerURL;
	private int length; // in minutes
	private int icon = -1;
	private boolean ignored = false;
	private float rating;
	private int ratingCounts;

	public Movie() {

	}

	public Movie(String title, int icon) {
		this.title = title;
		this.icon = icon;
		this.details = "";
		this.trailerURL = "";
		this.length = 100;
		this.id = 0;
	}

	public Movie(String title, int icon, String url) {
		this.title = title;
		this.icon = icon;
		this.details = "";
		this.trailerURL = "";
		this.length = 100;
		this.id = 0;
		this.trailerURL = url;
	}

	@Override
	public String toString() {
		return title;
	}

	public int getIcon() {
		return icon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isIgnored() {
		return ignored;
	}

	public void setIgnored(boolean ignored) {
		this.ignored = ignored;
	}

	public String getTrailerURL() {
		return trailerURL;
	}

	public void setTrailerURL(String trailerURL) {
		this.trailerURL = trailerURL;
	}

	public float getRating() {
		return this.rating;
	}

	public void setRating(float newRating, int ratingCount) {
		this.rating = newRating;
		this.ratingCounts = ratingCount;
	}

	public void giveRating(float ratingGiven) {
		rating = (rating * ratingCounts + ratingGiven) / (ratingCounts + 1);
		ratingCounts++;
	}

	public int getRatingCount() {
		return this.ratingCounts;
	}
}
