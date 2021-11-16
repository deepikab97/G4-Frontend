package com.deccan.dto;

import java.time.LocalTime;

public class BatchReportDto {

	private LocalTime startTime;
	private LocalTime endTime;
	private String sport;
	private int size;
	private int availability;
	private long enrollment;

	public BatchReportDto() {
		super();
	}

	public BatchReportDto(LocalTime startTime, LocalTime endTime, String sport, int size, int availability,
			long enrollment) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.sport = sport;
		this.size = size;
		this.availability = availability;
		this.enrollment = enrollment;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public long getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(long enrollment) {
		this.enrollment = enrollment;
	}
}
