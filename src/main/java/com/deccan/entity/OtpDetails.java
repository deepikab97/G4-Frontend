package com.deccan.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OtpDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;

	@Column(length = 6, unique = true, nullable = false)
	private String otp;

	@Column(nullable = false)
	private LocalDateTime generatedOn;
	private LocalDateTime validTill;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	public OtpDetails() {

	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getGeneratedOn() {
		return generatedOn;
	}

	public void setGeneratedOn(LocalDateTime generatedOn) {
		this.generatedOn = generatedOn;
	}

	public LocalDateTime getValidTill() {
		return validTill;
	}

	public void setValidTill(LocalDateTime validTill) {
		this.validTill = validTill;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
