package com.aurionpro.loanapp.service;

import com.aurionpro.loanapp.dto.officer.OfficerDashboardDto;

public interface IOfficerService {	
    OfficerDashboardDto getOfficerDashboard(String officerEmail);

}
