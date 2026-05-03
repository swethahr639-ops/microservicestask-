package com.atlas.authservice2.service.impl;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.atlas.authservice2.config.SecurityConfig;
import com.atlas.authservice2.dao.UserRepository;
import com.atlas.authservice2.dao.model.*;
import com.atlas.authservice2.dao.model.User;
import com.atlas.authservice2.service.MyUserDetailsService;

import jakarta.annotation.PostConstruct;

@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

	//private final SecurityConfig securityConfig;

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	
	public MyUserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByUserName(username);
		if (optionalUser.isEmpty()) {
			throw new UsernameNotFoundException("username is not found for given username" + username);
		}
		return optionalUser.get();
	}

	private void createDefaultAdmin() {
		String defaultUsername = "shwetha";
		String emailId = "shwethahr@gmail.com";

		boolean isUserExist = userRepository.existsByEmailid(emailId)
				|| userRepository.existsByUserName(defaultUsername);

		if (!isUserExist) {
			User user = new User();
			user.setEmailId(emailId);
			user.setUsername(defaultUsername);

			String encrptedPassword = passwordEncoder.encode("shwe@123456");
			user.setPassword(encrptedPassword);
			user.setFirstName("shwetha");
			user.setLastName("hr");
			user.setContactNumber("8123456789");
			Timestamp createdAt = new Timestamp(System.currentTimeMillis());
			user.setCreatedAt(createdAt);
			user.setStatus("active");

			userRepository.save(user);
		}

	}

	@PostConstruct
	protected void initialize() {
		createDefaultAdmin();
	}

}