package com.catalog.model;

import java.util.HashSet;
import java.util.Set;

public class Student implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Parent parent;
	private ClassGroup classGroup;
	// @NameConstraint
	private String lastName;
	private String classg;
	private String serialNumber;
	// @CNPConstraint
	private String cnp;
	// @NameConstraint
	private String firstName;
	private Set<StudentMark> studentMarks = new HashSet<StudentMark>(0);
	private Set<Attendance> attendances = new HashSet<Attendance>(0);
	private Set<StudentReport> studentAverage = new HashSet<StudentReport>(0);
	private Set<StudentFinalScore> studentFinalScore = new HashSet<StudentFinalScore>(
			0);
	private boolean status;

	public Student() {
	}

	public Student(ClassGroup classGroup, String lastName, String serialNumber,
			String cnp, String firstName, boolean status) {
		this.classGroup = classGroup;
		this.lastName = lastName;
		this.serialNumber = serialNumber;
		this.cnp = cnp;
		this.firstName = firstName;
		this.status = status;
	}

	public Student(Parent parent, ClassGroup classGroup, String lastName,
			String serialNumber, String cnp, String firstName,
			Set<StudentMark> studentMarks, Set<Attendance> attendances,
			Set<StudentReport> studentAverage) {
		this.parent = parent;
		this.classGroup = classGroup;
		this.lastName = lastName;
		this.serialNumber = serialNumber;
		this.cnp = cnp;
		this.firstName = firstName;
		this.studentMarks = studentMarks;
		this.attendances = attendances;
		this.studentAverage = studentAverage;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassg() {
		return classg;
	}

	public void setClassg(String classg) {
		this.classg = classg;
	}

	public Parent getParent() {
		return this.parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public ClassGroup getClassgroup() {
		return this.classGroup;
	}

	public void setClassgroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCnp() {
		return this.cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Set<StudentMark> getStudentmarks() {
		return this.studentMarks;
	}

	public void setStudentmarks(Set<StudentMark> studentMarks) {
		this.studentMarks = studentMarks;
	}

	public Set<Attendance> getAttendances() {
		return this.attendances;
	}

	public void setAttendances(Set<Attendance> attendances) {
		this.attendances = attendances;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
