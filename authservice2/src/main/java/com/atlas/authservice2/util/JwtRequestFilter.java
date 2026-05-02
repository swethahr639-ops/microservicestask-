package com.atlas.authservice2.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.atlas.authservice2.service.impl.MyUserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private final MyUserDetailsServiceImpl myUserDetailsServiceImpl;

	private final JwtTokenUtil jwtTokenUtil;

	public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, MyUserDetailsServiceImpl myUserDetailsServiceImpl) {
		super();
		this.jwtTokenUtil = jwtTokenUtil;
		this.myUserDetailsServiceImpl = myUserDetailsServiceImpl;
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
				username = jwtTokenUtil.extractUsernameFromToken(jwtToken);

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
						&& jwtTokenUtil.validateToken(jwtToken, username)) {

					List<GrantedAuthority> authList = new ArrayList<>();
					authList.add(new SimpleGrantedAuthority(jwtTokenUtil.getRoleFromToken(jwtToken)));
					UsernamePasswordAuthenticationToken usernamePassordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							username, null, authList);
					usernamePassordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePassordAuthenticationToken);
				}
				filterChain.doFilter(request, response);

			}

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("unauthorized");
		}
	}

}
