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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

import com.catalog.model.Attendance;
import com.catalog.model.ChartData;
import com.catalog.model.GradesAttendForSubject;
import com.catalog.model.LoginCredentials;
import com.catalog.model.StudentMark;
import com.catalog.model.TimetableDays;
import com.catalog.model.views.AttendanceSingleVM;
import com.catalog.model.views.AttendanceVM;
import com.catalog.model.views.GradesAttendForSubjectVM;
import com.catalog.model.views.MasterClassVM;
import com.catalog.model.views.StudentMarkVM;
import com.catalog.model.views.StudentMarksVM;
import com.catalog.model.views.StudentsVM;
import com.catalog.model.views.SubjectClassesVM;
import com.catalog.model.views.SubjectTeacherForClassVM;
import com.catalog.model.views.TeacherVM;
import com.catalog.model.views.TeachersVM;
import com.catalog.model.views.TimetableVM;
import com.catalog.model.views.UserVM;

public class Api implements Api_I {

	private static Api API;
	private long startTime;

	private static final int SUCCESS = Constants.SUCCESS;
	private static final int BAD_CONNECTION = Constants.BAD_CONNECTION;
	private static final int UNAUTHORIZED = Constants.UNAUTHORIZED;
	private static String IP_EXTERNAL = "www.e-racovita.ro:8022/";
	private static String IP_INTERNAL = "192.168.31.6:8080/";
	private static String EXTENSION_EXTERNAL = "catalog";
	private static String EXTENSION_INTERNAL = "catalog";

	private String IP = "";
	private String EXTENSION = "";

	private Api(boolean isExternal) {
		if (isExternal) {
			IP = IP_EXTERNAL;
			EXTENSION = EXTENSION_EXTERNAL;
		} else {
			IP = IP_INTERNAL;
			EXTENSION = EXTENSION_INTERNAL;
		}
	};

	public static Api getInstance(boolean isExternal) {
		if (API == null)
			API = new Api(isExternal);
		return API;
	}

	@Override
	public int login(String username, String password) {
		setStartTime();

		String url = "http://" + IP + EXTENSION
				+ "/resources/j_spring_security_check";

		// Set the username and password for creating a Basic Auth request
		HttpAuthentication authHeader = new HttpBasicAuthentication(username,
				password);
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAuthorization(authHeader);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		// Make the HTTP GET request, marshaling the response from JSON to
		// an array of Events
		try {
			restTemplate.exchange(url, HttpMethod.GET, requestEntity,
					Object.class);

		} catch (Exception e) {
			// Unauthorized, probably.
			// maybe check for network status?
			e.printStackTrace();
			if (e.getMessage().contains("Unauthorized"))
				return UNAUTHORIZED;

			return BAD_CONNECTION;
		}
		// everything went fine
		setLoginCredentials(username, password);
		getElapsedTime("login - ");
		return SUCCESS;

	}

