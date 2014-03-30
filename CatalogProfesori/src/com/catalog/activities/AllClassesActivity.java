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
import java.util.List;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.catalog.activities.fragments.AllClassesStudentsDetailsFragment;
import com.catalog.core.CatalogApplication;
import com.catalog.dialogs.AddGradesForAllClassDialog;
import com.catalog.helper.Constants;
import com.catalog.model.ClassGroup;
import com.catalog.model.Semester;
import com.catalog.model.Student;
import com.catalog.model.Subject;
import com.catalog.model.SubjectClasses;
import com.catalog.model.Teacher;
import com.catalog.model.views.SemesterVM;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

/**
 * Activity which covers the teachers (not head of class) views.
 * 
 * @author Alex
 */
public class AllClassesActivity extends Activity {
	/*
	 * Static members
	 */
	private static final String CLASS_NAME = Constants.AllClassesActivity;

	/*
	 * Public members
	 */
	public ArrayList<SubjectClasses> subjectClassesList;

	/*
	 * Private members
	 */
	private TextView classTitle;
	private ProgressBar progressBar;
	private ClassGroup classGroup;
	private ImageButton ibGrid, ibList;
	private Button btnClassDetails;
	private Button btnAddGradesAllClass;
	private boolean viewingAsList;
	private int mIndex;
	private int mPosition;

	private ArrayList<Student> students;
	private Teacher teacher;
	private SemesterVM semestersInfo;

	private Subject subject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_class);
		CatalogApplication.getGaTracker().set(Fields.SCREEN_NAME, CLASS_NAME);
		initUI();
	}

	/**
	 * Initializes the UI elements.
	 */
	private void initUI() {
		classTitle = (TextView) findViewById(R.id.tv_className);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		btnClassDetails = (Button) findViewById(R.id.btnClassDetails);
		btnAddGradesAllClass = (Button) findViewById(R.id.btnAddGradesAllClass);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			teacher = (Teacher) extras
					.getSerializable(Constants.Bundle_Teacher);
			semestersInfo = (SemesterVM) extras
					.getSerializable(Constants.Bundle_Semester);
		}

		btnClassDetails.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (classGroup != null) {
					Intent intent = new Intent(getApplicationContext(),
							DetailedClassActivity.class);
					Bundle b = new Bundle();
					b.putSerializable(Constants.Bundle_ClassGroup, classGroup);
					b.putSerializable(Constants.Bundle_Teacher, teacher);
					b.putSerializable(Constants.Bundle_Semester, semestersInfo);
					intent.putExtras(b);
					startActivity(intent);
				}
			}
		});

		btnAddGradesAllClass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AddGradesForAllClassDialog d = new AddGradesForAllClassDialog(
						AllClassesActivity.this, students, subject, teacher,
						classGroup);
				d.show();
			}
		});

		ibGrid = (ImageButton) findViewById(R.id.ib_grid);
		ibGrid.setOnClickListener(viewChangerClickListener);
		ibList = (ImageButton) findViewById(R.id.ib_list);
		ibList.setOnClickListener(viewChangerClickListener);

		viewingAsList = false;
		ibGrid.setBackgroundResource(R.drawable.ic_grid_selected);
		ibList.setBackgroundResource(R.drawable.ic_list_unselected);

	}

	public boolean isMultiPane() {
		return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	/**
	 * Helper function to show the details of a selected item, either by
	 * displaying a fragment in-place in the current UI, or starting a whole new
	 * activity in which it is displayed.
	 */
	public void showStudents(int subjectNumber, int position, boolean asList) {
		btnAddGradesAllClass.setVisibility(View.INVISIBLE);
		if (isMultiPane()) {
			// Check what fragment is shown, replace if needed.
			// basically it's the frame layout
			mIndex = subjectNumber;
			mPosition = position;
			// Make new fragment to show this selection.
			subject = subjectClassesList.get(subjectNumber).getSubject();

			SubjectClasses subjectClass = subjectClassesList.get(subjectNumber);

			if (subjectClass != null) {
				List<ClassGroup> classes = subjectClass.getClasses();

				if (classes != null) {
					classGroup = classes.get(position);
					classTitle.setText("Clasa a" + classGroup.getYearOfStudy()
							+ "-a " + classGroup.getName());

					AllClassesStudentsDetailsFragment details = AllClassesStudentsDetailsFragment
							.newInstance(subjectNumber, subject, classGroup,
									teacher, viewingAsList);

					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					ft.setCustomAnimations(R.animator.fade_in,
							R.animator.fade_out);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.replace(R.id.vf_details, details);
					ft.commit();
					getFragmentManager().executePendingTransactions();

					btnClassDetails.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	OnClickListener viewChangerClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.ib_grid) {
				if (viewingAsList) {
					viewingAsList = false;
					showStudents(mIndex, mPosition, viewingAsList);
					ibGrid.setBackgroundResource(R.drawable.ic_grid_selected);
					ibList.setBackgroundResource(R.drawable.ic_list_unselected);
				}
			} else {
				if (!viewingAsList) {
					viewingAsList = true;
					showStudents(mIndex, mPosition, viewingAsList);
					ibGrid.setBackgroundResource(R.drawable.ic_grid_unselected);
					ibList.setBackgroundResource(R.drawable.ic_list_selected);
				}
			}
		}
	};

	/**
	 * @return the students
	 */
	public ArrayList<Student> getStudents() {
		return students;
	}

	/**
	 * @param students
	 *            the students to set
	 */
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
		btnAddGradesAllClass.setVisibility(View.VISIBLE);
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
