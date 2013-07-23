package com.catalog.model;

public class SpecializationSubject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Subject subject;
	private Specialization specialization;

	public SpecializationSubject() {
	}

	public SpecializationSubject(Subject subject, Specialization specialization) {
		this.subject = subject;
		this.specialization = specialization;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Specialization getSpecialization() {
		return this.specialization;
	}

	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

}
