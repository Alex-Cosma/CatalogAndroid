package com.catalog.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

public class AttendancesPerSubject implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String month;
	private Subject subject;
	private ClassGroup classGroup;
	private int noAbs;
	private int noMotAbs;
	private static SchoolLevel schoolLevel;
	private static final int COUNTY_CODE = 3;
	private static final int COD_ST = 1;
	private static final String language = "romana";

	public AttendancesPerSubject() {

	}

	public AttendancesPerSubject(String month, Subject subject, ClassGroup cg,
			int noAbs, int noMotAbs) {
		this.month = month;
		this.subject = subject;
		this.classGroup = cg;
		this.noAbs = noAbs;
		this.noMotAbs = noMotAbs;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public ClassGroup getClassgroup() {
		return classGroup;
	}

	public void setClassgroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

	public int getNoAbs() {
		return noAbs;
	}

	public void setNoAbs(int noAbs) {
		this.noAbs = noAbs;
	}

	public int getNoMotAbs() {
		return noMotAbs;
	}

	public void setNoMotAbs(int noMotAbs) {
		this.noMotAbs = noMotAbs;
	}

	public static int getCountyCode() {
		return COUNTY_CODE;
	}

	public static int getCodSt() {
		return COD_ST;
	}

	public static String getLanguage() {
		return language;
	}

}
