package com.aurionpro.loanapp.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.loanapp.dto.UpdateUserProfileResponseDto;
import com.aurionpro.loanapp.dto.user.UpdateUserProfilePhotoRequestDto;
import com.aurionpro.loanapp.dto.user.UpdateUserProfileRequestDto;
import com.aurionpro.loanapp.dto.user.UserDto;
import com.aurionpro.loanapp.service.IUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
	private final IUserService userService;

	@PutMapping("/profile/image")
	public ResponseEntity<UpdateUserProfileResponseDto> updateProfileImage(Principal principal,
			@Valid @ModelAttribute UpdateUserProfilePhotoRequestDto requestDto) {
		UpdateUserProfileResponseDto responseDto = userService.updateUserProfilePhoto(principal.getName(), requestDto);
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/profile")
	public ResponseEntity<UserDto> updateUserProfile(Principal principal,
			@Valid @RequestBody UpdateUserProfileRequestDto requestDto) {

		UserDto responseDto = userService.updateUserProfile(principal.getName(), requestDto);

		return ResponseEntity.ok(responseDto);
	}

}
