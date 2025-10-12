package com.aurionpro.loanapp.dto.auth;

import com.aurionpro.loanapp.property.RoleType;

import lombok.Data;

@Data
public class LoginResponseDto {
	private String username;
	private RoleType role;
	private String token;

}
