package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.StudentMark;

public class StudentMarksVM {
	private String status;

	private double mean;

	private ArrayList<StudentMark> studentMarkList;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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
