package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.Attendance;

public class AttendanceVM {
	private ArrayList<Attendance> attendanceList;

	private String status;

	public ArrayList<Attendance> getAttendanceList() {
		return attendanceList;
	}

	public void setAttendanceList(ArrayList<Attendance> attendanceList) {
		this.attendanceList = attendanceList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
