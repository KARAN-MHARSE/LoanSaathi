package com.aurionpro.loanapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeRequestDto;
import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeResponseDto;
import com.aurionpro.loanapp.dto.page.PageResponseDto;
import com.aurionpro.loanapp.service.ILoanSchemeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/loan-schemes")
@RequiredArgsConstructor
public class LoanSchemeController {
	private final ILoanSchemeService loanSchemeService;

	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<LoanSchemeResponseDto> createLoanScheme(
			@Valid @RequestBody LoanSchemeRequestDto requestDto) {
		LoanSchemeResponseDto response = loanSchemeService.createLoanScheme(requestDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/{schemeId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<LoanSchemeResponseDto> updateLoanScheme(
			@PathVariable Long schemeId,
			@Valid @RequestBody LoanSchemeRequestDto requestDto) {
		LoanSchemeResponseDto response = loanSchemeService.updateLoanScheme(schemeId, requestDto);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{schemeId}")
	@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER','OFFICER')")
	public ResponseEntity<LoanSchemeResponseDto> getLoanSchemeById(@PathVariable Long schemeId) {
		LoanSchemeResponseDto response = loanSchemeService.getLoanSchemeById(schemeId);
		return ResponseEntity.ok(response);
	}

	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER','OFFICER')")
	public ResponseEntity<PageResponseDto<LoanSchemeResponseDto>> getAllLoanSchemes(
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "10") int pageSize) {
		PageResponseDto<LoanSchemeResponseDto> response = loanSchemeService.getAllLoanSchemes(pageNumber, pageSize);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{schemeId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteLoanScheme(@PathVariable Long schemeId) {
		loanSchemeService.deleteLoanScheme(schemeId);
		return ResponseEntity.noContent().build();
	}

}
