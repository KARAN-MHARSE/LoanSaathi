package com.aurionpro.loanapp.service;

import java.util.List;

import com.aurionpro.loanapp.dto.BankAccountDto;

public interface IBankAccountService {
    BankAccountDto addBankAccount(BankAccountDto bankAccountDto);
    List<BankAccountDto> getBankAccountsForCurrentUser();
    void deleteBankAccount(Long accountId);
}