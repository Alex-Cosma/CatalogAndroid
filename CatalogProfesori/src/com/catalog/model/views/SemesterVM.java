package com.catalog.model.views;

import com.catalog.model.Semester;

public class SemesterVM extends GenericVM {

	private Semester semester;

	/**
	 * @return the semester
	 */
	public Semester getSemester() {
		return semester;
	}

	/**
	 * @param semester
	 *            the semester to set
	 */
	public void setSemester(Semester semester) {
		this.semester = semester;
	}

}
