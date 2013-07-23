package com.catalog.model;

import java.util.Date;

public class Attendance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private SubjectTeacherForClass subjectTeacherForClass;
	private Student student;
	private Date date;
	private boolean motivat;
	private Semester semester;

	public Attendance() {
	}

	public Attendance(SubjectTeacherForClass subjectTeacherForClass,
			Student student, Date date, Semester semester, boolean motivat) {
		this.subjectTeacherForClass = subjectTeacherForClass;
		this.student = student;
		this.date = date;
		this.motivat = motivat;
		this.semester = semester;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SubjectTeacherForClass getSubjectteacherforclass() {
		return this.subjectTeacherForClass;
	}

	public void setSubjectteacherforclass(
			SubjectTeacherForClass subjectTeacherForClass) {
		this.subjectTeacherForClass = subjectTeacherForClass;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Semester getSemester() {
		return this.semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public boolean isMotivat() {
		return this.motivat;
	}

	public void setMotivat(boolean motivat) {
		this.motivat = motivat;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
