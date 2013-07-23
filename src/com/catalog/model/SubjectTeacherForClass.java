package com.catalog.model;

import java.util.HashSet;
import java.util.Set;

public class SubjectTeacherForClass implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Teacher teacher;
	private Subject subject;
	private ClassGroup classGroup;
	private Set<Attendance> attendances = new HashSet<Attendance>(0);
	private Set<StudentMark> studentMarks = new HashSet<StudentMark>(0);
	private Set<StudentAverage> studentAverage = new HashSet<StudentAverage>(0);
	private Set<Timetable> timetable = new HashSet<Timetable>(0);

	public SubjectTeacherForClass() {
	}

	public SubjectTeacherForClass(Teacher teacher, Subject subject,
			ClassGroup classGroup, Timetable timetable) {
		this.teacher = teacher;
		this.subject = subject;
		this.classGroup = classGroup;

	}

	public SubjectTeacherForClass(Teacher teacher, Subject subject,
			ClassGroup classGroup) {
		this.teacher = teacher;
		this.subject = subject;
		this.classGroup = classGroup;

	}

	public SubjectTeacherForClass(Teacher teacher, Subject subject,
			ClassGroup classGroup, Set<Attendance> attendances,
			Set<StudentMark> studentMarks, Set<Timetable> stfcTimetable,
			Set<StudentAverage> studentAverage) {
		this.teacher = teacher;
		this.subject = subject;
		this.classGroup = classGroup;
		this.attendances = attendances;
		this.studentMarks = studentMarks;
		this.timetable = stfcTimetable;
		this.studentAverage = studentAverage;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public ClassGroup getClassgroup() {
		return this.classGroup;
	}

	public void setClassgroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

	public Set<Attendance> getAttendances() {
		return this.attendances;
	}

	public void setAttendances(Set<Attendance> attendances) {
		this.attendances = attendances;
	}

	public Set<StudentMark> getStudentmarks() {
		return this.studentMarks;
	}

	public void setStudentmarks(Set<StudentMark> studentMarks) {
		this.studentMarks = studentMarks;
	}

	public Set<StudentAverage> getStudentaverage() {
		return this.studentAverage;
	}

	public void setStudentaverage(Set<StudentAverage> studentAverage) {
		this.studentAverage = studentAverage;
	}

	public Set<Timetable> getTimetable() {
		return this.timetable;
	}

	public void setTimetable(Set<Timetable> timetable) {
		this.timetable = timetable;
	}
}
