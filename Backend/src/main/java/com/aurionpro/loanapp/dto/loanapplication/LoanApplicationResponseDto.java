package com.aurionpro.loanapp.dto.loanapplication;

import com.aurionpro.loanapp.dto.dashboard.document.DocumentResponseDto;
import com.aurionpro.loanapp.property.LoanApplicationStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class LoanApplicationResponseDto {
    private Long id;
    private String applicationId;
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
    private List<DocumentResponseDto> documents;
}