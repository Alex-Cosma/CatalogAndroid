package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.Student;

public class StudentsVM {
	private String status;

	private ArrayList<Student> studentList;

	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
