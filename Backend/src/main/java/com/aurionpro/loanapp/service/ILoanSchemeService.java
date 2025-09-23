package com.aurionpro.loanapp.service;

import java.util.List;

import com.aurionpro.loanapp.dto.LoanSchemeDto;

public interface ILoanSchemeService {
    LoanSchemeDto createLoanScheme(LoanSchemeDto schemeDto);
    LoanSchemeDto updateLoanScheme(Long schemeId, LoanSchemeDto schemeDto);
    LoanSchemeDto getLoanSchemeById(Long schemeId);
    List<LoanSchemeDto> getAllLoanSchemes();
    void deleteLoanScheme(Long schemeId);
}