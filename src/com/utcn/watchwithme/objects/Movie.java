package com.utcn.watchwithme.objects;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private String director;
	private List<String> actors;
	private int length; // in minutes
	private int icon = -1;

	public Movie() {

	}

	public Movie(String title) {
		this.title = title;
	}

	public Movie(String title, int icon) {
		this.title = title;
		this.icon = icon;
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

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
