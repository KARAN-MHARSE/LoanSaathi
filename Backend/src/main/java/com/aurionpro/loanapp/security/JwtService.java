package com.aurionpro.loanapp.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${jwt.secret.key}")
	private String secretKey;

	@Value("${jwt.expiration.date}")
	private long expirtyDate;

	public String generateToken(Authentication authentication) {
		return Jwts.builder()
				.subject(authentication.getName())
				.claim("roles", authentication.getAuthorities())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expirtyDate))
				.signWith(generateKey(), SignatureAlgorithm.HS256).compact();
	}
	
	public boolean validateToken(String token) {
		Jwts.parser()
		.setSigningKey(generateKey())
		.build()
		.parse(token);
		return true;	
	}
	
	public String getUserNameFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(generateKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	public SecretKey generateKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	public String generateTokenWithUserDetails(User user) {
	    return Jwts.builder()
	        .setSubject(user.getEmail())
	        .claim("roles", user.getRole().getRoleName().name())  // assuming single role; adapt if multiple
	        .setIssuedAt(new Date(System.currentTimeMillis()))
	        .setExpiration(new Date(System.currentTimeMillis() + expirtyDate))
	        .signWith(generateKey(), SignatureAlgorithm.HS256)
	        .compact();
	}
}
