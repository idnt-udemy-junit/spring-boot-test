package org.idnt.udemy.springboot.app.example.service;

import org.idnt.udemy.springboot.app.example.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    Account findById(Long idAccount);
    int checkTotalTransactions(Long idBank);
    BigDecimal checkBalance(Long idAccount);
    void transfer(Long idBank, Long idAccountOrigin, Long idAccountTarget, BigDecimal quantity);
    List<Account> findAll();
    Account save(Account account);
    void deleteById(Long id);
}
