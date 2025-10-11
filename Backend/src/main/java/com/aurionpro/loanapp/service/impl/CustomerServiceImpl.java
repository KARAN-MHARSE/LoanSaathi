package com.aurionpro.loanapp.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.customer.CustomerDashboard;
import com.aurionpro.loanapp.dto.customer.CustomerProfileRequestDto;
import com.aurionpro.loanapp.dto.customer.CustomerProfileResponseDto;
import com.aurionpro.loanapp.entity.Customer;
import com.aurionpro.loanapp.entity.LoanApplication;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.exception.ResourceNotFoundException;
import com.aurionpro.loanapp.exception.UserNotFoundException;
import com.aurionpro.loanapp.property.LoanApplicationStatus;
import com.aurionpro.loanapp.repository.CustomerRepository;
import com.aurionpro.loanapp.repository.UserRepository;
import com.aurionpro.loanapp.service.ICustomerService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
	private final UserRepository userRepository;
	private final CustomerRepository customerRepository;
	private final ModelMapper mapper;

	@Override
	@Transactional
	public CustomerProfileResponseDto createCustomerProfile(CustomerProfileRequestDto requestDto, String userEmail) {
		User user = userRepository.findByEmail(userEmail)
				.orElseThrow(()-> new UserNotFoundException("User not found with email id "+ userEmail));
		
		if(customerRepository.existsById(user.getId())) {
            throw new IllegalStateException("Customer profile already exists for this user.");
		}
		
		Customer customer = mapper.map(requestDto,Customer.class);
		customer.setUser(user);
		
		Customer savedCustomer = customerRepository.save(customer);
		
		return mapper.map(savedCustomer, CustomerProfileResponseDto.class);
		
	}

	@Override
	public CustomerDashboard viewCustomerDashboard(String email) {
		Customer customer = customerRepository.findByUserEmail(email)
				.orElseThrow(()-> new UserNotFoundException("Customer not found with email id "+ email));
		
		List<LoanApplication> applications = customer.getLoanApplications();
		
		long total = applications.size();
		long pending = applications.stream().filter(a-> a.getApplicationStatus().equals(LoanApplicationStatus.PENDING)).count();
		long approved = applications.stream().filter(a-> a.getApplicationStatus().equals(LoanApplicationStatus.APPROVED)).count();
		long rejected = applications.stream().filter(a-> a.getApplicationStatus().equals(LoanApplicationStatus.REJECTED)).count();
		
		CustomerDashboard dashboard = new CustomerDashboard();
		dashboard.setFirstName(customer.getUser().getFirstName());
		dashboard.setLastName(customer.getUser().getLastName());
        dashboard.setEmail(customer.getUser().getEmail());
        dashboard.setPhoneNumber(customer.getUser().getPhoneNumber());
        dashboard.setDateOfBirth(customer.getUser().getDateOfBirth());
        dashboard.setProfileUrl(customer.getUser().getProfileUrl());
        dashboard.setTotalApplications((int) total);
        dashboard.setPendingApplications((int) pending);
        dashboard.setApprovedApplications((int) approved);
        dashboard.setRejectedApplications((int) rejected);
        
        
        return dashboard;


	}

}
