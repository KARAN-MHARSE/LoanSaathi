package com.aurionpro.loanapp.service;

import java.util.List;

import com.aurionpro.loanapp.dto.emi.EmiCalculationRequestDto;
import com.aurionpro.loanapp.dto.emi.EmiCalculationResponseDto;
import com.aurionpro.loanapp.dto.loan.LoanResponseDto;

public interface ILoanService {
	
    List<LoanResponseDto> getLoansForCurrentUser(Long customerId);
    LoanResponseDto getLoanDetails(Long loanId);
    byte[] generateLoanStatement(Long loanId); // Returns PDF bytes
//    void processRepayment(Long loanId, RepaymentRequestDto repaymentRequestDto);
//      List<InstallmentDto> getRepaymentSchedule(String loanId);
//      void requestLoanClosure(String loanId);
    EmiCalculationResponseDto calculateEMI(EmiCalculationRequestDto calculationRequestDto, Long loanSchemeId);
}