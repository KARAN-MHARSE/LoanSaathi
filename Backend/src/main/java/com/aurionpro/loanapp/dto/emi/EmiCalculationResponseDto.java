package com.aurionpro.loanapp.dto.emi;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EmiCalculationResponseDto {

	BigDecimal emi;
}
