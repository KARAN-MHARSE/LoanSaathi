package com.aurionpro.loanapp.entity;

import com.aurionpro.loanapp.property.EligibilityCriteria;
import com.aurionpro.loanapp.property.Operator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Eligibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EligibilityCriteria name;
    private String description;
    private String value;

    @Enumerated(EnumType.STRING)  // Store as string in DB (more readable than ordinal)
    private Operator operator;
    
    @ManyToOne
    @JoinColumn(name = "loan_scheme_id")
    private LoanScheme loanScheme;
    
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;
    

}