package com.aurionpro.loanapp.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponseDto {
	private boolean success;
	private int statusCode;
	private String message;
	private LocalDateTime timestamp;
}
