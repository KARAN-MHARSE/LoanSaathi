package com.aurionpro.loanapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aurionpro.loanapp.entity.Officer;

import jakarta.validation.Valid;

public interface OfficerRepository extends JpaRepository<Officer, Long> {
	Optional<Officer> findByUserEmail(String officerEmail);

	boolean existsByUserEmail(String email);

	Optional<Officer> findByEmployeeId(@Valid String empId);

	Page<Officer> findByIsActiveTrue(PageRequest of);

	@Query(value = "SELECT o.* FROM officers o " + "JOIN users u ON o.user_id = u.id " + "WHERE u.is_active = true "
			+ "ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Officer findRandomActiveOfficer();
}
