package com.deccan.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class HealthInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false)
	private double height;

	@Column(nullable = false)
	private double weight;

	private double bmi;

	@Column(name = "blood_group")
	private String bloodGroup;

	@JsonBackReference
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;

	public HealthInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

//	public HealthInfo(int id, double height, double weight, double bmi, String bloodGroup) {
//		super();
//		this.id = id;
//		this.height = height;
//		this.weight = weight;
//		this.bmi = bmi;
//		this.bloodGroup = bloodGroup;
//	}

	// getter and setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getBmi() {
		return bmi;
	}

	public void setBmi(double bmi) {
		this.bmi = bmi;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public HealthInfo(int id, double height, double weight, double bmi, String bloodGroup) {
		super();
		this.id = id;
		this.height = height;
		this.weight = weight;
		this.bmi = bmi;
		this.bloodGroup = bloodGroup;
	}

//	public void setUser(Optional<User> user2) {
//		this.user = user;
//		
//	}

}
