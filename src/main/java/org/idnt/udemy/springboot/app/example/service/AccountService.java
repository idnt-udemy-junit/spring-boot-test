package org.idnt.udemy.springboot.app.example.service;

import org.idnt.udemy.springboot.app.example.model.Account;

import java.math.BigDecimal;

public interface AccountService {
    Account findById(Long id);
    int checkTotalTransactions(Long id);
    BigDecimal checkBalance(Long id);
    void transger(Long idOrigin, Long idTarget, BigDecimal quantity);
}
