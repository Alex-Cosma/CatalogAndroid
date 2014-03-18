package com.catalog.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Semester implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Date startDate;
	private Date endDate;
	private Set<Attendance> attendances = new HashSet<Attendance>(0);
	private Set<StudentMark> studentMarks = new HashSet<StudentMark>(0);
	private Set<StudentReport> studentAverage = new HashSet<StudentReport>(0);

	private Set<StudentFinalScore> studentFinalScore = new HashSet<StudentFinalScore>(
			0);

	public Semester() {
	}

	public Semester(String name, Date startDate, Date endDate) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Semester(String name, Date startDate, Date endDate,
			Set<Attendance> attendances, Set<StudentMark> studentMarks,
			Set<StudentReport> studentAverage) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.attendances = attendances;
		this.studentMarks = studentMarks;
		this.studentAverage = studentAverage;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Set<Attendance> getAttendances() {
		return this.attendances;
	}

	public void setAttendances(Set<Attendance> attendances) {
		this.attendances = attendances;
	}

	public Set<StudentMark> getStudentmarks() {
		return this.studentMarks;
	}

	public void setStudentmarks(Set<StudentMark> studentMarks) {
		this.studentMarks = studentMarks;
	}

	public Set<StudentReport> getStudentaverage() {
		return this.studentAverage;
	}

	public void setStudentaverage(Set<StudentReport> studentAverage) {
		this.studentAverage = studentAverage;
	}

	public Set<StudentFinalScore> getStudentfinalscore() {
		return this.studentFinalScore;
	}

	public void setStudentfinalscore(Set<StudentFinalScore> studentScore) {
		this.studentFinalScore = studentScore;
	}

}
