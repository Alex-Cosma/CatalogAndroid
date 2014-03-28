/*
 * Copyright (C) 2013 Catalog Online Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.catalog.core;

import java.util.ArrayList;

import com.catalog.model.Attendance;
import com.catalog.model.GradesAttendForSubject;
import com.catalog.model.Semester;
import com.catalog.model.StudentMark;
import com.catalog.model.TimetableDays;
import com.catalog.model.views.MasterClassVM;
import com.catalog.model.views.MotivateIntervalVM;
import com.catalog.model.views.SemesterVM;
import com.catalog.model.views.StudentFinalScoresForAllSemestersVM;
import com.catalog.model.views.StudentsVM;
import com.catalog.model.views.SubjectClassesVM;
import com.catalog.model.views.SubjectTeacherForClassVM;
import com.catalog.model.views.TeacherVM;

interface Api_I {

	int login(String username, String password);

	TeacherVM getTeacher();

	boolean changePassword(String newPassword);

	MasterClassVM getMasterClass(int id);

	StudentsVM getStudentsForClass(int id);

	ArrayList<TimetableDays> getTeacherTimetable();

	StudentMark addStudentMark(int studentId, int stfcId, int grade,
			boolean finalExam, long date);

	StudentMark editStudentMark(int markId, int newMark, long date);

	Attendance addAttendance(int studentId, int stfcId, long date);

	Attendance editAttendance(int attendanceId, boolean motivated);

	SubjectTeacherForClassVM getSubjectTeacherForClass(int classGroupId,
			int subjectId, int teacherId);

	SubjectClassesVM getSubjectsForTeacherSubjects();

	ArrayList<GradesAttendForSubject> getGradesAttendForSubjectList(
			int studentId, Semester semester);

	MotivateIntervalVM motivateInterval(int studentId, int teacherId,
			int classGroupId, long date1, long date2);

	SemesterVM getSemestersInfo();

	void setStartTime();

	long getElapsedTime(String callingMethod);

	StudentFinalScoresForAllSemestersVM getFinalScoresForStudent(int studentId);

}
