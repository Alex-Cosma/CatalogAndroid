package com.catalog.model;

import java.util.List;

public class SubjectClasses {
	private Subject subject;
	private List<ClassGroup> classes;

	public SubjectClasses()
	{}
	public SubjectClasses(Subject subject, List<ClassGroup> classes) {
		this.subject = subject;
		this.classes = classes;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public List<ClassGroup> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassGroup> classes) {
		this.classes = classes;
	}

}
