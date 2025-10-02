package com.aurionpro.loanapp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.customer.CustomerProfileRequestDto;
import com.aurionpro.loanapp.dto.customer.CustomerProfileResponseDto;
import com.aurionpro.loanapp.entity.Customer;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.exception.ResourceNotFoundException;
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
				.orElseThrow(()-> new ResourceNotFoundException("User not found with email id "+ userEmail));
		
		if(customerRepository.existsById(user.getId())) {
            throw new IllegalStateException("Customer profile already exists for this user.");
		}
		
		Customer customer = mapper.map(requestDto,Customer.class);
		customer.setUser(user);
		
		Customer savedCustomer = customerRepository.save(customer);
		
		return mapper.map(savedCustomer, CustomerProfileResponseDto.class);
		
	}

}
