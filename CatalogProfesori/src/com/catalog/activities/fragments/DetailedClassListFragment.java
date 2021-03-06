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

import android.app.Activity;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.catalog.activities.DetailedClassActivity;
import com.catalog.activities.R;
import com.catalog.core.AppPreferences;
import com.catalog.core.AsyncTaskFactory;
import com.catalog.core.CatalogApplication;
import com.catalog.helper.Constants;
import com.catalog.helper.MergeAdapter;
import com.catalog.model.ClassGroup;
import com.catalog.model.Student;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

/**
 * The leftmost List Fragment of the DetailedClassActivity.
 * 
 * @author Alex
 * 
 */
public class DetailedClassListFragment extends ListFragment {
	/*
	 * Static members
	 */
	private static final String CLASS_NAME = Constants.DetailedClassListFragment;
	private static final String GETSTUDENTS = Constants.Method_GetStudents;

	/*
	 * Public members
	 */
	public ArrayAdapter<String> adapter;
	public ArrayList<Student> students;
	public MergeAdapter mergeAdapter;
	public DetailedClassActivity myActivity;

	/*
	 * Private members
	 */
	private int mCurCheckPosition = 0;
	private ClassGroup classGroup;
	private AsyncTaskFactory asyncTaskFactory;
	private AsyncTask<Object, Void, Integer> getStudentsTask;

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
		this.myActivity = (DetailedClassActivity) myActivity;

	}

	@Override
	public void onActivityCreated(Bundle icicle) {

		super.onActivityCreated(icicle);
		adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.listitem_class_and_student, R.id.tv_class_or_student);
		mergeAdapter = new MergeAdapter();
		classGroup = this.myActivity.getClassGroup();
		asyncTaskFactory = AsyncTaskFactory.getInstance(AppPreferences
				.getInstance(getActivity()).isIpExternal());

		mergeAdapter.addView(buildSubjectListItem("Clasa a"
				+ classGroup.getYearOfStudy() + "-a " + classGroup.getName()));

		getStudentsTask = asyncTaskFactory
				.getTask(this, CLASS_NAME, GETSTUDENTS);
		getStudentsTask.execute(classGroup.getId());

	}

	public void onSaveInstanceState(Bundle icicle) {
		super.onSaveInstanceState(icicle);
		icicle.putInt("curChoice", mCurCheckPosition);
	}

	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		if (v.getId() != R.id.layout_subject) {

			myActivity.showStudents(pos - 1);
			mCurCheckPosition = pos;

		}
	}

	private View buildSubjectListItem(String subject) {
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.listitem_class_teached_subject, null);

		TextView subjectTv = (TextView) v.findViewById(R.id.tv_subject);
		subjectTv.setText(subject);

		return (v);
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
