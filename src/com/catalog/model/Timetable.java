package com.catalog.model;

import java.sql.Time;

public class Timetable implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String day;
	private Time hour;
	private String room;
	private SubjectTeacherForClass stfc;

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

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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
}
