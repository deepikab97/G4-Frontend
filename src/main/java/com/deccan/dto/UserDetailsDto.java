package com.deccan.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.deccan.entity.Address;
import com.deccan.enums.GenderType;

public class UserDetailsDto {

	private String firstName;
	private String lastName;
	private String password;
	private LocalDate birthDate;
	private GenderType gender;
	private Set<String> contacts;
	private List<Address> address;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public Set<String> getContacts() {
		return contacts;
	}

	public void setContacts(Set<String> contacts) {
		this.contacts = contacts;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

}
