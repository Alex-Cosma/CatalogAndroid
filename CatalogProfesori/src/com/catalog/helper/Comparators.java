package com.catalog.helper;

import java.util.Comparator;

import com.catalog.model.Attendance;
import com.catalog.model.Student;
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

	public static Comparator<Student> ComparatorByName = new Comparator<Student>() {

		public int compare(Student s1, Student s2) {
			if (s1.getLastName().compareTo(s2.getLastName()) == 0) {
				return s1.getFirstName().compareTo(s2.getFirstName());
			} else
				return s1.getLastName().compareTo(s2.getLastName());
		}
	};
}
