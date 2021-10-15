package org.idnt.udemy.springboot.app.example.test;

import org.idnt.udemy.springboot.app.example.model.Account;
import org.idnt.udemy.springboot.app.example.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration-jpa")
@DataJpaTest
public class IntegrationJpaTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("Test that checks that the first record of the account table is retrieved via the ID.")
    void testFindById() {
        //Given
        final Long ID = 1L;

        //When
        Optional<Account> accountOP = this.accountRepository.findById(ID);

        //Then
        assertTrue(accountOP.isPresent(), () -> "The result mustn't be null");
        Account account = accountOP.get();
        assertEquals("Andrés", account.getPersonName());
        assertEquals("1000.00", account.getBalance().toPlainString());
    }

    @Test
    @DisplayName("Test that checks that an exception is retrieved when searching for a record in the account table via ID that doesn't exist.")
    void testFindByIdThrowException() {
        //Given
        final Long ID = 5L;

        //When
        Optional<Account> accountOP = this.accountRepository.findById(ID);

        //Then
        assertThrows(NoSuchElementException.class, accountOP::orElseThrow);
        assertFalse(accountOP.isPresent(), "The result must be null");
    }

    @Test
    @DisplayName("Test that checks that the first record of the account table is retrieved via the person name.")
    void testFindByPersona() {
        //Given
        final String PERSON_NAME = "Andrés";

        //When
        Optional<Account> accountOP = this.accountRepository.findByPersonaName(PERSON_NAME);

        //Then
        assertTrue(accountOP.isPresent(), () -> "The result mustn't be null");
        Account account = accountOP.get();
        assertEquals("Andrés", account.getPersonName());
        assertEquals("1000.00", account.getBalance().toPlainString());
    }

    @Test
    @DisplayName("Test that checks that an exception is retrieved when searching for a record in the account table via person name that doesn't exist.")
    void testFindByPersonaThrowException() {
        //Given
        final String PERSON_NAME = "Pepe";

        //When
        Optional<Account> accountOP = this.accountRepository.findByPersonaName(PERSON_NAME);

        //Then
        assertThrows(NoSuchElementException.class, accountOP::orElseThrow);
        assertFalse(accountOP.isPresent(), "The result must be null");
    }

    @Test
    @DisplayName("Test that checks that all records in the accounts table are retrieved.")
    void testFindAll() {
        //When
        List<Account> accounts = this.accountRepository.findAll();

        //Then
        assertFalse(accounts.isEmpty(), () -> "The result list mustn't be empty");
        assertEquals(2, accounts.size(), () -> "The size of the list isn't what was expected.");
    }

    @Test
    @DisplayName("Test that verifies that an account is stored in the database correctly.")
    void testSave() {
        //Given
        final String PERSON_NAME = "Patricia";
        Account newAccount = new Account(null, PERSON_NAME, new BigDecimal("3000"));

        //When
        Account accountSaved = this.accountRepository.save(newAccount);

        //Then
        assertNotNull(accountSaved, () -> "The result mustn't be null");
        assertEquals("Patricia", accountSaved.getPersonName());
        assertEquals("3000", accountSaved.getBalance().toPlainString());
//        Optional<Account> accountOP = this.accountRepository.findByPersonaName(PERSON_NAME);
//        assertTrue(accountOP.isPresent(), () -> "The result mustn't be null");
//        Account account = accountOP.get();
//        assertEquals("Patricia", account.getPersonName());
//        assertEquals("3000", account.getBalance().toPlainString());
    }

    @Test
    @DisplayName("Test that checks that a record in the accounts table is correctly updated.")
    void testUpdate() {
        //Given
        final String PERSON_NAME = "Patricia";
        Account newAccount = new Account(null, PERSON_NAME, new BigDecimal("3000"));
        Account accountSaved = this.accountRepository.save(newAccount);

        //When
        accountSaved.debit(new BigDecimal("500"));
        Account accountEdited = this.accountRepository.save(accountSaved);

        //Then
        assertNotNull(accountEdited, () -> "The result mustn't be null");
        assertEquals("Patricia", accountEdited.getPersonName());
        assertEquals("2500", accountEdited.getBalance().toPlainString());
    }

    @Test
    @DisplayName("Test that checks that a record in the accounts table is deleted correctly.")
    void testDelete() {
        //Given
        final Long ID = 2L;
        Account accountToDelete = this.accountRepository.getById(ID);

        //When
        this.accountRepository.delete(accountToDelete);

        //Then
        assertThrows(NoSuchElementException.class, this.accountRepository.findById(ID)::orElseThrow);
    }
}
