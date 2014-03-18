package com.catalog.model.views;

import java.io.Serializable;

import com.catalog.model.SubjectTeacherForClass;

public class SubjectTeacherForClassVM implements Serializable {

	private String status;
	private SubjectTeacherForClass subjectTeacherForClass;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SubjectTeacherForClass getSubjectTeacherForClass() {
		return subjectTeacherForClass;
	}

	public void setSubjectTeacherForClass(
			SubjectTeacherForClass subjectTeacherForClass) {
		this.subjectTeacherForClass = subjectTeacherForClass;
	}

}
