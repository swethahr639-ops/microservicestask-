package com.atlas.authservice2.util;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.jpa.repository.query.EqlParser.Extract_datetime_fieldContext;
import org.springframework.stereotype.Component;

import com.atlas.authservice2.dao.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {

	@Value("${app.Jwt.secret}")
	private String SECRET_KEY;

	private SecretKey getSecret() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	}

	private static final long EXPAIRE_DURATION = 30l * 24 * 60 * 60 * 1000;

	public String generateAccessToken(User user) {
		return Jwts.builder().setSubject(String.format("%s,%s", user.getUsername(), user.getEmailid()))
				.setIssuer("shwetha").claim("name", user.getUsername()).claim("role", user.getRole())
				.claim("userId", user.getUserId()).claim("UserFirst", user.getFirstName()).setIssuedAt(new Date())
				.signWith(getSecret()).compact();

	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(getSecret()).parseClaimsJws(token).getBody();
	}

	public String extractUsernameFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return (String) claims.get("name");
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public boolean validateToken(String token, String usernameToken) {
		try {
			String username = extractUsernameFromToken(token);
			boolean isValidateUsername = username.equals(usernameToken);
			boolean isTokenExpired = isTokenExpired(token);
			return isValidateUsername && isTokenExpired;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public String getRoleFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return (String) claims.get("role");
	}
}