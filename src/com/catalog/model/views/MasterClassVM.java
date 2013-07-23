package com.catalog.model.views;

import java.io.Serializable;

import com.catalog.model.ClassGroup;

public class MasterClassVM implements Serializable {
	private String status;

	private ClassGroup classGroup;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ClassGroup getClassGroup() {
		return classGroup;
	}

	public void setClassGroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

}
