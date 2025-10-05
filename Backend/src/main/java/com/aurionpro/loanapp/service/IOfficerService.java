package com.aurionpro.loanapp.service;

import com.aurionpro.loanapp.dto.officer.OfficerDashboardDto;
import com.aurionpro.loanapp.dto.officer.OfficerRequestDto;

import jakarta.validation.Valid;

public interface IOfficerService {	
    OfficerDashboardDto getOfficerDashboard(String officerEmail);

	void addOfficer(@Valid OfficerRequestDto requestDto);

	void removeOfficer(@Valid String empId);

}
