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
    public Account findById(final Long idAccount) {
        return this.accountRepository.findById(idAccount);
    }

    @Override
    public int checkTotalTransactions(final Long idBank) {
        Bank bank = this.bankRepository.findById(idBank);
        return bank.getTotalTransactions();
    }

    @Override
    public BigDecimal checkBalance(final Long idAccount) {
        Account account = this.accountRepository.findById(idAccount);
        return account.getBalance();
    }

    @Override
    public void transfer(final Long idBank, final Long idAccountOrigin, final Long idAccountTarget, final BigDecimal quantity) {
        Account accountOrigin = this.accountRepository.findById(idAccountOrigin);
        accountOrigin.debit(quantity);
        this.accountRepository.update(accountOrigin);

        Account accountTarget = this.accountRepository.findById(idAccountTarget);
        accountTarget.credit(quantity);
        this.accountRepository.update(accountTarget);

        Bank bank = this.bankRepository.findById(idBank);
        int totalTransactions = bank.getTotalTransactions();
        bank.setTotalTransactions(++totalTransactions);
        this.bankRepository.update(bank);
    }
}
