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

public class MotivateIntervalDialog extends Dialog {
	/*
	 * Static members
	 */
	private static final String CLASS_NAME = Constants.MotivateIntervalDialog;

	/*
	 * Public members
	 */
	// none

	/*
	 * Private members
	 */
	private int mYear1, mYear2;
	private int mMonth1, mMonth2;
	private int mDay1, mDay2;
	private AsyncTaskFactory asyncTaskFactory;
	private AsyncTask<Object, Void, Integer> motivateIntervalTask;

	public MotivateIntervalDialog(final Context context,
			final ClassGroup classGroup, final Student student,
			final Teacher teacher, final int studentIndex) {
		super(context);
		getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		setContentView(R.layout.dialog_motivate_interval);
		setTitle(student.getLastName() + " " + student.getFirstName());

		final Button btnMotivateInterval = (Button) findViewById(R.id.btnMotivateInterval);
		asyncTaskFactory = AsyncTaskFactory.getInstance(AppPreferences
				.getInstance(context).isIpExternal());

		btnMotivateInterval.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				motivateIntervalTask = asyncTaskFactory.getTask(context,
						CLASS_NAME, "");
				if (validDateInterval()) {

					motivateIntervalTask.execute(classGroup.getId(),
							teacher.getId(), student.getId(),
							getFirstSelectedDate().getTime(),
							getSecondSelectedDate().getTime(), studentIndex);

					CatalogApplication.getGaTracker().send(
							MapBuilder.createEvent(Constants.UI_ACTION,
									Constants.UI_ACTION_MOTIVATE_INTERVAL,
									null, null).build());

					dismiss();
				} else {
					CustomToast toast = new CustomToast(context, context
							.getResources().getString(
									R.string.invalid_date_interval));

					toast.show();
				}

			}
		});

		final Button btnFirstDate = (Button) findViewById(R.id.btnFirstDate);
		final Button btnSecondDate = (Button) findViewById(R.id.btnSecondDate);
		final Calendar c = Calendar.getInstance();
		mYear1 = mYear2 = c.get(Calendar.YEAR);
		mMonth1 = mMonth2 = c.get(Calendar.MONTH);
		mDay1 = mDay2 = c.get(Calendar.DAY_OF_MONTH);
		final StringBuilder currentDateAsString = new StringBuilder()
				.append(mDay1).append("/").append(mMonth1 + 1).append("/")
				.append(mYear1).append("");
		btnFirstDate.setText(currentDateAsString);
		btnSecondDate.setText(currentDateAsString);

		btnFirstDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog dpd = new DatePickerDialog(context,
						mDateSetListener, mYear1, mMonth1, mDay1);

				dpd.show();

			}

			private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					mYear1 = year;
					mMonth1 = monthOfYear;
					mDay1 = dayOfMonth;
					updateDisplay();
				}
			};

			private void updateDisplay() {
				btnFirstDate.setText(new StringBuilder().append(mDay1)
						.append("/").append(mMonth1 + 1).append("/")
						.append(mYear1).append(""));
			}
		});

		btnSecondDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog dpd = new DatePickerDialog(context,
						mDateSetListener, mYear2, mMonth2, mDay2);

				dpd.show();

			}

			private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					mYear2 = year;
					mMonth2 = monthOfYear;
					mDay2 = dayOfMonth;
					updateDisplay();
				}
			};

			private void updateDisplay() {
				btnSecondDate.setText(new StringBuilder().append(mDay2)
						.append("/").append(mMonth2 + 1).append("/")
						.append(mYear2).append(""));
			}
		});
	}

	protected boolean validDateInterval() {
		return (getFirstSelectedDate().before(getSecondSelectedDate()));
	}

	private Date getFirstSelectedDate() {

		Calendar c = Calendar.getInstance();
		c.set(mYear1, mMonth1, mDay1);
		return new Date(c.getTime().getTime());
	}

	private Date getSecondSelectedDate() {

		Calendar c = Calendar.getInstance();
		c.set(mYear2, mMonth2, mDay2);
		return new Date(c.getTime().getTime());
	}
}