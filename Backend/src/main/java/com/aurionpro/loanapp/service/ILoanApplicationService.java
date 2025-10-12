package com.aurionpro.loanapp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.loanapp.dto.dashboard.document.DocumentRequestDto;
import com.aurionpro.loanapp.dto.dashboard.document.DocumentUploadRequestDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationRequestDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationResponseDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationStatusUpdateDto;
import com.aurionpro.loanapp.dto.page.PageResponseDto;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.property.LoanApplicationStatus;

public interface ILoanApplicationService {
	LoanApplicationResponseDto applyForLoan(LoanApplicationRequestDto applicationRequestDto, String customerEmail);

	LoanApplicationResponseDto submitLoanApplication(Long applicationId, String email);

	LoanApplicationResponseDto uploadDocuments(Long applicationId, List<DocumentUploadRequestDto> documents)
			throws IOException;

	public LoanApplicationStatusUpdateDto updateApplicationStatus(String officerEmail,
			LoanApplicationStatusUpdateDto request);

	List<LoanApplicationDto> getApplicationsForCurrentUser(String email);

	LoanApplicationDto getApplicationStatus(String applicationId);

//    Officer (User from authentication)
	PageResponseDto<LoanApplicationDto> getAssignedApplicationsOfOfficer(String officerEmail, int pageNumber,
			int pageSize);
	// void requestAdditionalDocuments(String applicationId, DocumentRequestDto
	// documentRequestDto);
//
//
//    List<LoanApplicationDto> getAllApplications(String statusFilter, int page, int size);
	LoanApplicationResponseDto getApplicationById(Long applicationId, Authentication authentication);

	PageResponseDto<LoanApplicationDto> getApplicationsOfOfficerByStatus(String officerEmail, LoanApplicationStatus status,
			int pageNumber, int pageSize);
}
