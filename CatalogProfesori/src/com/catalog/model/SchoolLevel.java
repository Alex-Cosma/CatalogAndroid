package com.catalog.model;

public enum SchoolLevel {
	PRIMARY("PRIMAR"), GYMNASIUM("GIMNAZIAL"), HIGHSCHOOL("LICEAL");

	private String type;

	SchoolLevel(String type) {
		this.type = type;

	}

	public String getType() {
		return type;
	}
}
