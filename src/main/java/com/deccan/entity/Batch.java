package com.deccan.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Batch {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "start_time", nullable = false)
	private LocalTime startTime;

	@Column(name = "end_time", nullable = false)
	private LocalTime endTime;

	@Column(name = "created_on", nullable = false)
	private final LocalDate createdOn = LocalDate.now();

	@Column(nullable = false)
	private int size;

	@Column(nullable = false)
	private int availability;

	@Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
	private boolean isActive = true;

	@Column(nullable = false)
	private String uniqueField;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "sport_id", referencedColumnName = "id", nullable = false)
	private Sport sport;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "manager_id", referencedColumnName = "id", nullable = false)
	private User manager;

	@JsonIgnore
	@OneToMany(mappedBy = "batch", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Enrollment> enrollment;

	public Batch() {
		super();
		
	}

	public Batch(int id, LocalTime startTime, LocalTime endTime, int size) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.size = size;

	}

	public Batch(int id, LocalTime startTime, LocalTime endTime, int size, Sport sport, User manager) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.size = size;
		this.sport = sport;
		this.manager = manager;
	}

	// getter and setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getUniqueField() {
		return uniqueField;
	}

	public void setUniqueField(String uniqueField) {
		this.uniqueField = uniqueField;
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

	public List<Enrollment> getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(List<Enrollment> enrollment) {
		this.enrollment = enrollment;
	}

}
