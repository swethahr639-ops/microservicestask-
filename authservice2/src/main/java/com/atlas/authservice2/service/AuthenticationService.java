package com.atlas.authservice2.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {

	Authentication authenticatWithCredentials(String userName, String Password);
}
