package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.Subject;

public class SubjectsVM {
	private String status;

	private ArrayList<Subject> subjectList;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(ArrayList<Subject> subjectList) {
		this.subjectList = subjectList;
	}
}
