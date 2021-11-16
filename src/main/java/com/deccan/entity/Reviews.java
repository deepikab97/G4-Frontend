package com.deccan.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Reviews {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "is_liked", columnDefinition = "boolean default false")
	private boolean isLiked;

	private String comment;

	@Column(name = "is_commented", columnDefinition = "boolean default false")
	private boolean isCommented;

	@Column(name = "commented_on")
	private LocalDateTime commentedOn;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "plan_id", referencedColumnName = "id", nullable = false)
	private Plan plan;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;

	public Reviews() {
		super();

	}

	// getter and setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCommentedOn() {
		return commentedOn;
	}

	public void setCommentedOn(LocalDateTime commentedOn) {
		this.commentedOn = commentedOn;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isCommented() {
		return isCommented;
	}

	public void setCommented(boolean isCommented) {
		this.isCommented = isCommented;
	}

}
