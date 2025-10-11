package com.aurionpro.loanapp.dto.eligibility;

import com.aurionpro.loanapp.property.EligibilityCriteria;
import com.aurionpro.loanapp.property.Operator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EligibilityResponseDto {
	
	    private Long id;

	    private EligibilityCriteria name;
	    private String description;
	    private String value;

	    private Operator operator;
}
