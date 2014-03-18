package com.catalog.model;

import java.util.HashSet;
import java.util.Set;

public class Subject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private boolean enabled;
	private Set<SubjectTeacherForClass> subjectteacherforclasses = new HashSet<SubjectTeacherForClass>(
			0);
	private Set<SpecializationSubject> specializationSubjects = new HashSet<SpecializationSubject>(
			0);
	private Set<AttendancesPerSubject> subjects = new HashSet<AttendancesPerSubject>(
			0);

	public Subject() {
	}

	public Subject(String name) {
		this.name = name;
	}

	public Subject(String name,
			Set<SubjectTeacherForClass> subjectteacherforclasses,
			Set<SpecializationSubject> specializationSubjects) {
		this.name = name;
		this.subjectteacherforclasses = subjectteacherforclasses;
		this.specializationSubjects = specializationSubjects;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<SubjectTeacherForClass> getSubjectteacherforclasses() {
		return this.subjectteacherforclasses;
	}

	public void setSubjectteacherforclasses(
			Set<SubjectTeacherForClass> subjectteacherforclasses) {
		this.subjectteacherforclasses = subjectteacherforclasses;
	}

	public Set<SpecializationSubject> getSpecializationsubjects() {
		return this.specializationSubjects;
	}

	public void setSpecializationsubjects(
			Set<SpecializationSubject> specializationSubjects) {
		this.specializationSubjects = specializationSubjects;
	}

	public Set<AttendancesPerSubject> getAttendancespersubject() {
		return subjects;
	}

	public void setAttendancespersubject(Set<AttendancesPerSubject> subjects) {
		this.subjects = subjects;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
