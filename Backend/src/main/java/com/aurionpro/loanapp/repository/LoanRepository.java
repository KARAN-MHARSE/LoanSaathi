package com.aurionpro.loanapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.loanapp.entity.Loan;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.property.LoanStatus;

public interface LoanRepository extends JpaRepository<Loan, Long> {
	Optional<Loan> findById(Long loanId);

    List<Loan> findByUserId(User borrower);
    List<Loan> findByStatus(LoanStatus loanStatus);
}
