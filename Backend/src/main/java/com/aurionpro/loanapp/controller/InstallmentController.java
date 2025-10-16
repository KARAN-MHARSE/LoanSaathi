package com.aurionpro.loanapp.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.loanapp.dto.InstallmentResponseDto;
import com.aurionpro.loanapp.dto.payment.PaymentRequestDto;
import com.aurionpro.loanapp.dto.payment.PaymentResponseDto;
import com.aurionpro.loanapp.property.PaymentStatus;
import com.aurionpro.loanapp.service.IInstallmentService;
import com.aurionpro.loanapp.service.IPaymentService;
import com.stripe.exception.StripeException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/installments")
@RequiredArgsConstructor
public class InstallmentController {
    private final IInstallmentService installmentService;
    private final IPaymentService paymentService;

    @GetMapping("/{installmentId}")
    public ResponseEntity<InstallmentResponseDto> getInstallmentById(@PathVariable Long installmentId) {
        InstallmentResponseDto dto = installmentService.findInstallmentById(installmentId);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<InstallmentResponseDto>> getAllInstallmentsOfLoan(@PathVariable Long loanId) {
        List<InstallmentResponseDto> installments = installmentService.findAllInstallmentsOfLoan(loanId);
        return ResponseEntity.ok(installments);
    }

    @GetMapping("/loan/{loanId}/status")
    public ResponseEntity<List<InstallmentResponseDto>> getInstallmentsByStatus(
            @PathVariable Long loanId,
            @RequestParam PaymentStatus status) {

        List<InstallmentResponseDto> installments =
                installmentService.findInstallmentsByInstallmentStatusAndLoanId(loanId, status);

        return ResponseEntity.ok(installments);
    }

    @GetMapping("/customer/{customerId}/pending")
    public ResponseEntity<List<InstallmentResponseDto>> getPendingInstallmentsByCustomerId(@PathVariable Long customerId) {
        List<InstallmentResponseDto> installments = installmentService.findAllPendingInstallmentsByCustomerId(customerId);
        return ResponseEntity.ok(installments);
    }


    @PostMapping("/{installmentId}/pay")
    public ResponseEntity<PaymentResponseDto> payInstallment(
            @PathVariable Long installmentId,
            @RequestBody PaymentRequestDto paymentRequest) {
        
        try {
			return ResponseEntity.ok(paymentService.createPaymentIntent(paymentRequest));
		} catch (StripeException e) {
			throw new RuntimeException("Something went wrong");
		}
    }
    
    @PutMapping("/{installmentId}/status")
    public ResponseEntity<InstallmentResponseDto> updateInstallmentStatus(
            @PathVariable Long installmentId,
            @RequestParam PaymentStatus status) {

        InstallmentResponseDto dto = installmentService.updateInstallmentStatus(installmentId, status);
        return ResponseEntity.ok(dto);
    }
}
