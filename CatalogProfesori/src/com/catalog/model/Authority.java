package com.catalog.model;

import java.util.HashSet;
import java.util.Set;

public class Authority implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String authority;
	private Set<UserAuthority> userAuthorities = new HashSet<UserAuthority>(0);

	public Authority() {
	}

	public Authority(Integer id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Set<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(Set<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

}
