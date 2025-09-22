package com.aurionpro.loanapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.loanapp.dto.LoginRequestDto;
import com.aurionpro.loanapp.dto.LoginResponseDto;
import com.aurionpro.loanapp.dto.RegisterRequestDto;
import com.aurionpro.loanapp.dto.RegisterResponseDto;
import com.aurionpro.loanapp.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
		LoginResponseDto response = authService.login(loginRequestDto);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
		RegisterResponseDto response = authService.register(registerRequestDto);
		return ResponseEntity.ok(response);
	} 

}
