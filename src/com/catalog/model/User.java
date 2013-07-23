package com.catalog.model;

import java.util.HashSet;
import java.util.Set;

public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer user_id;
	private String username;
	private String password;
	private boolean enabled;
	private String token;
	private Teacher teacher;
	private Parent parent;
	private Set<UserAuthority> userAuthorities = new HashSet<UserAuthority>(0);

	public User() {
	}

	public User(String username, String password, boolean enabled,
			String token, Set<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.token = token;
	}

	public User(String username, String password, boolean enabled,
			String token, Set<UserAuthority> userAuthorities, Teacher teacher,
			Parent parent) {
		this.userAuthorities = userAuthorities;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.token = token;
		this.teacher = teacher;
		this.parent = parent;
	}

	public Integer getId() {
		return this.user_id;
	}

	public void setId(Integer id) {
		this.user_id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Set<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(Set<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Parent getParent() {
		return this.parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

}
