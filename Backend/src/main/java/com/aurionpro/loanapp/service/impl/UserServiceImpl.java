package com.aurionpro.loanapp.service.impl;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.UpdateUserProfilePhotoRequestDto;
import com.aurionpro.loanapp.dto.UpdateUserProfileResponseDto;
import com.aurionpro.loanapp.dto.user.UserDto;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.exception.ResourceNotFoundException;
import com.aurionpro.loanapp.property.UserStatus;
import com.aurionpro.loanapp.repository.UserRepository;
import com.aurionpro.loanapp.service.IUserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
	private final UserRepository userRepository;
	private final Cloudinary cloudinary;
	private final ModelMapper mapper;
	

	@Override
	public UserDto getCurrentUserProfile(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(()-> new ResourceNotFoundException("User not found with email"+email));
		
		return mapper.map(user, UserDto.class);
	}

	@Override
	@Transactional
    public UpdateUserProfileResponseDto updateUserProfilePhoto(String email,UpdateUserProfilePhotoRequestDto requestDto) {    
		try {
			User user = userRepository.findByEmail(email)
					.orElseThrow(()-> new ResourceNotFoundException("User not found with email"+email));

			
			Map uploadResult = cloudinary.uploader().upload(requestDto.getProfileImage().getBytes(),
					ObjectUtils.asMap("folder", "loan_documents", "resource_type", "auto"));
			
			UpdateUserProfileResponseDto response = new UpdateUserProfileResponseDto();
			String profileUrl = uploadResult.get("secure_url").toString();
			response.setProfileUrl(profileUrl);
			
			user.setProfileUrl(profileUrl);
			userRepository.save(user);
			return response;

			
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong");
		}
		
	}

	@Override
	public UserDto getUserById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDto> getAllUsers(String role, int page, int size, String sortBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto updateUserStatus(Long userId, UserStatus userStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public UserDto updateUserProfile(String username,com.aurionpro.loanapp.dto.user.UpdateUserProfileRequestDto requestDto) {
		    User user = userRepository.findByEmail(username)
		            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

		    user.setFirstName(requestDto.getFirstName());
		    user.setLastName(requestDto.getLastName());
		    user.setEmail(requestDto.getEmail());
		    user.setPhoneNumber(requestDto.getPhoneNumber());
		    user.setDateOfBirth(requestDto.getDateOfBirth());

		    User updatedUser = userRepository.save(user);

		   return mapper.map(updatedUser, UserDto.class);

	}

}
