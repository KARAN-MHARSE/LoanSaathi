package com.aurionpro.loanapp.dto;

import java.time.LocalDateTime;

import org.springframework.scheduling.support.SimpleTriggerContext;


import lombok.Data;

@Data
public class RegisterResponseDto {
	private Long id;
	private String username;
	private LocalDateTime createdAt;

}
