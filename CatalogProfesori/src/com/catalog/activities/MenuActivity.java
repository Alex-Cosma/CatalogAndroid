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
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;

import com.catalog.core.AppPreferences;
import com.catalog.core.AsyncTaskFactory;
import com.catalog.core.CatalogApplication;
import com.catalog.dialogs.ChangePasswordDialog;
import com.catalog.game.GameMainActivity;
import com.catalog.helper.Constants;
import com.catalog.helper.CustomToast;
import com.catalog.model.ClassGroup;
import com.catalog.model.Semester;
import com.catalog.model.Teacher;
import com.catalog.model.TimetableDays;
import com.example.android.notepad.NotesList;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

/**
 * Activity which displays the main menu of the application.
 * 
 * @author Alex
 * 
 */
public class MenuActivity extends Activity implements OnTouchListener {

	/*
	 * Static members
	 */
	private static final String CLASS_NAME = Constants.MenuActivity;

	/*
	 * Public members
	 */

	/*
	 * Private members
	 */

	private int NUMBER_OF_TASKS = 4;

	private Button btnMyClass;
	private Button btnTimeTable;
	private Button btnMarks;
	private Button btnStats;
	private Button btnNotes;
	private Button btnExtras;
	private Animation animScaleUp, animScaleDown;
	private Activity act;
	private ProgressBar progressBar;
	private Context ctx;
	private AsyncTaskFactory asyncTaskFactory;
	private AsyncTask<Object, Void, Integer> getMasterClassTask;
	private AsyncTask<Object, Void, Integer> getTeacherTask;
	private AsyncTask<Object, Void, Integer> getTimetableTask;
	private AsyncTask<Object, Void, Integer> getCurrentSemesterTask;
	private ClassGroup masterClassGroup;
	private Teacher teacher;
	private ArrayList<TimetableDays> timetable;
	private Semester currentSemester;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		setContentView(R.layout.activity_menu);
		CatalogApplication.getGaTracker().set(Fields.SCREEN_NAME, CLASS_NAME);

