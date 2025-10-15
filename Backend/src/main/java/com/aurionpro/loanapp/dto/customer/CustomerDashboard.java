package com.aurionpro.loanapp.dto.customer;

import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; // Changed to NoArgsConstructor for better compatibility

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private double totalLoanAmount;      
    private double totalOutstandingAmount;
    private double totalPaidAmount;        
    private double nextEMIAmount;          
    private LocalDate nextEMIDueDate;      
    private int activeLoans;               
    private int closedLoans;               

    private List<LoanApplicationDto> applications;
}