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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.TimePicker;

import com.catalog.activities.extras.AsyncTaskFactory;
import com.catalog.helper.AppPreferences;
import com.catalog.helper.Constants;
import com.catalog.helper.MyDBManager;
import com.catalog.model.Teacher;
import com.catalog.model.Timetable;
import com.catalog.model.TimetableDays;

/**
 * Activity which shows the main Timetable for each teacher.
 * 
 * @author Alex
 * 
 */
@SuppressWarnings("deprecation")
public class TimetableActivity extends TabActivity implements
		OnTabChangeListener {
	/*
	 * Static members
	 */
	private static final String CLASSNAME = Constants.TimetableActivity;
	private static final int SUCCESS = Constants.SUCCESS;
	private static final int FAIL = Constants.FAIL;
	private static final String MONDAY_TAB_TAG = "Luni";
	private static final String TUESDAY_TAB_TAG = "Marti";
	private static final String WEDNESDAY_TAB_TAG = "Miercuri";
	private static final String THURSDAY_TAB_TAG = "Joi";
	private static final String FRIDAY_TAB_TAG = "Vineri";

	/*
	 * Public members
	 */
	public ArrayList<TimetableDays> timetable;

	/*
	 * Private members
	 */
	private TabHost tabHost;
	private ListView mondayLW;
	private ListView tuesdayLW;
	private ListView wednesdayLW;
	private ListView thursdayLW;
	private ListView fridayLW;
	private ListView currentLW;
	private MyDBManager dm;
	private ArrayList<HashMap<String, Object>> results;
	private SimpleAdapter simpleAdapter;
	private String[] from = { "SName", "STeacher", "HClass", "HStart", "HEnd",
			"HRoom" };
	private static int[] to = { R.id.SName, R.id.STeacher, R.id.HClass,
			R.id.HStart, R.id.HEnd, R.id.HRoom };
	private int currentDay;
	private Intent intent;
	private Dialog addDialog;
	private EditText HClass;
	private String[] arraydays;
	private ArrayAdapter<String> Sarrayadapter;
	private ArrayAdapter<String> arrayadapter;
	private String selectedDay;
	private TimePickerDialog fromDialog;
	private TimePickerDialog toDialog;
	private TextView start;
	private TextView end;
	private String[] arraySubjects;
	private Spinner SName;
	private Spinner HDay;
	private String selectedSubject;
	private String action = "insert";
	private String OLDSName;
	private String OLDHClass;
	private int OLDHDay;
	private String OLDHStart;
	private String OLDHEnd;
	private AlertDialog.Builder builder;
	private AlertDialog.Builder builder2;
	private AlertDialog alert;
	private AlertDialog alert2;
	private View footerView;
	private updateTask task;
	private TimetableActivity act;
	private Teacher teacher;
	private AppPreferences prefs;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.activity_timetable);

		dm = new MyDBManager(this); // Create DB
		act = this;
		prefs = AppPreferences.getInstance(act);
		Bundle b = getIntent().getExtras();

		timetable = (ArrayList<TimetableDays>) b
				.getSerializable(Constants.Timetable);
		teacher = (Teacher) b.getSerializable(Constants.Teacher);

		insertIntoDB();

		// setup tab host
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.setOnTabChangedListener(this);

		// setup lists
		mondayLW = (ListView) findViewById(R.id.monday);
		tuesdayLW = (ListView) findViewById(R.id.tuesday);
		wednesdayLW = (ListView) findViewById(R.id.wednesday);
		thursdayLW = (ListView) findViewById(R.id.thursday);
		fridayLW = (ListView) findViewById(R.id.friday);
		LayoutInflater inflater = this.getLayoutInflater();
		footerView = inflater.inflate(R.layout.listitem_add_footer, null);
		mondayLW.addFooterView(footerView);
		tuesdayLW.addFooterView(footerView);
		wednesdayLW.addFooterView(footerView);
		thursdayLW.addFooterView(footerView);
		fridayLW.addFooterView(footerView);

		setListeners();

		// add views to tab host
		tabHost.addTab(tabHost.newTabSpec(MONDAY_TAB_TAG)
				.setIndicator(MONDAY_TAB_TAG)
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return mondayLW;
					}
				}));
		tabHost.addTab(tabHost.newTabSpec(TUESDAY_TAB_TAG)
				.setIndicator(TUESDAY_TAB_TAG)
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return tuesdayLW;
					}
				}));
		tabHost.addTab(tabHost.newTabSpec(WEDNESDAY_TAB_TAG)
				.setIndicator(WEDNESDAY_TAB_TAG)
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return wednesdayLW;
					}
				}));
		tabHost.addTab(tabHost.newTabSpec(THURSDAY_TAB_TAG)
				.setIndicator(THURSDAY_TAB_TAG)
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return thursdayLW;
					}
				}));
		tabHost.addTab(tabHost.newTabSpec(FRIDAY_TAB_TAG)
				.setIndicator(FRIDAY_TAB_TAG)
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return fridayLW;
					}
				}));

		currentDay = getCurrentDayofWeek();
		tabHost.setCurrentTab(currentDay);
		currentLW = mondayLW;

		task = new updateTask(this);
		task.execute();

		intent = new Intent(getApplicationContext(),
				TimetableAddSubjectsActivity.class);

		builder = new AlertDialog.Builder(this);
		builder.setMessage("Sunteti siguri ca doriti sa stergeti toate orele?")
				.setCancelable(false)
				.setPositiveButton("Da", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dm.deleteAllHours();
						task = new updateTask(act);
						task.execute();
					}
				})
				.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		alert = builder.create();

		builder2 = new AlertDialog.Builder(this);
		builder2.setMessage("Sunteti siguri ca doriti sa stergeti aceasta ora?")
				.setCancelable(false)
				.setPositiveButton("Da", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dm.deleteHour(OLDSName, OLDHDay, OLDHClass, OLDHStart,
								OLDHEnd);
						task = new updateTask(act);
						task.execute();
					}
				})
				.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		alert2 = builder2.create();

	}

	private void setListeners() {
		mondayLW.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				if (id == footerView.getId()) {
					action = "insert";
					ArrayList<HashMap<String, Object>> arrayS = dm
							.selectSubjects();
					arraySubjects = new String[arrayS.size()];
					Iterator<HashMap<String, Object>> it = arrayS.iterator();
					int i = 0;
					while (it.hasNext()) {
						HashMap<String, Object> hm = it.next();
						arraySubjects[i] = hm.get("SName").toString();
						i++;
					}
					Sarrayadapter = new ArrayAdapter<String>(
							getApplicationContext(),
							android.R.layout.simple_spinner_item, arraySubjects);
					Sarrayadapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					SName.setAdapter(Sarrayadapter);
					addDialog.setTitle("Adauga Ora de clasa");
					addDialog.show();
				}

			}
		});

		tuesdayLW.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				if (id == footerView.getId()) {
					action = "insert";
					ArrayList<HashMap<String, Object>> arrayS = dm
							.selectSubjects();
					arraySubjects = new String[arrayS.size()];
					Iterator<HashMap<String, Object>> it = arrayS.iterator();
					int i = 0;
					while (it.hasNext()) {
						HashMap<String, Object> hm = it.next();
						arraySubjects[i] = hm.get("SName").toString();
						i++;
					}
					Sarrayadapter = new ArrayAdapter<String>(
							getApplicationContext(),
							android.R.layout.simple_spinner_item, arraySubjects);
					Sarrayadapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					SName.setAdapter(Sarrayadapter);
					addDialog.setTitle("Adauga Ora de clasa");
					addDialog.show();
				}

			}
		});

		wednesdayLW.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				if (id == footerView.getId()) {
					action = "insert";
					ArrayList<HashMap<String, Object>> arrayS = dm
							.selectSubjects();
					arraySubjects = new String[arrayS.size()];
					Iterator<HashMap<String, Object>> it = arrayS.iterator();
					int i = 0;
					while (it.hasNext()) {
						HashMap<String, Object> hm = it.next();
						arraySubjects[i] = hm.get("SName").toString();
						i++;
					}
					Sarrayadapter = new ArrayAdapter<String>(
							getApplicationContext(),
							android.R.layout.simple_spinner_item, arraySubjects);
					Sarrayadapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					SName.setAdapter(Sarrayadapter);
					addDialog.setTitle("Adauga Ora de clasa");
					addDialog.show();
				}

			}
		});

		thursdayLW.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				if (id == footerView.getId()) {
					action = "insert";
					ArrayList<HashMap<String, Object>> arrayS = dm
							.selectSubjects();
					arraySubjects = new String[arrayS.size()];
					Iterator<HashMap<String, Object>> it = arrayS.iterator();
					int i = 0;
					while (it.hasNext()) {
						HashMap<String, Object> hm = it.next();
						arraySubjects[i] = hm.get("SName").toString();
						i++;
					}
					Sarrayadapter = new ArrayAdapter<String>(
							getApplicationContext(),
							android.R.layout.simple_spinner_item, arraySubjects);
					Sarrayadapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					SName.setAdapter(Sarrayadapter);
					addDialog.setTitle("Adauga Ora de clasa");
					addDialog.show();
				}

			}
		});

		fridayLW.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				if (id == footerView.getId()) {
					action = "insert";
					ArrayList<HashMap<String, Object>> arrayS = dm
							.selectSubjects();
					arraySubjects = new String[arrayS.size()];
					Iterator<HashMap<String, Object>> it = arrayS.iterator();
					int i = 0;
					while (it.hasNext()) {
						HashMap<String, Object> hm = it.next();
						arraySubjects[i] = hm.get("SName").toString();
						i++;
					}
					Sarrayadapter = new ArrayAdapter<String>(
							getApplicationContext(),
							android.R.layout.simple_spinner_item, arraySubjects);
					Sarrayadapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					SName.setAdapter(Sarrayadapter);
					addDialog.setTitle("Adauga Ora de clasa");
					addDialog.show();
				}

			}
		});

	}

	public void refreshLists() {
		task = new updateTask(this);
		task.execute();
	}

	private void insertIntoDB() {
		if (prefs.isFirstEntry()) {
			prefs.setFirstEntry(false);
			dm.deleteAllHours();
			int HDay;

			for (TimetableDays ttd : timetable) {
				for (Timetable tt : ttd.getTimetables()) {
					try {
						dm.insertIntoSubjects(tt.getSubjectteacherforclass()
								.getSubject().getName(), teacher.getLastName()
								+ " " + teacher.getFirstName());
					} catch (Exception e) {

					} finally {
						HDay = SDay2IDay(tt.getDay());

						String startHour = tt
								.getHour()
								.toString()
								.substring(0,
										tt.getHour().toString().length() - 3);
						String endHour;
						if (tt.getHour().getHours() + 1 >= 10)
							endHour = String
									.valueOf(tt.getHour().getHours() + 1)
									+ ":00";
						else {
							endHour = "0"
									+ String.valueOf(tt.getHour().getHours() + 1)
									+ ":00";
						}

						String classGroup = "a"
								+ tt.getSubjectteacherforclass()
										.getClassgroup().getYearOfStudy()
								+ "-a "
								+ tt.getSubjectteacherforclass()
										.getClassgroup().getName();

						dm.insertIntoHours(tt.getSubjectteacherforclass()
								.getSubject().getName(), HDay, "Clasa: "
								+ classGroup, startHour, endHour, " Sala: "
								+ tt.getRoom());
					}
				}
			}
		}
	}

	private int getCurrentDayofWeek() {

		Calendar c = Calendar.getInstance();
		int currentDay = c.get(Calendar.DAY_OF_WEEK);
		switch (currentDay) {
		case 1:
			/* Sunday */
			currentLW = mondayLW;
			currentDay = 0;
			break;
		case 2:
			/* Monday */
			currentLW = mondayLW;
			currentDay -= 2;
			break;
		case 3:
			/* Tuesday */
			currentLW = tuesdayLW;
			currentDay -= 2;
			break;
		case 4:
			/* Wednesday */
			currentLW = wednesdayLW;
			currentDay -= 2;
			break;
		case 5:
			/* Thursday */
			currentLW = thursdayLW;
			currentDay -= 2;
			break;
		case 6:
			/* Friday */
			currentLW = fridayLW;
			currentDay -= 2;
			break;
		case 7:
			/* Saturday */
			currentLW = mondayLW;
			currentDay = 0;
			break;
		}
		return currentDay;
	}

	public void onTabChanged(String tabName) {
		/** Change the tab content to the appropriated one */

		if (tabName.equals(MONDAY_TAB_TAG)) {
			currentLW = mondayLW;
			currentDay = 0;
		} else if (tabName.equals(TUESDAY_TAB_TAG)) {
			currentLW = tuesdayLW;
			currentDay = 1;
		} else if (tabName.equals(WEDNESDAY_TAB_TAG)) {
			currentLW = wednesdayLW;
			currentDay = 2;
		} else if (tabName.equals(THURSDAY_TAB_TAG)) {
			currentLW = thursdayLW;
			currentDay = 3;
		} else if (tabName.equals(FRIDAY_TAB_TAG)) {
			currentLW = fridayLW;
			currentDay = 4;
		}
		task = new updateTask(this);
		task.execute();

		registerForContextMenu(mondayLW);
		registerForContextMenu(tuesdayLW);
		registerForContextMenu(wednesdayLW);
		registerForContextMenu(thursdayLW);
		registerForContextMenu(fridayLW);

		addDialog = new Dialog(this);
		action = "insert";
		addDialog.setTitle("Add Lesson");
		addDialog.setContentView(R.layout.dialog_add_hour);

		HClass = (EditText) addDialog.findViewById(R.id.classroomEdit);
		arraydays = new String[] { "Luni", "Marti", "Miercuri", "Joi", "Vineri" };
		HDay = (Spinner) addDialog.findViewById(R.id.day);
		arrayadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arraydays);
		arrayadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		HDay.setAdapter(arrayadapter);
		HDay.setSelection(SDay2IDay(IDay2SDay(currentDay)));
		HDay.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {
				selectedDay = arraydays[position];
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		fromDialog = new TimePickerDialog(this, fromTimeSetListener, 12, 0,
				true);
		toDialog = new TimePickerDialog(this, toTimeSetListener, 12, 0, true);

		SName = (Spinner) addDialog.findViewById(R.id.SName);
		SName.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {
				selectedSubject = arraySubjects[position];
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		Button buttonFrom = (Button) addDialog.findViewById(R.id.buttonFrom);
		buttonFrom.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				fromDialog.show();
			}
		});

		Button buttonTo = (Button) addDialog.findViewById(R.id.buttonTo);
		buttonTo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toDialog.show();
			}
		});

		start = (TextView) addDialog.findViewById(R.id.start);
		end = (TextView) addDialog.findViewById(R.id.end);

		Button cancel = (Button) addDialog.findViewById(R.id.cancel);
		cancel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				addDialog.cancel();
				fromDialog.updateTime(12, 0);
				toDialog.updateTime(12, 0);
				start.setText("--:--");
				end.setText("--:--");
				HClass.setText(null);
			}

		});

		Button ok = (Button) addDialog.findViewById(R.id.ok);
		ok.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				int HDay = SDay2IDay(selectedDay);
				if (action.equals("insert"))
					dm.insertIntoHours(selectedSubject, HDay, HClass.getText()
							.toString(), start.getText().toString(), end
							.getText().toString(), "");
				else if (action.equals("edit")) {
					dm.updateHours(OLDSName, OLDHClass, OLDHDay, OLDHStart,
							OLDHEnd, selectedSubject, HDay, HClass.getText()
									.toString(), start.getText().toString(),
							end.getText().toString());
				}
				addDialog.cancel();
				fromDialog.updateTime(12, 0);
				toDialog.updateTime(12, 0);
				start.setText("--:--");
				end.setText("--:--");
				HClass.setText(null);
				task = new updateTask(act);
				task.execute();
			}

		});

	}

	private TimePickerDialog.OnTimeSetListener fromTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		public void onTimeSet(TimePicker view, int hour, int minute) {

			StringBuilder sb = new StringBuilder();

			if (hour < 10)
				sb.append("0");
			sb.append(hour + ":");
			if (minute < 10)
				sb.append("0");
			sb.append(minute);

			start.setText(sb);
		}

	};

	private TimePickerDialog.OnTimeSetListener toTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		public void onTimeSet(TimePicker view, int hour, int minute) {

			StringBuilder sb = new StringBuilder();

			if (hour < 10)
				sb.append("0");
			sb.append(hour + ":");
			if (minute < 10)
				sb.append("0");
			sb.append(minute);

			end.setText(sb);
		}
	};

	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, "Gestioneaza Materii").setIcon(
				android.R.drawable.ic_menu_manage);
		menu.add(1, 1, 1, "Adauga Ora de clasa").setIcon(
				android.R.drawable.ic_menu_add);
		menu.add(1, 2, 2, "Reseteaza orarul").setIcon(
				android.R.drawable.ic_menu_revert);
		menu.add(1, 3, 3, "Sterge Tot").setIcon(
				android.R.drawable.ic_menu_delete);

		return result;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			startActivityForResult(intent, 0);
			break;
		case 1:
			action = "insert";
			ArrayList<HashMap<String, Object>> arrayS = dm.selectSubjects();
			arraySubjects = new String[arrayS.size()];
			Iterator<HashMap<String, Object>> it = arrayS.iterator();
			int i = 0;
			while (it.hasNext()) {
				HashMap<String, Object> hm = it.next();
				arraySubjects[i] = hm.get("SName").toString();
				i++;
			}
			Sarrayadapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, arraySubjects);
			Sarrayadapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			SName.setAdapter(Sarrayadapter);
			addDialog.setTitle("Adauga Ora de clasa");
			addDialog.show();
			break;
		case 2:
			prefs.setFirstEntry(true);
			insertIntoDB();
			update();
			break;
		case 3:
			alert.show();
			break;

		}
		return true;
	}

	private int SDay2IDay(String selectedDay) {

		if (selectedDay.equals("Marti"))
			return 1;
		else if (selectedDay.equals("Miercuri"))
			return 2;
		else if (selectedDay.equals("Joi"))
			return 3;
		else if (selectedDay.equals("Vineri"))
			return 4;

		return 0;
	}

	private String IDay2SDay(int selectedDay) {

		if (selectedDay == 1)
			return "Marti";
		else if (selectedDay == 2)
			return "Miercuri";
		else if (selectedDay == 3)
			return "Joi";
		else if (selectedDay == 4)
			return "Vineri";

		return "Luni";
	}

	private void update() {

		results = dm.selectAllFromDay(currentDay);

		simpleAdapter = new myAdapter(this, results,
				R.layout.listitem_timetable_daylist, from, to);

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				currentLW.setAdapter(simpleAdapter);
				currentLW.invalidate();
			}

		});

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

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (((AdapterContextMenuInfo) menuInfo).position != currentLW
				.getAdapter().getCount() - 1) {
			menu.add(0, 1, 0, getResources().getString(R.string.editeaza));
			menu.add(0, 2, 0, getResources().getString(R.string.sterge));
			menu.setHeaderTitle(getResources().getString(R.string.optiuni));
		}
	}

	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		String course = results.get(info.position).get("SName").toString();
		String classroom = results.get(info.position).get("HClass").toString();
		String startH = results.get(info.position).get("HStart").toString();
		String endH = results.get(info.position).get("HEnd").toString();
		int day = currentDay;

		switch (item.getItemId()) {
		case 1:
			action = "edit";
			ArrayList<HashMap<String, Object>> arrayS = dm.selectSubjects();
			arraySubjects = new String[arrayS.size()];
			Iterator<HashMap<String, Object>> it = arrayS.iterator();
			int i = 0;
			while (it.hasNext()) {
				HashMap<String, Object> hm = it.next();
				arraySubjects[i] = hm.get("SName").toString();
				i++;
			}
			Sarrayadapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, arraySubjects);
			Sarrayadapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			SName.setAdapter(Sarrayadapter);
			SName.setSelection(Sarrayadapter.getPosition(course));
			String dayS = IDay2SDay(day);
			HDay.setSelection(arrayadapter.getPosition(dayS));
			CharSequence fhour = startH.subSequence(0, 2);
			CharSequence fminutes = startH.subSequence(3, 5);
			CharSequence thour = endH.subSequence(0, 2);
			CharSequence tminutes = endH.subSequence(3, 5);
			int fhourInt;
			int fminutesInt;
			try {
				fhourInt = Integer.parseInt((String) fhour);
				fminutesInt = Integer.parseInt((String) fminutes);
			} catch (Exception e) {
				fhourInt = 12;
				fminutesInt = 0;
			}
			int thourInt;
			int tminutesInt;
			try {
				thourInt = Integer.parseInt((String) thour);
				tminutesInt = Integer.parseInt((String) tminutes);
			} catch (Exception e) {
				thourInt = 12;
				tminutesInt = 0;
			}
			fromDialog.updateTime(fhourInt, fminutesInt);
			toDialog.updateTime(thourInt, tminutesInt);
			start.setText(startH);
			end.setText(endH);
			HClass.setText(classroom);
			addDialog.setTitle("Edit Lesson");
			OLDSName = course;
			OLDHClass = classroom;
			OLDHDay = day;
			OLDHStart = startH;
			OLDHEnd = endH;
			addDialog.show();
			break;
		case 2:
			OLDSName = course;
			OLDHClass = classroom;
			OLDHDay = day;
			OLDHStart = startH;
			OLDHEnd = endH;
			alert2.show();
			break;
		}
		task = new updateTask(this);
		task.execute();
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		task = new updateTask(this);
		task.execute();
	}

	private static class updateTask extends AsyncTask<Object, Void, Integer> {
		private WeakReference<TimetableActivity> mActivityRef;

		public updateTask(TimetableActivity activity) {
			mActivityRef = new WeakReference<TimetableActivity>(activity);
		}

		@Override
		protected void onPreExecute() {
			if (mActivityRef == null) {
				return;
			}

			TimetableActivity activity = mActivityRef.get();

			if (activity == null) {
				return;
			}

		}

		@Override
		protected Integer doInBackground(Object... params) {
			TimetableActivity activity = mActivityRef.get();

			if (activity == null) {
				return FAIL;
			}

			activity.update();
			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (mActivityRef == null) {
				return;
			}

			TimetableActivity activity = mActivityRef.get();

			return;
		}
	}

}