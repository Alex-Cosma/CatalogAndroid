package com.catalog.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Attendance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private SubjectTeacherForClass subjectTeacherForClass;
	private Student student;
	private Date date;
	private boolean motivat;
	private boolean toBeDeleted;
	private Semester semester;
	private Set<Logs> logs = new HashSet<Logs>(0);

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
	
	public Attendance(SubjectTeacherForClass subjectTeacherForClass,
			Student student, Date date, Semester semester, boolean motivat,Set<Logs>logs) {
		this.subjectTeacherForClass = subjectTeacherForClass;
		this.student = student;
		this.date = date;
		this.motivat = motivat;
		this.semester = semester;
		this.logs = logs;
	}
 
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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
	
	public boolean isToBeDeleted() {
		return this.toBeDeleted;
	}

	public void setToBeDeleted(boolean toBeDeleted) {
		this.toBeDeleted = toBeDeleted;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Set<Logs> getLogs() {
		return this.logs;
	}

	public void setLogs(Set<Logs> logs) {
		this.logs = logs;
	}

}
