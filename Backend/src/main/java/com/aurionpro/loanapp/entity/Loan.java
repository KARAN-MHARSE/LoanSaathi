package com.aurionpro.loanapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.aurionpro.loanapp.property.LoanStatus;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private UUID loanNumber;
    @Column(nullable = false)
    private BigDecimal loanAmount;
    @Column(nullable = false)
    private BigDecimal interestRate;
    @Column(nullable = false)
    private Integer tenureMonths;
    
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @ManyToOne
    @JoinColumn(name = "loan_scheme_id")
    private LoanScheme loanScheme;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    private LocalDateTime createdAt;

}

