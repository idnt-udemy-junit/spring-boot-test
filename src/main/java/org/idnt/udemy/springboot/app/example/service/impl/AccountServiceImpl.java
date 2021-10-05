package org.idnt.udemy.springboot.app.example.service.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.idnt.udemy.springboot.app.example.model.Account;
import org.idnt.udemy.springboot.app.example.model.Bank;
import org.idnt.udemy.springboot.app.example.repository.AccountRepository;
import org.idnt.udemy.springboot.app.example.repository.BankRepository;
import org.idnt.udemy.springboot.app.example.service.AccountService;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private BankRepository bankRepository;

    @Override
    public Account findById(final Long id) {
        return this.accountRepository.findByid(id);
    }

    @Override
    public int checkTotalTransactions(final Long id) {
        Bank bank = this.bankRepository.findByid(id);
        return bank.getTotalTransactions();
    }

    @Override
    public BigDecimal checkBalance(final Long id) {
        Account account = this.accountRepository.findByid(id);
        return account.getBalance();
    }

    @Override
    public void transfer(final Long idOrigin, final Long idTarget, final BigDecimal quantity) {
        Bank bank = this.bankRepository.findByid(1L);
        int totalTransactions = bank.getTotalTransactions();
        bank.setTotalTransactions(++totalTransactions);
        this.bankRepository.update(bank);

        Account accountOrigin = this.accountRepository.findByid(idOrigin);
        accountOrigin.debit(quantity);
        this.accountRepository.update(accountOrigin);

        Account accountTarget = this.accountRepository.findByid(idTarget);
        accountTarget.credit(quantity);
        this.accountRepository.update(accountTarget);
    }
}
