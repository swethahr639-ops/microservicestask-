package com.atlas.authservice2.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.atlas.authservice2.service.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {

	private final AuthenticationManager authenticationManager;

	public AuthenticationServiceImpl(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication authenticatWithCredentials(String userName, String Password) {

		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, Password));
	}

}
