package com.catalog.model;

import java.sql.Time;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Timetable implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String day;
	private Time hour;
	private String room;
	private SubjectTeacherForClass stfc;
	private String stfcSel;
	private String hourString;

	public Timetable() {
	}

	public Timetable(String day, Time hour, String room) {
		this.day = day;
		this.hour = hour;
		this.room = room;
	}

	public Timetable(String day, Time hour, String room,
			SubjectTeacherForClass stfc) {
		this.day = day;
		this.hour = hour;
		this.room = room;
		this.stfc = stfc;

	}

	public int getId() {
		return this.id;
	}

	public String getStfcSel() {
		return stfcSel;
	}

	public void setStfcSel(String stfcSel) {
		this.stfcSel = stfcSel;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDay() {
		return this.day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getRoom() {
		return this.room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Time getHour() {
		return this.hour;
	}

	public void setHour(Time hour) {
		this.hour = hour;
	}

	public SubjectTeacherForClass getSubjectteacherforclass() {
		return this.stfc;
	}

	public void setSubjectteacherforclass(SubjectTeacherForClass stfc) {
		this.stfc = stfc;
	}

	public String getHourString() {
		return hourString;
	}

	public void setHourString(String hourString) {
		this.hourString = hourString;
	}

}
