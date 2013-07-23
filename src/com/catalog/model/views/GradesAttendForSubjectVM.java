package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.GradesAttendForSubject;

public class GradesAttendForSubjectVM {

	private String status;

	private ArrayList<GradesAttendForSubject> gradesAttendForSubjectList;

	/**
	 * @return the gradesAttendForSubjectList
	 */
	public ArrayList<GradesAttendForSubject> getGradesAttendForSubjectList() {
		return gradesAttendForSubjectList;
	}

	/**
	 * @param gradesAttendForSubjectList
	 *            the gradesAttendForSubjectList to set
	 */
	public void setGradesAttendForSubjectList(
			ArrayList<GradesAttendForSubject> gradesAttendForSubjectList) {
		this.gradesAttendForSubjectList = gradesAttendForSubjectList;
	}

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
}
