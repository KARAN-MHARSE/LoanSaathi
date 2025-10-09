package com.aurionpro.loanapp.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super.Instantiation;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.InstallmentResponseDto;
import com.aurionpro.loanapp.entity.Customer;
import com.aurionpro.loanapp.entity.Loan;
import com.aurionpro.loanapp.entity.LoanInstallment;
import com.aurionpro.loanapp.exception.ResourceNotFoundException;
import com.aurionpro.loanapp.property.PaymentStatus;
import com.aurionpro.loanapp.repository.CustomerRepository;
import com.aurionpro.loanapp.repository.LoanInstallmentRepository;
import com.aurionpro.loanapp.repository.LoanRepository;
import com.aurionpro.loanapp.service.IInstallmentService;
import com.stripe.model.PaymentSourceTypeAdapterFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstallmentServiceImpl implements IInstallmentService {
	private final LoanInstallmentRepository installmentRepository;
	private final LoanRepository loanRepository;
	private final CustomerRepository customerRepository;
	private final ModelMapper mapper;

	@Override
	public InstallmentResponseDto findInstallmentById(Long installmentId) {
		LoanInstallment installment = installmentRepository.findById(installmentId)
				.orElseThrow(()-> new ResourceNotFoundException("Installment not found with id" +installmentId));
		
		return mapper.map(installment, InstallmentResponseDto.class);
	}

	@Override
	public InstallmentResponseDto updateInstallmentStatus(Long installmentId, PaymentStatus status) {
		LoanInstallment installment = installmentRepository.findById(installmentId)
				.orElseThrow(()-> new ResourceNotFoundException("Installment not found with id" +installmentId));
		
		installment.setStatus(status);
		installment.setPaymentDate(LocalDateTime.now());
		LoanInstallment savedInstallment = installmentRepository.save(installment);
		return mapper.map(savedInstallment, InstallmentResponseDto.class);
	}

	@Override
	public List<InstallmentResponseDto> findInstallmentsByInstallmentStatusAndLoanId(Long loanId,PaymentStatus status) {
		Loan loan = loanRepository.findById(loanId)
				.orElseThrow(()-> new ResourceNotFoundException("Loan not found with id" +loanId));
		
		List<InstallmentResponseDto> filteredInstallments = loan.getInstallments().stream()
				.filter(installment-> installment.getStatus().equals(status))
				.map(installment-> mapper.map(installment,InstallmentResponseDto.class))
				.collect(Collectors.toList());

		return filteredInstallments;

	}

	@Override
	public List<InstallmentResponseDto> findAllInstallmentsOfLoan(Long loanId) {
		Loan loan = loanRepository.findById(loanId)
				.orElseThrow(()-> new ResourceNotFoundException("Loan not found with id" +loanId));
		
	    List<LoanInstallment> existingInstallments = new ArrayList<>(loan.getInstallments());

	    int lastInstallmentNumber = 0;
	    LocalDate lastDueDate = LocalDate.of(loan.getCreatedAt().getYear(), loan.getCreatedAt().getMonth(), 2);
	    
	    if (!existingInstallments.isEmpty()) {
	        LoanInstallment lastInstallment = existingInstallments.stream()
	                .max(Comparator.comparing(LoanInstallment::getInstallmentNumber))
	                .get(); // We know it's present due to the !isEmpty check
	        lastInstallmentNumber = lastInstallment.getInstallmentNumber();
	        lastDueDate = lastInstallment.getDueDate().toLocalDate();
	    }
	    
	    if (lastInstallmentNumber >= loan.getTenureMonths()) {
	        // If yes, just return the existing list mapped to DTOs.
	        return existingInstallments.stream()
	                .map(i -> mapper.map(i, InstallmentResponseDto.class))
	                .collect(Collectors.toList());
	    }
	    
	    List<LoanInstallment> newlyGeneratedInstallments = new ArrayList<>();
	    LocalDate nextDueDate = lastDueDate.plusMonths(1);

	    for (int i = lastInstallmentNumber + 1; i <= loan.getTenureMonths(); i++) {
	        LoanInstallment installment = new LoanInstallment();
	        installment.setLoan(loan); // Associate with the parent loan
	        installment.setAmount(loan.getEmiAmount());
	        installment.setInstallmentNumber(i);
	        installment.setDueDate(nextDueDate.atTime(11, 59));
	        installment.setStatus(PaymentStatus.PENDING);
	        installment.setCreatedAt(LocalDateTime.now()); // Use current time for creation
	        
	        newlyGeneratedInstallments.add(installment);

	        nextDueDate = nextDueDate.plusMonths(1);
	    }
	    
	    List<LoanInstallment> allInstallments = new ArrayList<>(existingInstallments);
	    allInstallments.addAll(newlyGeneratedInstallments);

	    return allInstallments.stream()
	    	    .sorted(Comparator.comparing(LoanInstallment::getInstallmentNumber))
	            .map(i -> mapper.map(i, InstallmentResponseDto.class))
	            .collect(Collectors.toList());
	}

	@Override
	public List<InstallmentResponseDto> findAllPendingInstallmentsByCustomerId(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(()-> new ResourceNotFoundException("Customer not found with id" +customerId));

		List<LoanInstallment> pendingLoanInstallments = new ArrayList<>();
		
		for(Loan loan : customer.getActiveLoans()) {
			List<LoanInstallment> installments = loan.getInstallments().stream()
					.filter(i->i.getStatus().equals(PaymentStatus.PENDING))
					.collect(Collectors.toList());
			
			pendingLoanInstallments.addAll(installments);
		}
		return pendingLoanInstallments.stream()
				.map(i-> mapper.map(i,InstallmentResponseDto.class))
				.collect(Collectors.toList());
	}
	
	
}
