package com.aurionpro.loanapp.dto.user;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserProfilePhotoRequestDto {
    @NotNull(message = "Profile image must not be null")
	private MultipartFile profileImage;

}
