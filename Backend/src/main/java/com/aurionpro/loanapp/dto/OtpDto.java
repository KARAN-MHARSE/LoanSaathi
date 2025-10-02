package com.aurionpro.loanapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpDto {
	public String code;
	private String email;
	private long createdAt;
}
