package com.aurionpro.loanapp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.aurionpro.loanapp.property.LoanStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
    private Integer tenureMonths;
    
    @Column(nullable = false)
    private BigDecimal emiAmount; 
    
//    @Column(nullable=false)
//    private BigDecimal outstandingAmount;
    
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
    
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanInstallment> installments;
    
    @CreationTimestamp
    private LocalDateTime createdAt; 
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
//    @PrePersist
//    public void setOutstandingAmountBeforeSave() {
//        // If not already set, calculate total payable amount
//        if (emiAmount != null && tenureMonths != null) {
//            this.outstandingAmount = emiAmount.multiply(BigDecimal.valueOf(tenureMonths));
//        } else if (loanAmount != null) {
//
//            this.outstandingAmount = loanAmount;
//        }
//    }
//    
//    public void makePayment(BigDecimal paymentAmount) {
//        this.outstandingAmount = this.outstandingAmount.subtract(paymentAmount);
//    }

}

