package com.aurionpro.loanapp.dto.loan;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.aurionpro.loanapp.property.LoanStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LoanResponseDto {

	    private UUID loanNumber;

	    private BigDecimal loanAmount;
	    
	    private BigDecimal interest;

	    private Integer tenureMonths;

	    private BigDecimal emiAmount; 
	    
	    private LocalDateTime startDate;
	    
	    private LocalDateTime endDate;
	    
	    private String loanSchemeName;

	    private LoanStatus status;
}
