package com.atlas.authservice2.service.impl;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.atlas.authservice2.dao.model.User;
import com.atlas.authservice2.exception.InvalidUserException;
import com.atlas.authservice2.service.UserService;
import com.atlas.authservice2.service.dto.LoginRequestDto;
import com.atlas.authservice2.service.dto.LoginResponseDto;
import com.atlas.authservice2.util.JwtTokenUtil;

@Service
public class UserServiceImpl implements UserService {

	private final OpenAPI apiInfo;

	private final JwtTokenUtil jwtTokenUtil;
	private final AuthenticationServiceImpl authenticationService;

	public UserServiceImpl(JwtTokenUtil jwtTokenUtil, AuthenticationServiceImpl authenticationService,
			OpenAPI apiInfo) {
		super();
		this.jwtTokenUtil = jwtTokenUtil;
		this.authenticationService = authenticationService;
		this.apiInfo = apiInfo;
	}

	@Override
	public LoginResponseDto authenticate(LoginRequestDto loginRequestDto) {

		try {
			Authentication authentication = authenticationService
					.authenticatWithCredentials(loginRequestDto.getUserName(), loginRequestDto.getPassword());
			User user = (User) authentication.getPrincipal();
			LoginResponseDto loginResponseDto = new LoginResponseDto();
			loginResponseDto.setUserId(user.getUserId());
			loginResponseDto.setToken(jwtTokenUtil.generateAccessToken(user));
			loginResponseDto.setFirstName(user.getFirstName());
			loginResponseDto.setLastName(user.getLastName());
			loginResponseDto.setContactNember(user.getEmailid());
			loginResponseDto.setUserName(user.getUsername());

		} catch (BadCredentialsException ex) {
			throw new InvalidUserException("Inalid user name or password");

		}
		return null;

	}

	@Override
	public LoginResponseDto authenticate(LoginResponseDto loginRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
