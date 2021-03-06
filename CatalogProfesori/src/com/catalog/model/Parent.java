package com.catalog.model;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Parent implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private User user;
	// @NameConstraint
	private String lastName;
	private String email;
	private String workplace;
	// @NameConstraint
	private String firstName;

	private String cnpChild;
	private String password;
	private String repeatPassword;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getCnpChild() {
		return cnpChild;
	}

	public void setCnpChild(String cnpChild) {
		this.cnpChild = cnpChild;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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
