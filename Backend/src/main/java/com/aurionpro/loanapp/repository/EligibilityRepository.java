package com.aurionpro.loanapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.loanapp.entity.Eligibility;
import com.aurionpro.loanapp.property.EligibilityCriteria;

public interface EligibilityRepository extends JpaRepository<Eligibility, Long>{
	boolean existsByName(EligibilityCriteria elibilityName);

}
