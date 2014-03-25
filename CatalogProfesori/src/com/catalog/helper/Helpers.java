package com.catalog.helper;

import java.util.List;

import com.catalog.model.Semester;

public class Helpers {
	public static final String getFormattedSemesterName(String unformattedName) {
		String sem = "Sem. I";
		return sem += (unformattedName.contains("II") ? "I" : "");
	}

	public static Semester getOtherSemester(Semester currentSemester,
			List<Semester> semesterList) {
		return (semesterList.get(0).equals(currentSemester) ? semesterList
				.get(1) : semesterList.get(0));
	}
}
