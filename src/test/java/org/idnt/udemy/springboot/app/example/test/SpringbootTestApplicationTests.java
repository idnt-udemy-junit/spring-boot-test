package org.idnt.udemy.springboot.app.example.test;

import org.idnt.udemy.springboot.app.example.exception.NotEnoughMoneyException;
import org.idnt.udemy.springboot.app.example.model.Account;
import org.idnt.udemy.springboot.app.example.model.Bank;
import org.idnt.udemy.springboot.app.example.repository.AccountRepository;
import org.idnt.udemy.springboot.app.example.repository.BankRepository;
import org.idnt.udemy.springboot.app.example.service.AccountService;
import org.idnt.udemy.springboot.app.example.test.util.DATA;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SpringbootTestApplicationTests {
	@MockBean
	private AccountRepository accountRepository;

	@MockBean
	private BankRepository bankRepository;

	@Autowired
	private AccountService accountService;

	@Test
	@DisplayName("Test that checks that an inter-account transfer has been made correctly")
	void testCorrectTransferInterAccounts() {
		//Given
		final Long ID_ACCOUNT_ORIGIN = 1L;
		final Long ID_ACCOUNT_TARGET = 2L;
		final Long ID_BANK = 1L;

		when(this.accountRepository.findById(ID_ACCOUNT_ORIGIN)).thenReturn(DATA.getAccount001());
		when(this.accountRepository.findById(ID_ACCOUNT_TARGET)).thenReturn(DATA.getAccount002());
		when(this.bankRepository.findById(ID_BANK)).thenReturn(DATA.getBank());

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
		verify(this.accountRepository, times(3)).findById(ID_ACCOUNT_ORIGIN);
		verify(this.accountRepository, times(3)).findById(ID_ACCOUNT_TARGET);
		verify(this.accountRepository, times(6)).findById(anyLong());
		verify(this.accountRepository, times(2)).save(any(Account.class));
		verify(this.accountRepository, never()).findAll();
		verify(this.bankRepository, times(3)).findById(ID_BANK);
		verify(this.bankRepository).save(any(Bank.class));
		verify(this.bankRepository, never()).findAll();
	}

	@Test
	@DisplayName("Test that checks that the \"NotEnoughMoneyException\" exception is thrown when a transfer is made between accounts when the originating account does not have enough money.")
	void testTransferInterAccounts_NotEnoughMoneyException() {
		//Given
		final Long ID_ACCOUNT_ORIGIN = 1L;
		final Long ID_ACCOUNT_TARGET = 2L;
		final Long ID_BANK = 1L;

		when(this.accountRepository.findById(ID_ACCOUNT_ORIGIN)).thenReturn(DATA.getAccount001());
		when(this.accountRepository.findById(ID_ACCOUNT_TARGET)).thenReturn(DATA.getAccount002());
		when(this.bankRepository.findById(ID_BANK)).thenReturn(DATA.getBank());

		//When - Get balances from accounts and get total transactions from bank
		BigDecimal balanceOrigin = this.accountService.checkBalance(ID_ACCOUNT_ORIGIN);
		BigDecimal balanceTarget = this.accountService.checkBalance(ID_ACCOUNT_TARGET);
		int totalTransactions = this.accountService.checkTotalTransactions(ID_BANK);

		//Then - Checks balances and total transactions
		assertEquals("1000", balanceOrigin.toPlainString());
		assertEquals("2000", balanceTarget.toPlainString());
		assertEquals(0, totalTransactions);

		//When - Transfer more than the balance in the source account.
		assertThrows(NotEnoughMoneyException.class, () -> {
			this.accountService.transfer(ID_BANK, ID_ACCOUNT_ORIGIN, ID_ACCOUNT_TARGET, new BigDecimal("1500"));
		});

		//When - Get new balances from accounts and get total transactions from bank.
		BigDecimal newBalanceOrigin = this.accountService.checkBalance(ID_ACCOUNT_ORIGIN);
		BigDecimal newBalanceTarget = this.accountService.checkBalance(ID_ACCOUNT_TARGET);
		totalTransactions = this.accountService.checkTotalTransactions(ID_BANK);

		//Then - Checks new balances and total transactions
		assertEquals("1000", newBalanceOrigin.toPlainString());
		assertEquals("2000", newBalanceTarget.toPlainString());
		assertEquals(0, totalTransactions);

		//Then - Verifiy calls methods
		verify(this.accountRepository, times(3)).findById(ID_ACCOUNT_ORIGIN);
		verify(this.accountRepository, times(2)).findById(ID_ACCOUNT_TARGET);
		verify(this.accountRepository, times(5)).findById(anyLong());
		verify(this.accountRepository, never()).findAll();
		verify(this.accountRepository, never()).save(any(Account.class));
		verify(this.bankRepository, times(2)).findById(ID_BANK);
		verify(this.bankRepository, never()).save(any(Bank.class));
		verify(this.bankRepository, never()).findAll();
	}

	@Test
	@DisplayName("Assertions.assertSame() - Test that proves that the \"findById\" method of the \"AccountServiceImpl\" class returns the same reference when searching for the same account by ID.")
	void testCheckAccountSameReference_assertSame() {
		//Given
		final Long ID_ACCOUNT = 1L;

		when(this.accountRepository.findById(ID_ACCOUNT)).thenReturn(DATA.getAccount001());

		//When - Find account by ID
		Account account = this.accountService.findById(ID_ACCOUNT);
		final Account EXPECTED = this.accountService.findById(ID_ACCOUNT);

		//Then - Checks that accounts are equals and the "findById" method of "AccountRepository" call 2 times
		assertSame(EXPECTED, account);
		assertTrue(account == EXPECTED,
				() -> String.format("The accounts must be equals. EXPECTED: %s // ACTUAL: %s", EXPECTED, account));

		verify(this.accountRepository, times(2)).findById(ID_ACCOUNT);
		verify(this.accountRepository, never()).save(any(Account.class));
		verify(this.accountRepository, never()).findAll();
		verify(this.bankRepository, never()).findById(anyLong());
		verify(this.bankRepository, never()).save(any(Bank.class));
		verify(this.bankRepository, never()).findAll();

	}
}
