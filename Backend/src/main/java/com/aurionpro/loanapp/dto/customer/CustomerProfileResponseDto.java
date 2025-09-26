package com.aurionpro.loanapp.dto.customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class CustomerProfileResponseDto {
	private Long userId;
	private String panNumber;
	private String aadhaarNumber;
	private String occupation;
	private BigDecimal annualIncome;
	private LocalDateTime profileCreatedAt;

}
