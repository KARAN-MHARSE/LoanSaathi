package com.aurionpro.loanapp.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserProfileResponseDto {
	private String profileUrl;

}
