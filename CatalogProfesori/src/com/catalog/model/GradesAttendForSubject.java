package com.catalog.model;

import java.io.Serializable;
import java.util.List;

public class GradesAttendForSubject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<StudentMark> marks;
	private List<Attendance> attendaces;
	private Subject subject;
	private int noOfAttendances;
	private float studentAverage1;
	private float studentAverage2;
	private StudentReport studentReport1;
	private StudentReport studentReport2;
	private Semester semester;

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

	/**
	 * @return the noOfAttendances
	 */
	public int getNoOfAttendances() {
		return noOfAttendances;
	}

	/**
	 * @param noOfAttendances
	 *            the noOfAttendances to set
	 */
	public void setNoOfAttendances(int noOfAttendances) {
		this.noOfAttendances = noOfAttendances;
	}

	/**
	 * @return the studentAverage
	 */
	public float getStudentAverage1() {
		return studentAverage1;
	}

	/**
	 * @param studentAverage
	 *            the studentAverage to set
	 */
	public void setStudentAverage1(float studentAverage1) {
		this.studentAverage1 = studentAverage1;
	}

	/**
	 * @return the studentAverage2
	 */
	public float getStudentAverage2() {
		return studentAverage2;
	}

	/**
	 * @param studentAverage2
	 *            the studentAverage2 to set
	 */
	public void setStudentAverage2(float studentAverage2) {
		this.studentAverage2 = studentAverage2;
	}

	/**
	 * @return the studentReport1
	 */
	public StudentReport getStudentReport1() {
		return studentReport1;
	}

	/**
	 * @param studentReport1
	 *            the studentReport1 to set
	 */
	public void setStudentReport1(StudentReport studentReport1) {
		this.studentReport1 = studentReport1;
	}

	/**
	 * @return the studentReport2
	 */
	public StudentReport getStudentReport2() {
		return studentReport2;
	}

	/**
	 * @param studentReport2
	 *            the studentReport2 to set
	 */
	public void setStudentReport2(StudentReport studentReport2) {
		this.studentReport2 = studentReport2;
	}

	/**
	 * @return the semester
	 */
	public Semester getSemester() {
		return semester;
	}

	/**
	 * @param semester
	 *            the semester to set
	 */
	public void setSemester(Semester semester) {
		this.semester = semester;
	}

}
