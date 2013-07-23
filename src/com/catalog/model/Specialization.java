package com.catalog.model;

import java.util.HashSet;
import java.util.Set;

public class Specialization implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Set<SpecializationSubject> specializationSubjects = new HashSet<SpecializationSubject>(
			0);
	private Set<ClassGroup> classGroups = new HashSet<ClassGroup>(0);

	public Specialization() {
	}

	public Specialization(String name) {
		this.name = name;
	}

	public Specialization(String name,
			Set<SpecializationSubject> specializationSubjects,
			Set<ClassGroup> classGroups) {
		this.name = name;
		this.specializationSubjects = specializationSubjects;
		this.classGroups = classGroups;
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

	public Set<SpecializationSubject> getSpecializationsubjects() {
		return this.specializationSubjects;
	}

	public void setSpecializationsubjects(
			Set<SpecializationSubject> specializationSubjects) {
		this.specializationSubjects = specializationSubjects;
	}

	public Set<ClassGroup> getClassgroups() {
		return this.classGroups;
	}

	public void setClassgroups(Set<ClassGroup> classGroups) {
		this.classGroups = classGroups;
	}

}
