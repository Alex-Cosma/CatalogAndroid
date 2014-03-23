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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.catalog.core.CatalogApplication;
import com.catalog.helper.Constants;
import com.catalog.helper.MyDBManager;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

/**
 * Activity in which a user can add a timetable item.
 * 
 * @author Alex
 * 
 */
public class TimetableAddSubjectsActivity extends Activity {

	/*
	 * Static member
	 */
	private static final String CLASSNAME = Constants.TimetableAddSubjectsActivity;

	/*
	 * Public members
	 */
	// none

	/*
	 * Private members
	 */
	private ListView lv;
	private String[] from = { "SName", "STeacher" };
	private int[] to = { R.id.SNameL, R.id.STeacherL };
	private MyDBManager dm;
	private ArrayList<HashMap<String, Object>> results;
	private Dialog addDialog;
	private Button addBT;
	private Button cancelBT;
	private TextView SName;
	private TextView STeacher;
	private String action = "insert";
	private String SNameS;
	private AlertDialog.Builder builder;
	private AlertDialog alert;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.scale_up, R.anim.fadeout);
		setContentView(R.layout.activity_addsubjects);
		CatalogApplication.getGaTracker().set(Fields.SCREEN_NAME,
				Constants.TimetableAddSubjectsActivity);

		lv = (ListView) findViewById(R.id.Slist);
		dm = new MyDBManager(this);
		update();
		addDialog = new Dialog(this);
		addDialog.setCancelable(false);
		addDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		addDialog.setContentView(R.layout.dialog_add_subject);
		SName = (TextView) addDialog.findViewById(R.id.SName);
		STeacher = (TextView) addDialog.findViewById(R.id.STeacher);

		cancelBT = (Button) addDialog.findViewById(R.id.cancelBT);
		cancelBT.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				addDialog.cancel();
				SName.setText(null);
				STeacher.setText(null);
			}

		});
		addBT = (Button) addDialog.findViewById(R.id.addBT);
		addBT.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (action == "insert") {
					dm.insertIntoSubjects(SName.getText().toString(), STeacher
							.getText().toString());
				} else if (action == "update") {
					dm.updateSubjects(SName.getText().toString(), STeacher
							.getText().toString());
				}
				update();
				addDialog.cancel();
				SName.setText(null);
				STeacher.setText(null);
			}

		});

		registerForContextMenu(lv);

		builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Sunteti siguri ca doriti sa stergeti aceasta materie? (toate orele care corespund acestei materii vor fi de asemenea sterse).")
				.setCancelable(false)
				.setPositiveButton("Da", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dm.deleteSubjects(SNameS);
						update();
					}
				})
				.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		alert = builder.create();
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 1, 0, getResources().getString(R.string.editeaza));
		menu.add(0, 2, 0, getResources().getString(R.string.sterge));
		menu.setHeaderTitle(getResources().getString(R.string.optiuni));
	}

	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		SNameS = results.get(info.position).get("SName").toString();
		String STeacherS = results.get(info.position).get("STeacher")
				.toString();

		switch (item.getItemId()) {
		case 1:
			addDialog.show();
			SName.setText(SNameS);
			STeacher.setText(STeacherS);
			action = "update";
			SName.setEnabled(false);
			SName.setText(SNameS);
			STeacher.setText(STeacherS);
			break;
		case 2:
			alert.show();
			break;
		}
		update();
		return true;
	}

	public void addSubject(View v) {
		SName.setEnabled(true);
		addDialog.show();
		action = "insert";
		SName.requestFocus();
	}

	public class myAdapter extends SimpleAdapter {

		public myAdapter(Context context, List<? extends Map<String, ?>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);

		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);

			return view;
		}
	}

	private void update() {
		results = dm.selectSubjects();

		myAdapter mA = new myAdapter(this, results,
				R.layout.listitem_timetable_subject, from, to);
		lv.setAdapter(mA);
	}

	public void onBackPressed() {
		finish();
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