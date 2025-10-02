package com.aurionpro.loanapp.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequestDto {
	private String email;
	private String oldPassword;
	private String newPassword;
}
