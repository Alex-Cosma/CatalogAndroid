package com.catalog.model.views;

import java.io.Serializable;

import com.catalog.model.ClassGroup;

public class MasterClassVM extends GenericVM implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClassGroup classGroup;

	public ClassGroup getClassGroup() {
		return classGroup;
	}

	public void setClassGroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

}
