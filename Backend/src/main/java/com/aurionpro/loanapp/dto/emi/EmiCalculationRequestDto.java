package com.aurionpro.loanapp.dto.emi;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmiCalculationRequestDto {

	private BigDecimal loanAmount;
	private Integer tenureMonths;
}
