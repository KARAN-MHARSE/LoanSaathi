package com.aurionpro.loanapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.loanapp.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByCustomerId(Long customerId);
    List<BankAccount> findByAccountHolderName(String holderName);
    Optional<BankAccount> findByCustomerIdAndIsPrimaryTrue(Long customerId);
}
