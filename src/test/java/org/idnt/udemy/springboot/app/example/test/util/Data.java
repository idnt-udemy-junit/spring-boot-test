package org.idnt.udemy.springboot.app.example.test.util;

import org.idnt.udemy.springboot.app.example.model.Account;
import org.idnt.udemy.springboot.app.example.model.Bank;

import java.math.BigDecimal;

public class Data {
    public static final Account ACCOUNT_001 = new Account(1L, "Andrés", new BigDecimal("1000"));
    public static final Account ACCOUNT_002 = new Account(2L, "Jhon", new BigDecimal("2000"));
    public static final Bank BANK = new Bank(1L, "El banco financiero", 0);
}
