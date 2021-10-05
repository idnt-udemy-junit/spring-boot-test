package org.idnt.udemy.springboot.app.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
    private Long id;
    private String name;
    private int totalTransactions;
}
