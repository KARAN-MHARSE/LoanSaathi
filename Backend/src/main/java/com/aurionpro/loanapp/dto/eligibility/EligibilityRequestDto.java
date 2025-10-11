package com.aurionpro.loanapp.dto.eligibility;

import com.aurionpro.loanapp.property.EligibilityCriteria;
import com.aurionpro.loanapp.property.Operator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityRequestDto {

//		@NotBlank(message = "Name is required")
	    private EligibilityCriteria name;

	    private String description;

	    @NotBlank(message = "Value is required")
	    private String value;

	    @NotNull(message = "Operator is required")
	    private Operator operator;

	    @NotNull(message = "Loan Scheme ID is required")
	    private Long loanSchemeId;

}
