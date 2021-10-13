package org.idnt.udemy.springboot.app.example.repository;

import org.idnt.udemy.springboot.app.example.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT c FROM Account c WHERE c.personName = ?1")
    Optional<Account> findByPersonaName(String personaName);
}
