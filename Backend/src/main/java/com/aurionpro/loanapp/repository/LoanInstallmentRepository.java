package com.aurionpro.loanapp.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aurionpro.loanapp.entity.Loan;
import com.aurionpro.loanapp.entity.LoanInstallment;

public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {
    boolean existsByLoanAndDueDate(Loan loan, LocalDateTime dueDate);

	@Query("SELECT MAX(i.dueDate) FROM LoanInstallment i WHERE i.loan = :loan")
    Optional<LocalDateTime> findLastDueDateByLoan(@Param("loan") Loan loan);
}
