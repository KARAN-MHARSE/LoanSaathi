package com.aurionpro.loanapp.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.loanapp.dto.UpdateUserProfileRequestDto;
import com.aurionpro.loanapp.dto.UpdateUserProfileResponseDto;
import com.aurionpro.loanapp.service.IUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
	private final IUserService userService;
	
	@PutMapping("/profile/image")
	public ResponseEntity<UpdateUserProfileResponseDto> updateProfileImage(
	        Principal principal,
	        @Valid @ModelAttribute UpdateUserProfileRequestDto requestDto) {
		UpdateUserProfileResponseDto responseDto=  userService.updateUserProfile(principal.getName(), requestDto);
	    return ResponseEntity.ok(responseDto);
	}

}
