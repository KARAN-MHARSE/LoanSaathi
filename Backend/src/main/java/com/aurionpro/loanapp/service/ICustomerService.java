package com.aurionpro.loanapp.service;

import com.aurionpro.loanapp.dto.customer.CustomerProfileRequestDto;
import com.aurionpro.loanapp.dto.customer.CustomerProfileResponseDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationResponseDto;

public interface ICustomerService {
	CustomerProfileResponseDto createCustomerProfile(CustomerProfileRequestDto requestDto,String userEmail);
	
}
