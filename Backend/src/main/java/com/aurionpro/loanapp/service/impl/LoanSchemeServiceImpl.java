package com.aurionpro.loanapp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeDto;
import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeRequestDto;
import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeResponseDto;
import com.aurionpro.loanapp.entity.LoanScheme;
import com.aurionpro.loanapp.repository.LoanSchemeRepository;
import com.aurionpro.loanapp.service.ILoanApplicationService;
import com.aurionpro.loanapp.service.ILoanSchemeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanSchemeServiceImpl implements ILoanSchemeService {
	private final LoanSchemeRepository loanSchemeRepository;
	private final ModelMapper mapper;

	@Override
	public LoanSchemeResponseDto createLoanScheme(LoanSchemeRequestDto request) {
		 if (loanSchemeRepository.existsBySchemeName(request.getSchemeName())) {
		        throw new IllegalArgumentException("A scheme with this name already exists.");
		    }
		 
		 if (request.getMinLoanAmount().compareTo(request.getMaxLoanAmount()) >= 0) {
		        throw new IllegalArgumentException("Minimum loan amount must be less than maximum loan amount.");
		    }
		 
		 LoanScheme loanScheme = mapper.map(request, LoanScheme.class);
		 
		 LoanScheme savedLoanScheme = loanSchemeRepository.save(loanScheme);
		 
		 return mapper.map(savedLoanScheme, LoanSchemeResponseDto.class);
	}

}
