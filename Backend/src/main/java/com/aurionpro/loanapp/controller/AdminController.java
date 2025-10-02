package com.aurionpro.loanapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeDto;
import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeRequestDto;
import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeResponseDto;
import com.aurionpro.loanapp.service.ILoanSchemeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
	
	 private final ILoanSchemeService loanSchemeService; // Or a new AdminService

	    @PostMapping("/loan-schemes")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<LoanSchemeResponseDto> createLoanScheme(
	            @Valid @RequestBody LoanSchemeRequestDto request) {
	    	LoanSchemeResponseDto newScheme = loanSchemeService.createLoanScheme(request);
	        return new ResponseEntity<>(newScheme, HttpStatus.CREATED);
	    }

}
