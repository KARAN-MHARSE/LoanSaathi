	package com.aurionpro.loanapp.dto.loanapplication;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.aurionpro.loanapp.property.LoanApplicationStatus;

import lombok.Data;

@Data
public class LoanApplicationDto {
	private Long id; // DB id
	private String applicationId; 
	private BigDecimal requiredAmount;
	private BigDecimal annualIncome;
	private String occupation;
	private String reason;
	private int tenure;
	private LoanApplicationStatus applicationStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
