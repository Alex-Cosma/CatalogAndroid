package com.catalog.model;

import java.util.HashSet;
import java.util.Set;

public class Teacher implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private ClassGroup classGroup;
	private String cg;
	private User user;
	private String lastName;
	private boolean admin;
	private String telephoneNo;
	private String address;
	private String email;
	private String firstName;
	private boolean enabled;
	private Set<SubjectTeacherForClass> subjectteacherforclasses = new HashSet<SubjectTeacherForClass>(
			0);
	private Set<Logs> logs = new HashSet<Logs>(0);

	public Teacher() {
	}

	public Teacher(String lastName, boolean admin, String email, User user,
			String firstName) {
		this.user = user;
		this.lastName = lastName;
		this.admin = admin;
		this.email = email;
		this.firstName = firstName;
	}

	public Teacher(ClassGroup classGroup, String lastName, boolean admin,
			String telephoneNo, String address, String email, User user,
			String firstName) {
		this.classGroup = classGroup;
		this.user = user;
		this.lastName = lastName;
		this.admin = admin;
		this.telephoneNo = telephoneNo;
		this.address = address;
		this.email = email;
		this.firstName = firstName;

	}

	public Teacher(String lastName, boolean admin, String telephoneNo,
			String address, String email, String firstName) {
		this.lastName = lastName;
		this.admin = admin;
		this.telephoneNo = telephoneNo;
		this.address = address;
		this.email = email;
		this.firstName = firstName;

	}

	public Teacher(String lastName, boolean admin, String telephoneNo,
			String address, String email, String firstName, ClassGroup classg) {
		this.lastName = lastName;
		this.admin = admin;
		this.telephoneNo = telephoneNo;
		this.address = address;
		this.email = email;
		this.firstName = firstName;
		this.classGroup = classg;

	}

	public Teacher(String lastName, boolean admin, String telephoneNo,
			String address, String email, User user, String firstName) {
		this.user = user;
		this.lastName = lastName;
		this.admin = admin;
		this.telephoneNo = telephoneNo;
		this.address = address;
		this.email = email;
		this.firstName = firstName;

	}

	public Teacher(ClassGroup classGroup, String lastName, boolean admin,
			String telephoneNo, String address, String email, User user,
			String firstName,
			Set<SubjectTeacherForClass> subjectteacherforclasses, Set<Logs> logs) {
		this.classGroup = classGroup;
		this.user = user;
		this.lastName = lastName;
		this.admin = admin;
		this.telephoneNo = telephoneNo;
		this.address = address;
		this.email = email;
		this.firstName = firstName;
		this.subjectteacherforclasses = subjectteacherforclasses;
		this.logs = logs;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isAdmin() {
		return this.admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getTelephoneNo() {
		return this.telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<SubjectTeacherForClass> getSubjectteacherforclasses() {
		return this.subjectteacherforclasses;
	}

	public void setSubjectteacherforclasses(
			Set<SubjectTeacherForClass> subjectteacherforclasses) {
		this.subjectteacherforclasses = subjectteacherforclasses;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Logs> getLogs() {
		return this.logs;
	}

	public void setLogs(Set<Logs> logs) {
		this.logs = logs;
	}

	public String getCg() {
		return cg;
	}

	public void setCg(String cg) {
		this.cg = cg;
	}
}
