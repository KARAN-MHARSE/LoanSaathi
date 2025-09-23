package com.aurionpro.loanapp.service;

import com.aurionpro.loanapp.dto.dashboard.AdminDashboardDto;
import com.aurionpro.loanapp.dto.dashboard.OfficerDashboardDto;
import com.aurionpro.loanapp.dto.dashboard.PartnerDashboardDto;

public interface IDashboardService {
    AdminDashboardDto getAdminDashboardData();
    OfficerDashboardDto getOfficerDashboardData();
    PartnerDashboardDto getPartnerDashboardData();
}