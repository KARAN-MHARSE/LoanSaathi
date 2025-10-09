package com.aurionpro.loanapp.service;

import java.time.LocalDate;

import com.aurionpro.loanapp.dto.auth.RegisterRequestDto;
import com.aurionpro.loanapp.dto.officer.OfficerDashboardDto;

import jakarta.validation.Valid;

public interface IOfficerService {	
    OfficerDashboardDto getOfficerDashboard(String officerEmail);

	void addOfficer(@Valid RegisterRequestDto requestDto, LocalDate date);

	void removeOfficer(@Valid String empId);

}
