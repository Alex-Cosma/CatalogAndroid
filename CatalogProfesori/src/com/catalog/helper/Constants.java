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
package com.catalog.helper;

public class Constants {
	/*
	 * Requests constants
	 */
	public static final int SUCCESS = 1;
	public static final int FAIL = 0;
	public static final int UNAUTHORIZED = 9;
	public static final int BAD_CONNECTION = 8;

	/*
	 * Class names constants (for AsyncTask Factory)
	 */
	public static final String AllClassesActivity = "AllClassesActivity";
	public static final String AllClassesListFragment = "AllClassesListFragment";
	public static final String AllClassesStudentsDetailsFragment = "AllClassesStudentsDetailsFragment";

	public static final String DetailedClassActivity = "DetailedClassActivity";
	public static final String DetailedClassListFragment = "DetailedClassListFragment";
	public static final String DetailedClassStudentsDetailsFragment = "DetailedClassStudentsDetailsFragment";

	public static final String LoginActivity = "LoginActivity";
	public static final String MenuActivity = "MenuActivity";

	public static final String TimetableActivity = "TimetableActivity";
	public static final String TimetableAddSubjectsActivity = "TimetableAddSubjectsActivity";

	public static final String NoteEditorActivity = "NoteEditorActivity";
	public static final String NotesListActivity = "NotesListActivity";
	public static final String NotesLiveFolderActivity = "NotesLiveFolderActivity";
	public static final String TitleEditorActivity = "TitleEditorActivity";

	public static final String GameActivity = "GameActivity";
	public static final String GameMainActivity = "GameMainActivity";

	public static final String AboutAppActivity = "AboutAppActivity";

	/*
	 * Dialogs
	 */
	public static final String AddGradesOrAbsenceDialog = "AddGradesOrAbsenceDialog";
	public static final String EditGradesOrAbsencesDialog = "EditGradesOrAbsencesDialog";
	public static final String ChangePasswordDialog = "ChangePasswordDialog";
	public static final String MotivateIntervalDialog = "MotivateIntervalDialog";

	/*
	 * Method names constants (for AsyncTask Factory)
	 */
	public static final String Method_GetStudents = "GetStudents";
	public static final String Method_GetSubjects = "GetSubjects";
	public static final String Method_GetMarks = "GetMarks";
	public static final String Method_GetAttendance = "GetAttendance";
	public static final String Method_EditMark = "EditMark";
	public static final String Method_EditAbsance = "EditAbsance";
	public static final String Method_GetTeacher = "GetTeacher";
	public static final String Method_AddMark = "AddMark";
	public static final String Method_AddAbsence = "AddAbsence";
	public static final String Method_GetGradesAndAbsencesForStudent = "GetGradesAndAbsencesForStudent";
	public static final String Method_GetTeacherTimetable = "GetTeacherTimetable";
	public static final String Method_GetMasterClass = "GetMasterClass";
	public static final String Method_GetCurrentSmester = "GetCurrentSemest";

	/*
	 * Bundle constants
	 */
	public static final String Bundle_MasterClass = "MasterClass";
	public static final String Bundle_Student = "Student";
	public static final String Bundle_Students = "Subjects";
	public static final String Bundle_ClassGroup = "ClassGroup";
	public static final String Bundle_Teacher = "Teacher";
	public static final String Bundle_GradesAndAbsences = "MarksAndAbsences";
	public static final String Bundle_Timetable = "Timetable";
	public static final String Bundle_SelectedStudentIndex = "selectedStudentIndex";
	public static final String Bundle_Semester = "Semester";
	public static final String Bundle_ClosedSituation = "ClosedSituation";
	public static final String Bundle_SelectedSemester = "SelectedSemester";

	
	/*
	 * Shared Prefs
	 */
	public static final String Pref_Username = "Username";
	public static final String Pref_Password = "Password";
	public static final String Pref_FirstEntry = "FirstEntry";
	public static final String Pref_IPExternal = "IpExternal";

	/*
	 * Analytics
	 */
	public static final String ANALYTICS_TRACKER_ID = "UA-49280710-1";
	public static final String UI_ACTION = "UX";
	public static final String UI_ACTION_ADD_GRADE = "UX_ADD_GRADE";
	public static final String UI_ACTION_ADD_ABSANCE = "UX_ADD_ABSANCE";
	public static final String UI_ACTION_CHANGE_PASSWORD = "UX_CHANGE_PASSWORD";
	public static final String UI_ACTION_MOTIVATE_GRADE = "UX_MOTIVATE_ABSANCE";
	public static final String UI_ACTION_EDIT_GRADE = "UX_EDIT_GRADE";
	public static final String UI_ACTION_ADD_NOTE = "UX_ADD_NOTE";
	public static final String UI_ACTION_VIEW_EDIT_NOTE = "UX_VIEW_NOTE";
	public static final String UI_ACTION_DELETE_NOTE = "UX_DELETE_NOTE";
	public static final String UI_ACTION_WORK_FROM_SCHOOL = "UX_WORK_FROM_SCHOOL";
	public static final String UI_ACTION_WORK_FROM_HOME = "UX_WORK_FROM_HOME";
	public static final String UI_ACTION_MOTIVATE_INTERVAL = "UX_MOTIVATE_INTERVAL";

}
