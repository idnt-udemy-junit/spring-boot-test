package org.idnt.udemy.springboot.app.example.repository;

import org.idnt.udemy.springboot.app.example.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {}
