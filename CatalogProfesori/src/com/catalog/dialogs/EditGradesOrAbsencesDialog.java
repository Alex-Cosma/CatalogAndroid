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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
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

import com.catalog.activities.R;
import com.catalog.activities.fragments.DetailedClassStudentsDetailsFragment;
import com.catalog.core.AppPreferences;
import com.catalog.core.AsyncTaskFactory;
import com.catalog.core.CatalogApplication;
import com.catalog.helper.Constants;
import com.catalog.helper.CustomToast;
import com.catalog.helper.InputFilterMinMax;
import com.catalog.model.Attendance;
import com.catalog.model.Student;
import com.catalog.model.StudentMark;
import com.catalog.model.Subject;
import com.google.analytics.tracking.android.MapBuilder;

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
	private static final String CLASS_NAME = Constants.EditGradesOrAbsencesDialog;
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
	private boolean editedGrades[];
	private boolean editedAttendances[];

	public EditGradesOrAbsencesDialog(final Context context,
			final DetailedClassStudentsDetailsFragment fragment,
			final int positionInSubjectsList, Student student, Subject subject,
			final ArrayList<StudentMark> marks,
			final ArrayList<Attendance> attendances) {
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
		editedGrades = new boolean[numberOfGrades];

		LinearLayout gradesLayout[] = new LinearLayout[numberOfGrades];
		final EditText editableGrades[] = new EditText[numberOfGrades];
		TextView gradesDates[] = new TextView[numberOfGrades];

		for (int i = 0; i < numberOfGrades; i++) {

			StudentMark mark = marks.get(i);
			gradesLayout[i] = new LinearLayout(context);
			gradesDates[i] = new TextView(context);

			editableGrades[i] = createEditableGrade(context, i, mark);

			gradesDates[i].setText(dateFormat.format(mark.getDate()));
			gradesDates[i].setTextSize(18);
			gradesLayout[i].setOrientation(LinearLayout.HORIZONTAL);
			gradesLayout[i].setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			gradesLayout[i].addView(editableGrades[i]);
			gradesLayout[i].addView(gradesDates[i]);
			gradesContainerLayout.addView(gradesLayout[i]);
		}

		Arrays.fill(editedGrades, false);
		
		/*
		 * Absances
		 */
		LinearLayout absencesContainerLayout = (LinearLayout) this
				.findViewById(R.id.layout_absences);

		int numberOfAbsances = attendances.size();
		editedAttendances = new boolean[numberOfAbsances];
		TextView absencesTv[] = new TextView[numberOfAbsances];

		for (int i = 0; i < numberOfAbsances; i++) {
			Attendance attendence = attendances.get(i);
			absencesTv[i] = createAttendenceText(context, i, attendence);
			absencesContainerLayout.addView(absencesTv[i]);
		}
		
		Arrays.fill(editedAttendances, false);
		final Button btnSave = (Button) findViewById(R.id.btn_save);

		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				for (int i = 0; i < editedAttendances.length; i++) {
					if (editedAttendances[i] == true) {
						int attendenceId = attendances.get(i).getId();

						asyncTaskFactory.getTask(context, CLASS_NAME,
								Constants.Method_EditAbsance).execute(
								attendenceId, positionInSubjectsList, fragment);

						CatalogApplication.getGaTracker().send(
								MapBuilder.createEvent(Constants.UI_ACTION,
										Constants.UI_ACTION_MOTIVATE_GRADE,
										null, null).build());
					}
				}
				for (int i = 0; i < editedGrades.length; i++) {
					if (editedGrades[i] == true) {
						int markId = marks.get(i).getId();
						Integer mark = Integer.valueOf(editableGrades[i]
								.getText().toString());
						long markTimestamp = marks.get(i).getDate().getTime();

						asyncTaskFactory.getTask(context, CLASS_NAME,
								Constants.Method_EditMark).execute(markId,
								mark, markTimestamp, positionInSubjectsList,
								fragment);

						CatalogApplication.getGaTracker().send(
								MapBuilder.createEvent(Constants.UI_ACTION,
										Constants.UI_ACTION_EDIT_GRADE, null,
										null).build());
					}
				}

				dismiss();
			}
		});
	}

	private EditText createEditableGrade(Context context, int i,
			StudentMark mark) {
		EditText et = new EditText(context);

		if (mark.isFinalExam())
			et.setTextColor(Color.RED);

		et.setText("" + mark.getMark());
		et.setId(i);
		et.setTextSize(18);
		et.setMaxEms(3);
		et.setMinEms(1);
		et.setMaxLines(1);
		et.addTextChangedListener(new TWatcher(et));
		et.setOnFocusChangeListener(focusChangeListenerForEditTexts);
		et.setFilters(new InputFilter[] { new InputFilterMinMax(1, 10),
				new InputFilter.LengthFilter(2) });
		et.setInputType(InputType.TYPE_CLASS_NUMBER);

		return et;
	}

	private TextView createAttendenceText(Context context, int i,
			Attendance attendance) {

		TextView att = new TextView(context);

		att.setTextSize(18);
		att.setId(i);
		att.setText("" + dateFormat.format(attendance.getDate()));
		if (attendance.isMotivat()) {
			att.setPaintFlags(att.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		} else
			att.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					((TextView) v).setPaintFlags(((TextView) v).getPaintFlags()
							| Paint.STRIKE_THRU_TEXT_FLAG);
					editedAttendances[v.getId()] = true;
				}
			});

		return att;
	}

	private class TWatcher implements TextWatcher {

		private final View v;

		private TWatcher(View v) {
			this.v = v;
		}

		@Override
		public void afterTextChanged(Editable s) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			editedGrades[v.getId()] = true;
		}

	}

	OnFocusChangeListener focusChangeListenerForEditTexts = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(final View v, boolean hasFocus) {
			if (hasFocus)
				((EditText) v).setSelection(((EditText) v).getText().toString()
						.length());
		}
	};

}
