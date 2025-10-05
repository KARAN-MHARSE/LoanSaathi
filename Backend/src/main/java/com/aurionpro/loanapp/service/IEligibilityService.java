package com.aurionpro.loanapp.service;

import com.aurionpro.loanapp.dto.eligibility.EligibilityRequestDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationRequestDto;

import jakarta.validation.Valid;

public interface IEligibilityService {

	void addEligibility(@Valid EligibilityRequestDto eligibilityDto);

	void removeEligibility(@Valid Long eligibilityId);

	boolean checkEligibility(@Valid LoanApplicationRequestDto requestDto);

}
