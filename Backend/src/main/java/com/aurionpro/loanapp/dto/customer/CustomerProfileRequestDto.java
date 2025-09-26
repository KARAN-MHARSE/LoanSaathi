package com.aurionpro.loanapp.dto.customer;

import java.math.BigDecimal;
import java.util.List;

import com.aurionpro.loanapp.entity.BankAccount;
import com.aurionpro.loanapp.entity.Loan;
import com.aurionpro.loanapp.entity.LoanApplication;
import com.aurionpro.loanapp.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerProfileRequestDto {
	@Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN number format.")
	@NotBlank
	private String panNumber;

	@Pattern(regexp = "^[2-9]{1}[0-9]{3}[0-9]{4}[0-9]{4}$", message = "Invalid Aadhaar number format.")
	@NotBlank
	private String aadhaarNumber;

	@NotBlank
	private String occupation;

	@DecimalMin(value = "0.0")
	private BigDecimal annualIncome;

}
