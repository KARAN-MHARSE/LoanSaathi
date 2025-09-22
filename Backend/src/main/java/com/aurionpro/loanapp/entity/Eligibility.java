package com.aurionpro.loanapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Eligibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String value;

    @ManyToOne
    @JoinColumn(name = "loan_scheme_id")
    private LoanScheme loanScheme;
}
