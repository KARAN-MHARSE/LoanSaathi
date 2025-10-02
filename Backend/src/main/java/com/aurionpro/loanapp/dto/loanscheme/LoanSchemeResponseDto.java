package com.aurionpro.loanapp.dto.loanscheme;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.aurionpro.loanapp.entity.LoanType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanSchemeResponseDto {
    private Long id;
    private String schemeName;
    private LoanType loanType;
    private BigDecimal minLoanAmount;
    private BigDecimal maxLoanAmount;
    private BigDecimal interestRate;
    private String description;
    private int minTenureMonths;
    private int maxTenureMonths;
    private LocalDateTime createdAt;
}