		initUI();
	}

	private void initUI() {
		ctx = getApplicationContext();
		act = this;
		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		initButtons();
		initTasks();
	}

	private void initButtons() {
		btnMyClass = (Button) findViewById(R.id.btn_myClass);
		btnTimeTable = (Button) findViewById(R.id.btn_timetable);
		btnMarks = (Button) findViewById(R.id.btn_marks);
		btnStats = (Button) findViewById(R.id.btn_stats);
		btnNotes = (Button) findViewById(R.id.btn_notes);
		btnExtras = (Button) findViewById(R.id.btn_extra);

		btnMyClass.setOnTouchListener(this);
		btnMarks.setOnTouchListener(this);
		btnStats.setOnTouchListener(this);
		btnNotes.setOnTouchListener(this);
		btnExtras.setOnTouchListener(this);
		btnTimeTable.setOnTouchListener(this);

		animScaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
		animScaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);
	}

	private void initTasks() {
		asyncTaskFactory = AsyncTaskFactory.getInstance(AppPreferences
				.getInstance(this).isIpExternal());

		getMasterClassTask = asyncTaskFactory.getTask(this, CLASS_NAME,
				Constants.Method_GetMasterClass);
		getMasterClassTask.execute(2);

		getTeacherTask = asyncTaskFactory.getTask(this, CLASS_NAME,
				Constants.Method_GetTeacher);
		getTeacherTask.execute();

		getTimetableTask = asyncTaskFactory.getTask(this, CLASS_NAME,
				Constants.Method_GetTeacherTimetable);
		getTimetableTask.execute();

		getCurrentSemesterTask = asyncTaskFactory.getTask(this, CLASS_NAME,
				Constants.Method_GetCurrentSmester);
		getCurrentSemesterTask.execute();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			v.startAnimation(animScaleDown);

		} else if (event.getAction() == MotionEvent.ACTION_UP) {

			if (v == btnMyClass) {
				btnMyClass.startAnimation(animScaleUp);
				btnMyClass.clearAnimation();
				if (getMasterClassGroup().getId() > -1) {
					if (getTeacher() != null) {
						Intent intent = new Intent(ctx,
								DetailedClassActivity.class);
						Bundle b = new Bundle();
						b.putSerializable(Constants.Bundle_ClassGroup,
								getMasterClassGroup());
						b.putSerializable(Constants.Bundle_Teacher,
								getTeacher());
						intent.putExtras(b);
						startActivity(intent);
					} else {

					}
				} else {
					CustomToast toast = new CustomToast(act, getResources()
							.getString(R.string.not_classMaster));
					toast.show();
				}
			} else if (v == btnTimeTable) {
				btnTimeTable.startAnimation(animScaleUp);
				btnTimeTable.clearAnimation();

				if (getTimetable() != null) {
					Intent intent = new Intent(ctx, TimetableActivity.class);

					Bundle b = new Bundle();
					b.putSerializable(Constants.Bundle_Timetable,
							getTimetable());
					b.putSerializable(Constants.Bundle_Teacher, getTeacher());
					intent.putExtras(b);
					startActivity(intent);
				} else {

				}

			} else if (v == btnMarks) {
				btnMarks.startAnimation(animScaleUp);
				btnMarks.clearAnimation();
				Intent intent = new Intent(ctx, AllClassesActivity.class);
				Bundle b = new Bundle();
				b.putSerializable(Constants.Bundle_Teacher, getTeacher());
				intent.putExtras(b);
				startActivity(intent);
			} else if (v == btnStats) {
				btnStats.startAnimation(animScaleUp);
				btnStats.clearAnimation();
				// Intent intent = new Intent(ctx, ChartDemo.class);
				// startActivity(intent);
				CustomToast toast = new CustomToast(act, getResources()
						.getString(R.string.coming_soon));
				toast.show();
			} else if (v == btnNotes) {
				btnNotes.startAnimation(animScaleUp);
				btnNotes.clearAnimation();
				Intent intent = new Intent(ctx, NotesList.class);
				startActivity(intent);
			} else if (v == btnExtras) {
				btnExtras.startAnimation(animScaleUp);
				btnExtras.clearAnimation();
				Intent intent = new Intent(ctx, GameMainActivity.class);
				startActivity(intent);
			}
		}

		return false;
	}

	public void showLoading() {
		progressBar.setVisibility(View.VISIBLE);
		btnExtras.setEnabled(false);
		btnMarks.setEnabled(false);
		btnMyClass.setEnabled(false);
		btnNotes.setEnabled(false);
		btnStats.setEnabled(false);
		btnTimeTable.setEnabled(false);
	}

	public void hideLoading() {

		NUMBER_OF_TASKS -= 1;
		if (NUMBER_OF_TASKS == 0) {
			progressBar.setVisibility(View.INVISIBLE);
			btnExtras.setEnabled(true);
			btnMarks.setEnabled(true);
			btnMyClass.setEnabled(true);
			btnNotes.setEnabled(true);
			btnStats.setEnabled(true);
			btnTimeTable.setEnabled(true);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.action_bar_menu, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		handleMenuItems(item);

		return super.onOptionsItemSelected(item);

	}

	private void handleMenuItems(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.change_password:
			ChangePasswordDialog cpd = new ChangePasswordDialog(this);
			cpd.show();
			break;

		case R.id.logout:
			AppPreferences prefs = AppPreferences.getInstance(this);
			prefs.saveLoginCredentials("", "");
			prefs.setFirstEntry(true);
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			moveTaskToBack(true);
		}

		return true;
	}

	/**
	 * @return the currentSemester
	 */
	public Semester getCurrentSemester() {
		return currentSemester;
	}

	/**
	 * @param currentSemester
	 *            the currentSemester to set
	 */
	public void setCurrentSemester(Semester currentSemester) {
		this.currentSemester = currentSemester;
	}

	/**
	 * @return the timetable
	 */
	public ArrayList<TimetableDays> getTimetable() {
		return timetable;
	}

	/**
	 * @param timetable
	 *            the timetable to set
	 */
	public void setTimetable(ArrayList<TimetableDays> timetable) {
		this.timetable = timetable;
	}

	/**
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher
	 *            the teacher to set
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * @return the masterClassGroup
	 */
	public ClassGroup getMasterClassGroup() {
		return masterClassGroup;
	}

	/**
	 * @param masterClassGroup
	 *            the masterClassGroup to set
	 */
	public void setMasterClassGroup(ClassGroup masterClassGroup) {
		this.masterClassGroup = masterClassGroup;
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
