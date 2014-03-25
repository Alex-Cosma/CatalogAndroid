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
package com.catalog.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.catalog.activities.fragments.DetailedClassStudentsDetailsFragment;
import com.catalog.core.AppPreferences;
import com.catalog.core.AsyncTaskFactory;
import com.catalog.core.CatalogApplication;
import com.catalog.helper.Constants;
import com.catalog.helper.Helpers;
import com.catalog.model.ClassGroup;
import com.catalog.model.GradesAttendForSubject;
import com.catalog.model.Semester;
import com.catalog.model.Student;
import com.catalog.model.Teacher;
import com.catalog.model.views.SemesterVM;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

/**
 * Activity which covers the head of class (not teacher) views. <br>
 * Also covers the detailed views on teachers (but not for all the subjects).
 * 
 * @author Alex
 * 
 */
public class DetailedClassActivity extends Activity {

	/*
	 * Static members3
	 */
	private static final String CLASS_NAME = Constants.DetailedClassActivity;

	/*
	 * Public members
	 */
	public ArrayList<Student> students;

	/*
	 * Private members
	 */
	private ClassGroup classGroup;
	private Teacher teacher;
	private SemesterVM semestersInfo;
	private Semester currentSemester;
	private int currentSemesterIndex;

	private TextView classTitle;
	private ProgressBar progressBar;
	private AsyncTaskFactory asyncTaskFactory;
	private AsyncTask<Object, Void, Integer> getGradesAndAbsancesTask;
	private int currentSelectedStudentIndex;

	private Button btnToggleSemester;
	private TextView tvSemester;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_myclass);
		CatalogApplication.getGaTracker().set(Fields.SCREEN_NAME, CLASS_NAME);
		initUI();
	}

	private void initUI() {
		currentSelectedStudentIndex = -1;

		btnToggleSemester = (Button) findViewById(R.id.btnToggleSemester);

		View headerView = findViewById(R.id.listitem_grades_head);
		tvSemester = (TextView) headerView.findViewById(R.id.tv_semester);

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		classTitle = (TextView) findViewById(R.id.tv_className);
		asyncTaskFactory = AsyncTaskFactory.getInstance(AppPreferences
				.getInstance(this).isIpExternal());

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			classGroup = (ClassGroup) extras
					.getSerializable(Constants.Bundle_ClassGroup);
			teacher = (Teacher) extras
					.getSerializable(Constants.Bundle_Teacher);
			semestersInfo = (SemesterVM) extras
					.getSerializable(Constants.Bundle_Semester);
		}
		// if classGroup is null finish everything, no use going further
		if (classGroup == null)
			finish();

		if (semestersInfo != null) {
			currentSemester = semestersInfo.getCurrentSemester();
			currentSemesterIndex = semestersInfo.getSemesterList().indexOf(
					currentSemester);

			updateViewsForSemesterChange();

		}

		classTitle.setText("Clasa a" + classGroup.getYearOfStudy() + "-a "
				+ classGroup.getName());

		btnToggleSemester.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentSelectedStudentIndex > -1) {
					
					currentSemesterIndex = (currentSemesterIndex + 1) % 2;
					currentSemester = semestersInfo.getSemesterList().get(
							currentSemesterIndex);

					updateViewsForSemesterChange();
					
					showStudents(currentSelectedStudentIndex);
				
					btnToggleSemester.setVisibility(View.INVISIBLE);
				}
			}
		});

	}

	public boolean isMultiPane() {
		return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	public void showStudents(int pos) {
		currentSelectedStudentIndex = pos;
		
		getGradesAndAbsancesTask = asyncTaskFactory.getTask(this, CLASS_NAME,
				Constants.Method_GetGradesAndAbsencesForStudent);

		getGradesAndAbsancesTask.execute(students.get(pos).getId(), pos, currentSemester);
		// this will be called later from the async thread
		// showStudents(pos, gradesAndAttendances);
	}

	/**
	 * Helper function to show the details of a selected item, either by
	 * displaying a fragment in-place in the current UI, or starting a whole new
	 * activity in which it is displayed.
	 */
	public void showStudents(int studentIndex,
			ArrayList<GradesAttendForSubject> gradesAndAttendances) {
		if (students == null)
			return;
		if (isMultiPane()) {

			// Check what fragment is shown, replace if needed.
			// basically it's the frame layout

			Student selectedStudent = students.get(studentIndex);

			// Make new fragment to show this selection.
			DetailedClassStudentsDetailsFragment details = DetailedClassStudentsDetailsFragment
					.newInstance(studentIndex, selectedStudent, classGroup,
							teacher, gradesAndAttendances);

			classTitle.setText("Clasa a" + classGroup.getYearOfStudy() + "-a "
					+ classGroup.getName() + " - "
					+ selectedStudent.getLastName() + " "
					+ selectedStudent.getFirstName());

			// Execute a transaction, replacing any existing
			// fragment with this one inside the frame.
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.replace(R.id.listview, details);
			// ft.addToBackStack(TAG);
			ft.commit();
			getFragmentManager().executePendingTransactions();
			btnToggleSemester.setVisibility(View.VISIBLE);
		}
	}

	private void updateViewsForSemesterChange() {
		tvSemester.setText(Helpers.getFormattedSemesterName(currentSemester
				.getName()));
		Semester otherSemester = semestersInfo.getSemesterList().get(
				(currentSemesterIndex + 1) % 2);
		btnToggleSemester.setText(Helpers
				.getFormattedSemesterName(otherSemester.getName()));
	}

	public ClassGroup getClassGroup() {
		return this.classGroup;
	}

	public void showLoading() {
		progressBar.setVisibility(View.VISIBLE);

	}

	public void hideLoading() {
		progressBar.setVisibility(View.INVISIBLE);
	}

	public boolean isLoading() {
		return (progressBar.getVisibility() == View.VISIBLE) ? true : false;
	}

	@Override
	public void onStart() {
		super.onStart();
		CatalogApplication.getGaTracker().send(
				MapBuilder.createAppView().build());
	}

	@Override
	public void onStop() {
		super.onStop();
		CatalogApplication.getGaTracker().set(Fields.SCREEN_NAME, null);
	}

}
