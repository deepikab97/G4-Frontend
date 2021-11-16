package com.deccan.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.deccan.enums.GenderType;
import com.deccan.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email", nullable = false, unique = true, length = 200)
	private String email;

	@Column(name = "password", nullable = false, length = 50)
	private String password;

	@Column(name = "birth_date", nullable = false)
	private LocalDate birthDate;

	@Column(name = "gender", nullable = false)
	@Enumerated(EnumType.STRING)
	private GenderType gender;

	@ElementCollection
	@CollectionTable(name = "contact", joinColumns = @JoinColumn(name = "user_id"))
	private Set<String> contacts;

	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Address> address;

	@Column(name = "joining_date", nullable = false)
	private final LocalDate joiningDate = LocalDate.now();

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private RoleType role = RoleType.MEMBER;

	@Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
	private boolean isActive = true;

	@Column(name = "account_non_locked", nullable = false, columnDefinition = "boolean default true")
	private boolean accountNonLocked = true;

	@Column(name = "failed_attempts", nullable = false, columnDefinition = "int default 0")
	private int failedAttempts;

	@Column(name = "last_attempt_date", nullable = false)
	private LocalDateTime lastAttemptDate = LocalDateTime.now();

	@Column(name = "lock_date")
	private LocalDateTime lockDate;

	@Column(name = "profile_picture")
	private Byte[] profilePicture;

	@JsonIgnore
	@OneToMany(mappedBy = "manager", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Batch> batch;

	@JsonIgnore
	@OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Plan> plan;

	@OneToOne(mappedBy = "user", orphanRemoval = true)
	private HealthInfo healthInfo;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Reviews> reviews;

	@JsonIgnore
	@OneToMany(mappedBy = "user", orphanRemoval = true)
	private List<Enrollment> enrollment;

	@JsonIgnore
	@OneToMany(mappedBy = "manager", orphanRemoval = true)
	private List<Enrollment> enrollmentManager;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
	private List<OtpDetails> otp;

	public User() {
		super();

	}

	public User(int id, String firstName, String lastName, String email, String password, LocalDate string,
			GenderType string2, Set<String> i, RoleType j, boolean string3, boolean string4, int b, LocalDateTime k,
			LocalDateTime string5, Byte[] string6, HealthInfo c) {
		super();
		this.id = id;

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.birthDate = string;
		this.gender = string2;
		this.contacts = i;
		this.role = j;
		this.isActive = string3;
		this.accountNonLocked = string4;
		this.failedAttempts = b;
		this.lastAttemptDate = k;
		this.lockDate = string5;
		this.profilePicture = string6;
		this.healthInfo = c;
	}

	public User(int i, String string, String string2, String string3, String string4, String string5, LocalDate now,
			String string6, Set<String> contacts2, String string7, boolean b, boolean c, int j, LocalDateTime now2,
			LocalDateTime now3, Object object, Object object2) {

	}

	// getter and setters

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	public LocalDateTime getLastAttemptDate() {
		return lastAttemptDate;
	}

	public void setLastAttemptDate(LocalDateTime lastAttemptDate) {
		this.lastAttemptDate = lastAttemptDate;
	}

	public LocalDateTime getLockDate() {
		return lockDate;
	}

	public void setLockDate(LocalDateTime lockDate) {
		this.lockDate = lockDate;
	}

	public Byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(Byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public List<Batch> getBatch() {
		return batch;
	}

	public void setBatch(List<Batch> batch) {
		this.batch = batch;
	}

	public List<Plan> getPlan() {
		return plan;
	}

	public void setPlan(List<Plan> plan) {
		this.plan = plan;
	}

	public HealthInfo getHealthInfo() {
		return healthInfo;
	}

	public void setHealthInfo(HealthInfo healthInfo) {
		this.healthInfo = healthInfo;
	}

	public List<Reviews> getReviews() {
		return reviews;
	}

	public void setReviews(List<Reviews> reviews) {
		this.reviews = reviews;
	}

	public List<Enrollment> getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(List<Enrollment> enrollment) {
		this.enrollment = enrollment;
	}

	@JsonIgnore
	public List<Enrollment> getEnrollmentManager() {
		return enrollmentManager;
	}

	public void setEnrollmentManager(List<Enrollment> enrollmentManager) {
		this.enrollmentManager = enrollmentManager;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", birthDate=" + birthDate + ", gender=" + gender + ", contacts="
				+ contacts + ", address=" + address + ", joiningDate=" + joiningDate + ", role=" + role + ", isActive="
				+ isActive + ", accountNonLocked=" + accountNonLocked + ", failedAttempts=" + failedAttempts
				+ ", lastAttemptDate=" + lastAttemptDate + ", lockDate=" + lockDate + ", profilePicture="
				+ Arrays.toString(profilePicture) + ", batch=" + batch + ", plan=" + plan + ", healthInfo=" + healthInfo
				+ ", reviews=" + reviews + ", enrollment=" + enrollment + ", enrollmentManager=" + enrollmentManager
				+ ", otp=" + otp + "]";
	}

}
