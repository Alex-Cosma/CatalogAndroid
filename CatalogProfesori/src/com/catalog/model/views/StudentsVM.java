package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.Student;

public class StudentsVM extends GenericVM {
	private ArrayList<Student> studentList;

	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}
}
