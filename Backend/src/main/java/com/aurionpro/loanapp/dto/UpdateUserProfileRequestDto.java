package com.aurionpro.loanapp.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserProfileRequestDto {
    @NotNull(message = "Profile image must not be null")
	private MultipartFile profileImage;

}
