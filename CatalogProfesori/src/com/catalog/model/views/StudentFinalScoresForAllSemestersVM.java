package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.StudentFinalScore;

public class StudentFinalScoresForAllSemestersVM extends GenericVM {
	private ArrayList<StudentFinalScore> studentFinalScoreList;

	/**
	 * @return the studentFinalScoreList
	 */
	public ArrayList<StudentFinalScore> getStudentFinalScoreList() {
		return studentFinalScoreList;
	}

	/**
	 * @param studentFinalScoreList
	 *            the studentFinalScoreList to set
	 */
	public void setStudentFinalScoreList(
			ArrayList<StudentFinalScore> studentFinalScoreList) {
		this.studentFinalScoreList = studentFinalScoreList;
	}

}
