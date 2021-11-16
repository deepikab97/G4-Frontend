package com.deccan.dto;

public class PlanReportDto {
	private String PlanName;
	private String SportName;
	private long enrollmentCount;

	public PlanReportDto() {
		super();
	}

	public PlanReportDto(String planName, String sportName, long enrollmentCount) {
		super();
		PlanName = planName;
		SportName = sportName;
		this.enrollmentCount = enrollmentCount;
	}

	public String getPlanName() {
		return PlanName;
	}

	public void setPlanName(String planName) {
		PlanName = planName;
	}

	public String getSportName() {
		return SportName;
	}

	public void setSportName(String sportName) {
		SportName = sportName;
	}

	public long getEnrollmentCount() {
		return enrollmentCount;
	}

	public void setEnrollmentCount(long enrollmentCount) {
		this.enrollmentCount = enrollmentCount;
	}
}
