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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catalog.activities.DetailedClassStudentsDetailsFragment;
import com.catalog.activities.R;
import com.catalog.helper.AppPreferences;
import com.catalog.helper.Constants;
import com.catalog.helper.InputFilterMinMax;
import com.catalog.model.Attendance;
import com.catalog.model.Student;
import com.catalog.model.StudentMark;
import com.catalog.model.Subject;

/**
 * Dialog which covers the editing of a grade or absence of a specific student.
 * 
 * @author Alex
 * 
 */
public class EditGradesOrAbsencesDialog extends Dialog {
	/*
	 * Static members
	 */
	private static final String CLASSNAME = Constants.EditGradesOrAbsencesDialog;
	private static final int SUCCESS = Constants.SUCCESS;
	private static final int FAIL = Constants.FAIL;
	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM",
			Locale.UK);

	/*
	 * Public members
	 */
	// none

	/*
	 * Private members
	 */
	private AsyncTaskFactory asyncTaskFactory;
	private boolean editedMarks[];
	private boolean editedAbsances[];

	public EditGradesOrAbsencesDialog(final Context context,
			final DetailedClassStudentsDetailsFragment fragment,
			final int positionInSubjectsList, Student student, Subject subject,
			final ArrayList<StudentMark> marks,
			final ArrayList<Attendance> attendance) {
		super(context);

		CustomToast toast = new CustomToast(context,
				"Apasati pe data absentei pentru a o motiva!");
		toast.show();

		getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		setContentView(R.layout.dialog_edit_student);
		setTitle(student.getLastName() + " " + student.getFirstName());

		asyncTaskFactory = AsyncTaskFactory.getInstance(AppPreferences
				.getInstance(context).isIpExternal());
		/*
		 * Grades
		 */

		// container layout
		LinearLayout gradesContainerLayout = (LinearLayout) this
				.findViewById(R.id.layout_grades);
		int numberOfGrades = marks.size();
		editedMarks = new boolean[numberOfGrades];

		LinearLayout gradesLayout[] = new LinearLayout[numberOfGrades];
		final EditText editableGrades[] = new EditText[numberOfGrades];
		TextView gradesDates[] = new TextView[numberOfGrades];

		for (int i = 0; i < numberOfGrades; i++) {
			gradesLayout[i] = new LinearLayout(context);
			editableGrades[i] = new EditText(context);
			gradesDates[i] = new TextView(context);

			if (marks.get(i).isFinalExam())
				editableGrades[i].setTextColor(Color.RED);
			editableGrades[i].setText("" + marks.get(i).getMark());
			editableGrades[i].setId(i);
			editableGrades[i].setTextSize(18);
			editableGrades[i].setMaxEms(3);
			editableGrades[i].setMinEms(1);
			editableGrades[i].setMaxLines(1);
			editableGrades[i].addTextChangedListener(new TWatcher(
					editableGrades[i]));
			editableGrades[i]
					.setOnFocusChangeListener(focusChangeListenerForEditTexts);
			editableGrades[i].setFilters(new InputFilter[] {
					new InputFilterMinMax(1, 10),
					new InputFilter.LengthFilter(2) });
			editableGrades[i].setInputType(InputType.TYPE_CLASS_NUMBER);
			gradesDates[i].setText(dateFormat.format(marks.get(i).getDate()));
			gradesDates[i].setTextSize(18);
			gradesLayout[i].setOrientation(LinearLayout.HORIZONTAL);
			gradesLayout[i].setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			gradesLayout[i].addView(editableGrades[i]);
			gradesLayout[i].addView(gradesDates[i]);
			gradesContainerLayout.addView(gradesLayout[i]);
		}

		/*
		 * Absances
		 */
		LinearLayout absencesContainerLayout = (LinearLayout) this
				.findViewById(R.id.layout_absences);

		int numberOfAbsances = attendance.size();
		editedAbsances = new boolean[numberOfAbsances];
		TextView absencesTv[] = new TextView[numberOfAbsances];

		for (int i = 0; i < numberOfAbsances; i++) {

			absencesTv[i] = new TextView(context);
			absencesTv[i].setTextSize(18);
			absencesTv[i].setId(i);
			absencesTv[i].setText(""
					+ dateFormat.format(attendance.get(i).getDate()));
			if (attendance.get(i).isMotivat()) {
				absencesTv[i].setPaintFlags(absencesTv[i].getPaintFlags()
						| Paint.STRIKE_THRU_TEXT_FLAG);
			} else
				absencesTv[i].setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						((TextView) v).setPaintFlags(((TextView) v)
								.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
						editedAbsances[v.getId()] = true;
					}
				});

			absencesContainerLayout.addView(absencesTv[i]);
		}

		final Button btnSave = (Button) findViewById(R.id.btn_save);

		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				for (int i = 0; i < editedAbsances.length; i++) {
					if (editedAbsances[i] == true) {
//						asyncTaskFactory.getTask(context, CLASSNAME,
//								Constants.Method_EditAbsance).execute(
//								attendance.get(i).getId(),
//								positionInSubjectsList, fragment);
					}
				}
				for (int i = 0; i < editedMarks.length; i++) {
					if (editedMarks[i] == true) {
//						asyncTaskFactory.getTask(context, CLASSNAME,
//								Constants.Method_EditMark).execute(
//								marks.get(i).getId(),
//								Integer.valueOf(editableGrades[i].getText()
//										.toString()),
//								marks.get(i).getDate().getTime(),
//								positionInSubjectsList, fragment);
					}
				}

				dismiss();
			}
		});
	}

	private class TWatcher implements TextWatcher {

		View v;

		private TWatcher(View v) {
			this.v = v;
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			editedMarks[v.getId()] = true;
		}

	}

	OnFocusChangeListener focusChangeListenerForEditTexts = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus)
				((EditText) v).setSelection(((EditText) v).getText().toString()
						.length());
		}
	};

}
