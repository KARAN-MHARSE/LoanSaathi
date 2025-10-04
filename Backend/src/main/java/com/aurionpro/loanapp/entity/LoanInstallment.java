package com.aurionpro.loanapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.jsf.FacesContextUtils;

import com.aurionpro.loanapp.property.PaymentStatus;

@Entity
@Table(name="installments")
@Getter
@Setter
@NoArgsConstructor
public class LoanInstallment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer installmentNumber;
    
    @Column(nullable = false)
    private LocalDateTime dueDate;
    
    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private LocalDateTime paymentDate;
    
    private BigDecimal penaltyAmount;
    
    @CreationTimestamp
    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id",nullable = false)
    private Loan loan;
    
    @OneToMany(mappedBy = "installment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Penalty> penalties = new ArrayList<>();

}
