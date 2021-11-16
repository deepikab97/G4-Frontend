package com.deccan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.deccan.services.IUserService;

@Component
public class OnAuthenticationFailure {
	
	@Autowired
	IUserService userService;
	
	@EventListener
	public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
		String email =(String)event.getAuthentication().getPrincipal();
		
		userService.incrementFailedAttempts(email);
		
	}
}
