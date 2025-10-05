package com.aurionpro.loanapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationDto;
import com.aurionpro.loanapp.dto.officer.OfficerDashboardDto;
import com.aurionpro.loanapp.dto.officer.OfficerRequestDto;
import com.aurionpro.loanapp.entity.LoanApplication;
import com.aurionpro.loanapp.entity.Officer;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.exception.ResourceNotFoundException;
import com.aurionpro.loanapp.repository.OfficerRepository;
import com.aurionpro.loanapp.repository.UserRepository;
import com.aurionpro.loanapp.service.IOfficerService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OfficerServiceImpl implements IOfficerService{
    private final OfficerRepository officerRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
	public OfficerDashboardDto getOfficerDashboard(String officerEmail) {
    	 Officer officer = officerRepository.findByUserEmail(officerEmail)
                 .orElseThrow(() -> new RuntimeException("Officer not found"));

         User user = officer.getUser();

         List<LoanApplication> applications = officer.getLoanApplications();

         List<LoanApplicationDto> applicationDtos = applications.stream()
                 .map(app -> mapper.map(app,LoanApplicationDto.class))
                 .collect(Collectors.toList());

         long total = applications.size();
         long pending = applications.stream().filter(a -> a.getApplicationStatus().name().equals("PENDING")).count();
         long approved = applications.stream().filter(a -> a.getApplicationStatus().name().equals("APPROVED")).count();
         long rejected = applications.stream().filter(a -> a.getApplicationStatus().name().equals("REJECTED")).count();

         return new OfficerDashboardDto(
                 user.getFirstName(),
                 user.getLastName(),
                 user.getEmail(),
                 user.getPhoneNumber(),
                 officer.getEmployeeId(),
                 officer.getHireDate(),
                 user.getProfileUrl(),
                 (int) total,
                 (int) pending,
                 (int) approved,
                 (int) rejected,
                 applicationDtos
         );
	}

	@Override
	public void addOfficer(@Valid OfficerRequestDto requestDto) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(requestDto.getId()).
				orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		
		Officer officer = new Officer();
		
		long timestamp = System.currentTimeMillis();
		officer.setEmployeeId("EMP"+timestamp);
		officer.setId(user.getId());
		officer.setUser(user);
		officer.setHireDate(requestDto.getHireDate());
		
		officerRepository.save(officer);
		
	}

	@Override
	public void removeOfficer(@Valid String empId) {
		// TODO Auto-generated method stub
		 Officer officer = officerRepository.findByEmployeeId(empId)
                 .orElseThrow(() -> new RuntimeException("Officer not found"));
		 officer.setActive(false);
		 officerRepository.save(officer);
	}
	
}
