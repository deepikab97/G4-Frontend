package com.deccan.dto;

public class ManagerPlanReportDto {
	private String planName;
	private String sportName;
	private long enrollmentCount;

	private boolean isActive;

	@Override
	public String toString() {
		return "ManagerPlanReportDto [planName=" + planName + ", sportName=" + sportName + ", enrollmentCount="
				+ enrollmentCount + ", isActive=" + isActive + "]";
	}

	public ManagerPlanReportDto() {
		super();
	}

	public ManagerPlanReportDto(String planName, String sportName, long enrollmentCount, boolean isActive) {
		super();
		this.planName = planName;
		this.sportName = sportName;
		this.enrollmentCount = enrollmentCount;
		this.isActive = isActive;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public long getEnrollmentCount() {
		return enrollmentCount;
	}

	public void setEnrollmentCount(long enrollmentCount) {
		this.enrollmentCount = enrollmentCount;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}