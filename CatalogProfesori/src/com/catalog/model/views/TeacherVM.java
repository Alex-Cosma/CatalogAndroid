package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.ClassGroup;
import com.catalog.model.Teacher;

public class TeacherVM {
	private String status;
	private Teacher teacher;
	private ArrayList<ClassGroup> classGroupList;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public ArrayList<ClassGroup> getClassGroupList() {
		return classGroupList;
	}

	public void setClassGroupList(ArrayList<ClassGroup> classGroupList) {
		this.classGroupList = classGroupList;
	}
}
