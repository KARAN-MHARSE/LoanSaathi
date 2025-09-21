package com.aurionpro.loanapp.dto;

import com.aurionpro.loanapp.property.RoleType;

import lombok.Data;

@Data
public class RegisterRequestDto {
	private String username;
	private String password;
    private RoleType roleName;

}
