package com.aurionpro.loanapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class LoanApplicationRequestDto {

    @NotNull(message = "Loan scheme ID is required.")
    private Long loanSchemeId;

    @NotNull(message = "Required amount is required.")
    @DecimalMin(value = "1000.00", message = "Loan amount must be at least 1000.")
    private BigDecimal requiredAmount;

    @NotNull(message = "Annual income is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Annual income must be greater than zero.")
    private BigDecimal annualIncome;

    @NotBlank(message = "Occupation is required.")
    private String occupation;

    @NotBlank(message = "Reason for the loan is required.")
    @Size(max = 500, message = "Reason cannot exceed 500 characters.")
    private String reason;

    @NotNull(message = "Tenure in months is required.")
    @Min(value = 6, message = "Tenure must be at least 6 months.")
    @Max(value = 360, message = "Tenure cannot exceed 360 months.")
    private int tenure;
}