package com.catalog.dialogs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catalog.activities.AllClassesActivity;
import com.catalog.activities.R;
import com.catalog.core.AppPreferences;
import com.catalog.core.AsyncTaskFactory;
import com.catalog.helper.Constants;
import com.catalog.helper.InputFilterMinMax;
import com.catalog.model.ClassGroup;
import com.catalog.model.Student;
import com.catalog.model.Subject;
import com.catalog.model.Teacher;

public class AddGradesForAllClassDialog extends Dialog {
	/*
	 * Static members
	 */
	private static final String CLASS_NAME = Constants.AddGradesForAllClassDialog;
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
	private int mYear;
	private int mMonth;
	private int mDay;

	public AddGradesForAllClassDialog(final AllClassesActivity act,
			final ArrayList<Student> students, final Subject subject,
			final Teacher teacher, final ClassGroup classGroup) {
		super(act);

		getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		setContentView(R.layout.dialog_add_grades_all_class);

		final CheckBox finalGrade = (CheckBox) findViewById(R.id.cbFinalMark);

		setTitle("Clasa a" + classGroup.getYearOfStudy() + "-a "
				+ classGroup.getName());

		asyncTaskFactory = AsyncTaskFactory.getInstance(AppPreferences
				.getInstance(act).isIpExternal());
		/*
		 * Students
		 */

		LinearLayout containerLayout = (LinearLayout) this
				.findViewById(R.id.llGrades1);

		int numberOfStudents = students.size();
		editedGrades = new boolean[numberOfStudents];
		LinearLayout gradesLayout[] = new LinearLayout[numberOfStudents];
		final EditText editableGrades[] = new EditText[numberOfStudents];
		TextView studentNames[] = new TextView[numberOfStudents];

		for (int i = 0; i < numberOfStudents; i++) {

			Student student = students.get(i);
			gradesLayout[i] = new LinearLayout(act);
			studentNames[i] = new TextView(act);

			editableGrades[i] = createEditableGrade(act, i, student);

			studentNames[i].setText(student.getLastName() + " "
					+ student.getFirstName());
			studentNames[i].setTextSize(18);
			gradesLayout[i].setOrientation(LinearLayout.HORIZONTAL);
			gradesLayout[i].setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			gradesLayout[i].addView(studentNames[i]);
			gradesLayout[i].addView(editableGrades[i]);
			containerLayout.addView(gradesLayout[i]);
		}

		Arrays.fill(editedGrades, false);
		
		final Button btnSave = (Button) findViewById(R.id.btn_save);

		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				asyncTaskFactory.getTask(act, CLASS_NAME, "").execute(
						subject.getId(), teacher.getId(), classGroup.getId(),
						students, editableGrades, editedGrades,
						finalGrade.isChecked(), getSelectedDate().getTime());

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
				DatePickerDialog dpd = new DatePickerDialog(act,
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

	private EditText createEditableGrade(Context context, int i, Student student) {
		EditText et = new EditText(context);

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