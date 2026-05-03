package com.atlas.authservice2.service;

import com.atlas.authservice2.service.dto.LoginRequestDto;
import com.atlas.authservice2.service.dto.LoginResponseDto;

public interface UserService {
	
	public LoginResponseDto authenticate(LoginResponseDto loginRequestDto);

	LoginResponseDto authenticate(LoginRequestDto loginRequestDto); 
		
	

}
