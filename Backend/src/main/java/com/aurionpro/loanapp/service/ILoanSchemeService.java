package com.aurionpro.loanapp.service;

import java.util.List;

import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeDto;
import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeRequestDto;
import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeResponseDto;

public interface ILoanSchemeService {
    LoanSchemeResponseDto createLoanScheme(LoanSchemeRequestDto schemeDto);
//    LoanSchemeDto updateLoanScheme(Long schemeId, LoanSchemeDto schemeDto);
//    LoanSchemeDto getLoanSchemeById(Long schemeId);
//    List<LoanSchemeDto> getAllLoanSchemes();
//    void deleteLoanScheme(Long schemeId);
}