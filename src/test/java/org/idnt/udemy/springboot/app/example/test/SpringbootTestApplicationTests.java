package org.idnt.udemy.springboot.app.example.test;

import org.idnt.udemy.springboot.app.example.model.Account;
import org.idnt.udemy.springboot.app.example.model.Bank;
import org.idnt.udemy.springboot.app.example.repository.AccountRepository;
import org.idnt.udemy.springboot.app.example.repository.BankRepository;
import org.idnt.udemy.springboot.app.example.service.AccountService;
import org.idnt.udemy.springboot.app.example.service.impl.AccountServiceImpl;
import org.idnt.udemy.springboot.app.example.test.util.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class SpringbootTestApplicationTests {
	private AccountRepository accountRepository;
	private BankRepository bankRepository;
	private AccountService accountService;

	private Account account001;
	private Account account002;
	private Bank bank;

	@BeforeEach
	void setUp() {
		this.accountRepository = mock(AccountRepository.class);
		this.bankRepository = mock(BankRepository.class);
		this.accountService = new AccountServiceImpl(this.accountRepository, this.bankRepository);

		this.account001 = new Account(Data.ACCOUNT_001.getId(), Data.ACCOUNT_001.getPersonName(), Data.ACCOUNT_001.getBalance());
		this.account002 = new Account(Data.ACCOUNT_002.getId(), Data.ACCOUNT_002.getPersonName(), Data.ACCOUNT_002.getBalance());
		this.bank = new Bank(Data.BANK.getId(), Data.BANK.getName(), Data.BANK.getTotalTransactions());
	}

	@Test
	@DisplayName("Test that checks that an inter-account transfer has been made correctly")
	void testCorrectTransferInterAccounts() {
		//Given
		final Long ID_ACCOUNT_ORIGIN = 1L;
		final Long ID_ACCOUNT_TARGET = 2L;
		final Long ID_BANK = 1L;

		when(this.accountService.findById(ID_ACCOUNT_ORIGIN)).thenReturn(this.account001);
		when(this.accountService.findById(ID_ACCOUNT_TARGET)).thenReturn(this.account002);
		when(this.bankRepository.findById(ID_BANK)).thenReturn(this.bank);

		//When - Get balances from accounts and get total transactions from bank
		BigDecimal balanceOrigin = this.accountService.checkBalance(ID_ACCOUNT_ORIGIN);
		BigDecimal balanceTarget = this.accountService.checkBalance(ID_ACCOUNT_TARGET);
		int totalTransactions = this.accountService.checkTotalTransactions(ID_BANK);

		//Then - Checks balances and total transactions
		assertEquals("1000", balanceOrigin.toPlainString());
		assertEquals("2000", balanceTarget.toPlainString());
		assertEquals(0, totalTransactions);

		//When - Transfer money inter-accounts - Get new balances from accounts and get total transactions from bank
		this.accountService.transfer(ID_BANK, ID_ACCOUNT_ORIGIN, ID_ACCOUNT_TARGET, new BigDecimal("100"));
		BigDecimal newBalanceOrigin = this.accountService.checkBalance(ID_ACCOUNT_ORIGIN);
		BigDecimal newBalanceTarget = this.accountService.checkBalance(ID_ACCOUNT_TARGET);
		totalTransactions = this.accountService.checkTotalTransactions(ID_BANK);

		//Then - Checks new balances and total transactions
		assertEquals("900", newBalanceOrigin.toPlainString());
		assertEquals("2100", newBalanceTarget.toPlainString());
		assertEquals(1, totalTransactions);

		//Then - Verifiy calls methods
		verify(this.accountRepository, times(3)).findByid(ID_ACCOUNT_ORIGIN);
		verify(this.accountRepository, times(3)).findByid(ID_ACCOUNT_TARGET);
		verify(this.accountRepository, times(2)).update(any(Account.class));
		verify(this.bankRepository, times(3)).findById(ID_BANK);
		verify(this.bankRepository).update(any(Bank.class));
	}

}
