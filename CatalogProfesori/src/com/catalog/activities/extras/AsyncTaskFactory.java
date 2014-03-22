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
package com.catalog.activities.extras;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.catalog.activities.AddGradesClassListFragment;
import com.catalog.activities.AddGradesClassStudentsDetailsFragment;
import com.catalog.activities.DetailedClassActivity;
import com.catalog.activities.DetailedClassListFragment;
import com.catalog.activities.DetailedClassStudentsDetailsFragment;
import com.catalog.activities.LoginActivity;
import com.catalog.activities.MenuActivity;
import com.catalog.activities.R;
import com.catalog.activities.TimetableActivity;
import com.catalog.helper.Api;
import com.catalog.helper.AppPreferences;
import com.catalog.helper.Constants;
import com.catalog.model.Attendance;
import com.catalog.model.ClassGroup;
import com.catalog.model.GradesAttendForSubject;
import com.catalog.model.LoginCredentials;
import com.catalog.model.Student;
import com.catalog.model.StudentMark;
import com.catalog.model.Subject;
import com.catalog.model.SubjectClasses;
import com.catalog.model.SubjectTeacherForClass;
import com.catalog.model.TimetableDays;
import com.catalog.model.views.MasterClassVM;
import com.catalog.model.views.SemesterVM;
import com.catalog.model.views.StudentsVM;
import com.catalog.model.views.SubjectClassesVM;
import com.catalog.model.views.SubjectTeacherForClassVM;
import com.catalog.model.views.TeacherVM;

/**
 * Singleton class which returns the appropriate Async Task. <br>
 * Do not try to instantiate, use the <i>getInstance()</i> method instead.
 * 
 * @author Alex
 */
public class AsyncTaskFactory {

	/*
	 * Static members
	 */
	private static final int SUCCESS = Constants.SUCCESS;
	private static final int FAIL = Constants.FAIL;
	private static AsyncTaskFactory f;
	private static Api api;

	/*
	 * Disabling instantiation
	 */
	private AsyncTaskFactory() {
	};

	/**
	 * 
	 * @return - The singleton instance of the Async Factory
	 */
	public static AsyncTaskFactory getInstance(boolean isExternal) {
		if (f == null) {
			f = new AsyncTaskFactory();
			api = Api.getInstance(isExternal);
		}
		return f;
	}

	/**
	 * Method which returns the appropriate Async task.
	 * 
	 * @param ctx
	 *            - The context from which it is called
	 * @param className
	 *            - The name of the class which calls the method (use CLASSNAME
	 *            attribute)
	 * @param methodName
	 *            - A method name
	 * @return - The requested Async task
	 */
	public AsyncTask<Object, Void, Integer> getTask(Object ctx,
			String className, String methodName) {

		/**
		 * AddGradesClassStudentsDetailsFragment
		 */
		if (className.equals(Constants.AddGradesClassStudentsDetailsFragment)) {
			return new GetStudentsTaskAddGrades(
					(AddGradesClassStudentsDetailsFragment) ctx);
		}

		else if (className.equals(Constants.TimetableActivity)) {
			return new GetTeacherTimetableTask((TimetableActivity) ctx);
		}

		/**
		 * AddGradesOrAbsanceDialog
		 */
		else if (className.equals(Constants.AddGradesOrAbsenceDialog)) {
			if (methodName.equals(Constants.Method_AddMark)) {
				return new AddMarkTask((DetailedClassActivity) ctx);
			} else if (methodName.equals(Constants.Method_AddAbsence)) {
				return new AddAbsanceTask((DetailedClassActivity) ctx);
			} else
				return null;
		}

		else if (className.equals(Constants.ChangePasswordDialog)) {
			return new ChangePasswordTask((MenuActivity) ctx);
		}

		/**
		 * EditGradesOrAbsanceDialog
		 */
		if (className.equals(Constants.EditGradesOrAbsencesDialog)) {
			if (methodName.equals(Constants.Method_EditMark)) {
				return new EditStudentMark((DetailedClassActivity) ctx);
			} else if (methodName.equals(Constants.Method_EditAbsance)) {
				return new EditAbsanceTask((DetailedClassActivity) ctx);
			}

			else
				return null;
		}
		/**
		 * AddGradesClassListFragment
		 */
		else if (className.equals(Constants.AddGradesClassListFragment)) {
			return new GetSubjectsAndClassesTask(
					(AddGradesClassListFragment) ctx);
		}

		/**
		 * DetailedClassActivity
		 */
		else if (className.equals(Constants.DetailedClassActivity)) {
			if (methodName
					.equals(Constants.Method_GetGradesAndAbsencesForStudent))
				return new GetAllMarksAndAbsencesForSubjects(
						(DetailedClassActivity) ctx);
			else
				return null;
		}
		/**
		 * DetailedClassListFragment
		 */
		else if (className.equals(Constants.DetailedClassListFragment)) {
			return new GetStudentsTask((DetailedClassListFragment) ctx);
		}
		/**
		 * MenuActivity
		 */
		else if (className.equals(Constants.MenuActivity)) {
			if (methodName.equals(Constants.Method_GetTeacher)) {
				return new GetTeacherTask((MenuActivity) ctx);

			} else if (methodName.equals(Constants.Method_GetTeacherTimetable)) {
				return new GetTeacherTimetableTask((MenuActivity) ctx);
			} else if (methodName.equals(Constants.Method_GetMasterClass))
				return new GetMasterClassTask((MenuActivity) ctx);
			else if (methodName.equals(Constants.Method_GetCurrentSmester)) {
				return new GetCurrentSemesterTask((MenuActivity) ctx);
			} else
				return null;
		}
		/**
		 * LoginActivity
		 */
		else if (className.equals(Constants.LoginActivity)) {
			return new UserLoginTask((LoginActivity) ctx);
		} else
			return null;
	}

