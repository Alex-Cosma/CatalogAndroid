package com.catalog.model.views;

import java.io.Serializable;

import com.catalog.model.SubjectTeacherForClass;

public class SubjectTeacherForClassVM extends GenericVM implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SubjectTeacherForClass subjectTeacherForClass;

	public SubjectTeacherForClass getSubjectTeacherForClass() {
		return subjectTeacherForClass;
	}

	public void setSubjectTeacherForClass(
			SubjectTeacherForClass subjectTeacherForClass) {
		this.subjectTeacherForClass = subjectTeacherForClass;
	}

}
