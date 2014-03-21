package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.StudentMark;

public class StudentMarksVM extends GenericVM {

	private double mean;

	private ArrayList<StudentMark> studentMarkList;

	public ArrayList<StudentMark> getStudentMarkList() {
		return studentMarkList;
	}

	public void setStudentMarkList(ArrayList<StudentMark> studentMarkList) {
		this.studentMarkList = studentMarkList;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}
}
