package com.catalog.model.views;

import com.catalog.model.Attendance;

public class AttendanceSingleVM {

	private Attendance attendance;
	private String status;

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

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
