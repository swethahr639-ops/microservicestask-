package com.atlas.authservice2.util;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtTokenUtil;

	public JwtRequestFilter(JwtTokenUtil jwtTokenUtil) {
		super();
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		try {
			if (authorizationHeader.startsWith("Bearer ")) {
				jwtToken = authorizationHeader.substring(7);
			}

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("unauthorized");
		}
	}

}
