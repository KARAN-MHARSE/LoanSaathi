package com.aurionpro.loanapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.loanapp.entity.LoanScheme;
import com.aurionpro.loanapp.entity.LoanType;

public interface LoanSchemeRepository extends JpaRepository<LoanScheme, Long> {
	Page<LoanScheme> findAll(Pageable pageable);
	Optional<LoanScheme> findBySchemeName(String schemeName);
	boolean existsBySchemeName(String schemeName);

	Optional<LoanScheme> findById(Long id);
	List<LoanScheme> findAllByLoanType(LoanType loanType);
}
