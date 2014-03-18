package com.catalog.model;

import java.util.Date;

public class Logs implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date date;
	private Teacher teacher;
	private StudentMark studentMark;
	private Attendance attendance;
	private String logMessage;

	public Logs()
	{}
	public Logs(Date date, Teacher teacher, StudentMark studentMark,
			Attendance attendance, String logMessage) {

		this.date = date;
		this.teacher = teacher;
		this.studentMark = studentMark;
		this.attendance= attendance;
		this.logMessage = logMessage;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getLogMessage()
	{
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public StudentMark getStudentmark() {
		return this.studentMark;
	}

	public void setStudentmark(StudentMark sm) {
		this.studentMark = sm;
	}
	
	public Attendance getAttendance() {
		return this.attendance;
	}

	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}
}
