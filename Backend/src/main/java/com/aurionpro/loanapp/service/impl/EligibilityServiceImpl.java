package com.aurionpro.loanapp.service.impl;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.eligibility.EligibilityRequestDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationRequestDto;
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

	@Override
	public void addEligibility(@Valid EligibilityRequestDto eligibilityDto) {
		// Fetch the loan scheme
		LoanScheme loanScheme = loanSchemeRepository.findById(eligibilityDto.getLoanSchemeId()).orElseThrow(
				() -> new RuntimeException("Loan scheme not found with id: " + eligibilityDto.getLoanSchemeId()));
		for(Eligibility eligibility:loanScheme.getEligibilities()) {
			if(eligibility.getName()== eligibilityDto.getName()) {
				throw new DuplicateResourceException("Eligibility criteria already present");
			}
		}
		

		Eligibility eligibility = Eligibility.builder()
				.name(eligibilityDto.getName())
				.description(eligibilityDto.getDescription())
				.value(eligibilityDto.getValue())
				.operator(eligibilityDto.getOperator())
				.loanScheme(loanScheme)
				.build();

		loanScheme.getEligibilities().add(eligibility);
		eligibilityRepository.save(eligibility);

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
	public boolean checkEligibility(@Valid LoanApplicationRequestDto requestDto) {

	    LoanScheme loanScheme= loanSchemeRepository.findById(requestDto.getLoanSchemeId())
	    		.orElseThrow(()->new ResourceNotFoundException("Loan Scheme Not Found"));
	    List<Eligibility> rules = loanScheme.getEligibilities();

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
	
	private boolean evaluateRule(LoanApplicationRequestDto dto, Eligibility rule) {
	    try {
	        Field field = LoanApplicationRequestDto.class.getDeclaredField(rule.getName().toString());
	        field.setAccessible(true);
	        Object actualValue = field.get(dto);
	        String expectedValue = rule.getValue();
	        return rule.getOperator().evaluate(actualValue, expectedValue);
	    } catch (NoSuchFieldException e) {
	        // Skip rule if no matching field
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}


}