package com.aurionpro.loanapp.service;

import com.aurionpro.loanapp.dto.auth.ForgotPasswordRequestDto;
import com.aurionpro.loanapp.dto.auth.LoginRequestDto;
import com.aurionpro.loanapp.dto.auth.LoginResponseDto;
import com.aurionpro.loanapp.dto.auth.RegisterRequestDto;
import com.aurionpro.loanapp.dto.auth.RegisterResponseDto;

public interface AuthService {

	RegisterResponseDto register(RegisterRequestDto registerDto);
	LoginResponseDto login(LoginRequestDto requestDto);
	void forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto);
    void resetPassword(AuthService resetPasswordRequestDto);
//    LoginResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);
}
