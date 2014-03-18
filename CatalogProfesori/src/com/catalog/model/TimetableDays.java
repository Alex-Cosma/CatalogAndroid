package com.catalog.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TimetableDays implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Timetable> timetables;

	public TimetableDays(ArrayList<Timetable> timetables) {
		super();
		this.timetables = timetables;
	}

	public TimetableDays() {

	}

	public ArrayList<Timetable> getTimetables() {
		return timetables;
	}

	public void setTimetables(List<Timetable> list) {
		this.timetables = (ArrayList<Timetable>) list;
	}

}
