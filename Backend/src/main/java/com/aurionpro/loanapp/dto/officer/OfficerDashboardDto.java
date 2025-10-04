package com.aurionpro.loanapp.dto.officer;

import java.time.LocalDate;
import java.util.List;

import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficerDashboardDto {

    // Officer personal details
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String employeeId;
    private LocalDate hireDate;
    private String profileUrl;

    // Statistics
    private int totalAssignedApplications;
    private int pendingApplications;
    private int approvedApplications;
    private int rejectedApplications;

    // List of assigned applications (optional, for quick view)
    private List<LoanApplicationDto> assignedApplications;
}
