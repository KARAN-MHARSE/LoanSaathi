package com.aurionpro.loanapp.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "officers")
@Getter
@Setter
@NoArgsConstructor
public class Officer {
	@Id
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name="user_id")
	private User user;
	
    @Column(nullable = false, unique = true, length = 20)
    private String employeeId;
    
    @Column(nullable = false)
    private LocalDate hireDate = LocalDate.now();
    
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean isActive = true;

    @OneToMany(mappedBy = "assignedOfficer")
    List<LoanApplication> loanApplications;
    

}
