package com.aurionpro.loanapp.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.aurionpro.loanapp.property.DocumentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "loan_application_id")
	private LoanApplication loanApplication;

	@Enumerated(EnumType.STRING)
	private DocumentType type;

	@Column(name="document_url",nullable = false)
	private String url;
	
	@Column(nullable = false)
	private String name;
	
	@CreationTimestamp
	private LocalDateTime uploadedAt;
}
