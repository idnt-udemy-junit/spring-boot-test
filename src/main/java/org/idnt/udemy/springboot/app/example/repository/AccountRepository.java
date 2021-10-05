package org.idnt.udemy.springboot.app.example.repository;

import org.idnt.udemy.springboot.app.example.model.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();
    Account findById(Long id);
    void update(Account account);
}
