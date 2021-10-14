package org.idnt.udemy.springboot.app.example.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.idnt.udemy.springboot.app.example.model.Account;
import org.idnt.udemy.springboot.app.example.model.Bank;
import org.idnt.udemy.springboot.app.example.repository.AccountRepository;
import org.idnt.udemy.springboot.app.example.repository.BankRepository;
import org.idnt.udemy.springboot.app.example.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private BankRepository bankRepository;

    @Override
    @Transactional(readOnly = true)
    public Account findById(final Long idAccount) {
        return this.accountRepository.findById(idAccount).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public int checkTotalTransactions(final Long idBank) {
        Bank bank = this.bankRepository.findById(idBank).orElseThrow();
        return bank.getTotalTransactions();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal checkBalance(final Long idAccount) {
        Account account = this.accountRepository.findById(idAccount).orElseThrow();
        return account.getBalance();
    }

    @Override
    @Transactional
    public void transfer(final Long idBank, final Long idAccountOrigin, final Long idAccountTarget, final BigDecimal quantity) {
        Account accountOrigin = this.accountRepository.findById(idAccountOrigin).orElseThrow();
        accountOrigin.debit(quantity);
        this.accountRepository.save(accountOrigin);

        Account accountTarget = this.accountRepository.findById(idAccountTarget).orElseThrow();
        accountTarget.credit(quantity);
        this.accountRepository.save(accountTarget);

        Bank bank = this.bankRepository.findById(idBank).orElseThrow();
        int totalTransactions = bank.getTotalTransactions();
        bank.setTotalTransactions(++totalTransactions);
        this.bankRepository.save(bank);
    }

    @Override
    public List<Account> findAll() {
        return this.accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        return null;
    }
}
