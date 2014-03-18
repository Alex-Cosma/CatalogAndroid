package com.catalog.model;

import java.util.List;

public class StudentSubjectMarks {

	private List<StudentMark> marks;
	private Subject subject;
	public StudentSubjectMarks(List<StudentMark> marks, Subject subject) {
		super();
		this.marks = marks;
		this.subject = subject;
	}
	public StudentSubjectMarks()
	{}
	public List<StudentMark> getMarks() {
		return marks;
	}
	public void setMarks(List<StudentMark> marks) {
		this.marks = marks;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	
	
	

}
