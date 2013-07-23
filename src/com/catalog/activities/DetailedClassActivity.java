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
import java.util.Calendar;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.catalog.activities.extras.AsyncTaskFactory;
import com.catalog.helper.AppPreferences;
import com.catalog.helper.Constants;
import com.catalog.model.ClassGroup;
import com.catalog.model.GradesAttendForSubject;
import com.catalog.model.Student;
import com.catalog.model.Subject;
import com.catalog.model.Teacher;

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
	private static final String CLASSNAME = Constants.DetailedClassActivity;

	/*
	 * Public members
	 */
	public ArrayList<Student> students;
	public ArrayList<GradesAttendForSubject> gradesAndAttendances;

	/*
	 * Private members
	 */
	private TextView classTitle;
	private int mIndex;
	private ClassGroup classGroup;
	private Teacher teacher;
	private ProgressBar progressBar;
	private AsyncTaskFactory asyncTaskFactory;
	private AsyncTask<Object, Void, Integer> getGradesAndAbsancesTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_myclass);

		initUI();
	}

	private void initUI() {
		View headerView = findViewById(R.id.listitem_grades_head);
		TextView semester = (TextView) headerView
				.findViewById(R.id.tv_semester);

		if ((Calendar.getInstance().get(Calendar.MONTH) > 9 && Calendar
				.getInstance().get(Calendar.MONTH) < 12)
				|| (Calendar.getInstance().get(Calendar.MONTH) > 0 && Calendar
						.getInstance().get(Calendar.MONTH) < 2)) {
			semester.setText("Sem I");
		} else
			semester.setText("Sem II");
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		classTitle = (TextView) findViewById(R.id.tv_className);
		asyncTaskFactory = AsyncTaskFactory.getInstance(AppPreferences
				.getInstance(this).isIpExternal());

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			classGroup = (ClassGroup) extras
					.getSerializable(Constants.ClassGroup);
			teacher = (Teacher) extras.getSerializable(Constants.Teacher);
		}
		// if classGroup is null finish everything, no use going further
		if (classGroup == null)
			finish();

		classTitle.setText("Clasa a" + classGroup.getYearOfStudy() + "-a "
				+ classGroup.getName());

	}

	public boolean isMultiPane() {
		return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	public void showStudents(int pos) {
		getGradesAndAbsancesTask = asyncTaskFactory.getTask(this, CLASSNAME,
				Constants.Method_GetGradesAndAbsencesForStudent);

		getGradesAndAbsancesTask.execute(students.get(pos).getId(), pos);
		// this will be called later
		// showStudents(pos, subjects);
	}

	/**
	 * Helper function to show the details of a selected item, either by
	 * displaying a fragment in-place in the current UI, or starting a whole new
	 * activity in which it is displayed.
	 */
	public void showStudents(int index, ArrayList<Subject> subjects) {
		if (students == null)
			return;
		if (isMultiPane()) {

			// Check what fragment is shown, replace if needed.
			// basically it's the frame layout
			mIndex = index;

			Student selectedStudent = students.get(mIndex);

			// Make new fragment to show this selection.
			DetailedClassStudentsDetailsFragment details = DetailedClassStudentsDetailsFragment
					.newInstance(index, selectedStudent, classGroup, teacher,
							gradesAndAttendances);

			classTitle.setText("Clasa a" + classGroup.getYearOfStudy() + "-a "
					+ classGroup.getName() + " - "
					+ selectedStudent.getLastName() + " "
					+ selectedStudent.getFirstName());

			// Execute a transaction, replacing any existing
			// fragment with this one inside the frame.
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			// See our res/animator directory for more animator
			// choices
			// ft.setCustomAnimations(R.animator.bounce_in_down,
			// R.animator.slide_out_down);
			ft.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.replace(R.id.listview, details);
			// ft.addToBackStack(TAG);
			ft.commit();
			getFragmentManager().executePendingTransactions();

			// Otherwise we need to launch a new activity to display
			// the dialog fragment with selected text.
			/**
			 * shouldn't get here, forcing landscape
			 */
			// Intent intent = new Intent();
			// intent.setClass(this, DetailsActivity.class);
			// intent.putExtra("index", index);
			// startActivity(intent);
		}
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

}
