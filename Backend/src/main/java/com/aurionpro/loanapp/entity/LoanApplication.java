package com.aurionpro.loanapp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.aurionpro.loanapp.property.LoanApplicationStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class LoanApplication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private BigDecimal requiredAmount;
	
	@Column(nullable = false)
	private BigDecimal annualIncome;
	
	private String occupation;
	
//	@Column(nullable = false)
	private String reason;
	
	@Column(nullable = false)
	private int tenure;
	
	@Column(nullable = false)
	private LoanApplicationStatus applicationStatus;
	
	@CreationTimestamp
	@Column(nullable = false,updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private User user;
	
//	@ManyToOne
//	@Column(name = "assigned_officer_id")
//	private User assignedOfficer;
	
	@ManyToOne
	@JoinColumn(name="loan_scheme_id")
	private LoanScheme loanScheme;

	@OneToMany(mappedBy = "loanApplication", cascade = CascadeType.ALL)
	private List<Document> documents;
}