	/**
	 * GetStudents task for AddGradesClassStudentsDetailsFragment
	 * 
	 * @author Alex
	 * 
	 */
	private class GetStudentsTaskAddGrades extends
			AsyncTask<Object, Void, Integer> {
		private WeakReference<AddGradesClassStudentsDetailsFragment> mActivityRef;

		public GetStudentsTaskAddGrades(
				AddGradesClassStudentsDetailsFragment activity) {
			mActivityRef = new WeakReference<AddGradesClassStudentsDetailsFragment>(
					activity);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}

			AddGradesClassStudentsDetailsFragment activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

			// disable user input
			activity.showLoading();
		}

		@Override
		protected Integer doInBackground(Object... params) {
			int id = (Integer) params[0];

			AddGradesClassStudentsDetailsFragment activity = mActivityRef.get();

			if (activity == null) {
				return FAIL;
			}

			StudentsVM studentsVM = api.getStudentsForClass(id);

			if (studentsVM == null)
				return FAIL;

			// put them in both places
			activity.students = studentsVM.getStudentList();
			Collections.sort(activity.students, activity.ComparatorByName);
			return SUCCESS;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (mActivityRef == null) {
				return;
			}

			AddGradesClassStudentsDetailsFragment activity = mActivityRef.get();
			if (activity == null) {
				return;
			}
			activity.hideLoading();

			if (activity.students != null && activity.students.size() > 0) {

				for (int i = 0; i < activity.students.size(); i++) {
					// gradesContainer
					// .addView(buildSubjectItem(m_students.get(i)));

					activity.m_adapter.add(activity.students.get(i));
				}
			}

			activity.gridview.setAdapter(activity.m_adapter);
			activity.listview.setAdapter(activity.m_adapter);
			activity.m_adapter.notifyDataSetChanged();

			return;
		}
	}

	/**
	 * GetStudents task for DetailedClassStudentsDetailsFragment
	 * 
	 * @author Alex
	 * 
	 */
	private class GetStudentsTask extends AsyncTask<Object, Void, Integer> {
		private WeakReference<DetailedClassListFragment> mActivityRef;

		public GetStudentsTask(DetailedClassListFragment activity) {
			mActivityRef = new WeakReference<DetailedClassListFragment>(
					activity);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}

			DetailedClassListFragment activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

		}

		@Override
		protected Integer doInBackground(Object... params) {
			int id = (Integer) params[0];
			DetailedClassListFragment activity = mActivityRef.get();

			if (activity == null) {
				return FAIL;
			}

			StudentsVM studentsVM = api.getStudentsForClass(id);

			if (studentsVM == null)
				return FAIL;

			// put them in both places
			activity.myActivity.students = studentsVM.getStudentList();
			activity.students = studentsVM.getStudentList();
			Collections.sort(activity.students, activity.ComparatorByName);
			return SUCCESS;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (mActivityRef == null) {
				return;
			}

			DetailedClassListFragment activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

			if (activity.students != null && activity.students.size() > 0) {

				for (int i = 0; i < activity.students.size(); i++) {
					activity.adapter.add(i + 1 + ". "
							+ activity.students.get(i).getLastName() + " "
							+ activity.students.get(i).getFirstName());
				}
			}

			activity.adapter.notifyDataSetChanged();

			activity.mergeAdapter.addAdapter(activity.adapter);

			activity.setListAdapter(activity.mergeAdapter);
			return;
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 * 
	 * @author Alex
	 */
	private class UserLoginTask extends AsyncTask<Object, Void, Integer> {

		private WeakReference<LoginActivity> mActivityRef;

		public UserLoginTask(LoginActivity activity) {
			mActivityRef = new WeakReference<LoginActivity>(activity);
		}

		@Override
		protected Integer doInBackground(Object... params) {
			String username = (String) params[0];
			String password = (String) params[1];

			// TODO: attempt authentication against a network service.

			int login = api.login(username, password);

			if (login == SUCCESS)
				return SUCCESS;
			else
				return login;
		}

		@Override
		protected void onPostExecute(Integer result) {
			LoginActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}
			activity.showProgress(false);

			if (result == SUCCESS) {
				Intent intent = new Intent(activity, MenuActivity.class);
				activity.startActivity(intent);
				activity.finish();

			} else if (result == Constants.UNAUTHORIZED) {
				activity.mPasswordView.setError(activity
						.getString(R.string.error_incorrect_password));
				activity.mPasswordView.requestFocus();
			} else {
				activity.mPasswordView.setError(activity
						.getString(R.string.bad_connection));
				activity.mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			LoginActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}
			activity.showProgress(false);
		}
	}

	private class ChangePasswordTask extends AsyncTask<Object, Void, Integer> {

		private WeakReference<MenuActivity> mActivityRef;
		private String password;

		public ChangePasswordTask(MenuActivity activity) {
			mActivityRef = new WeakReference<MenuActivity>(activity);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}
			MenuActivity activity = mActivityRef.get();

			activity.showLoading();

		}

		@Override
		protected Integer doInBackground(Object... params) {
			password = (String) params[0];

			// TODO: attempt authentication against a network service.

			boolean success = api.changePassword(password);

			if (success)
				return SUCCESS;
			else
				return FAIL;
		}

		@Override
		protected void onPostExecute(Integer result) {
			MenuActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}
			activity.hideLoading();

			if (result == SUCCESS) {
				AppPreferences prefs = AppPreferences.getInstance(activity);
				LoginCredentials creds = prefs.getLoginCredentials();
				prefs.saveLoginCredentials(creds.getUsername(), password);

				CustomToast toast = new CustomToast(activity, activity
						.getResources().getString(R.string.password_changed));
				toast.show();

			} else {
				CustomToast toast = new CustomToast(activity, activity
						.getResources()
						.getString(R.string.password_not_changed));
				toast.show();
			}
		}

		@Override
		protected void onCancelled() {
			MenuActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}
			activity.hideLoading();
			activity.hideLoading();
			activity.hideLoading();
		}
	}

	/**
	 * Get (or not) the class for which a teacher is master.
	 * 
	 * @author Alex
	 */
	private class GetMasterClassTask extends AsyncTask<Object, Void, Integer> {

		private WeakReference<MenuActivity> mActivityRef;

		public GetMasterClassTask(MenuActivity activity) {
			mActivityRef = new WeakReference<MenuActivity>(activity);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}

			MenuActivity activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

			// disable user input
			activity.showLoading();
		}

		@Override
		protected Integer doInBackground(Object... params) {
			int id = (Integer) params[0];
			MenuActivity activity = mActivityRef.get();

			MasterClassVM m = api.getMasterClass(id);

			if (m != null)
				activity.setMasterClassGroup(m.getClassGroup());

			return SUCCESS;
		}

		@Override
		protected void onPostExecute(Integer result) {
			MenuActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}

			// enable user input
			activity.hideLoading();

		}

		@Override
		protected void onCancelled() {
			MenuActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}

		}
	}

	/**
	 * Gets (or not) the class for which a teacher is master.
	 * 
	 * @author Alex
	 */
	private class GetAllMarksAndAbsencesForSubjects extends
			AsyncTask<Object, Void, Integer> {

		private WeakReference<DetailedClassActivity> mActivityRef;
		private int studentIndexInList;
		private ArrayList<GradesAttendForSubject> gradesAndAttendances;

		public GetAllMarksAndAbsencesForSubjects(DetailedClassActivity activity) {
			mActivityRef = new WeakReference<DetailedClassActivity>(activity);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}

			DetailedClassActivity activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

			// disable user input
			activity.showLoading();
		}

		@Override
		protected Integer doInBackground(Object... params) {

			int studentId = (Integer) params[0];
			studentIndexInList = (Integer) params[1];

			ArrayList<GradesAttendForSubject> m = api
					.getGradesAttendForSubjectList(studentId);

			if (m != null) {
				this.gradesAndAttendances = m;
				return SUCCESS;
			} else
				return FAIL;
		}

		@Override
		protected void onPostExecute(Integer result) {
			DetailedClassActivity activity = mActivityRef.get();
			if (activity == null || result == FAIL) {
				return;
			}

			activity.showStudents(studentIndexInList, gradesAndAttendances);
			// enable user input
			activity.hideLoading();

		}

		@Override
		protected void onCancelled() {
			DetailedClassActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}

		}
	}

	/**
	 * Get (or not) the currently logged in teacher.
	 * 
	 * @author Alex
	 */
	private class GetTeacherTask extends AsyncTask<Object, Void, Integer> {

		private WeakReference<MenuActivity> mActivityRef;
		private TeacherVM teacher;

		public GetTeacherTask(MenuActivity activity) {
			mActivityRef = new WeakReference<MenuActivity>(activity);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}

			MenuActivity activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

			// disable user input
			activity.showLoading();
		}

		@Override
		protected Integer doInBackground(Object... params) {

			MenuActivity activity = mActivityRef.get();
			if (activity == null)
				return FAIL;

			this.teacher = api.getTeacher();

			if (teacher != null)
				if (teacher.getTeacher() != null) {
					return SUCCESS;
				} else
					return FAIL;
			else
				return FAIL;
		}

		@Override
		protected void onPostExecute(Integer result) {
			MenuActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}
			if (result == SUCCESS) {
				activity.setTeacher(teacher.getTeacher());
			} else {
				// fail
			}
			// enable user input
			activity.hideLoading();

		}

		@Override
		protected void onCancelled() {
			MenuActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}

		}
	}

	/**
	 * Get (or not) the currently logged in teacher.
	 * 
	 * @author Alex
	 */
	private class GetCurrentSemesterTask extends
			AsyncTask<Object, Void, Integer> {

		private WeakReference<MenuActivity> mActivityRef;
		private SemesterVM currentSemester;

		public GetCurrentSemesterTask(MenuActivity activity) {
			mActivityRef = new WeakReference<MenuActivity>(activity);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}

			MenuActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}

			// disable user input
			activity.showLoading();
		}

		@Override
		protected Integer doInBackground(Object... params) {

			MenuActivity activity = mActivityRef.get();
			if (activity == null)
				return FAIL;

			this.currentSemester = api.getCurrentSemester();

			if (currentSemester != null)
				return SUCCESS;
			else
				return SUCCESS;

		}

		@Override
		protected void onPostExecute(Integer result) {
			MenuActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}
			if (result == SUCCESS) {
				// activity.setCurrentSemester(currentSemester.getSemester());
			} else {
				// fail
			}
			// enable user input
			activity.hideLoading();

		}

		@Override
		protected void onCancelled() {
			MenuActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}

		}
	}

	/**
	 * Add mark task.
	 * 
	 * @author Alex
	 */
	private class AddMarkTask extends AsyncTask<Object, Void, Integer> {

		private WeakReference<DetailedClassActivity> mActivityRef;
		private StudentMark mark;
		private int positionInSubjectsList;
		private DetailedClassStudentsDetailsFragment fragment;

		public AddMarkTask(DetailedClassActivity activity) {
			mActivityRef = new WeakReference<DetailedClassActivity>(activity);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}

			DetailedClassActivity activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

			// disable user input
			activity.showLoading();
		}

		@Override
		protected Integer doInBackground(Object... params) {
			int classGroupId = (Integer) params[0];
			int subjectId = (Integer) params[1];
			int teacherId = (Integer) params[2];
			Student student = (Student) params[3];
			int grade = (Integer) params[4];
			boolean finalExam = (Boolean) params[5];
			long date = (Long) params[6];
			positionInSubjectsList = (Integer) params[7];
			fragment = (DetailedClassStudentsDetailsFragment) params[8];

			DetailedClassActivity activity = mActivityRef.get();
			if (activity == null)
				return FAIL;

			SubjectTeacherForClassVM stfcVM = api.getSubjectTeacherForClass(
					classGroupId, subjectId, teacherId);

			SubjectTeacherForClass stfc = stfcVM.getSubjectTeacherForClass();

			if (stfc.getId() > -1 && student.getId() > -1)
				mark = api.addStudentMark(student.getId(), stfc.getId(), grade,
						finalExam, date);
			else
				return FAIL;
			if (mark != null && mark.getId() > -1)
				return SUCCESS;
			else
				return FAIL;
		}

		@Override
		protected void onPostExecute(Integer result) {
			DetailedClassActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}
			// TODO: AI GRIJA SA SCHIMBI AICI. TEMPORAR PETNRU FAZA CU
			// SEMESTRELE

			if (result == SUCCESS) {
				CustomToast toast = new CustomToast(activity, activity
						.getResources().getString(R.string.note_added));

				fragment.gradesAndAttendances.get(positionInSubjectsList)
						.getMarks().add(mark);

				fragment.reconstructGradesText(positionInSubjectsList);
				fragment.m_adapter.notifyDataSetChanged();
				toast.show();

			} else {
				CustomToast toast = new CustomToast(activity, activity
						.getResources().getString(R.string.note_not_added));

				toast.show();
			}
			// enable user input
			activity.hideLoading();

		}

		@Override
		protected void onCancelled() {
			DetailedClassActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}

		}
	}

	/**
	 * Edit a mark for a student
	 * 
	 * @author Alex
	 */
	private class EditStudentMark extends AsyncTask<Object, Void, Integer> {

		private WeakReference<DetailedClassActivity> mActivityRef;
		private int positionInSubjectsList;
		private DetailedClassStudentsDetailsFragment fragment;
		private int markId;
		private int newMark;

		public EditStudentMark(DetailedClassActivity activity) {
			mActivityRef = new WeakReference<DetailedClassActivity>(activity);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}

			DetailedClassActivity activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

			// disable user input
			activity.showLoading();
		}

		@Override
		protected Integer doInBackground(Object... params) {
			markId = (Integer) params[0];
			newMark = (Integer) params[1];
			long date = (Long) params[2];
			positionInSubjectsList = (Integer) params[3];
			fragment = (DetailedClassStudentsDetailsFragment) params[4];

			StudentMark sm = api.editStudentMark(markId, newMark, date);

			if (sm != null)
				return SUCCESS;
			else
				return FAIL;
		}

		@Override
		protected void onPostExecute(Integer result) {
			DetailedClassActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}
			if (result == SUCCESS) {
				CustomToast toast = new CustomToast(activity, activity
						.getResources().getString(R.string.changes_made));

				for (StudentMark s : fragment.gradesAndAttendances.get(
						positionInSubjectsList).getMarks()) {
					if (s.getId() == markId)
						s.setMark(newMark);
				}

				fragment.reconstructGradesText(positionInSubjectsList);
				fragment.m_adapter.notifyDataSetChanged();
				toast.show();
			} else {
				CustomToast toast = new CustomToast(activity, activity
						.getResources().getString(R.string.changes_not_made));
				toast.show();
			}
			// enable user input
			activity.hideLoading();

		}

		@Override
		protected void onCancelled() {
			DetailedClassActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}

		}
	}

	/**
	 * Add absence task.
	 * 
	 * @author Alex
	 */
	private class AddAbsanceTask extends AsyncTask<Object, Void, Integer> {

		private WeakReference<DetailedClassActivity> mActivityRef;
		private Attendance attendance;
		private int positionInSubjectsList;
		private DetailedClassStudentsDetailsFragment fragment;

		public AddAbsanceTask(DetailedClassActivity ctx) {
			mActivityRef = new WeakReference<DetailedClassActivity>(ctx);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}

			DetailedClassActivity activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

			// disable user input
			activity.showLoading();
		}

		@Override
		protected Integer doInBackground(Object... params) {
			int classGroupId = (Integer) params[0];
			int subjectId = (Integer) params[1];
			int teacherId = (Integer) params[2];
			Student student = (Student) params[3];
			long date = (Long) params[4];
			positionInSubjectsList = (Integer) params[5];
			fragment = (DetailedClassStudentsDetailsFragment) params[6];

			DetailedClassActivity activity = mActivityRef.get();
			if (activity == null)
				return FAIL;

			SubjectTeacherForClassVM stfcVM = api.getSubjectTeacherForClass(
					classGroupId, subjectId, teacherId);
			SubjectTeacherForClass stfc = stfcVM.getSubjectTeacherForClass();

			if (stfc.getId() > -1 && student.getId() > -1)
				attendance = api.addAttendance(student.getId(), stfc.getId(),
						date);
			else
				return FAIL;
			if (attendance != null)
				return SUCCESS;
			else
				return FAIL;
		}

		@Override
		protected void onPostExecute(Integer result) {
			DetailedClassActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}
			if (result == SUCCESS) {
				CustomToast toast = new CustomToast(activity, activity
						.getResources().getString(R.string.absance_added));
				fragment.gradesAndAttendances.get(positionInSubjectsList)
						.getAttendaces().add(attendance);
				fragment.reconstructAbsancesText(positionInSubjectsList);
				fragment.m_adapter.notifyDataSetChanged();
				toast.show();

			} else {
				CustomToast toast = new CustomToast(activity, activity
						.getResources().getString(R.string.absance_not_added));

				toast.show();
			}
			// enable user input
			activity.hideLoading();

		}

		@Override
		protected void onCancelled() {
			DetailedClassActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}

		}
	}

	/**
	 * Edit an absance for a student
	 * 
	 * @author Alex
	 */
	private class EditAbsanceTask extends AsyncTask<Object, Void, Integer> {

		private WeakReference<DetailedClassActivity> mActivityRef;
		private Attendance attendance;
		private int positionInSubjectsList;
		private DetailedClassStudentsDetailsFragment fragment;
		private int attendanceId;

		public EditAbsanceTask(DetailedClassActivity activity) {
			mActivityRef = new WeakReference<DetailedClassActivity>(activity);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}

			DetailedClassActivity activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

			// disable user input
			activity.showLoading();
		}

		@Override
		protected Integer doInBackground(Object... params) {
			attendanceId = (Integer) params[0];
			positionInSubjectsList = (Integer) params[1];
			fragment = (DetailedClassStudentsDetailsFragment) params[2];

			attendance = api.editAttendance(attendanceId, true);

			if (attendance != null)
				return SUCCESS;
			else
				return FAIL;
		}

		@Override
		protected void onPostExecute(Integer result) {
			DetailedClassActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}
			if (result == SUCCESS) {
				CustomToast toast = new CustomToast(activity, activity
						.getResources().getString(R.string.changes_made));

				for (Attendance a : fragment.gradesAndAttendances.get(
						positionInSubjectsList).getAttendaces()) {
					if (a.getId() == attendanceId)
						a.setMotivat(true);
				}

				fragment.reconstructAbsancesText(positionInSubjectsList);
				fragment.m_adapter.notifyDataSetChanged();
				toast.show();
			} else {
				CustomToast toast = new CustomToast(activity, activity
						.getResources().getString(R.string.changes_not_made));

				toast.show();
			}
			// enable user input
			activity.hideLoading();

		}

		@Override
		protected void onCancelled() {
			DetailedClassActivity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}

		}
	}

	/**
	 * Gets all subjects and corresponding classes for the logged teacher.
	 * 
	 * @author Alex
	 */
	private class GetSubjectsAndClassesTask extends
			AsyncTask<Object, Void, Integer> {

		private SubjectClassesVM cfast;
		private WeakReference<AddGradesClassListFragment> mActivityRef;

		public GetSubjectsAndClassesTask(AddGradesClassListFragment ctx) {
			mActivityRef = new WeakReference<AddGradesClassListFragment>(ctx);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}

			AddGradesClassListFragment activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

			// disable user input
		}

		@Override
		protected Integer doInBackground(Object... params) {

			AddGradesClassListFragment activity = mActivityRef.get();
			if (activity == null)
				return FAIL;

			cfast = api.getSubjectsForTeacherSubjects();

			if (cfast != null)
				return SUCCESS;
			else
				return FAIL;
		}

		@Override
		protected void onPostExecute(Integer result) {
			AddGradesClassListFragment activity = mActivityRef.get();
			if (activity == null) {
				return;
			}
			if (result == SUCCESS) {
				activity.subjectClassesList = cfast.getSubjectClassesList();
				activity.myActivity.subjectClassesList = cfast
						.getSubjectClassesList();

				int size = cfast.getSubjectClassesList().size();
				activity.classesAdapter = new ArrayAdapter[size];
				for (int i = 0; i < size; i++) {

					SubjectClasses subjectClasses = cfast
							.getSubjectClassesList().get(i);

					Subject subject = subjectClasses.getSubject();

					if (subject != null) {
						activity.classesAdapter[i] = new ArrayAdapter<String>(
								activity.getActivity(),
								R.layout.listitem_class_and_student,
								R.id.tv_class_or_student);

						activity.mergeAdapter.addView(activity
								.buildSubjectListItem(subject.getName()));

						for (SubjectClasses sc : cfast.getSubjectClassesList()) {
							Collections.sort(sc.getClasses(),
									activity.ComparatorByYearOfStudy);
						}

						for (ClassGroup c : cfast.getSubjectClassesList()
								.get(i).getClasses()) {

							activity.classesAdapter[i].add("Clasa a"
									+ c.getYearOfStudy() + "-a " + c.getName());
						}
						activity.classesAdapter[i].notifyDataSetChanged();
						activity.mergeAdapter
								.addAdapter(activity.classesAdapter[i]);

					} else {
						CustomToast t = new CustomToast(activity.getActivity(),
								activity.getResources().getString(
										R.string.no_subject_entries));
						t.show();
					}

					activity.setListAdapter(activity.mergeAdapter);
				}

			} else {
			}
			// enable user input

		}

		@Override
		protected void onCancelled() {
			AddGradesClassListFragment activity = mActivityRef.get();
			if (activity == null) {
				return;
			}

		}
	}

	/**
	 * Get (or not) the class for which a teacher is master.
	 * 
	 * @author Alex
	 */
	private class GetTeacherTimetableTask extends
			AsyncTask<Object, Void, Integer> {

		private WeakReference<Activity> mActivityRef;

		public GetTeacherTimetableTask(Activity activity) {
			mActivityRef = new WeakReference<Activity>(activity);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}

			Activity activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

			// disable user input
			if (activity instanceof MenuActivity)
				((MenuActivity) activity).showLoading();
		}

		@Override
		protected Integer doInBackground(Object... params) {
			Activity activity = mActivityRef.get();

			ArrayList<TimetableDays> tt = api.getTeacherTimetable();

			if (tt != null) {
				if (activity instanceof MenuActivity)
					((MenuActivity) activity).setTimetable(tt);
				else {
					((TimetableActivity) activity).timetable = tt;
					((TimetableActivity) activity).refreshLists();

				}
				return SUCCESS;
			} else
				return FAIL;
		}

		@Override
		protected void onPostExecute(Integer result) {

			Activity activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

			if (result == SUCCESS) {

			} else if (result == FAIL) {

			}
			// enable user input
			if (activity instanceof MenuActivity)
				((MenuActivity) activity).hideLoading();

		}

		@Override
		protected void onCancelled() {
			Activity activity = mActivityRef.get();
			if (activity == null) {
				return;
			}

		}
	}
}
