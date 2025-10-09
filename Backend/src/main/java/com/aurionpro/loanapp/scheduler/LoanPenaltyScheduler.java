package com.aurionpro.loanapp.scheduler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.http.entity.AbstractHttpEntity;
import org.springframework.mail.MailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aurionpro.loanapp.dto.EmailDto;
import com.aurionpro.loanapp.entity.LoanInstallment;
import com.aurionpro.loanapp.entity.Penalty;
import com.aurionpro.loanapp.property.PaymentStatus;
import com.aurionpro.loanapp.repository.LoanInstallmentRepository;
import com.aurionpro.loanapp.service.EmailService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoanPenaltyScheduler {
	private final LoanInstallmentRepository installmentRepository;
	private final EmailService emailService;

	@Scheduled(cron = "0 1 0 3 * ?")
	@Transactional
	public void generatePenalties() {
		List<LoanInstallment> pendingLoanInstallments = installmentRepository.findAllByStatus(PaymentStatus.PENDING);

		for (LoanInstallment pending : pendingLoanInstallments) {
			if (pending.getDueDate().isBefore(LocalDateTime.now())) {
				Penalty penalty = new Penalty();
				penalty.setAmount(BigDecimal.valueOf(300));
				penalty.setAppliedDate(LocalDateTime.now());
				penalty.setInstallment(pending);
				penalty.setReason("Installement not paid on time");

				pending.getPenalties().add(penalty);

				installmentRepository.save(pending);

				sendMail(pending);

			}

		}

	}

	private void sendMail(LoanInstallment pending) {
		String to = pending.getLoan().getCustomer().getUser().getEmail();
	    String subject = "Penalty Applied for Unpaid Installment";
	    String body = String.format(
	            "Dear %s,\n\nA penalty of ₹300 has been applied to your unpaid installment due on %s for Loan #%s.\n" +
	            "Please clear your dues at the earliest to avoid further penalties.\n\nThank you,\nLoan Department",
	            pending.getLoan().getCustomer().getUser().getFirstName(),
	            pending.getDueDate().toLocalDate(),
	            pending.getLoan().getLoanNumber()
	        );
	    
		EmailDto emailDto = new EmailDto();
		emailDto.setTo(to);
		emailDto.setBody(body);
		emailDto.setSubject(subject);
		emailService.sendEmailWithoutImage(emailDto);

	}

}
