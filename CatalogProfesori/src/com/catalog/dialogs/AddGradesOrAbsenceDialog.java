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
package com.catalog.dialogs;

import java.sql.Date;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.catalog.activities.R;
import com.catalog.activities.fragments.DetailedClassStudentsDetailsFragment;
import com.catalog.core.AppPreferences;
import com.catalog.core.AsyncTaskFactory;
import com.catalog.core.CatalogApplication;
import com.catalog.helper.Constants;
import com.catalog.helper.CustomToast;
import com.catalog.helper.InputFilterMinMax;
import com.catalog.model.ClassGroup;
import com.catalog.model.Student;
import com.catalog.model.Subject;
import com.catalog.model.Teacher;
import com.google.analytics.tracking.android.MapBuilder;

/**
 * Dialog which covers the adding of a grade or absence to a specific student.
 * 
 * @author Alex
 * 
 */
public class AddGradesOrAbsenceDialog extends Dialog {
	/*
	 * Static members
	 */
	private static final String CLASS_NAME = Constants.AddGradesOrAbsenceDialog;

	/*
	 * Public members
	 */
	// none

	/*
	 * Private members
	 */
	private int mYear;
	private int mMonth;
	private int mDay;
	private AsyncTaskFactory asyncTaskFactory;
	private AsyncTask<Object, Void, Integer> addMarkTask;
	private AsyncTask<Object, Void, Integer> addAbsenceTask;

	public AddGradesOrAbsenceDialog(final Context context,
			final DetailedClassStudentsDetailsFragment fragment,
			final int positionInSubjectsList, final ClassGroup classGroup,
			final Student student, final Subject subject, final Teacher teacher) {
		super(context);
		getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		setContentView(R.layout.dialog_add_grades_absences);
		setTitle(student.getLastName() + " " + student.getFirstName());

		final EditText grade = (EditText) findViewById(R.id.et_grade);
		final CheckBox finalGrade = (CheckBox) findViewById(R.id.cbFinalMark);
		grade.setFilters(new InputFilter[] { new InputFilterMinMax(1, 10),
				new InputFilter.LengthFilter(2) });

		final Button btnAddGrade = (Button) findViewById(R.id.btn_addGrade);
		asyncTaskFactory = AsyncTaskFactory.getInstance(AppPreferences
				.getInstance(context).isIpExternal());

		btnAddGrade.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addMarkTask = asyncTaskFactory.getTask(context, CLASS_NAME,
						Constants.Method_AddMark);
				int gradeText;
				if (!grade.getText().toString().equals("")) {
					gradeText = Integer.parseInt(grade.getText().toString());

					addMarkTask.execute(classGroup.getId(), subject.getId(),
							teacher.getId(), student, gradeText,
							finalGrade.isChecked(),
							getSelectedDate().getTime(),
							positionInSubjectsList, fragment);

					CatalogApplication.getGaTracker().send(
							MapBuilder.createEvent(Constants.UI_ACTION,
									Constants.UI_ACTION_ADD_GRADE, null, null)
									.build());

					dismiss();
				} else {
					CustomToast toast = new CustomToast(context, context
							.getResources().getString(
									R.string.insert_valid_grade));

					toast.show();
				}

			}
		});

		final Button btnAbsent = (Button) findViewById(R.id.addAbsentButton);
		btnAbsent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				addAbsenceTask = asyncTaskFactory.getTask(context, CLASS_NAME,
						Constants.Method_AddAbsence);

				int classGroupId = classGroup.getId();
				int subjectId = subject.getId();
				int teacherId = teacher.getId();

				addAbsenceTask.execute(classGroupId, subjectId, teacherId,
						student, getSelectedDate().getTime(),
						positionInSubjectsList, fragment);

				CatalogApplication.getGaTracker().send(
						MapBuilder.createEvent(Constants.UI_ACTION,
								Constants.UI_ACTION_ADD_ABSANCE, null, null)
								.build());
				dismiss();
			}
		});

		final Button btnDate = (Button) findViewById(R.id.btn_selectDate);
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		btnDate.setText(new StringBuilder().append(mDay).append("/")
				.append(mMonth + 1).append("/").append(mYear).append(""));
		btnDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog dpd = new DatePickerDialog(context,
						mDateSetListener, mYear, mMonth, mDay);

				dpd.show();

			}

			private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					mYear = year;
					mMonth = monthOfYear;
					mDay = dayOfMonth;
					updateDisplay();
				}
			};

			private void updateDisplay() {
				btnDate.setText(new StringBuilder().append(mDay).append("/")
						.append(mMonth + 1).append("/").append(mYear)
						.append(""));
			}
		});
	}

	private Date getSelectedDate() {

		Calendar c = Calendar.getInstance();
		c.set(mYear, mMonth, mDay);
		return new Date(c.getTime().getTime());
	}
}
