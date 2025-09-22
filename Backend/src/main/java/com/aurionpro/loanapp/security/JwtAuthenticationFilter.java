package com.aurionpro.loanapp.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("I am here");
		String authorizationHeader = request.getHeader("Authorization");
		String token = getTokenFromAuthorizationHeader(authorizationHeader);
		System.out.println(authorizationHeader);

		if(token != null && !token.isEmpty() 
				&& jwtService.validateToken(token)
				&& SecurityContextHolder.getContext().getAuthentication() == null) {
			
			String userName = jwtService.getUserNameFromToken(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(authToken);
			
		}
		
		filterChain.doFilter(request, response);

		
	}
	
	private String getTokenFromAuthorizationHeader(String authorizationHeader) {
		if(authorizationHeader== null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer")) return null;
		
		return authorizationHeader.split("Bearer ")[1];
	}
	
	

}
