package com.aurionpro.loanapp.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.EmailDto;
import com.aurionpro.loanapp.dto.auth.RegisterRequestDto;
import com.aurionpro.loanapp.dto.auth.RegisterResponseDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationDto;
import com.aurionpro.loanapp.dto.officer.OfficerDashboardDto;
import com.aurionpro.loanapp.entity.LoanApplication;
import com.aurionpro.loanapp.entity.Officer;
import com.aurionpro.loanapp.entity.Role;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.exception.ResourceNotFoundException;
import com.aurionpro.loanapp.exception.UserAlreadyExistException;
import com.aurionpro.loanapp.exception.UserNotFoundException;
import com.aurionpro.loanapp.property.RoleType;
import com.aurionpro.loanapp.repository.OfficerRepository;
import com.aurionpro.loanapp.repository.RoleRepository;
import com.aurionpro.loanapp.repository.UserRepository;
import com.aurionpro.loanapp.service.EmailService;
import com.aurionpro.loanapp.service.IAuthService;
import com.aurionpro.loanapp.service.IOfficerService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OfficerServiceImpl implements IOfficerService {
	private final OfficerRepository officerRepository;
	private final RoleRepository roleRepository;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper mapper;

	@Override
	public OfficerDashboardDto getOfficerDashboard(String officerEmail) {
		Officer officer = officerRepository.findByUserEmail(officerEmail)
				.orElseThrow(() -> new RuntimeException("Officer not found"));

		User user = officer.getUser();

		List<LoanApplication> applications = officer.getLoanApplications();

		List<LoanApplicationDto> applicationDtos = applications.stream()
				.map(app -> mapper.map(app, LoanApplicationDto.class)).collect(Collectors.toList());

		long total = applications.size();
		long pending = applications.stream().filter(a -> a.getApplicationStatus().name().equals("PENDING")).count();
		long approved = applications.stream().filter(a -> a.getApplicationStatus().name().equals("APPROVED")).count();
		long rejected = applications.stream().filter(a -> a.getApplicationStatus().name().equals("REJECTED")).count();

		return new OfficerDashboardDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(),
				officer.getEmployeeId(), officer.getHireDate(), user.getProfileUrl(), (int) total, (int) pending,
				(int) approved, (int) rejected, applicationDtos);
	}

	@Override
	@Transactional
	public RegisterResponseDto addOfficer(@Valid RegisterRequestDto requestDto) {
		if (officerRepository.existsByUserEmail(requestDto.getEmail())) {
			throw new UserAlreadyExistException("User already exist with email id " + requestDto.getEmail());
		}

		User user = mapper.map(requestDto, User.class);

		user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

		Role role = roleRepository.findByRoleName(RoleType.ROLE_OFFICER)
				.orElseThrow(() -> new RuntimeException("Role is not exist"));

		role.getUsers().add(user);
		user.setRole(role);
		
		Officer officer = new Officer();
		officer.setActive(true);
		officer.setEmployeeId("E"+System.currentTimeMillis());
		officer.setHireDate(LocalDate.now());
		officer.setUser(user);
		

		Officer savedOfficer = officerRepository.save(officer);
		RegisterResponseDto registerResponseDto = new RegisterResponseDto();
		registerResponseDto.setCreatedAt(savedOfficer.getUser().getCreatedAt());
		registerResponseDto.setId(savedOfficer.getId());
		registerResponseDto.setUsername(requestDto.getEmail());
		
		sendAddOfficerEmail(requestDto);
		
		return registerResponseDto;
	}



	@Async
	private void sendAddOfficerEmail(RegisterRequestDto requestDto) {
	    EmailDto emailDto = new EmailDto();
	    emailDto.setTo(requestDto.getEmail());
	    emailDto.setSubject("Welcome to LoanSaathi - Your Officer Account Created");
	    
	    String body = String.format(
	        "Dear %s %s,\n\n" +
	        "Your officer account has been created successfully.\n\n" +
	        "Here are your login details:\n" +
	        "Email: %s\n" +
	        "Password: %s\n\n" +
	        "Please log in and change your password immediately for security.\n\n" +
	        "Regards,\nLoanSaathi Team",
	        requestDto.getFirstName(),
	        requestDto.getLastName(),
	        requestDto.getEmail(),
	        requestDto.getPassword()
	    );
	    
	    emailDto.setBody(body);

	    emailService.sendEmailWithoutImage(emailDto);
	}


	@Override
	public void removeOfficer(@Valid String empId) {
		Officer officer = officerRepository.findByEmployeeId(empId)
				.orElseThrow(() -> new UserNotFoundException("Officer not found with id "+empId));
		officer.setActive(false);
		officer.getUser().setActive(false);
		officerRepository.save(officer);
	}

}
