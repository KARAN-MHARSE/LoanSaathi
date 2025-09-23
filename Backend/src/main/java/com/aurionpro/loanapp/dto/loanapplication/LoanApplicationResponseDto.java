package com.aurionpro.loanapp.dto;

import com.aurionpro.loanapp.property.LoanApplicationStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class LoanApplicationResponseDto {
    private Long id;
    private BigDecimal requiredAmount;
    private BigDecimal annualIncome;
    private String occupation;
    private String reason;
    private int tenure;
    private LoanApplicationStatus applicationStatus;
    private LocalDateTime createdAt;
    private Long customerId;
    private String customerName;
    private Long loanSchemeId;
    private String loanSchemeName;
    private List<DocumentDto> documents;
}