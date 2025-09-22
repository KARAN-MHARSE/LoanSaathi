package com.aurionpro.loanapp.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.LoginRequestDto;
import com.aurionpro.loanapp.dto.LoginResponseDto;
import com.aurionpro.loanapp.dto.RegisterRequestDto;
import com.aurionpro.loanapp.dto.RegisterResponseDto;
import com.aurionpro.loanapp.entity.Role;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.repository.RoleRepository;
import com.aurionpro.loanapp.repository.UserRepository;
import com.aurionpro.loanapp.security.JwtService;
import com.aurionpro.loanapp.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper mapper;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	
	@Override
	public RegisterResponseDto register(RegisterRequestDto registerDto) {
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			throw new RuntimeException("User already exist");
		}
		
		User user = mapper.map(registerDto, User.class);
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		Role role = roleRepository.findByRoleName(registerDto.getRoleName())
				.orElseThrow(()-> new RuntimeException("Role is not exist"));
		
		role.getUsers().add(user);
		user.setRole(role);
		
		User savedUser = userRepository.save(user);
		return mapper.map(savedUser, RegisterResponseDto.class);
		
	}
	@Override
	public LoginResponseDto login(LoginRequestDto requestDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword())
				);
		String token = jwtService.generateToken(authentication);
		
		LoginResponseDto response = new LoginResponseDto();
		response.setUsername(authentication.getName());
		response.setToken(token);
		
		return response;

	}

}
