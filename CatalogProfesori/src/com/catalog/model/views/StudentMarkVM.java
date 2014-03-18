package com.catalog.model.views;

import com.catalog.model.StudentMark;

public class StudentMarkVM {
	private StudentMark studentMark;
	private String status;

	public StudentMark getStudentMark() {
		return studentMark;
	}

	public void setStudentMark(StudentMark studentMark) {
		this.studentMark = studentMark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
