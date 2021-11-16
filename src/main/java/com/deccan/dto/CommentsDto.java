package com.deccan.dto;

import java.time.LocalDateTime;

public class CommentsDto {

	private String firstName;
	private String lastName;
	private boolean isCommented;
	private String comment;
	private LocalDateTime commentedOn;

	public CommentsDto() {
	}

	public CommentsDto(String firstName, String lastName, boolean isCommented, String comment,
			LocalDateTime commentedOn) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.isCommented = isCommented;
		this.comment = comment;
		this.commentedOn = commentedOn;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isCommented() {
		return isCommented;
	}

	public void setCommented(boolean isCommented) {
		this.isCommented = isCommented;
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

}
