package com.aurionpro.loanapp.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeRequestDto;
import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeResponseDto;
import com.aurionpro.loanapp.dto.page.PageResponseDto;
import com.aurionpro.loanapp.entity.LoanScheme;
import com.aurionpro.loanapp.exception.ResourceNotFoundException;
import com.aurionpro.loanapp.repository.LoanSchemeRepository;
import com.aurionpro.loanapp.service.ILoanSchemeService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanSchemeServiceImpl implements ILoanSchemeService {
	private final LoanSchemeRepository loanSchemeRepository;
	private final ModelMapper mapper;

	@Override
	@Transactional
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

	@Override
	@Transactional
	public LoanSchemeResponseDto updateLoanScheme(Long schemeId, LoanSchemeRequestDto schemeDto) {
		  LoanScheme existingScheme = loanSchemeRepository.findById(schemeId)
	                .orElseThrow(() -> new ResourceNotFoundException("Loan scheme not found with id: " + schemeId));

	        if (!existingScheme.getSchemeName().equalsIgnoreCase(schemeDto.getSchemeName())
	                && loanSchemeRepository.existsBySchemeName(schemeDto.getSchemeName())) {
	            throw new IllegalArgumentException("A scheme with this name already exists.");
	        }

	        if (schemeDto.getMinLoanAmount().compareTo(schemeDto.getMaxLoanAmount()) >= 0) {
	            throw new IllegalArgumentException("Minimum loan amount must be less than maximum loan amount.");
	        }

	        existingScheme.setSchemeName(schemeDto.getSchemeName());
	        existingScheme.setDescription(schemeDto.getDescription());
	        existingScheme.setMinLoanAmount(schemeDto.getMinLoanAmount());
	        existingScheme.setMaxLoanAmount(schemeDto.getMaxLoanAmount());
	        existingScheme.setInterestRate(schemeDto.getInterestRate());
	        existingScheme.setMinTenureMonths(schemeDto.getMinTenureMonths());
	        existingScheme.setMaxTenureMonths(schemeDto.getMaxTenureMonths());
	        existingScheme.setActive(schemeDto.isActive());

	        LoanScheme updated = loanSchemeRepository.save(existingScheme);
	        return mapper.map(updated, LoanSchemeResponseDto.class);
	}

	@Override
	public LoanSchemeResponseDto getLoanSchemeById(Long schemeId) {
		 LoanScheme loanScheme = loanSchemeRepository.findById(schemeId)
	                .orElseThrow(() -> new ResourceNotFoundException("Loan scheme not found with id: " + schemeId));

	        return mapper.map(loanScheme, LoanSchemeResponseDto.class);
	}

	@Override
	public PageResponseDto<LoanSchemeResponseDto> getAllLoanSchemes(int pageNumber,int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
        Page<LoanScheme> schemes = loanSchemeRepository.findAllByIsActiveTrue(pageable);
        
        List<LoanSchemeResponseDto> dtoList = schemes.getContent()
                .stream()
                .map(scheme -> mapper.map(scheme, LoanSchemeResponseDto.class))
                .toList();
        
        PageResponseDto<LoanSchemeResponseDto> response = mapper.map(schemes, PageResponseDto.class);
        
        response.setContent(dtoList);
        

        return response;

		
	}

	@Override
	@Transactional
	public void deleteLoanScheme(Long schemeId) {
	    LoanScheme loanScheme = loanSchemeRepository.findById(schemeId)
	            .orElseThrow(() -> new EntityNotFoundException("Loan scheme not found with id: " + schemeId));

	    loanScheme.setActive(false);

	    loanSchemeRepository.save(loanScheme);
	}

	

}
