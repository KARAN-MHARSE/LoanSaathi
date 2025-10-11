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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="loan_applications")
//@ToString(exclude = {"customer", "loanOfficer", "documents"})
@Getter
@Setter
@NoArgsConstructor
public class LoanApplication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String applicationId;

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
	
//	Relations

	@ManyToOne(cascade= {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "assigned_officer_id")
	private Officer assignedOfficer;
	
	@ManyToOne
	@JoinColumn(name="loan_scheme_id")
	private LoanScheme loanScheme;

	@OneToMany(mappedBy = "loanApplication", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Document> documents;
}
