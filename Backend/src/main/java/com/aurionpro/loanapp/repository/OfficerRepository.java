package com.aurionpro.loanapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.loanapp.entity.Officer;

public interface OfficerRepository extends JpaRepository<Officer, Long> {

}
