package com.catalog.model.views;

import java.io.Serializable;
import java.util.List;

import com.catalog.model.Semester;

public class SemesterVM extends GenericVM implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Semester currentSemester;
	private List<Semester> semesterList;

	/**
	 * @return the currentSemester
	 */
	public Semester getCurrentSemester() {
		return currentSemester;
	}

	/**
	 * @param currentSemester
	 *            the currentSemester to set
	 */
	public void setCurrentSemester(Semester currentSemester) {
		this.currentSemester = currentSemester;
	}

	/**
	 * @return the semesterList
	 */
	public List<Semester> getSemesterList() {
		return semesterList;
	}

	/**
	 * @param semesterList
	 *            the semesterList to set
	 */
	public void setSemesterList(List<Semester> semesterList) {
		this.semesterList = semesterList;
	}

}
