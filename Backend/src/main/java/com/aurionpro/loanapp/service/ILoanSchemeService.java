package com.aurionpro.loanapp.service;

import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeRequestDto;
import com.aurionpro.loanapp.dto.loanscheme.LoanSchemeResponseDto;
import com.aurionpro.loanapp.dto.page.PageResponseDto;

public interface ILoanSchemeService {
    LoanSchemeResponseDto createLoanScheme(LoanSchemeRequestDto schemeDto);
    LoanSchemeResponseDto updateLoanScheme(Long schemeId, LoanSchemeRequestDto schemeDto);
    LoanSchemeResponseDto getLoanSchemeById(Long schemeId);
    PageResponseDto<LoanSchemeResponseDto> getAllLoanSchemes(int pageNumber,int pageSize);
    void deleteLoanScheme(Long schemeId);
//    public EligibilityResponseDto checkEligibility(Long customerId, Long schemeId);

}