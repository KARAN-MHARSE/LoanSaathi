package com.aurionpro.loanapp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.loanapp.dto.DocumentUploadRequestDto;
import com.aurionpro.loanapp.dto.dashboard.document.DocumentRequestDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationRequestDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationResponseDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationStatusUpdateRequestDto;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.property.LoanApplicationStatus;

public interface ILoanApplicationService {
    LoanApplicationResponseDto applyForLoan(LoanApplicationRequestDto applicationRequestDto, String customerEmail);
    LoanApplicationResponseDto submitLoanApplication(Long applicationId,String email);
    LoanApplicationResponseDto uploadDocuments(Long applicationId, List<DocumentUploadRequestDto> documents) throws IOException;
	public LoanApplicationResponseDto updateApplicationStatus(LoanApplicationStatusUpdateRequestDto request);


//    List<LoanApplicationDto> getApplicationsForCurrentUser(User user);
//    LoanApplicationDto getApplicationStatus(String applicationId);

//    Officer (User from authentication)
//    List<LoanApplicationDto> getAssignedApplications(User user);
//    void approveApplication(String applicationId);
//    void rejectApplication(String applicationId, LoanApplicationStatus applicationStatus);
//    void requestAdditionalDocuments(String applicationId, DocumentRequestDto documentRequestDto);
//
//
//    List<LoanApplicationDto> getAllApplications(String statusFilter, int page, int size);
}
