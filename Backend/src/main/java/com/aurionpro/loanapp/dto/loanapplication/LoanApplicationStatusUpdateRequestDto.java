package com.aurionpro.loanapp.dto.loanapplication;

import com.aurionpro.loanapp.property.LoanApplicationStatus;

import lombok.Data;

@Data
public class LoanApplicationStatusUpdateRequestDto {
	private Long applicationId;
	private LoanApplicationStatus newLoanApplicationStatus;
	private Long officerId;
}
