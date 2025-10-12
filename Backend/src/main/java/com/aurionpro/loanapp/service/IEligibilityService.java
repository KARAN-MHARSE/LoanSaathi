package com.aurionpro.loanapp.service;

import java.util.List;

import com.aurionpro.loanapp.dto.eligibility.CheckEligibilityDto;
import com.aurionpro.loanapp.dto.eligibility.EligibilityRequestDto;
import com.aurionpro.loanapp.dto.eligibility.EligibilityResponseDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationRequestDto;
import com.aurionpro.loanapp.entity.Eligibility;

import jakarta.validation.Valid;

public interface IEligibilityService {

	void addEligibility(@Valid EligibilityRequestDto eligibilityDto);

	void removeEligibility(@Valid Long eligibilityId);

	boolean checkEligibility(@Valid CheckEligibilityDto requestDto, Long loanSchemeId);

	List<EligibilityResponseDto> getEligibility(@Valid Long schemeId);

}
