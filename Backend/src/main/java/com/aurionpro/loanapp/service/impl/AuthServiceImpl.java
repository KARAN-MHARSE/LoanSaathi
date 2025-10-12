package com.aurionpro.loanapp.service.impl;

import java.util.Collections;

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
import com.aurionpro.loanapp.property.AuthProvider;
import com.aurionpro.loanapp.property.RoleType;
import com.aurionpro.loanapp.repository.RoleRepository;
import com.aurionpro.loanapp.repository.UserRepository;
import com.aurionpro.loanapp.security.JwtService;
import com.aurionpro.loanapp.service.EmailService;
import com.aurionpro.loanapp.service.IAuthService;
import com.aurionpro.loanapp.service.IOtpService;
import com.aurionpro.loanapp.util.PasswordGenerator;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

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
	public void sendEmailValidateOtp(String email) {
		String otp = otpService.generateOtp(email, 120L);
		String mailBody = "Otp to validate the email is " + otp;

		EmailDto emailDto = new EmailDto();
		emailDto.setTo(email);
		emailDto.setSubject("Otp to Validate email");
		emailDto.setBody(mailBody);

		emailService.sendEmailWithoutImage(emailDto);
		
	}
	
	@Override
	public RegisterResponseDto register(RegisterRequestDto registerDto) {
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new RuntimeException("User already exist");
		}
		
		User user = mapper.map(registerDto, User.class);
		
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Role role = roleRepository.findByRoleName(RoleType.ROLE_CUSTOMER)
				.orElseThrow(() -> new RuntimeException("Role is not exist"));

		role.getUsers().add(user);
		user.setRole(role);

		User savedUser = userRepository.save(user);
		return mapper.map(savedUser, RegisterResponseDto.class);

	}

	@Override
	public LoginResponseDto login(LoginRequestDto requestDto) {
		User user = userRepository.findByEmail(requestDto.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found"));
		if (user.getAuthProvider() == AuthProvider.FOREIGN) {
			// Directly generate JWT without AuthenticationManager
			String token = jwtService.generateTokenWithUserDetails(user);

			LoginResponseDto response = new LoginResponseDto();
			response.setUsername(user.getEmail());
			response.setToken(token);
			return response;
		}
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

	@Override
	public LoginResponseDto loginWithGoogle(String idTokenString) {
		GoogleIdToken idToken = verifyGoogleIdToken(idTokenString); // verify like before
		if (idToken == null) {
			throw new RuntimeException("Invalid Google ID token.");
		}

		GoogleIdToken.Payload payload = idToken.getPayload();
		String email = payload.getEmail();
		String firstName = (String) payload.get("given_name");
		String lastName = (String) payload.get("family_name");

		// Check if user exists; if not, register
		if (!userRepository.existsByEmail(email)) {
			RegisterRequestDto registerDto = new RegisterRequestDto();
			registerDto.setEmail(email);
			registerDto.setFirstName(firstName);
			registerDto.setLastName(lastName);
			registerDto.setPassword(PasswordGenerator.generateRandomPassword(12)); // generate a secure random password
			registerDto.setAuthProvider(AuthProvider.FOREIGN);

			register(registerDto);
		}

		// Now login user with email only; password is handled internally in login flow
		LoginRequestDto loginRequest = new LoginRequestDto();
		loginRequest.setEmail(email);
		loginRequest.setPassword(""); // password won't be used here, or skip auth logic for google login

		return login(loginRequest); // generate JWT and return login response
	}

	public GoogleIdToken verifyGoogleIdToken(String idTokenString) {
		try {
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
					GsonFactory.getDefaultInstance()).setAudience(Collections.singletonList("YOUR_GOOGLE_CLIENT_ID"))
					.build();

			GoogleIdToken idToken = verifier.verify(idTokenString);
			return idToken;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	

}
