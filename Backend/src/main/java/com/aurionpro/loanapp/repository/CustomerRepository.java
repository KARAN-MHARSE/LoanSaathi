package com.aurionpro.loanapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.loanapp.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	boolean existsById(Long userId);
	Optional<Customer> findByUserEmail(String email);
}
