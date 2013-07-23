package com.catalog.model;

import java.util.HashSet;
import java.util.Set;

public class Parent implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private User user;
	private String lastName;
	private String email;
	private String workplace;
	private String firstName;
	private Set<Student> students = new HashSet<Student>(0);

	public Parent() {
	}

	public Parent(User user, String lastName, String email, String firstName) {
		this.user = user;
		this.lastName = lastName;
		this.email = email;
		this.firstName = firstName;
	}

	public Parent(User user, String lastName, String email, String workplace,
			String firstName, Set<Student> students) {
		this.user = user;
		this.lastName = lastName;
		this.email = email;
		this.workplace = workplace;
		this.firstName = firstName;
		this.students = students;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkplace() {
		return this.workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Set<Student> getStudents() {
		return this.students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
