package com.catalog.model;

public class UserAuthority implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int user_authority_id;
	private User user;
	private Authority authority;

	public UserAuthority() {

	}

	public UserAuthority(User user, Authority auth) {
		this.user = user;
		this.authority = auth;
	}

	public int getId() {
		return this.user_authority_id;
	}

	public void setId(int id) {
		this.user_authority_id = id;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
