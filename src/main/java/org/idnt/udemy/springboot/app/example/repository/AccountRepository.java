package org.idnt.udemy.springboot.app.example.repository;

import org.idnt.udemy.springboot.app.example.model.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();
    Account findByid(Long id);
    void update(Account account);
}
