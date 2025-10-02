package com.aurionpro.loanapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.loanapp.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>{

}
