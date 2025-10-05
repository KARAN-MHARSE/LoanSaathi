package com.aurionpro.loanapp.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aurionpro.loanapp.repository.LoanInstallmentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoanPenaltyScheduler {
    private final LoanInstallmentRepository installmentRepository;
    
    @Scheduled(cron = "0 0 0 2 * ?")
    @Transactional
    public void generatePenalties() {
        LocalDateTime now = LocalDateTime.now();

    	
    }

}
