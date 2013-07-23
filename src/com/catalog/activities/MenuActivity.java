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

import com.catalog.activities.extras.AsyncTaskFactory;
import com.catalog.activities.extras.ChangePasswordDialog;
import com.catalog.activities.extras.CustomToast;
import com.catalog.game.GameMainActivity;
import com.catalog.helper.AppPreferences;
import com.catalog.helper.Constants;
import com.catalog.model.ClassGroup;
import com.catalog.model.Teacher;
import com.catalog.model.TimetableDays;
import com.example.android.notepad.NotesList;

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
	private static final String CLASSNAME = Constants.MenuActivity;

	/*
	 * Public members
	 */
	public ClassGroup masterClassGroup;
	public Teacher teacher;
	public ArrayList<TimetableDays> timetable;

	/*
	 * Private members
	 */
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
	private int finishedTasksCounter;
	private AsyncTaskFactory asyncTaskFactory;
	private AsyncTask<Object, Void, Integer> getMasterClassTask;
	private AsyncTask<Object, Void, Integer> getTeacherTask;
	private AsyncTask<Object, Void, Integer> getTimetableTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		setContentView(R.layout.activity_menu);

		initUI();
	}

	private void initUI() {
		ctx = getApplicationContext();
		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		asyncTaskFactory = AsyncTaskFactory.getInstance(AppPreferences
				.getInstance(this).isIpExternal());

		act = this;
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

		finishedTasksCounter = 3;

		getMasterClassTask = asyncTaskFactory.getTask(this, CLASSNAME, "");
		getMasterClassTask.execute(2);
		getTeacherTask = asyncTaskFactory.getTask(this, CLASSNAME,
				Constants.Method_GetTeacher);
		getTeacherTask.execute();

		getTimetableTask = asyncTaskFactory.getTask(this, CLASSNAME,
				Constants.Method_GetTeacherTimetable);
		getTimetableTask.execute();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (v == btnMyClass)
				btnMyClass.startAnimation(animScaleDown);
			else if (v == btnTimeTable)
				btnTimeTable.startAnimation(animScaleDown);
			else if (v == btnMarks)
				btnMarks.startAnimation(animScaleDown);
			else if (v == btnStats)
				btnStats.startAnimation(animScaleDown);
			else if (v == btnNotes)
				btnNotes.startAnimation(animScaleDown);
			else if (v == btnExtras)
				btnExtras.startAnimation(animScaleDown);

		} else if (event.getAction() == MotionEvent.ACTION_UP) {

			if (v == btnMyClass) {
				btnMyClass.startAnimation(animScaleUp);
				btnMyClass.clearAnimation();
				if (masterClassGroup.getId() != null) {
					if (teacher != null) {
						Intent intent = new Intent(ctx,
								DetailedClassActivity.class);
						Bundle b = new Bundle();
						b.putSerializable(Constants.ClassGroup,
								masterClassGroup);
						b.putSerializable(Constants.Teacher, teacher);
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

				if (timetable != null) {
					Intent intent = new Intent(ctx, TimetableActivity.class);

					Bundle b = new Bundle();
					b.putSerializable(Constants.Timetable, timetable);
					b.putSerializable(Constants.Teacher, teacher);
					intent.putExtras(b);
					startActivity(intent);
				} else {

				}

			} else if (v == btnMarks) {
				btnMarks.startAnimation(animScaleUp);
				btnMarks.clearAnimation();
				Intent intent = new Intent(ctx, AddGradesClassActivity.class);
				Bundle b = new Bundle();
				b.putSerializable(Constants.Teacher, teacher);
				intent.putExtras(b);
				startActivity(intent);
			} else if (v == btnStats) {
				btnStats.startAnimation(animScaleUp);
				btnStats.clearAnimation();
				Intent intent = new Intent(ctx, StatisticsActivity.class);
				// Intent intent = new Intent(ctx, ChartDemo.class);
				startActivity(intent);
				// CustomToast toast = new CustomToast(act, getResources()
				// .getString(R.string.coming_soon));
				// toast.show();
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

		finishedTasksCounter -= 1;
		if (finishedTasksCounter == 0) {
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
}