	@Override
	public boolean changePassword(String newPassword) {

		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://" + IP + EXTENSION + "/user/changePassword/"
				+ newPassword + ".json";
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		ResponseEntity<UserVM> responseEntity;
		UserVM user = null;

		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, UserVM.class);
			user = responseEntity.getBody();

		} catch (RestClientException e) {
			return false;
		}

		Log.d("TAAAG", user.toString());
		getElapsedTime("changePassword - ");
		if (user.getStatus().equals("OK"))
			return true;
		else
			return false;
	}

	@Override
	public TeachersVM getTeachers() {
		setStartTime();

		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://" + IP + EXTENSION + "/teacher/list.json";
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		ResponseEntity<TeachersVM> responseEntity = null;
		TeachersVM teachers = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, TeachersVM.class);
			teachers = responseEntity.getBody();
		} catch (RestClientException e) {
			return null;
		}

		Log.d("TAAAG", teachers.toString());
		// dueTheDue();

		getElapsedTime("getTeachers - ");
		return teachers;
	}

	@Override
	public MasterClassVM getMasterClass(int id) {
		setStartTime();

		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();
		String url = "http://" + IP + EXTENSION + "/teacher/masterClassT";
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		ResponseEntity<MasterClassVM> responseEntity = null;
		MasterClassVM masterClass = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, MasterClassVM.class);
			masterClass = responseEntity.getBody();
		} catch (RestClientException e) {
			return null;
		}

		Log.d("TAAAG", masterClass.toString());

		getElapsedTime("getMasterClass - ");

		return masterClass;
	}

	@Override
	public StudentsVM getStudentsForClass(int id) {
		setStartTime();

		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();
		String url = "http://" + IP + EXTENSION + "/student/classStudents/"
				+ id + ".json";
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		ResponseEntity<StudentsVM> responseEntity = null;
		StudentsVM students = null;

		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, StudentsVM.class);
			students = responseEntity.getBody();
		} catch (RestClientException e) {
			return null;
		}

		Log.d("TAAAG", students.toString());

		getElapsedTime("getStudentsForClass - ");

		return students;
	}

	@Override
	public StudentMarksVM getMarksForStudent(int studentId, int subjectId) {
		setStartTime();

		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://" + IP + EXTENSION + "/student/studentMarks/" + 2
				+ "," + 4 + ".json";
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		ResponseEntity<StudentMarksVM> responseEntity = null;
		StudentMarksVM marks = null;

		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, StudentMarksVM.class);
			marks = responseEntity.getBody();
		} catch (RestClientException e) {
			return null;
		}

		Log.d("TAAAG", marks.toString());

		getElapsedTime("getMarksForStudent - ");

		return marks;
	}

	@Override
	public AttendanceVM getAttendanceForStudent(int studentId, int subjectId) {
		setStartTime();

		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://" + IP + EXTENSION
				+ "/student/studentAttendances/" + 2 + "," + 4 + ".json";
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		ResponseEntity<AttendanceVM> responseEntity = null;
		AttendanceVM attendances = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, AttendanceVM.class);
			attendances = responseEntity.getBody();
		} catch (RestClientException e) {
			return null;
		}

		Log.d("TAAAG", attendances.toString());

		getElapsedTime("getAttendanceForStudent - ");

		return attendances;
	}

	@Override
	public StudentMark addStudentMark(int studentId, int stfcId, int grade,
			boolean finalExam, long date) {
		setStartTime();

		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();

		// Add the Jackson message converter
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

		int finalExamInt = finalExam ? 1 : 0;
		String url = "http://" + IP + EXTENSION
				+ "/studentMark/formStudentMark/" + studentId + "," + stfcId
				+ "," + grade + "," + finalExamInt + "," + date + ".json";

		ResponseEntity<StudentMarkVM> responseEntity = null;

		StudentMarkVM response = null;

		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, StudentMarkVM.class);
			response = responseEntity.getBody();
		} catch (RestClientException e) {
			return null;
		}

		Log.d("TAAAG", response.toString());
		getElapsedTime("addStudentMark - ");
		return response.getStudentMark();
	}

	@Override
	public StudentMark editStudentMark(int markId, int newMark, long date) {
		setStartTime();

		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();

		// Add the Jackson message converter
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		restTemplate.getMessageConverters().add(
				new StringHttpMessageConverter());
		restTemplate.getMessageConverters().add(
				new ByteArrayHttpMessageConverter());
		restTemplate.getMessageConverters().add(
				new ResourceHttpMessageConverter());

		String url = "http://" + IP + EXTENSION
				+ "/studentMark/editStudentMark/" + markId + "," + newMark
				+ "," + date + ".json";

		ResponseEntity<StudentMarkVM> responseEntity = null;
		StudentMarkVM response = null;

		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, StudentMarkVM.class);
			response = responseEntity.getBody();
		} catch (RestClientException e) {
			return null;
		}

		Log.d("TAAAG", response.toString());
		getElapsedTime("editStudentMark - ");
		return response.getStudentMark();
	}

	@Override
	public Attendance addAttendance(int studentId, int stfcId, long date) {
		setStartTime();

		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();

		// Add the Jackson message converter
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		restTemplate.getMessageConverters().add(
				new StringHttpMessageConverter());
		restTemplate.getMessageConverters().add(
				new ByteArrayHttpMessageConverter());
		restTemplate.getMessageConverters().add(
				new ResourceHttpMessageConverter());

		String url = "http://" + IP + EXTENSION
				+ "/studentMark/formAttendance/" + studentId + "," + stfcId
				+ "," + date + ".json";

		ResponseEntity<AttendanceSingleVM> responseEntity = null;
		AttendanceSingleVM response = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, AttendanceSingleVM.class);
			response = responseEntity.getBody();
		} catch (RestClientException e) {
			return null;
		}

		Log.d("TAAAG", response.toString());

		getElapsedTime("addAttendance - ");
		return response.getAttendance();
	}

	@Override
	public Attendance editAttendance(int attendanceId, boolean motivated) {
		setStartTime();
		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();

		// Add the Jackson message converter
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		restTemplate.getMessageConverters().add(
				new StringHttpMessageConverter());
		restTemplate.getMessageConverters().add(
				new ByteArrayHttpMessageConverter());
		restTemplate.getMessageConverters().add(
				new ResourceHttpMessageConverter());

		String url = "http://" + IP + EXTENSION
				+ "/studentMark/editAttendance/" + attendanceId + ".json";

		ResponseEntity<AttendanceSingleVM> responseEntity = null;
		AttendanceSingleVM response = null;

		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, AttendanceSingleVM.class);
			response = responseEntity.getBody();
		} catch (RestClientException e) {
			return null;
		}

		Log.d("TAAAG", response.toString());
		getElapsedTime("editAttendance - ");
		return response.getAttendance();
	}

	@Override
	public TeacherVM getTeacher() {
		setStartTime();

		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();
		String url = "http://" + IP + EXTENSION + "/teacher/getCurrent";
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		ResponseEntity<TeacherVM> responseEntity = null;
		TeacherVM teacher = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, TeacherVM.class);
			teacher = responseEntity.getBody();
		} catch (RestClientException e) {
			return null;
		}

		Log.d("TAAAG", teacher.toString());

		getElapsedTime("getTeacher - ");
		return teacher;
	}

	@Override
	public SubjectTeacherForClassVM getSubjectTeacherForClass(int classGroupId,
			int subjectId, int teacherId) {
		setStartTime();

		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://" + IP + EXTENSION
				+ "/subjectTeacherForClass/findByClassSubjectTeacher/"
				+ classGroupId + "," + subjectId + "," + teacherId + ".json";

		// Add the Jackson message converter
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		ResponseEntity<SubjectTeacherForClassVM> responseEntity = null;
		SubjectTeacherForClassVM stfc = null;

		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, SubjectTeacherForClassVM.class);
			stfc = responseEntity.getBody();
		} catch (RestClientException e) {
			return null;
		}

		Log.d("TAAAG", stfc.toString());

		getElapsedTime("getSubjectTeacherForClass - ");
		return stfc;
	}

	@Override
	public SubjectClassesVM getSubjectsForTeacherSubjects() {
		setStartTime();

		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://" + IP + EXTENSION
				+ "/teacher/classesForTeacherSubjects.json";

		// Add the Jackson message converter
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		ResponseEntity<SubjectClassesVM> responseEntity = null;
		SubjectClassesVM cfast = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, SubjectClassesVM.class);
			cfast = responseEntity.getBody();
		} catch (RestClientException e) {
			return null;
		}

		Log.d("TAAAG", cfast.toString());

		getElapsedTime("getSubjectsForTeacherSubjects - ");

		return cfast;
	}

	@Override
	public ArrayList<GradesAttendForSubject> getGradesAttendForSubjectList(
			int studentId) {
		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://" + IP + EXTENSION + "/teacher/gradesForStudentT/"
				+ studentId + ".json";

		// Add the Jackson message converter
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		ResponseEntity<GradesAttendForSubjectVM> responseEntity = null;
		GradesAttendForSubjectVM gradesAbsences = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, GradesAttendForSubjectVM.class);
			gradesAbsences = responseEntity.getBody();
		} catch (RestClientException e) {
			return null;
		}

		getElapsedTime("getGradesAttendForSubjectList - ");
		return gradesAbsences.getGradesAttendForSubjectList();
	}

	@Override
	public ArrayList<TimetableDays> getTeacherTimetable() {
		HttpEntity<?> requestEntity = getAuthHttpEntity();

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://" + IP + EXTENSION
				+ "/timetable/getTeacherTimetable.json";

		// Add the Jackson message converter
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		ResponseEntity<TimetableVM> responseEntity;
		TimetableVM timetable = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, TimetableVM.class);
			timetable = responseEntity.getBody();

		} catch (Exception e) {
			return null;
		}

		getElapsedTime("getTeacherTimetable - ");
		return timetable.getTimetableDaysList();
	}

	@Override
	public List<ChartData> getStatisticsDataForChart(String typeOfChart) {
		// HttpEntity<?> requestEntity = getAuthHttpEntity();

		// RestTemplate restTemplate = new RestTemplate();

		// rest to be continued

		if (typeOfChart.equals(Constants.CHARTS_NAMES[0])) {

		} else if (typeOfChart.equals(Constants.CHARTS_NAMES[1])) {

		} else if (typeOfChart.equals(Constants.CHARTS_NAMES[2])) {

		} else if (typeOfChart.equals(Constants.CHARTS_NAMES[3])) {

		} else if (typeOfChart.equals(Constants.CHARTS_NAMES[4])) {

		} else if (typeOfChart.equals(Constants.CHARTS_NAMES[5])) {

		}

		ChartData d1 = new ChartData();
		d1.setWhatItRepresents("Media Generala Sem 1");

		String[] x1 = { "A V-a A", "A V-a B", "A VI-a A", "A VI-a B",
				"A VII-a A", "A VII-a B", "A VIII-a A", "A VIII-a B" };

		LinkedList<Float> y1 = new LinkedList<Float>();
		y1.add(5.6f);
		y1.add(4.5f);
		y1.add(8.0f);
		y1.add(9.5f);
		y1.add(4.9f);
		y1.add(7.0f);
		y1.add(8.0f);
		y1.add(9.0f);

		d1.setxValues(x1);
		d1.setyValues(y1);

		List<ChartData> d = new LinkedList<ChartData>();
		d.add(d1);

		ChartData d2 = new ChartData();
		d2.setWhatItRepresents("Absente Sem 1");

		y1 = new LinkedList<Float>();
		y1.add(6.6f);
		y1.add(3.5f);
		y1.add(4.0f);
		y1.add(5.5f);
		y1.add(8.9f);
		y1.add(2.0f);
		y1.add(4.0f);
		y1.add(6.0f);

		d2.setxValues(x1);
		d2.setyValues(y1);
		d.add(d2);

		return d;
	}

	private HttpEntity<?> getAuthHttpEntity() {
		LoginCredentials loginCred = getLoginCredentials();
		if (loginCred == null)
			return null;

		HttpAuthentication authHeader = new HttpBasicAuthentication(
				loginCred.getUsername(), loginCred.getPassword());
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application", "json"));

		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAuthorization(authHeader);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		return requestEntity;
	}

	private void setLoginCredentials(String username, String password) {
		AppPreferences ap = AppPreferences.getInstance(null);
		if (ap != null) {
			ap.saveLoginCredentials(username, password);
		}
	}

	private LoginCredentials getLoginCredentials() {
		AppPreferences ap = AppPreferences.getInstance(null);
		if (ap != null) {
			LoginCredentials loginCred = ap.getLoginCredentials();

			if (!loginCred.getUsername().equals("")
					&& !loginCred.getPassword().equals(""))
				return loginCred;
			else
				return null;
		} else
			return null;
	}

	@Override
	public void setStartTime() {
		this.startTime = System.currentTimeMillis();
		System.setProperty("http.keepAlive", "false");
	}

	@Override
	public long getElapsedTime(String callingMethod) {
		long elapsedTime = System.currentTimeMillis() - this.startTime;
		Log.d(callingMethod, String.valueOf(elapsedTime));
		return elapsedTime;
	}

}
