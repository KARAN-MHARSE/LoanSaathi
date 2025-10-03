package com.aurionpro.loanapp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationResponseDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationStatusUpdateRequestDto;
import com.aurionpro.loanapp.dto.page.PageResponseDto;
import com.aurionpro.loanapp.entity.LoanApplication;
import com.aurionpro.loanapp.service.ILoanApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/officer")
@PreAuthorize("hasRole('OFFICER')")
@RequiredArgsConstructor
public class OfficerController {
	private final ILoanApplicationService loanApplicationService;

	@PutMapping("/applications")
	public ResponseEntity<LoanApplicationResponseDto> updateLoanApplicationStatus(Principal principal,
			@RequestBody LoanApplicationStatusUpdateRequestDto requestDto) {
		LoanApplicationResponseDto response= loanApplicationService.updateApplicationStatus(principal.getName(), requestDto);
		return new ResponseEntity<LoanApplicationResponseDto>(response,HttpStatus.ACCEPTED);
	}

	@GetMapping("/applications/assigned")
	public ResponseEntity<PageResponseDto<LoanApplicationDto>> getAssignedApplicationsOfOfficer(Principal principal,
			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
		PageResponseDto<LoanApplicationDto> response = loanApplicationService
				.getAssignedApplicationsOfOfficer(principal.getName(), pageNumber, pageSize);

		return ResponseEntity.ok(response);
	}
}
