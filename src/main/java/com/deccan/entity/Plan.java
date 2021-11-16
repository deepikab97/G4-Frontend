package com.deccan.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "plan_name", nullable = false)
	private String planName;

	@Column(name = "created_on", nullable = false)
	private final LocalDate createdOn = LocalDate.now();

	@Column(nullable = false)
	private int duration;

	@Column(nullable = false)
	private double amount;

	@Column(name = "likes_counter")
	private int likesCounter;

	@Column(name = "comments_counter")
	private int commentsCounter;

	@Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
	private boolean isActive = true;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "sport_id", referencedColumnName = "id", nullable = false)
	private Sport sport;

	@JsonBackReference
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "manager_id", referencedColumnName = "id", nullable = false)
	private User manager;

	@JsonIgnore
	@OneToMany(mappedBy = "plan", orphanRemoval = true)
	private List<Reviews> review;

	@JsonIgnore
	@OneToMany(mappedBy = "plan", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Enrollment> enrollment;

	public Plan() {
		super();

	}

	// getter and setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getLikesCounter() {
		return likesCounter;
	}

	public void setLikesCounter(int likesCounter) {
		this.likesCounter = likesCounter;
	}

	public int getCommentsCounter() {
		return commentsCounter;
	}

	public void setCommentsCounter(int commentsCounter) {
		this.commentsCounter = commentsCounter;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public List<Reviews> getReview() {
		return review;
	}

	public void setReview(List<Reviews> review) {
		this.review = review;
	}

	public List<Enrollment> getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(List<Enrollment> enrollment) {
		this.enrollment = enrollment;
	}

	@Override
	public String toString() {
		return "Plan [id=" + id + ", planName=" + planName + ", createdOn=" + createdOn + ", duration=" + duration
				+ ", amount=" + amount + ", likesCounter=" + likesCounter + ", commentsCounter=" + commentsCounter
				+ ", isActive=" + isActive + ", sport=" + sport + ", manager=" + manager + ", review=" + review
				+ ", enrollment=" + enrollment + "]";
	}

}
