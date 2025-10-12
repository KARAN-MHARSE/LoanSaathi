package com.aurionpro.loanapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.loanapp.dto.OtpDto;
import com.aurionpro.loanapp.dto.auth.ForgotPasswordRequestDto;
import com.aurionpro.loanapp.dto.auth.LoginRequestDto;
import com.aurionpro.loanapp.dto.auth.LoginResponseDto;
import com.aurionpro.loanapp.dto.auth.RegisterRequestDto;
import com.aurionpro.loanapp.dto.auth.RegisterResponseDto;
import com.aurionpro.loanapp.dto.auth.ResetPasswordRequestDto;
import com.aurionpro.loanapp.service.IAuthService;
import com.aurionpro.loanapp.service.impl.OtpService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
	private final IAuthService authService;
	private final OtpService otpService;

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
	
	@GetMapping("/send-otp")
	public ResponseEntity<?> sendEmailValidationOtp(@RequestParam String email){
		System.out.println(email);
		authService.sendEmailValidateOtp(email);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/validate-otp")
	public ResponseEntity<Boolean> validateOtp(@RequestBody OtpDto requestDto) {
		System.out.println(requestDto.getCode());
		boolean isValid = otpService.validateOtp(requestDto.getEmail(), requestDto.getCode());
		return ResponseEntity.ok(isValid);
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
	
	@PostMapping("/google-login")
	public ResponseEntity<LoginResponseDto> googleLogin(@RequestBody String idToken) {
	    LoginResponseDto response = authService.loginWithGoogle(idToken);
	    return ResponseEntity.ok(response);
	}

}
