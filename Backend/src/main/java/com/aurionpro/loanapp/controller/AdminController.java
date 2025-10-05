package com.aurionpro.loanapp.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.loanapp.dto.auth.RegisterRequestDto;
import com.aurionpro.loanapp.dto.eligibility.EligibilityRequestDto;
import com.aurionpro.loanapp.service.IEligibilityService;
import com.aurionpro.loanapp.service.IOfficerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

	@Autowired
	IOfficerService officerService;
	@Autowired
	IEligibilityService eligibilityService;
	
	//Add Officer
	@PostMapping("/add/officer")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> addOfficer(
			@RequestBody @Valid RegisterRequestDto requestDto, LocalDate date
			){
		
		officerService.addOfficer(requestDto,date);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	//remove officer
	@PostMapping("/remove/officer")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> removeOfficer(@RequestParam @Valid String empId){
		officerService.removeOfficer(empId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	//add eligibility for loan scheme
	@PostMapping("/loanscheme/eligibility")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> addEligibility(@RequestBody @Valid EligibilityRequestDto eligibilityDto){
		eligibilityService.addEligibility(eligibilityDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PutMapping("/loanscheme/eligibility/{id}/deactivate")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> deactivateEligibility(@PathVariable @Valid Long eligibilityId) {
	    eligibilityService.removeEligibility(eligibilityId);
	    return ResponseEntity.ok().build(); // 200 OK
	}

}
