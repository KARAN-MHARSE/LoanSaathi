package com.aurionpro.loanapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.loanapp.dto.dashboard.document.DocumentUploadRequestDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationRequestDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationResponseDto;
import com.aurionpro.loanapp.property.DocumentType;
import com.aurionpro.loanapp.service.IEligibilityService;
import com.aurionpro.loanapp.service.ILoanApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {
	private final ILoanApplicationService loanApplicationService;
	private final IEligibilityService eligibilityService;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<LoanApplicationResponseDto> applyForLoan(
			@RequestBody @Valid LoanApplicationRequestDto requestDto, Principal principal) {
		System.out.println(principal);
		LoanApplicationResponseDto response = loanApplicationService.applyForLoan(requestDto, principal.getName());
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PatchMapping("/{applicationId}")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<LoanApplicationResponseDto> submitLoanApplication(@PathVariable Long applicationId,
			Principal principal) {
		LoanApplicationResponseDto response = loanApplicationService.submitLoanApplication(applicationId,
				principal.getName());
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping(value = "/{applicationId}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<LoanApplicationResponseDto> uploadDocuments(@PathVariable Long applicationId,
			@RequestParam("documents") List<MultipartFile> files, @RequestParam("types") List<String> types,
			Principal principal) throws IOException {

		
		if (files.size() != types.size()) {
			System.out.println("No files");
			return ResponseEntity.badRequest().build();
		}

		List<DocumentUploadRequestDto> requestDtos = new ArrayList<>();
		for (int i = 0; i < files.size(); i++) {
			DocumentUploadRequestDto dto = new DocumentUploadRequestDto();
			dto.setDocumentType(DocumentType.valueOf(types.get(i)));
			dto.setFile(files.get(i));

			requestDtos.add(dto);
		}
		LoanApplicationResponseDto response = loanApplicationService.uploadDocuments(applicationId, requestDtos);

		return ResponseEntity.ok(response);

	}
	
	@GetMapping("/{applicationId}")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_OFFICER')")
    public ResponseEntity<Object> getLoanApplicationById(@PathVariable Long applicationId, Authentication authentication) {
		LoanApplicationResponseDto applicationDTO = loanApplicationService.getApplicationById(applicationId, authentication);
        return ResponseEntity.ok(applicationDTO);
    }

}
