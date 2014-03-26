package com.catalog.model.views;

import java.util.List;

import com.catalog.model.Student;

public class MotivateIntervalVM extends GenericVM {

	private List<Student> students;

	/**
	 * @return the students
	 */
	public List<Student> getStudents() {
		return students;
	}

	/**
	 * @param students
	 *            the students to set
	 */
	public void setStudents(List<Student> students) {
		this.students = students;
	}
}
