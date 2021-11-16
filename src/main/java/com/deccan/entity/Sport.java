package com.deccan.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Sport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "sport_name", nullable = false)
	private String sportName;

	@Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
	private boolean isActive = true;

	@JsonIgnore
	@OneToMany(mappedBy = "sport", orphanRemoval = true)
	private Set<Plan> plan;

	@JsonIgnore
	@OneToMany(mappedBy = "sport", orphanRemoval = true)
	private List<Batch> batch;

	public Sport() {
		super();

	}

	//getter and setters
	
	public Sport(int id,String sportName, boolean isActive) {
		super();
		this.id=id;
		this.sportName = sportName;
		this.isActive = isActive;
	}


	public int getId() {
		return id;
	}

	public Sport(String sportName) {
		super();
		this.sportName = sportName;
	}

	public void setId(int id) {
		this.id = id;
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

	public Set<Plan> getPlan() {
		return plan;
	}

	public void setPlan(Set<Plan> plan) {
		this.plan = plan;
	}

	public List<Batch> getBatch() {
		return batch;
	}

	public void setBatch(List<Batch> batch) {
		this.batch = batch;
	}

}
