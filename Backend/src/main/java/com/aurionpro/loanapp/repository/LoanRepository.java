package com.aurionpro.loanapp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aurionpro.loanapp.entity.Loan;
import com.aurionpro.loanapp.property.LoanStatus;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByCustomerId(Long customerId);
    List<Loan> findByStatus(LoanStatus loanStatus);
    List<Loan> findAllByStatus(LoanStatus status);
    
    
}
