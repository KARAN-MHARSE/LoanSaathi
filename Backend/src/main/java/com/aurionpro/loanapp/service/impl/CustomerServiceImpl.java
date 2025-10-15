package com.aurionpro.loanapp.service.impl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.customer.CustomerDashboard;
import com.aurionpro.loanapp.dto.customer.CustomerProfileRequestDto;
import com.aurionpro.loanapp.dto.customer.CustomerProfileResponseDto;
import com.aurionpro.loanapp.entity.Customer;
import com.aurionpro.loanapp.entity.Loan;
import com.aurionpro.loanapp.entity.LoanApplication;
import com.aurionpro.loanapp.entity.LoanInstallment;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.exception.ResourceNotFoundException;
import com.aurionpro.loanapp.exception.UserNotFoundException;
import com.aurionpro.loanapp.property.LoanApplicationStatus;
import com.aurionpro.loanapp.property.LoanStatus;
import com.aurionpro.loanapp.property.PaymentStatus;
import com.aurionpro.loanapp.repository.CustomerRepository;
import com.aurionpro.loanapp.repository.UserRepository;
import com.aurionpro.loanapp.service.ICustomerService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
	private final UserRepository userRepository;
	private final CustomerRepository customerRepository;
	private final ModelMapper mapper;

	@Override
	@Transactional
	public CustomerProfileResponseDto createCustomerProfile(CustomerProfileRequestDto requestDto, String userEmail) {
		User user = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new UserNotFoundException("User not found with email id " + userEmail));

		if (customerRepository.existsById(user.getId())) {
			throw new IllegalStateException("Customer profile already exists for this user.");
		}

		Customer customer = mapper.map(requestDto, Customer.class);
		customer.setUser(user);

		Customer savedCustomer = customerRepository.save(customer);

		return mapper.map(savedCustomer, CustomerProfileResponseDto.class);

	}

	@Override
	public CustomerDashboard viewCustomerDashboard(String email) {

		Customer customer = customerRepository.findByUserEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Customer not found with email id " + email));

		List<LoanApplication> applications = customer.getLoanApplications();
		long totalApplications = applications.size();
		long pendingApplications = applications.stream()
				.filter(a -> a.getApplicationStatus().equals(LoanApplicationStatus.PENDING)).count();
		long approvedApplications = applications.stream()
				.filter(a -> a.getApplicationStatus().equals(LoanApplicationStatus.APPROVED)).count();
		long rejectedApplications = applications.stream()
				.filter(a -> a.getApplicationStatus().equals(LoanApplicationStatus.REJECTED)).count();

		List<Loan> loans = customer.getActiveLoans();

		BigDecimal totalLoanAmount = BigDecimal.ZERO;
		BigDecimal totalPaidAmount = BigDecimal.ZERO;
		LoanInstallment nextEMI = null;
		int activeLoans = 0;
		int closedLoans = 0;

		if (loans != null && !loans.isEmpty()) {
			activeLoans = (int) loans.stream().filter(loan -> loan.getStatus() == LoanStatus.ACTIVE).count();
			closedLoans = (int) loans.stream().filter(loan -> loan.getStatus() == LoanStatus.CLOSED).count();

			for (Loan loan : loans) {
				if (loan.getLoanAmount() != null) {
					totalLoanAmount = totalLoanAmount.add(loan.getLoanAmount());
				}

				// Calculate total paid amount from paid installments
				BigDecimal paidForThisLoan = loan.getInstallments().stream()
						.filter(inst -> inst.getStatus() == PaymentStatus.PAID && inst.getAmount() != null)
						.map(LoanInstallment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
				totalPaidAmount = totalPaidAmount.add(paidForThisLoan);

				// Find the earliest upcoming unpaid installment for the next EMI
				Optional<LoanInstallment> upcomingEMIForThisLoan = loan.getInstallments().stream()
						.filter(inst -> inst.getStatus() == PaymentStatus.PENDING)
						.min(Comparator.comparing(LoanInstallment::getDueDate));

				if (upcomingEMIForThisLoan.isPresent()) {
					if (nextEMI == null || upcomingEMIForThisLoan.get().getDueDate().isBefore(nextEMI.getDueDate())) {
						nextEMI = upcomingEMIForThisLoan.get();
					}
				}
			}
		}

		BigDecimal totalOutstandingAmount = totalLoanAmount.subtract(totalPaidAmount);

		CustomerDashboard dashboard = new CustomerDashboard();

		dashboard.setFirstName(customer.getUser().getFirstName());
		dashboard.setLastName(customer.getUser().getLastName());
		dashboard.setEmail(customer.getUser().getEmail());
		dashboard.setPhoneNumber(customer.getUser().getPhoneNumber());
		dashboard.setDateOfBirth(customer.getUser().getDateOfBirth());
		dashboard.setProfileUrl(customer.getUser().getProfileUrl());

		dashboard.setTotalApplications((int) totalApplications);
		dashboard.setPendingApplications((int) pendingApplications);
		dashboard.setApprovedApplications((int) approvedApplications);
		dashboard.setRejectedApplications((int) rejectedApplications);

		dashboard.setTotalLoanAmount(totalLoanAmount.doubleValue());
		dashboard.setTotalOutstandingAmount(totalOutstandingAmount.doubleValue());
		dashboard.setTotalPaidAmount(totalPaidAmount.doubleValue());
		dashboard.setActiveLoans(activeLoans);
		dashboard.setClosedLoans(closedLoans);

		if (nextEMI != null) {
			dashboard.setNextEMIAmount(nextEMI.getAmount().doubleValue());
			dashboard.setNextEMIDueDate(nextEMI.getDueDate().toLocalDate());
		}

		return dashboard;

	}

}
