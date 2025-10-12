package com.aurionpro.loanapp.service;

import com.aurionpro.loanapp.dto.auth.RegisterRequestDto;
import com.aurionpro.loanapp.dto.auth.RegisterResponseDto;
import com.aurionpro.loanapp.dto.officer.OfficerDashboardDto;

import jakarta.validation.Valid;

public interface IOfficerService {	
    OfficerDashboardDto getOfficerDashboard(String officerEmail);

    RegisterResponseDto addOfficer(@Valid RegisterRequestDto requestDto);

	void removeOfficer(@Valid String empId);

}
