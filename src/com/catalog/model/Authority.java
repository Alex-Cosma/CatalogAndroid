package com.catalog.model;

import java.util.HashSet;
import java.util.Set;

public class Authority implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer authority_id;
	private String authority;
	private Set<UserAuthority> userAuthorities = new HashSet<UserAuthority>(0);

	public Authority() {
	}

	public Authority(Integer authority_id) {
		super();
		this.authority_id = authority_id;
	}

	public Integer getAuthority_id() {
		return authority_id;
	}

	public void setAuthority_id(Integer authority_id) {
		this.authority_id = authority_id;
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
