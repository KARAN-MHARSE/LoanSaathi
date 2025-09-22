package com.aurionpro.loanapp.service;

import com.aurionpro.loanapp.dto.LoginRequestDto;
import com.aurionpro.loanapp.dto.LoginResponseDto;
import com.aurionpro.loanapp.dto.RegisterRequestDto;
import com.aurionpro.loanapp.dto.RegisterResponseDto;

public interface AuthService {

	RegisterResponseDto register(RegisterRequestDto registerDto);

	LoginResponseDto login(LoginRequestDto requestDto);

}
