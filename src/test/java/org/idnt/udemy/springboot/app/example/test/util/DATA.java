package org.idnt.udemy.springboot.app.example.test.util;

import org.idnt.udemy.springboot.app.example.model.Account;
import org.idnt.udemy.springboot.app.example.model.Bank;

import java.math.BigDecimal;

public class DATA {
    private static final Account ACCOUNT_001 = new Account(1L, "Andrés", new BigDecimal("1000"));
    private static final Account ACCOUNT_002 = new Account(2L, "Jhon", new BigDecimal("2000"));
    private static final Bank BANK = new Bank(1L, "El banco financiero", 0);

    public static Account getAccount001(){
        return new Account(ACCOUNT_001.getId(), ACCOUNT_001.getPersonName(), ACCOUNT_001.getBalance());
    }

    public static Account getAccount002(){
        return new Account(ACCOUNT_002.getId(), ACCOUNT_002.getPersonName(), ACCOUNT_002.getBalance());
    }

    public static Bank getBank(){
        return new Bank(BANK.getId(), BANK.getName(), BANK.getTotalTransactions());
    }
}
