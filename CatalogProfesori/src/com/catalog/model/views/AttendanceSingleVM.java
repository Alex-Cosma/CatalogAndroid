package com.catalog.model.views;

import com.catalog.model.Attendance;

public class AttendanceSingleVM extends GenericVM{

	private Attendance attendance;

	/**
	 * @return the attendance
	 */
	public Attendance getAttendance() {
		return attendance;
	}

	/**
	 * @param attendance
	 *            the attendance to set
	 */
	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}

}
