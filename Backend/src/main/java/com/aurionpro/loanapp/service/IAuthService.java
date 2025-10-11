
package com.aurionpro.loanapp.service;

import com.aurionpro.loanapp.dto.OtpDto;
import com.aurionpro.loanapp.dto.auth.ForgotPasswordRequestDto;
import com.aurionpro.loanapp.dto.auth.LoginRequestDto;
import com.aurionpro.loanapp.dto.auth.LoginResponseDto;
import com.aurionpro.loanapp.dto.auth.RegisterRequestDto;
import com.aurionpro.loanapp.dto.auth.RegisterResponseDto;
import com.aurionpro.loanapp.dto.auth.ResetPasswordRequestDto;

public interface IAuthService {

	RegisterResponseDto register(RegisterRequestDto registerDto);
	LoginResponseDto login(LoginRequestDto requestDto);
	void sendEmailValidateOtp(String email);
	boolean forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto);
	void sendForgetPasswordOtp(String email);
    void resetPassword(ResetPasswordRequestDto resetPasswordRequestDto);
    void logout(String email);
	LoginResponseDto loginWithGoogle(String idToken);
}
