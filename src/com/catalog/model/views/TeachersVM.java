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
package com.catalog.model.views;

import java.io.Serializable;
import java.util.ArrayList;

import com.catalog.model.Teacher;

public class TeachersVM implements Serializable {
	private ArrayList<Teacher> teacherList;

	/**
	 * @return the teacherList
	 */
	public ArrayList<Teacher> getTeacherList() {
		return teacherList;
	}

	/**
	 * @param teacherList
	 *            the teacherList to set
	 */
	public void setTeacherList(ArrayList<Teacher> teacherList) {
		this.teacherList = teacherList;
	}

}
