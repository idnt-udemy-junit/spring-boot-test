package org.idnt.udemy.springboot.app.example.service;

import org.idnt.udemy.springboot.app.example.model.Account;

import java.math.BigDecimal;

public interface AccountService {
    Account findById(Long idAccount);
    int checkTotalTransactions(Long idBank);
    BigDecimal checkBalance(Long idAccount);
    void transfer(Long idBank, Long idAccountOrigin, Long idAccountTarget, BigDecimal quantity);
}
