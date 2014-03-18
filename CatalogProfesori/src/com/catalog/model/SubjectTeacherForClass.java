package com.catalog.model;

import java.util.HashSet;
import java.util.Set;

public class SubjectTeacherForClass implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Teacher teacher;
	private Subject subject;
	private ClassGroup classGroup;
	private SubjectTeacherForClass stfcParent;
	private Set<Attendance> attendances = new HashSet<Attendance>(0);
	private Set<StudentMark> studentMarks = new HashSet<StudentMark>(0);
	private Set<Timetable> timetable = new HashSet<Timetable>(0);
	private Set<StudentReport> studentAverage = new HashSet<StudentReport>(0);
	private String teacherName;
	private String subjectName;
	private String className;
	private String stfcId;

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
			Set<StudentMark> studentMarks, Set<Timetable> stfcTimetable) {
		this.teacher = teacher;
		this.subject = subject;
		this.classGroup = classGroup;
		this.attendances = attendances;
		this.studentMarks = studentMarks;
		this.timetable = stfcTimetable;
	}

	public String getStfcId() {
		return this.id + "";
	}

	public void setStfcId(String stfcId) {
		this.stfcId = stfcId;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	public SubjectTeacherForClass getStfcParent() {
		return this.stfcParent;
	}

	public void setStfcParent(SubjectTeacherForClass stfc) {
		this.stfcParent = stfc;
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

	public Set<Timetable> getTimetable() {
		return this.timetable;
	}

	public void setTimetable(Set<Timetable> timetable) {
		this.timetable = timetable;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public Set<StudentReport> getStudentreport() {
		return this.studentAverage;
	}

	public void setStudentreport(Set<StudentReport> studentAverage) {
		this.studentAverage = studentAverage;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
