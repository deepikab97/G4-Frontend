package com.deccan.dto;

public class SportDto {

	private String sportName;
	private String plan;
	private String batch;
	private int planId;

	public SportDto() {
		super();
		
	}

	public SportDto(String sportName) {
		super();
		this.sportName = sportName;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

}