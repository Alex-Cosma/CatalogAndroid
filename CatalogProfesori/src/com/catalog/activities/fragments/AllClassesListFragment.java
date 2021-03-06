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
import java.util.Comparator;

import android.app.Activity;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.catalog.activities.AllClassesActivity;
import com.catalog.activities.R;
import com.catalog.activities.R.id;
import com.catalog.activities.R.layout;
import com.catalog.core.AppPreferences;
import com.catalog.core.AsyncTaskFactory;
import com.catalog.core.CatalogApplication;
import com.catalog.helper.Constants;
import com.catalog.helper.MergeAdapter;
import com.catalog.model.ClassGroup;
import com.catalog.model.Student;
import com.catalog.model.SubjectClasses;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

/*
 * The leftmost List Fragment of the AddGradesClassActivity.
 * 
 * @author Alex
 * 
 */
public class AllClassesListFragment extends ListFragment {
	/*
	 * Static members
	 */
	private static final String CLASS_NAME = Constants.AllClassesListFragment;
	/*
	 * Public members
	 */
	public ArrayList<SubjectClasses> subjectClassesList;
	public ArrayAdapter<String>[] classesAdapter;
	public MergeAdapter mergeAdapter;
	public AllClassesActivity myActivity;
	/*
	 * Private members
	 */
	private int mCurCheckPosition = 0;

	private AsyncTaskFactory asyncFactory;
	private AsyncTask<Object, Void, Integer> getSubjectsClassesTask;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (icicle != null) {
			// Restore last state for checked position.
			mCurCheckPosition = icicle.getInt("curChoice", 0);
		}

		CatalogApplication.getGaTracker().set(Fields.SCREEN_NAME, CLASS_NAME);

	}

	@Override
	public void onAttach(Activity myActivity) {

		super.onAttach(myActivity);
		this.myActivity = (AllClassesActivity) myActivity;
	}

	@Override
	public void onActivityCreated(Bundle icicle) {

		super.onActivityCreated(icicle);
		asyncFactory = AsyncTaskFactory.getInstance(AppPreferences.getInstance(
				getActivity()).isIpExternal());
		// // Populate list with our static array of titles.
		mergeAdapter = new MergeAdapter();

		ListView lv = getListView();
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		getSubjectsClassesTask = asyncFactory.getTask(this, CLASS_NAME, "");
		getSubjectsClassesTask.execute();

	}

	public void onSaveInstanceState(Bundle icicle) {
		super.onSaveInstanceState(icicle);
		icicle.putInt("curChoice", mCurCheckPosition);
	}

	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		if (v.getId() != R.id.layout_subject) {
			int numberOfSubjects = subjectClassesList.size();

			int posInList = pos - 1;

			for (int i = 0; i < numberOfSubjects; i++) {

				if (posInList <= classesAdapter[i].getCount()) {
					myActivity.showStudents(i, posInList,
							AllClassesStudentsDetailsFragment.viewingAsList);
					mCurCheckPosition = posInList;
					break;
				} else {
					posInList -= (classesAdapter[i].getCount() + 1);

				}

			}

		}
	}

	public View buildSubjectListItem(String subject) {
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.listitem_class_teached_subject, null);

		TextView subjectTv = (TextView) v.findViewById(R.id.tv_subject);
		subjectTv.setText(subject);

		return (v);
	}

	public Comparator<ClassGroup> ComparatorByYearOfStudy = new Comparator<ClassGroup>() {

		public int compare(ClassGroup c1, ClassGroup c2) {
			if (c1.getYearOfStudy() == c2.getYearOfStudy()) {
				return c1.getName().compareTo(c2.getName());
			} else
				return (c1.getYearOfStudy() < c2.getYearOfStudy() ? -1 : 1);
		}
	};

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
