package com.utcn.watchwithme.objects;

import java.io.Serializable;

public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;
	public static int nextID = 0;

	private int id;
	private String title;
	private String details;
	private String imageURL;
	private String trailerURL;
	private int length; // in minutes
	private float rating;

	private boolean ignored = false;
	private int ratingCounts;

	public Movie() {
		this.id = nextID++;
	}

	public Movie(int id, String title, String details, int length,
			String trailer, String image, float rating) {
		this.id = id;
		this.title = title;
		this.details = details;
		this.length = length;
		this.trailerURL = trailer;
		this.imageURL = image;
		this.rating = rating;
	}

	@Override
	public String toString() {
		return title;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Movie) {
			Movie x = (Movie) o;
			return this.id == x.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (new Integer(id)).hashCode();
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

	public String getImageURL() {
		return imageURL;
	}
}
