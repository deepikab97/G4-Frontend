package com.deccan.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deccan.dto.JwtResponse;
import com.deccan.dto.LoginUser;
import com.deccan.dto.UserDto;
import com.deccan.security.TokenProvider;
import com.deccan.services.IUserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	private IUserService userService;

	@PostMapping("/authenticate")
	public ResponseEntity<JwtResponse> login(@RequestBody LoginUser loginUser) throws AuthenticationException {

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()

				));

		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtTokenUtil.generateToken(authentication);

		userService.resetFailedAttempts((UserDto) authentication.getPrincipal());
		UserDto principal = (UserDto) authentication.getPrincipal();
		Collection<? extends GrantedAuthority> authority = principal.getAuthorities();
		List<String> roles = authority.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setFirstName(principal.getFirstName());
		jwtResponse.setUserId(principal.getUserId());
		jwtResponse.setUsername(principal.getUsername());
		jwtResponse.setRoles(roles);
		jwtResponse.setToken(token);
		return ResponseEntity.ok(jwtResponse);
	}

}
