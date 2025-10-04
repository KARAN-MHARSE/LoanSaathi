package com.aurionpro.loanapp.service;

import java.util.List;

import com.aurionpro.loanapp.dto.UpdateUserProfileResponseDto;
import com.aurionpro.loanapp.dto.user.UpdateUserProfilePhotoRequestDto;
import com.aurionpro.loanapp.dto.user.UpdateUserProfileRequestDto;
import com.aurionpro.loanapp.dto.user.UserDto;
import com.aurionpro.loanapp.property.UserStatus;

public interface IUserService {
	
    UserDto getCurrentUserProfile(String email);
    UpdateUserProfileResponseDto updateUserProfilePhoto(String email,UpdateUserProfilePhotoRequestDto requestDto);    
    // Admin methods
    UserDto getUserById(Long userId);
    List<UserDto> getAllUsers(String role, int page, int size, String sortBy);
    UserDto updateUserStatus(Long userId, UserStatus userStatus);
    UserDto updateUserProfile(String username,UpdateUserProfileRequestDto requestDto);
    
}