package com.catalog.model.views;

import com.catalog.model.Student;

public class StudentVM extends GenericVM {
	private Student student;

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

}
