package com.aurionpro.loanapp.scheduler;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aurionpro.loanapp.dto.EmailDto;
import com.aurionpro.loanapp.entity.LoanInstallment;
import com.aurionpro.loanapp.entity.Penalty;
import com.aurionpro.loanapp.property.PaymentStatus;
import com.aurionpro.loanapp.repository.LoanInstallmentRepository;
import com.aurionpro.loanapp.service.EmailService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoanInstallmentEmailScheduler {
	private final LoanInstallmentRepository installmentRepository;
	private final EmailService emailService;

	@Scheduled(cron = "0 5 0 20 * ?")
	public void sendInstallmentEmail() {
		List<LoanInstallment> pendingInstallments = installmentRepository.findAllByStatus(PaymentStatus.PENDING);

		for (LoanInstallment installment : pendingInstallments) {
			sendEmail(installment);
		}
	}

	@Async
	private void sendEmail(LoanInstallment installment) {
		String to = installment.getLoan().getCustomer().getUser().getEmail();
		String subject = "Monthly Loan Installment Reminder";
		BigDecimal installmentAmount = installment.getAmount();
		BigDecimal totalPenalty = BigDecimal.ZERO;

		if (installment.getPenalties() != null && !installment.getPenalties().isEmpty()) {
			for (Penalty p : installment.getPenalties()) {
				totalPenalty = totalPenalty.add(p.getAmount());
			}
		}

		StringBuilder bodyBuilder = new StringBuilder();
		bodyBuilder.append("Dear ").append(installment.getLoan().getCustomer().getUser().getFirstName()).append(",\n\n");
		bodyBuilder.append("This is a reminder for your upcoming loan installment.\n");
		bodyBuilder.append("Installment Amount: ₹").append(installmentAmount).append("\n");

		if (totalPenalty.compareTo(BigDecimal.ZERO) > 0) {
			bodyBuilder.append("Pending Penalty: ₹").append(totalPenalty).append("\n");
		}

		bodyBuilder.append("Due Date: ").append(installment.getDueDate().toLocalDate()).append("\n\n");
		bodyBuilder.append("Please ensure timely payment to avoid additional penalties.\n");
		bodyBuilder.append("Thank you,\n");
		bodyBuilder.append("Your Bank/Loan Team");

		EmailDto emailDto = new EmailDto();
		emailDto.setTo(to);
		emailDto.setSubject(subject);
		emailDto.setBody(bodyBuilder.toString());

		emailService.sendEmailWithoutImage(emailDto);
	}

}
