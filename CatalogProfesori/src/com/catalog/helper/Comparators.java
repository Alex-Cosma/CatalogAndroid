package com.catalog.helper;

import java.util.Comparator;

import com.catalog.model.Attendance;
import com.catalog.model.StudentMark;

public class Comparators {
	public static Comparator<StudentMark> ComparatorByMarkDate = new Comparator<StudentMark>() {

		public int compare(StudentMark m1, StudentMark m2) {

			return (((Long) m1.getDate().getTime()).compareTo((Long) m2
					.getDate().getTime()));
		}
	};

	public static Comparator<Attendance> ComparatorByAbsanceDate = new Comparator<Attendance>() {

		public int compare(Attendance a1, Attendance a2) {
			return (((Long) a1.getDate().getTime()).compareTo((Long) a2
					.getDate().getTime()));
		}
	};
}
