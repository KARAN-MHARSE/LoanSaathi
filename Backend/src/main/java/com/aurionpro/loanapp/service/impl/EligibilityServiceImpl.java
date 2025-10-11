package com.aurionpro.loanapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.eligibility.CheckEligibilityDto;
import com.aurionpro.loanapp.dto.eligibility.EligibilityRequestDto;
import com.aurionpro.loanapp.dto.eligibility.EligibilityResponseDto;
import com.aurionpro.loanapp.entity.Eligibility;
import com.aurionpro.loanapp.entity.LoanScheme;
import com.aurionpro.loanapp.exception.DuplicateResourceException;
import com.aurionpro.loanapp.exception.ResourceNotFoundException;
import com.aurionpro.loanapp.repository.EligibilityRepository;
import com.aurionpro.loanapp.repository.LoanSchemeRepository;
import com.aurionpro.loanapp.service.IEligibilityService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EligibilityServiceImpl implements IEligibilityService {

	private final EligibilityRepository eligibilityRepository;
	private final LoanSchemeRepository loanSchemeRepository;
	private final ModelMapper mapper;

	@Override
	public void addEligibility(@Valid EligibilityRequestDto eligibilityDto) {
		// Fetch the loan scheme
		LoanScheme loanScheme = loanSchemeRepository.findById(eligibilityDto.getLoanSchemeId()).orElseThrow(
				() -> new RuntimeException("Loan scheme not found with id: " + eligibilityDto.getLoanSchemeId()));
		boolean isDuplicate = loanScheme.getEligibilities().stream()
			    .anyMatch(e ->
			        Objects.equals(e.getName(), eligibilityDto.getName()) &&
			        Objects.equals(e.getOperator(), eligibilityDto.getOperator()) &&
			        Objects.equals(e.getLoanScheme().getId(), eligibilityDto.getLoanSchemeId())
			    );

			if (isDuplicate) {
			    throw new DuplicateResourceException(
			        String.format("Eligibility criteria '%s' with operator '%s' already exists for scheme ID %d",
			            eligibilityDto.getName(),
			            eligibilityDto.getOperator(),
			            eligibilityDto.getLoanSchemeId())
			    );
			}
		

		Eligibility eligibility = Eligibility.builder()
				.name(eligibilityDto.getName())
				.description(eligibilityDto.getDescription())
				.value(eligibilityDto.getValue())
				.operator(eligibilityDto.getOperator())
				.loanScheme(loanScheme)
				.isActive(true)
				.build();

		loanScheme.getEligibilities().add(eligibility);
		loanSchemeRepository.save(loanScheme);
		//eligibilityRepository.save(eligibility);

	}

	@Override
	public void removeEligibility(@Valid Long eligibilityId) {
		// TODO Auto-generated method stub
		Eligibility eligibility = eligibilityRepository.findById(eligibilityId)
				.orElseThrow(()-> new ResourceNotFoundException("Constraint Does not exist"));
		
		eligibility.setActive(false);
		eligibilityRepository.save(eligibility);
		
	}

	@Override
	public boolean checkEligibility(@Valid CheckEligibilityDto requestDto, Long loanSchemeId) {

	    LoanScheme loanScheme= loanSchemeRepository.findById(loanSchemeId)
	    		.orElseThrow(()->new ResourceNotFoundException("Loan Scheme Not Found"));
	    List<Eligibility> rules = loanScheme.getEligibilities();
	    System.out.println(rules);
	    for (Eligibility rule : rules) {
	    	if(!rule.isActive()) {
	    		continue;
	    	}
	    	
	        boolean passed = evaluateRule(requestDto, rule);

	        if (!passed) {
	            return false;
	        }
	    }

	    return true;
	}
	
	private boolean evaluateRule(CheckEligibilityDto dto, Eligibility rule) {
	    try {
	    	Object actualValue = dto.getCriteriaValues().entrySet().stream()
	    	        .filter(e -> e.getKey().equalsIgnoreCase(rule.getName().toString()))
	    	        .map(Map.Entry::getValue)
	    	        .findFirst()
	    	        .orElse(null);

	        String expectedValue = rule.getValue();

	        System.out.println("Evaluating rule -> " + rule.getName().toString()
	                + " | Operator: " + rule.getOperator()
	                + " | Expected: " + expectedValue
	                + " | Actual: " + actualValue);

	        if (actualValue == null) {
	            System.out.println("❌ Missing value for " + rule.getName());
	            return false;
	        }

	        boolean result = rule.getOperator().evaluate(actualValue, expectedValue);

	        System.out.println("✅ Rule result for " + rule.getName() + " = " + result);
	        return result;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	@Override
	public List<EligibilityResponseDto> getEligibility(@Valid Long schemeId) {
		// TODO Auto-generated method stub
		LoanScheme loanScheme= loanSchemeRepository.findById(schemeId)
	    		.orElseThrow(()->new ResourceNotFoundException("Loan Scheme Not Found"));
	    List<Eligibility> rules = loanScheme.getEligibilities();
	    
	    List<EligibilityResponseDto> res = new ArrayList<>();
	    for(Eligibility rule:rules) {
	    	res.add(mapper.map(rule,EligibilityResponseDto.class));
	    }
		return res;
	}


}