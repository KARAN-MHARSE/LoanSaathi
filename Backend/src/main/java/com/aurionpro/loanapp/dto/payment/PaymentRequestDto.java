package com.aurionpro.loanapp.dto.payment;

import lombok.Data;

@Data
public class PaymentRequestDto {
	private Long amount;
	private String currency;
}
