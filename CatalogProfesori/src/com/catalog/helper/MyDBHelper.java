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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

	private static final String DBName = "studenttimetable.db";
	private static final int DBVersion = 1;

	public MyDBHelper(Context context) {
		super(context, DBName, null, DBVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE Subjects" + "(SName TEXT PRIMARY KEY,"
				+ "STeacher TEXT," + "SColor TEXT" + ");";
		db.execSQL(query);
		query = "CREATE TABLE Hours"
				+ "(_id INTEGER AUTO_INCREMENT PRIMARY KEY," + "SName TEXT,"
				+ "HDay INTEGER," + "HClass INTEGER," + "HStart TEXT,"
				+ "HEnd TEXT," + "HRoom TEXT" + ");";
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Hours");
		db.execSQL("DROP TABLE IF EXISTS Subjects");
		onCreate(db);
	}
}
