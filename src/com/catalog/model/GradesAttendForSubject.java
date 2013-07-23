package com.catalog.model;

import java.io.Serializable;
import java.util.List;

public class GradesAttendForSubject implements Serializable {
	private List<StudentMark> marks;
	private List<Attendance> attendaces;
	private Subject subject;

	public GradesAttendForSubject(List<StudentMark> marks,
			List<Attendance> attendaces, Subject subject) {
		this.marks = marks;
		this.attendaces = attendaces;
		this.subject = subject;
	}

	public GradesAttendForSubject() {
	}

	public List<StudentMark> getMarks() {
		return marks;
	}

	public void setMarks(List<StudentMark> marks) {
		this.marks = marks;
	}

	public List<Attendance> getAttendaces() {
		return attendaces;
	}

	public void setAttendaces(List<Attendance> attendaces) {
		this.attendaces = attendaces;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}
