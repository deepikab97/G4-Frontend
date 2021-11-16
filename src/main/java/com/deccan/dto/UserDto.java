package com.deccan.dto;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.deccan.entity.User;
import com.deccan.enums.RoleType;

public class UserDto implements UserDetails {

	
	private static final long serialVersionUID = 1L;
	private int userId;
	private String firstName;
	private String email;
	private String password;
	private RoleType roles;
	private boolean accountNonLocked;
	private boolean isActive;

	public UserDto(User user) {

		this.userId = user.getId();
		this.firstName = user.getFirstName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = user.getRole();
		this.accountNonLocked = user.isAccountNonLocked();
		this.isActive = user.isActive();
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roles.toString());
		return Arrays.asList(authority);
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.isActive;
	}

}
