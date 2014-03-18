package com.catalog.model;

import java.io.Serializable;

public class LoginCredentials implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8300962055185174386L;
	private String username;
	private String password;

	public LoginCredentials() {
		this.username = "";
		this.password = "";
	}

	public LoginCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
