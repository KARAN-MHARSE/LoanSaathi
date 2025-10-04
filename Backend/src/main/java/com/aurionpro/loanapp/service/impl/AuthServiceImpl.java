package com.aurionpro.loanapp.service.impl;

import java.time.LocalDate;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.EmailDto;
import com.aurionpro.loanapp.dto.auth.ForgotPasswordRequestDto;
import com.aurionpro.loanapp.dto.auth.LoginRequestDto;
import com.aurionpro.loanapp.dto.auth.LoginResponseDto;
import com.aurionpro.loanapp.dto.auth.RegisterRequestDto;
import com.aurionpro.loanapp.dto.auth.RegisterResponseDto;
import com.aurionpro.loanapp.dto.auth.ResetPasswordRequestDto;
import com.aurionpro.loanapp.entity.Role;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.exception.AccessDeniedException;
import com.aurionpro.loanapp.exception.ResourceNotFoundException;
import com.aurionpro.loanapp.repository.RoleRepository;
import com.aurionpro.loanapp.repository.UserRepository;
import com.aurionpro.loanapp.security.JwtService;
import com.aurionpro.loanapp.service.EmailService;
import com.aurionpro.loanapp.service.IAuthService;
import com.aurionpro.loanapp.service.IOtpService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper mapper;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final IOtpService otpService;
	private final EmailService emailService;

	@Override
	public RegisterResponseDto register(RegisterRequestDto registerDto) {
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new RuntimeException("User already exist");
		}

		User user = mapper.map(registerDto, User.class);
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Role role = roleRepository.findByRoleName(registerDto.getRoleName())
				.orElseThrow(() -> new RuntimeException("Role is not exist"));

		role.getUsers().add(user);
		user.setRole(role);

		User savedUser = userRepository.save(user);
		return mapper.map(savedUser, RegisterResponseDto.class);

	}

	@Override
	public LoginResponseDto login(LoginRequestDto requestDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));
		System.err.println(authentication);
		String token = jwtService.generateToken(authentication);

		LoginResponseDto response = new LoginResponseDto();
		response.setUsername(authentication.getName());
		response.setToken(token);

		return response;

	}

	@Override
	public boolean forgotPassword(ForgotPasswordRequestDto requestDto) {
		User user = userRepository.findByEmail(requestDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("Email id not found"));

		boolean isValid = otpService.validateOtp(requestDto.getEmail(), requestDto.getOtp());
		if (!isValid)
			throw new IllegalArgumentException("Wrong otp password");

		user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
		userRepository.save(user);

		return true;

	}

	@Override
	public void sendForgetPasswordOtp(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Email id not found"));

		String otp = otpService.generateOtp(email, 120L);
		String mailBody = "Otp to reset the password is " + otp;

		EmailDto emailDto = new EmailDto();
		emailDto.setTo(email);
		emailDto.setSubject("Otp to reset password");
		emailDto.setBody(mailBody);

		emailService.sendEmailWithoutImage(emailDto);
	}

	@Override
	public void resetPassword(ResetPasswordRequestDto requestDto) {
		User user = userRepository.findByEmail(requestDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("Email id not found"));

		if (!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {
			throw new AccessDeniedException("Old password is wrong");
		}

		user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));

		userRepository.save(user);

	}

	@Override
	public void logout(String email) {
		// TODO Auto-generated method stub

	}

}
