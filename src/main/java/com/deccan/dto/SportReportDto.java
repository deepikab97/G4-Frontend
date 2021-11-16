package com.deccan.dto;

public class SportReportDto {
	private String sportName;
	private long planCount;
	private long batchCount;
	private long enrollmentCount;
	private boolean isActive;

	public SportReportDto() {
		super();
	}

	public SportReportDto(String sportName, long planCount, long batchCount, long enrollmentCount, boolean isActive) {
		super();
		this.sportName = sportName;
		this.planCount = planCount;
		this.batchCount = batchCount;
		this.enrollmentCount = enrollmentCount;
		this.isActive = isActive;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public long getPlanCount() {
		return planCount;
	}

	public void setPlanCount(long planCount) {
		this.planCount = planCount;
	}

	public long getBatchCount() {
		return batchCount;
	}

	public void setBatchCount(long batchCount) {
		this.batchCount = batchCount;
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
