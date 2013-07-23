package com.catalog.model;

import java.util.HashSet;
import java.util.Set;

public class Subject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private boolean invalid;
	private Set<SubjectTeacherForClass> subjectteacherforclasses = new HashSet<SubjectTeacherForClass>(
			0);
	private Set<SpecializationSubject> specializationSubjects = new HashSet<SpecializationSubject>(
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

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public boolean isInvalid() {
		return this.invalid;
	}

	public void setInvalid(boolean status) {
		this.invalid = status;
	}

}
