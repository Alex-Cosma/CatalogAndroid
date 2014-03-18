package com.catalog.model;

public class StudentAverage implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private double average;
	private SubjectTeacherForClass stfc;
	private Student student;
	private Semester semester;

	public StudentAverage() {

	}

	public StudentAverage(double average, SubjectTeacherForClass stfc,
			Student student, Semester semester) {
		this.average = average;
		this.stfc = stfc;
		this.student = student;
		this.semester = semester;

	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getAverage() {
		return this.average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public SubjectTeacherForClass getSubjectteacherforclass() {
		return this.stfc;
	}

	public void setSubjectteacherforclass(SubjectTeacherForClass stfc) {
		this.stfc = stfc;
	}

	public Semester getSemester() {
		return this.semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}
}
