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
package com.catalog.helper;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDBManager {

	public static MyDBHelper dbh;

	public MyDBManager(Context c) {
		dbh = new MyDBHelper(c);
	}

	public void insertIntoSubjects(String SName, String STeacher) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("SName", SName);
			values.put("STeacher", STeacher);

			db.insert("Subjects", "", values);
		} finally {
			if (db != null)
				db.close();
		}
	}

	public void updateSubjects(String SName, String STeacher) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("STeacher", STeacher);
			db.update("Subjects", values, "SName='" + SName + "'", null);
		} finally {
			if (db != null)
				db.close();
		}
	}

	public void deleteSubjects(String SName) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		try {
			db.delete("Subjects", "SName='" + SName + "'", null);
			db.delete("Hours", "SName='" + SName + "'", null);
		} finally {
			if (db != null)
				db.close();
		}
	}

	public void updateHours(String OLDSName, String OLDHClass, int OLDHDay,
			String OLDHStart, String OLDHEnd, String SName, int HDay,
			String HClass, String HStart, String HEnd) {

		SQLiteDatabase db = dbh.getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("SName", SName);
			values.put("HDay", HDay);
			values.put("HClass", HClass);
			values.put("HStart", HStart);
			values.put("HEnd", HEnd);
			db.update("Hours", values, "SName='" + OLDSName + "'"
					+ "AND HClass='" + OLDHClass + "'" + "AND HDay='" + OLDHDay
					+ "'" + "AND HStart='" + OLDHStart + "'" + "AND HEnd='"
					+ OLDHEnd + "'", null);
		} finally {
			if (db != null)
				db.close();
		}

	}

	public ArrayList<HashMap<String, Object>> selectSubjects() {
		SQLiteDatabase db = dbh.getReadableDatabase();
		try {
			ArrayList<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>();
			Cursor c = db.rawQuery("select * from Subjects", null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				do {
					HashMap<String, Object> resultsMap = new HashMap<String, Object>();
					resultsMap.put("SName",
							c.getString(c.getColumnIndex("SName")));
					resultsMap.put("STeacher",
							c.getString(c.getColumnIndex("STeacher")));
					results.add(resultsMap);
				} while (c.moveToNext());
			}
			c.close();
			return results;
		} finally {
			if (db != null)
				db.close();
		}
	}

	public void insertIntoHours(String SName, int HDay, String HClass,
			String HStart, String HEnd, String HRoom) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("SName", SName);
			values.put("HDay", HDay);
			values.put("HClass", HClass);
			values.put("HStart", HStart);
			values.put("HEnd", HEnd);
			values.put("HRoom", HRoom);
			db.insert("Hours", "", values);
		} finally {
			if (db != null)
				db.close();
		}
	}

	public void deleteHour(String SName, int HDay, String HClass,
			String HStart, String HEnd) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		try {
			db.delete("Hours", "SName = '" + SName + "' " + "AND HDay = '"
					+ HDay + "' " + "AND HClass = '" + HClass + "' "
					+ "AND HStart = '" + HStart + "' " + "AND HEnd = '" + HEnd
					+ "' ", null);
		} finally {
			if (db != null)
				db.close();
		}
	}

	public void deleteAllSubjects() {
		SQLiteDatabase db = dbh.getWritableDatabase();
		try {
			db.delete("SName", null, null);
			db.delete("STeacher", null, null);
		} finally {
			if (db != null)
				db.close();
		}
	}

	public void deleteAllHours() {
		SQLiteDatabase db = dbh.getWritableDatabase();
		try {
			db.delete("Hours", null, null);
		} finally {
			if (db != null)
				db.close();
		}
	}

	public ArrayList<HashMap<String, Object>> selectAllFromDay(int HDay) {
		SQLiteDatabase db = dbh.getReadableDatabase();
		try {
			ArrayList<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>();
			Cursor c = db.rawQuery(
					"select * from Subjects, Hours where Subjects.SName=Hours.SName and HDay="
							+ HDay + " order by HStart", null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				do {
					HashMap<String, Object> resultsMap = new HashMap<String, Object>();
					resultsMap.put("SName",
							c.getString(c.getColumnIndex("SName")));
					resultsMap.put("STeacher",
							c.getString(c.getColumnIndex("STeacher")));
					resultsMap.put("HClass",
							c.getString(c.getColumnIndex("HClass")));
					resultsMap.put("HStart",
							c.getString(c.getColumnIndex("HStart")));
					resultsMap.put("HEnd",
							c.getString(c.getColumnIndex("HEnd")));
					resultsMap.put("HRoom",
							c.getString(c.getColumnIndex("HRoom")));
					results.add(resultsMap);
				} while (c.moveToNext());
			}
			c.close();
			return results;
		} finally {
			if (db != null)
				db.close();
		}
	}
}