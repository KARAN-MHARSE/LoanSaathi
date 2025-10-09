package com.aurionpro.loanapp.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.aurionpro.loanapp.dto.InstallmentResponseDto;
import com.aurionpro.loanapp.dto.payment.PaymentResponseDto;
import com.aurionpro.loanapp.property.PaymentStatus;

public interface IInstallmentService {
	InstallmentResponseDto findInstallmentById(Long installmentId);
	InstallmentResponseDto updateInstallmentStatus(Long installmentId,PaymentStatus status);
	public List<InstallmentResponseDto> findInstallmentsByInstallmentStatusAndLoanId(Long loanId,PaymentStatus status);
	List<InstallmentResponseDto> findAllInstallmentsOfLoan(Long loanId);
	List<InstallmentResponseDto> findAllPendingInstallmentsByCustomerId(Long customerId);

}
