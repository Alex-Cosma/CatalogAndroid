package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.TimetableDays;

public class TimetableVM extends GenericVM {
	private ArrayList<TimetableDays> timetableDaysList;

	/**
	 * @return the timetableDaysList
	 */
	public ArrayList<TimetableDays> getTimetableDaysList() {
		return timetableDaysList;
	}

	/**
	 * @param timetableDaysList
	 *            the timetableDaysList to set
	 */
	public void setTimetableDaysList(ArrayList<TimetableDays> timetableDaysList) {
		this.timetableDaysList = timetableDaysList;
	}
}
