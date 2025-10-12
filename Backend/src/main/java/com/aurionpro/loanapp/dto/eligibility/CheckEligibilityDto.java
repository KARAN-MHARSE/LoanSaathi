package com.aurionpro.loanapp.dto.eligibility;

import java.util.Map;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CheckEligibilityDto {

	 @NotNull(message = "Applicant data is required")
	    private Map<String, Object> criteriaValues;
}
