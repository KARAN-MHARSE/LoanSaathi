package com.aurionpro.loanapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.loanapp.entity.LoanApplication;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.property.LoanApplicationStatus;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
	List<LoanApplication> findByCustomer(User applicant);

	Page<LoanApplication> findByAssignedOfficerId(Long officerId, Pageable pageable);

//    Page<LoanApplication> findAllByApplicationStatus(LoanApplicationStatus status, Pageable pageable);
}