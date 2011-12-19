package com.utcn.watchwithme.objects;

import java.io.Serializable;

import com.google.android.maps.GeoPoint;

public class Cinema implements Serializable {

	private static final long serialVersionUID = 1L;
	private static int nextID = 0;
	private int id;
	private String name;
	private String location;
	private String imageURL;
	private GeoPoint geoPoint;

	private boolean favorite;
	private int icon = -1;

	public Cinema() {
		this.id = nextID++;
	}

	public Cinema(int id, String name, String address, String imageURL,
			int lat, int lng) {
		this.id = id;
		this.name = name;
		this.location = address;
		this.imageURL = imageURL;
		this.geoPoint = new GeoPoint(lat, lng);
	}

	public Cinema(String name, String location, boolean favorite) {
		this.id = nextID++;
		this.name = name;
		this.location = location;
		this.favorite = favorite;
		this.geoPoint = new GeoPoint(46773464, 23592505);
	}

	public Cinema(String name, String location, boolean favorite, int icon,
			int lat, int lng) {
		this.id = nextID++;
		this.name = name;
		this.location = location;
		this.favorite = favorite;
		this.geoPoint = new GeoPoint(lat, lng);
		this.icon = icon;
	}

	public int getIcon() {
		return this.icon;
	}

	@Override
	public String toString() {
		return name + " - " + location;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Cinema) {
			Cinema x = (Cinema) o;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public GeoPoint getGeoPoint() {
		return geoPoint;
	}

	public void setGeoPoint(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public String getImageURL() {
		return imageURL;
	}

}
