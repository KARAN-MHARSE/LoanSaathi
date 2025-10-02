package com.aurionpro.loanapp.dto.loanscheme;

import java.math.BigDecimal;

import com.aurionpro.loanapp.entity.LoanType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanSchemeRequestDto {

    @NotBlank(message = "Scheme name is required")
    private String schemeName;

    @NotNull(message = "Loan type is required")
    private LoanType loanType;

    @NotNull(message = "Minimum loan amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Minimum loan amount must be greater than 0")
    private BigDecimal minLoanAmount;

    @NotNull(message = "Maximum loan amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum loan amount must be greater than 0")
    private BigDecimal maxLoanAmount;

    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Interest rate must be greater than 0")
    private BigDecimal interestRate;

    private String description;

    @Positive(message = "Minimum tenure must be positive")
    private int minTenureMonths;

    @Positive(message = "Maximum tenure must be positive")
    private int maxTenureMonths;
}
