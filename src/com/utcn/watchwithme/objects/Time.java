package com.utcn.watchwithme.objects;

public class Time {

	private int h, m;

	public Time() {
		this.h = 12;
		this.m = 0;
	}

	public Time(int h, int m) {
		this.h = h;
		this.m = m;
	}

	public String toString() {
		return ((h < 10) ? "0" + h : h) + ":" + ((m < 10) ? "0" + m : m);
	}
}
