package com.aurionpro.loanapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.aurionpro.loanapp.property.PaymentStatus;

import lombok.Data;

@Data
public class InstallmentResponseDto {
	 private Long id;
	    private Integer installmentNumber;
	    private LocalDateTime dueDate;
	    private BigDecimal amount;
	    private PaymentStatus status;
	    private LocalDateTime paymentDate;
	    private BigDecimal penaltyAmount;
	    private LocalDateTime createdAt;
}
