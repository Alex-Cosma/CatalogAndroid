package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.TimetableDays;

public class TimetableVM {
	private String status;
	private ArrayList<TimetableDays> timetableDaysList;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

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
