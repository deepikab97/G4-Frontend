package com.deccan.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.deccan.enums.StatusType;

public class EnrollmentReportDto {
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private int id;
	private String planSportName;
	private String planName;
	private LocalDate appliedDate;
	private double amountPaid;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDate startDate;
	private LocalDate endDate;
	private StatusType status;
	private String userName;
	private int planId;
	private int batchId;
	private int userId;
	private int managerId;

	public EnrollmentReportDto(String planSportName, String planName, LocalDate startDate, LocalDate endDate,
			StatusType status) {
		super();
		this.planSportName = planSportName;
		this.planName = planName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	public EnrollmentReportDto(String planSportName, String planName, LocalDate startDate, LocalDate endDate,
			StatusType status, String userName) {
		super();
		this.planSportName = planSportName;
		this.planName = planName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.userName = userName;
	}

	public EnrollmentReportDto(String planSportName, String planName, StatusType status, LocalDate startDate,
			LocalDate endDate) {
		super();
		this.planSportName = planSportName;
		this.planName = planName;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public LocalDate getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(LocalDate appliedDate) {
		this.appliedDate = appliedDate;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlanSportName() {
		return planSportName;
	}

	public void setPlanSportName(String planSportName) {
		this.planSportName = planSportName;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
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

}
