package com.aurionpro.loanapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.loanapp.entity.Eligibility;

public interface EligibilityRepository extends JpaRepository<Eligibility, Long>{

}
