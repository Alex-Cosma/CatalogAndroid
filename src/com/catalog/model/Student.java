package com.catalog.model;

import java.util.HashSet;
import java.util.Set;

public class Student implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Parent parent;
	private ClassGroup classGroup;
	private String lastName;
	private String serialNumber;
	private String cnp;
	private String firstName;
	private Set<StudentMark> studentMarks = new HashSet<StudentMark>(0);
	private Set<Attendance> attendances = new HashSet<Attendance>(0);
	private Set<StudentAverage> studentAverage = new HashSet<StudentAverage>(0);
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
			Set<StudentAverage> studentAverage) {
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

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Set<StudentAverage> getStudentaverage() {
		return this.studentAverage;
	}

	public void setStudentaverage(Set<StudentAverage> studentAverage) {
		this.studentAverage = studentAverage;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
