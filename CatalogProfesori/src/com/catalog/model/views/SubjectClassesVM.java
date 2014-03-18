package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.SubjectClasses;

public class SubjectClassesVM {

	private ArrayList<SubjectClasses> subjectClassesList;
	private String status;

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
	 * @return the subjectClassesList
	 */
	public ArrayList<SubjectClasses> getSubjectClassesList() {
		return subjectClassesList;
	}

	/**
	 * @param subjectClassesList
	 *            the subjectClassesList to set
	 */
	public void setSubjectClassesList(
			ArrayList<SubjectClasses> subjectClassesList) {
		this.subjectClassesList = subjectClassesList;
	}

}
