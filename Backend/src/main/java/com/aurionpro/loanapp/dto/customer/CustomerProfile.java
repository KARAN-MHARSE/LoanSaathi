package com.aurionpro.loanapp.dto.customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfile {

	    private String firstName;
	    private String lastName;
	    private String email;
	    private String phoneNumber;
	    private LocalDate dateOfBirth;
	    private String profileUrl;
	    private String panNumber;
	    private String aadhaarNumber;
	    private String occupation;
	    private BigDecimal annualIncome;

	    private LocalDateTime createdAt;
	    private boolean isActive;
	
}
