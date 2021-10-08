package org.idnt.udemy.springboot.app.example.repository;

import org.idnt.udemy.springboot.app.example.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {}
