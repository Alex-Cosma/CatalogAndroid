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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.catalog.activities.fragments.AllClassesStudentsDetailsFragment;
import com.catalog.helper.Constants;
import com.catalog.model.ClassGroup;
import com.catalog.model.Subject;
import com.catalog.model.SubjectClasses;
import com.catalog.model.Teacher;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * Activity which covers the teachers (not head of class) views.
 * 
 * @author Alex
 */
public class AllClassesActivity extends Activity {
	/*
	 * Static members
	 */
	private static final String CLASSNAME = Constants.AddGradesClassActivity;

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
	private Button btnInfo;
	private boolean viewingAsList;
	private Teacher teacher;
	private int mIndex;
	private int mPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_class);
		EasyTracker.getInstance(this).activityStart(this);
		initUI();
	}

	/**
	 * Initializes the UI elements.
	 */
	private void initUI() {
		classTitle = (TextView) findViewById(R.id.tv_className);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		btnInfo = (Button) findViewById(R.id.ib_info);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			teacher = (Teacher) extras
					.getSerializable(Constants.Bundle_Teacher);
		}

		btnInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (classGroup != null) {
					Intent intent = new Intent(getApplicationContext(),
							DetailedClassActivity.class);
					Bundle b = new Bundle();
					b.putSerializable(Constants.Bundle_ClassGroup, classGroup);
					b.putSerializable(Constants.Bundle_Teacher, teacher);
					intent.putExtras(b);
					startActivity(intent);
				}
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

		if (isMultiPane()) {
			// Check what fragment is shown, replace if needed.
			// basically it's the frame layout
			mIndex = subjectNumber;
			mPosition = position;
			// Make new fragment to show this selection.
			Subject subject = subjectClassesList.get(subjectNumber)
					.getSubject();

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

					// Execute a transaction, replacing any existing
					// fragment with this one inside the frame.
					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					// See our res/animator directory for more animator
					// choices
					// ft.setCustomAnimations(R.animator.bounce_in_down,
					// R.animator.slide_out_down);
					ft.setCustomAnimations(R.animator.fade_in,
							R.animator.fade_out);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.replace(R.id.vf_details, details);
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
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}
}
