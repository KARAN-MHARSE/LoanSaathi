package com.aurionpro.loanapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.loanapp.dto.auth.ForgotPasswordRequestDto;
import com.aurionpro.loanapp.dto.auth.LoginRequestDto;
import com.aurionpro.loanapp.dto.auth.LoginResponseDto;
import com.aurionpro.loanapp.dto.auth.RegisterRequestDto;
import com.aurionpro.loanapp.dto.auth.RegisterResponseDto;
import com.aurionpro.loanapp.dto.auth.ResetPasswordRequestDto;
import com.aurionpro.loanapp.service.IAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
	private final IAuthService authService;

	@PostMapping("/login")
	@CrossOrigin(origins = "*")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
		LoginResponseDto response = authService.login(loginRequestDto);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register")
	public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
		RegisterResponseDto response = authService.register(registerRequestDto);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDto requestDto) {
		authService.resetPassword(requestDto);
		return ResponseEntity.ok("Password  successfully changed");
	}

	@PostMapping("/forgot-password/send-otp")
	public ResponseEntity<String> sendForgotPasswordOtp(@RequestBody String email) {
		authService.sendForgetPasswordOtp(email);
		return ResponseEntity.ok("OTP sent to your email.");
	}
	
	@PostMapping("/forgot-password/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ForgotPasswordRequestDto request) {
        boolean success = authService.forgotPassword(request);
        if(success) {
            return ResponseEntity.ok("Password reset successfully.");
        } else {
            return ResponseEntity.badRequest().body("OTP verification failed.");
        }
    }

}
