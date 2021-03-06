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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.catalog.activities.AllClassesActivity;
import com.catalog.activities.R;
import com.catalog.activities.R.anim;
import com.catalog.activities.R.id;
import com.catalog.activities.R.layout;
import com.catalog.activities.R.string;
import com.catalog.core.AppPreferences;
import com.catalog.core.AsyncTaskFactory;
import com.catalog.core.CatalogApplication;
import com.catalog.dialogs.AddGradesOrAbsenceDialog;
import com.catalog.dialogs.EditGradesOrAbsencesDialog;
import com.catalog.helper.Constants;
import com.catalog.model.ClassGroup;
import com.catalog.model.Student;
import com.catalog.model.Subject;
import com.catalog.model.Teacher;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

/**
 * The main view / fragment of the AddGradesClassActivity situated at the right
 * of the list student list.
 * 
 * @author Alex
 * 
 */
public class AllClassesStudentsDetailsFragment extends Fragment {

	/*
	 * Static members
	 */
	private static final String CLASS_NAME = Constants.AllClassesStudentsDetailsFragment;
	public static boolean viewingAsList;
	private static ViewFlipper vf;

	/*
	 * Public members
	 */
	public GridView gridview;
	public ListView listview;
	private ArrayList<Student> students = null;
	public StudentAdapter m_adapter;

	/*
	 * Private members
	 */
	private int mIndex = 0;
	private ClassGroup selectedClassGroup;
	private Teacher teacher;
	private AllClassesActivity act;
	private AsyncTaskFactory asyncTaskFactory;
	private AsyncTask<Object, Void, Integer> getStudentsTask;
	private ProgressBar progressBar;

	public static AllClassesStudentsDetailsFragment newInstance(int index,
			Subject subject, ClassGroup classGroup, Teacher teacher,
			boolean viewingAsList) {

		AllClassesStudentsDetailsFragment df = new AllClassesStudentsDetailsFragment();
		AllClassesStudentsDetailsFragment.viewingAsList = viewingAsList;
		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		args.putSerializable(Constants.Bundle_ClassGroup, classGroup);
		args.putSerializable(Constants.Bundle_Teacher, teacher);
		df.setArguments(args);
		if (vf != null)
			if (viewingAsList) {
				vf.setDisplayedChild(1);
			} else {
				vf.setDisplayedChild(0);
			}
		return df;
	}

	@Override
	public void onCreate(Bundle myBundle) {
		super.onCreate(myBundle);

		mIndex = getArguments().getInt("index", 0);
		selectedClassGroup = (ClassGroup) getArguments().getSerializable(
				Constants.Bundle_ClassGroup);
		teacher = (Teacher) getArguments().getSerializable(
				Constants.Bundle_Teacher);

		if ((selectedClassGroup == null) || (teacher == null)) {
			return;
		}

		CatalogApplication.getGaTracker().set(Fields.SCREEN_NAME, CLASS_NAME);
	}

	public int getShownIndex() {
		return mIndex;
	}

	@Override
	public void onActivityCreated(Bundle icicle) {

		super.onActivityCreated(icicle);

		initUI();

	}

	/**
	 * Initializes the UI elements.
	 */
	private void initUI() {

		vf = (ViewFlipper) this.getActivity().findViewById(R.id.vf_details);
		act = (AllClassesActivity) getActivity();
		progressBar = (ProgressBar) act.findViewById(R.id.progressBar);

		asyncTaskFactory = AsyncTaskFactory.getInstance(AppPreferences
				.getInstance(getActivity()).isIpExternal());

		getStudentsTask = asyncTaskFactory.getTask(this, CLASS_NAME, "");
		getStudentsTask.execute(selectedClassGroup.getId());

		setStudents(new ArrayList<Student>());
		this.m_adapter = new StudentAdapter(act, R.layout.listgriditem_student,
				getStudents());

		gridview = (GridView) this.getActivity()
				.findViewById(R.id.details_grid);
		listview = (ListView) this.getActivity()
				.findViewById(R.id.details_list);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				constructAddGradeOrAbsenceDialog(position);

			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				constructAddGradeOrAbsenceDialog(position);

			}
		});

		// registerForContextMenu(gridview);
		// registerForContextMenu(listview);
		//
		// LayoutAnimationController controller = AnimationUtils
		// .loadLayoutAnimation(getActivity(),
		// R.anim.list_layout_controller);
		// gridview.setLayoutAnimation(controller);
		// listview.setLayoutAnimation(controller);

	}

	/**
	 * Class which extends the basic ArrayAdapter into a custom Student adapter. <br>
	 * the overridden getView method now copes with out routelist.xml which
	 * contains the layout of our items which will appear in the list.
	 * 
	 * Implements the ViewHolder pattern
	 * 
	 * @author Alex
	 * @version 1.0
	 */

	private static class ViewHolder {
		TextView nameText;
		TextView avarageText;
	}

	public class StudentAdapter extends ArrayAdapter<Student> {

		private ArrayList<Student> items;

		public StudentAdapter(Context context, int textViewResourceId,
				ArrayList<Student> items) {
			super(context, textViewResourceId, items);
			this.items = items;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;
			ViewHolder holder;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) act
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.listgriditem_student, null);

				holder = new ViewHolder();
				holder.nameText = (TextView) v
						.findViewById(R.id.tv_studentName);
				holder.avarageText = (TextView) v.findViewById(R.id.tv_avarage);

				// associate the holder with the view for later lookup
				v.setTag(holder);

			} else {
				// view already exists, get the holder instance from the view
				holder = (ViewHolder) v.getTag();
			}
			Student o = items.get(position);
			if (o != null) {

				holder.nameText.setText(position + 1 + ". " + o.getLastName()
						+ " " + o.getFirstName());

				// holder.avarageText.setText("10");

			}
			return v;

		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle(getResources().getString(R.string.optiuni));
		menu.add(0, v.getId(), 0,
				getResources().getString(R.string.editeaza_note_sau_absente));
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		if (item.getTitle().equals(
				getResources().getString(R.string.editeaza_note_sau_absente))) {
			constructEditGradesDialog(info.position);
		} else {
			return false;
		}
		return true;
	}

	private void constructAddGradeOrAbsenceDialog(int position) {

		// Student s = m_students.get(position);
		//
		// AddGradesOrAbsenceDialog d = new AddGradesOrAbsenceDialog(act, s);
		// d.show();
	}

	private void constructEditGradesDialog(int position) {
		Student s = getStudents().get(position);
		// EditGradesOrAbsencesDialog d = new EditGradesOrAbsencesDialog(act,
		// s);
		// d.show();

	}

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
		act.setStudents(students);
	}

	public void showLoading() {
		progressBar.setVisibility(View.VISIBLE);

	}

	public void hideLoading() {
		progressBar.setVisibility(View.INVISIBLE);
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
