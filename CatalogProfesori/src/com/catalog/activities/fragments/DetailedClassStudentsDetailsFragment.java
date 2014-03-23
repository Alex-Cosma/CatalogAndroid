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
package com.catalog.activities.fragments;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.catalog.activities.DetailedClassActivity;
import com.catalog.activities.R;
import com.catalog.activities.R.anim;
import com.catalog.activities.R.id;
import com.catalog.activities.R.layout;
import com.catalog.dialogs.AddGradesOrAbsenceDialog;
import com.catalog.dialogs.EditGradesOrAbsencesDialog;
import com.catalog.helper.Comparators;
import com.catalog.helper.Constants;
import com.catalog.model.Attendance;
import com.catalog.model.ClassGroup;
import com.catalog.model.GradesAttendForSubject;
import com.catalog.model.Student;
import com.catalog.model.StudentMark;
import com.catalog.model.Subject;
import com.catalog.model.Teacher;
import com.devsmart.android.ui.HorizontalListView;

/**
 * The main view / fragment of the DetailedClassActivity situated at the right
 * of the list student list.
 * 
 * @author Alex
 * 
 */
public class DetailedClassStudentsDetailsFragment extends Fragment {
	/*
	 * Static members
	 */
	private static final String CLASSNAME = Constants.DetailedClassStudentsDetailsFragment;

	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM",
			Locale.UK);

	/*
	 * Public members
	 */
	public StudentAdapter m_adapter;
	public DetailedClassActivity act;
	public String[] marksBuffer;
	public String[] attendanceBuffer;
	public String[] avarageBuffer;
	public String[] finalMarkBuffer;
	public ArrayList<GradesAttendForSubject> gradesAndAttendances;

	/*
	 * Private members
	 */
	private int selectedStudentIndex = 0;
	private Student selectedStudent;
	private ClassGroup selectedClassGroup;
	private Teacher teacher;
	private DetailedClassStudentsDetailsFragment currentFragment;

	public static DetailedClassStudentsDetailsFragment newInstance(
			int selectedStudentIndex, Student selectedStudent,
			ClassGroup classGroup, Teacher teacher,
			ArrayList<GradesAttendForSubject> gradesAndAttendances) {

		DetailedClassStudentsDetailsFragment df = new DetailedClassStudentsDetailsFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", selectedStudentIndex);
		args.putSerializable(Constants.Bundle_Student, selectedStudent);
		args.putSerializable(Constants.Bundle_ClassGroup, classGroup);
		args.putSerializable(Constants.Bundle_Teacher, teacher);
		args.putSerializable(Constants.Bundle_GradesAndAbsences,
				gradesAndAttendances);

		df.setArguments(args);

		return df;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle myBundle) {
		super.onCreate(myBundle);

		currentFragment = this;
		selectedStudentIndex = getArguments().getInt("index", 0);
		selectedStudent = (Student) getArguments().getSerializable(
				Constants.Bundle_Student);
		selectedClassGroup = (ClassGroup) getArguments().getSerializable(
				Constants.Bundle_ClassGroup);

		teacher = (Teacher) getArguments().getSerializable(
				Constants.Bundle_Teacher);

		gradesAndAttendances = (ArrayList<GradesAttendForSubject>) getArguments()
				.getSerializable(Constants.Bundle_GradesAndAbsences);
		if (selectedClassGroup == null || selectedStudent == null
				|| teacher == null || gradesAndAttendances == null)
			return;

		int length = gradesAndAttendances.size();
		initBuffers(length);
	}

	private void initBuffers(int length) {
		marksBuffer = new String[length];
		attendanceBuffer = new String[length];
		avarageBuffer = new String[length];
		finalMarkBuffer = new String[length];
		for (int i = 0; i < length; i++) {
			marksBuffer[i] = new String("");
			attendanceBuffer[i] = new String("");
			avarageBuffer[i] = new String("");
			finalMarkBuffer[i] = new String("");

		}
	}

	public int getShownIndex() {
		return selectedStudentIndex;
	}

	@Override
	public void onActivityCreated(Bundle icicle) {

		super.onActivityCreated(icicle);

		initUI();

	}

	private void initUI() {

		act = (DetailedClassActivity) getActivity();

		HorizontalListView listview = (HorizontalListView) act
				.findViewById(R.id.listview);

		m_adapter = new StudentAdapter(getActivity(),
				R.layout.listitem_subjectandgrades, gradesAndAttendances);
		listview.setAdapter(m_adapter);

		LayoutAnimationController controller = AnimationUtils
				.loadLayoutAnimation(getActivity(),
						R.anim.list_layout_controller);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				constructAddGradeOrAbsenceDialog(position);

			}
		});

		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				constructEditGradesDialog(position);

				return false;
			}

		});
		listview.setLayoutAnimation(controller);

	}

	/**
	 * Class which extends the basic ArrayAdapter into a custom Route adapter. <br>
	 * the overridden getView method now copes with out routelist.xml which
	 * contains the layout of our items which will appear in the list.
	 * 
	 * @author Alex
	 * @version 1.0
	 */

	private static class ViewHolder {
		TextView nameText;
		TextView attendanceText;
		TextView finalMarkText;
		TextView marksText;
		TextView avarageText;
	}

	public class StudentAdapter extends ArrayAdapter<GradesAttendForSubject> {

		private ArrayList<GradesAttendForSubject> subjects;

		public StudentAdapter(Context context, int textViewResourceId,
				ArrayList<GradesAttendForSubject> items) {
			super(context, textViewResourceId, items);
			this.subjects = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;
			ViewHolder holder;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) act
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.listitem_subjectandgrades, null);

				holder = new ViewHolder();
				holder.nameText = (TextView) v.findViewById(R.id.tv_subject);
				holder.avarageText = (TextView) v.findViewById(R.id.tv_avarage);
				holder.attendanceText = (TextView) v
						.findViewById(R.id.tv_attendence);
				holder.marksText = (TextView) v.findViewById(R.id.tv_marks);
				holder.finalMarkText = (TextView) v
						.findViewById(R.id.tv_finalMark);

				// associate the holder with the view for later lookup
				v.setTag(holder);
			} else {
				// view already exists, get the holder instance from the view
				holder = (ViewHolder) v.getTag();
			}
			Subject subject = subjects.get(position).getSubject();

			if (subject != null) {

				if (holder.nameText != null) {
					holder.nameText.setText(subject.getName());
				}

				if (holder.marksText != null && holder.avarageText != null) {
					if (marksBuffer[position].equals("")
							|| avarageBuffer[position].equals("")) {

						reconstructGradesText(position);

						holder.marksText.setText(marksBuffer[position]);
						holder.avarageText.setText(avarageBuffer[position]);
						holder.finalMarkText.setText(finalMarkBuffer[position]);

					} else {
						holder.marksText.setText(marksBuffer[position]);
						holder.avarageText.setText(avarageBuffer[position]);
						holder.finalMarkText.setText(finalMarkBuffer[position]);
					}
				}

				if (holder.attendanceText != null) {
					if (attendanceBuffer[position].equals("")) {

						reconstructAbsancesText(position);

						holder.attendanceText
								.setText(attendanceBuffer[position]);

					} else {
						holder.attendanceText
								.setText(attendanceBuffer[position]);
					}
				}

			}
			return v;

		}

	}

	public void reconstructAbsancesText(int position) {

		attendanceBuffer[position] = "";
		Collections.sort(gradesAndAttendances.get(position).getAttendaces(),
				Comparators.ComparatorByAbsanceDate);
		for (Attendance element : gradesAndAttendances.get(position)
				.getAttendaces()) {
			// TODO: Temporary if...
			if (element.getDate() != null) {
				if (!element.isMotivat())
					attendanceBuffer[position] += dateFormat.format(element
							.getDate()) + "\n";
				else

					attendanceBuffer[position] += dateFormat.format(element
							.getDate()) + "- M\n";
			} else
				attendanceBuffer[position] += "N/A\n";
		}
		// m_adapter.notifyDataSetChanged();
	}

	public void reconstructGradesText(int position) {

		marksBuffer[position] = "";
		finalMarkBuffer[position] = "";
		avarageBuffer[position] = "";

		Collections.sort(gradesAndAttendances.get(position).getMarks(),
				Comparators.ComparatorByMarkDate);

		float avarage = 0;
		float finalMark = 0;
		for (StudentMark mark : gradesAndAttendances.get(position).getMarks()) {
			if (!mark.isFinalExam()) {
				if (mark.getDate() != null)
					marksBuffer[position] += mark.getMark() + " "
							+ dateFormat.format(mark.getDate()) + "\n";
				else
					marksBuffer[position] += mark.getMark() + "\n";
				avarage += mark.getMark();
			} else {
				finalMarkBuffer[position] = String.valueOf(mark.getMark());

				finalMark = mark.getMark();
			}
		}
		if (gradesAndAttendances.get(position).getMarks().size() != 0) {
			if (finalMark != 0) {

				float marksAvarage = avarage
						/ (float) (gradesAndAttendances.get(position)
								.getMarks().size() - 1);

				float genAvarage = (float) (((marksAvarage * 3) + finalMark) / 4.0);

				avarageBuffer[position] = String.valueOf(genAvarage);

			} else
				avarageBuffer[position] = String
						.valueOf((avarage / (float) gradesAndAttendances
								.get(position).getMarks().size()));
		}
		// m_adapter.notifyDataSetChanged();
	}

	private void constructAddGradeOrAbsenceDialog(int position) {

		if (!act.isLoading()) {
			AddGradesOrAbsenceDialog d = new AddGradesOrAbsenceDialog(act,
					this, position, act.getClassGroup(), selectedStudent,
					gradesAndAttendances.get(position).getSubject(), teacher);

			d.show();
		}
	}

	private void constructEditGradesDialog(int position) {
		if (!act.isLoading()) {
			EditGradesOrAbsencesDialog d = new EditGradesOrAbsencesDialog(act,
					this, position, selectedStudent, gradesAndAttendances.get(
							position).getSubject(),
					(ArrayList<StudentMark>) gradesAndAttendances.get(position)
							.getMarks(),
					(ArrayList<Attendance>) gradesAndAttendances.get(position)
							.getAttendaces());
			d.show();
		}
	}

}
