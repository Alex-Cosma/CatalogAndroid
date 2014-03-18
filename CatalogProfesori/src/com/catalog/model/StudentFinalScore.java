package com.catalog.model;

import java.io.Serializable;

public class StudentFinalScore implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private double average;
	private int absenceNo;
	private int motAbsenceNo;
	private boolean closedSituation;
	private Student student;
	private Semester semester;

	public StudentFinalScore() {
	}

	public StudentFinalScore(double average, int absenceNo, int motAbsenceNo,
			boolean closedSituation, Student student, Semester semester) {

		this.average = average;
		this.absenceNo = absenceNo;
		this.motAbsenceNo = motAbsenceNo;
		this.closedSituation = closedSituation;
		this.student = student;
		this.semester = semester;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAverage() {
		return this.average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public int getAbsenceNo() {
		return this.absenceNo;
	}

	public void setAbsenceNo(int absNo) {
		this.absenceNo = absNo;
	}

	public int getMotAbsenceNo() {
		return motAbsenceNo;
	}

	public void setMotAbsenceNo(int motAbsenceNo) {
		this.motAbsenceNo = motAbsenceNo;
	}

	public boolean isClosedSituation() {
		return this.closedSituation;
	}

	public void setClosedSituation(boolean closedSituation) {
		this.closedSituation = closedSituation;
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

}
