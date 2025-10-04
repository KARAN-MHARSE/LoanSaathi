package com.aurionpro.loanapp.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EmiCalculator {
	
	public static BigDecimal calculateMonthlyInstallment(BigDecimal principal, BigDecimal annualRate, int tenureMonths) {
	    BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);
	    BigDecimal numerator = principal.multiply(monthlyRate).multiply((BigDecimal.ONE.add(monthlyRate)).pow(tenureMonths));
	    BigDecimal denominator = (BigDecimal.ONE.add(monthlyRate)).pow(tenureMonths).subtract(BigDecimal.ONE);
	    return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
	}


}
