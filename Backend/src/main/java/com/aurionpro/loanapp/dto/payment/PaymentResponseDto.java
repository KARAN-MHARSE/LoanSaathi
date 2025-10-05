package com.aurionpro.loanapp.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
	private String SecretKey;
}
