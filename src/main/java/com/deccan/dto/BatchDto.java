package com.deccan.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class BatchDto {

	private int id;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDate createdOn;
	private int size;
	private int availability;
	private int sportId;
	private int managerId;
	private String uniqueField;
	private String sportName;
	private boolean isActive = true;

	public BatchDto(int id, LocalTime startTime, LocalTime endTime, int size, int sportId, int managerId) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.size = size;
		this.sportId = sportId;
		this.managerId = managerId;
	}

	public BatchDto(int id, LocalTime startTime, LocalTime endTime, int size, int sportId, int managerId,
			boolean isActive) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.size = size;
		this.sportId = sportId;
		this.managerId = managerId;
		this.isActive = isActive;
	}

	public BatchDto() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
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

	public int getSportId() {
		return sportId;
	}

	public void setSportId(int sportId) {
		this.sportId = sportId;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getUniqueField() {
		return uniqueField;
	}

	public void setUniqueField(String uniqueField) {
		this.uniqueField = uniqueField;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "BatchDto [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", createdOn=" + createdOn
				+ ", size=" + size + ", availability=" + availability + ", sportId=" + sportId + ", managerId="
				+ managerId + ", uniqueField=" + uniqueField + ", sportName=" + sportName + ", isActive=" + isActive
				+ "]";
	}

}
