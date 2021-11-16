package com.deccan.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

public class JwtResponse {

	private int userId;
	private String username;
	private String firstName;
	private List<String> roles = new ArrayList<>();
	private String token;

	public JwtResponse() {
		super();
	}

	public JwtResponse(int userId, String username, List<String> roles, String token, String firstName) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.username = username;
		this.roles = roles;
		this.token = token;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "JwtResponse [userId=" + userId + ", username=" + username + ", roles=" + roles + ", token=" + token
				+ "]";
	}

}
