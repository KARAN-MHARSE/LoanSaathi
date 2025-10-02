package com.aurionpro.loanapp.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer {
	@Id
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name="user_id")
	private User user;
	
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN number format.")
    @Column(unique = true, length = 10)
	private String panNumber;
    
    @Pattern(regexp = "^[2-9]{1}[0-9]{3}[0-9]{4}[0-9]{4}$", message = "Invalid Aadhaar number format.")
    @Column(unique = true, length = 12)
    private String aadhaarNumber;
    
   
    private String occupation;
	
    private BigDecimal annualIncome;
    
//    Relations    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<BankAccount> bankAccounts;

    
    @OneToMany(mappedBy = "customer")
    private List<LoanApplication> loanApplications;
    
    @OneToMany(mappedBy = "customer")
    private List<Loan> activeLoans;

	
}
