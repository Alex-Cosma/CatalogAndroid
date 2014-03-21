package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.SubjectClasses;

public class SubjectClassesVM extends GenericVM {

	private ArrayList<SubjectClasses> subjectClassesList;

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
