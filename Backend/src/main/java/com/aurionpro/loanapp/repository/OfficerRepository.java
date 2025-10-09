package com.aurionpro.loanapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.loanapp.entity.Officer;

import jakarta.validation.Valid;

public interface OfficerRepository extends JpaRepository<Officer, Long> {
	Optional<Officer> findByUserEmail(String officerEmail);
	
	boolean existsByUserEmail(String email);

	Optional<Officer> findByEmployeeId(@Valid String empId);
}
