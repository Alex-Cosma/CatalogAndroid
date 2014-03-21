package com.catalog.model.views;

import java.util.ArrayList;

import com.catalog.model.Attendance;

public class AttendanceVM extends GenericVM {
	private ArrayList<Attendance> attendanceList;

	public ArrayList<Attendance> getAttendanceList() {
		return attendanceList;
	}

	public void setAttendanceList(ArrayList<Attendance> attendanceList) {
		this.attendanceList = attendanceList;
	}

}
