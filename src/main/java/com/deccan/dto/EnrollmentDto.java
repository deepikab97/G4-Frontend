package com.deccan.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.deccan.enums.StatusType;

public class EnrollmentDto {

	private int id;
	private StatusType status;
	private LocalDate startDate;
	private LocalDate appliedDate;
	private String planName;
	private int availability;
	private int duration;
	private LocalTime startTime;
	private LocalTime endTime;
	private String planSportName;
	private String userFirstName;
	private String userLastName;
	private int batchId;
	private int planId;
	private int userId;
	private double amountPaid;
	private int managerId;
	private boolean isCommented;

	public EnrollmentDto() {
		super();
	}

	public EnrollmentDto(StatusType status, LocalDate startDate, String planName, String planSportName, int planId,
			boolean isCommented) {
		super();
		this.status = status;
		this.startDate = startDate;
		this.planName = planName;
		this.planSportName = planSportName;
		this.planId = planId;
		this.isCommented = isCommented;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(LocalDate appliedDate) {
		this.appliedDate = appliedDate;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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

	public String getPlanSportName() {
		return planSportName;
	}

	public void setPlanSportName(String planSportName) {
		this.planSportName = planSportName;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isCommented() {
		return isCommented;
	}

	public void setCommented(boolean isCommented) {
		this.isCommented = isCommented;
	}

	@Override
	public String toString() {
		return "EnrollmentDto [id=" + id + ", status=" + status + ", startDate=" + startDate + ", appliedDate="
				+ appliedDate + ", planName=" + planName + ", availability=" + availability + ", duration=" + duration
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", planSportName=" + planSportName
				+ ", userFirstName=" + userFirstName + ", userLastName=" + userLastName + ", batchId=" + batchId
				+ ", planId=" + planId + ", userId=" + userId + ", amountPaid=" + amountPaid + ", managerId="
				+ managerId + "]";
	}

}
