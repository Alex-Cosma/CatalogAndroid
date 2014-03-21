package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.Subject;

public class SubjectsVM extends GenericVM {
	private ArrayList<Subject> subjectList;

	public ArrayList<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(ArrayList<Subject> subjectList) {
		this.subjectList = subjectList;
	}
}
