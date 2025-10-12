package com.aurionpro.loanapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.loanapp.dto.emi.EmiCalculationRequestDto;
import com.aurionpro.loanapp.dto.emi.EmiCalculationResponseDto;
import com.aurionpro.loanapp.dto.loan.LoanResponseDto;
import com.aurionpro.loanapp.service.ILoanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/loan")
@RequiredArgsConstructor
public class LoanController {

	private final ILoanService loanService;

	@GetMapping("/customer/{customerId}")
	@PreAuthorize("hasAnyRole('CUSTOMER', 'OFFICER','ADMIN')")
	ResponseEntity<List<LoanResponseDto>> getLoans(@PathVariable Long customerId) {
		List<LoanResponseDto> res = loanService.getLoansForCurrentUser(customerId);
		return ResponseEntity.ok(res);
	}

	@GetMapping("/{loanId}")
	@PreAuthorize("hasAnyRole('CUSTOMER', 'OFFICER', 'ADMIN')")
	public ResponseEntity<LoanResponseDto> getLoanDetails(@PathVariable Long loanId) {
		LoanResponseDto response = loanService.getLoanDetails(loanId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/statement/{loanId}")
    public ResponseEntity<byte[]> downloadLoanStatement(@PathVariable Long loanId) {
        byte[] pdf = loanService.generateLoanStatement(loanId);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=LoanStatement_" + loanId + ".pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    
    @PostMapping("/{loanSchemeId}/calculate-emi")
    public ResponseEntity<EmiCalculationResponseDto> calculateEMI(
            @PathVariable Long loanSchemeId,
            @RequestBody @Valid EmiCalculationRequestDto request) {

        EmiCalculationResponseDto response = loanService.calculateEMI(request, loanSchemeId);
        return ResponseEntity.ok(response);
    }
}
