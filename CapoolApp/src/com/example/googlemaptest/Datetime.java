package com.example.googlemaptest;

public class Datetime {
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;

	Datetime(int y, int m, int d, int h, int min) {
		year = y;
		month = m;
		day = d;
		hour = h;
		minute = min;
	}

	public void setYear(int y) {
		year = y;
	}

	public void setMonth(int m) {
		month = m;
	}

	public void setDay(int d) {
		day = d;
	}

	public void setHour(int h) {
		hour = h;
	}

	public void setMinute(int min) {
		minute = min;
	}
	
	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}
}
