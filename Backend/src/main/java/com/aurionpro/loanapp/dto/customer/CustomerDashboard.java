package com.aurionpro.loanapp.dto.customer;

import java.time.LocalDate;
import java.util.List;

import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerDashboard {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String profileUrl;

    private int totalApplications;
    private int pendingApplications;
    private int approvedApplications;
    private int rejectedApplications;
    
    private List<LoanApplicationDto> applications;

  
}
