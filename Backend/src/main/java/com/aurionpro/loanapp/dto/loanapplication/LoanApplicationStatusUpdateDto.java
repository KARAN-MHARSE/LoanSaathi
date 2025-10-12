	package com.aurionpro.loanapp.dto.loanapplication;
	
	import com.aurionpro.loanapp.property.LoanApplicationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
	
	@Data
	public class LoanApplicationStatusUpdateDto {
		@NotNull
		private Long applicationId;
		@NotNull
		private LoanApplicationStatus newLoanApplicationStatus;
		@NotNull
		private String remark;
	}
