package com.aurionpro.loanapp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.jsf.FacesContextUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="loan_schemes")
@Getter
@Setter
@NoArgsConstructor
public class LoanScheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 100)
    private String schemeName;
   
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanType loanType;
    
    @Column(nullable = false)
    private BigDecimal minLoanAmount;
    
    @Column(nullable = false)
    private BigDecimal maxLoanAmount;
    
    @Column(nullable = false)
    private BigDecimal interestRate;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private int minTenureMonths; 

    @Column(nullable = false)
    private int maxTenureMonths; 
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    
//    Relations
    @OneToMany(mappedBy = "loanScheme")
    private List<LoanApplication> loanApplications;
    
    @OneToMany(mappedBy = "loanScheme")
    private List<Loan> loans;

    @OneToMany(mappedBy = "loanScheme",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Eligibility> eligibilities;

}

