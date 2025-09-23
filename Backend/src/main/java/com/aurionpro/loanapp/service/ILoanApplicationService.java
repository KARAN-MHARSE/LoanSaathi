package com.aurionpro.loanapp.service;

import java.util.List;

import com.aurionpro.loanapp.dto.dashboard.document.DocumentRequestDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationRequestDto;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.property.LoanApplicationStatus;

public interface ILoanApplicationService {
    LoanApplicationDto applyForLoan(LoanApplicationRequestDto applicationRequestDto);
    List<LoanApplicationDto> getApplicationsForCurrentUser(User user);
    LoanApplicationDto getApplicationStatus(String applicationId);

//    Officer (User from authentication)
    List<LoanApplicationDto> getAssignedApplications(User user);
    void approveApplication(String applicationId);
    void rejectApplication(String applicationId, LoanApplicationStatus applicationStatus);
    void requestAdditionalDocuments(String applicationId, DocumentRequestDto documentRequestDto);


    List<LoanApplicationDto> getAllApplications(String statusFilter, int page, int size);
}
