package com.aurionpro.loanapp.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.loanapp.dto.customer.CustomerDashboard;
import com.aurionpro.loanapp.dto.customer.CustomerProfileRequestDto;
import com.aurionpro.loanapp.dto.customer.CustomerProfileResponseDto;
import com.aurionpro.loanapp.service.ICustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
	private final ICustomerService customerService;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<CustomerProfileResponseDto> createCustomerProfile(
			@Valid @RequestBody CustomerProfileRequestDto request, Principal principal) {
		String email = principal.getName();
		CustomerProfileResponseDto response = customerService.createCustomerProfile(request, email);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<CustomerDashboard> viewCustomerDashboard(Principal principal) {
		String email = principal.getName();
		CustomerDashboard response = customerService.viewCustomerDashboard(email);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}


}
