package com.aurionpro.loanapp.scheduler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aurionpro.loanapp.entity.Loan;
import com.aurionpro.loanapp.entity.LoanInstallment;
import com.aurionpro.loanapp.property.LoanStatus;
import com.aurionpro.loanapp.property.PaymentStatus;
import com.aurionpro.loanapp.repository.LoanInstallmentRepository;
import com.aurionpro.loanapp.repository.LoanRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoanInstallmentScheduler {
	private final LoanRepository loanRepository;
	private final LoanInstallmentRepository installmentRepository;

	@Scheduled(cron = "0 0 0 20 * ?")
	@Transactional
	public void generateMonthlyInstallment() {
		System.out.println("Start ins");
		List<Loan> activeLoans = loanRepository.findAllByStatus(LoanStatus.ACTIVE);
		
		for (Loan loan : activeLoans) {
			System.out.println(loan);
			LocalDateTime nextDueDate = getNextInstallmentDate(loan);

			if (nextDueDate.isBefore(loan.getCreatedAt().plusMonths(loan.getTenureMonths()))) {
				boolean exists = installmentRepository.existsByLoanAndDueDate(loan, nextDueDate);

                if (!exists && loan.getInstallments().size() < loan.getTenureMonths()) {
                	Optional<Integer> lastEMiNumber = loan.getInstallments().stream()
                			.map(LoanInstallment::getInstallmentNumber)
                			.max(Comparator.naturalOrder());
                	
					LoanInstallment installment = new LoanInstallment();
					installment.setLoan(loan);
					installment.setAmount(loan.getEmiAmount());
					installment.setDueDate(nextDueDate);
					installment.setStatus(PaymentStatus.PENDING);
					installment.setPenaltyAmount(BigDecimal.ZERO);
					
					if(lastEMiNumber.isPresent()){
						installment.setInstallmentNumber(lastEMiNumber.get()+1);
					}else {
						installment.setInstallmentNumber(1);
					}

					installmentRepository.save(installment);
				}

			}

		}
	}

	private LocalDateTime getNextInstallmentDate(Loan loan) {
		LocalDateTime lastDueDate = installmentRepository.findLastDueDateByLoan(loan)
				.orElse(loan.getStartDate().minusMonths(1));
		
        LocalDate nextMonth = lastDueDate.toLocalDate().plusMonths(1);
        
        LocalDate dueDate = LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), 2);


		return dueDate.atStartOfDay();

	}
}
