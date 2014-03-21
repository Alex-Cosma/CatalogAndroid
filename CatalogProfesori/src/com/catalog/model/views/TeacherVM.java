package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.ClassGroup;
import com.catalog.model.Teacher;

public class TeacherVM extends GenericVM {
	private Teacher teacher;
	private ArrayList<ClassGroup> classGroupList;

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
