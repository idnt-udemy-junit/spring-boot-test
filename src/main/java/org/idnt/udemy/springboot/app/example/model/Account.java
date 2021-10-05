package org.idnt.udemy.springboot.app.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.idnt.udemy.springboot.app.example.exception.NotEnoughMoneyException;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private String personName;
    private BigDecimal balance;

    public void debit(final BigDecimal quantity) throws NotEnoughMoneyException{
        BigDecimal result = this.getBalance().subtract(quantity);
        if(result.compareTo(BigDecimal.ZERO)<0){
            throw new NotEnoughMoneyException("Insufficient money in the account.");
        }
        this.setBalance(result);
    }

    public void credit(final BigDecimal quantity){
        this.setBalance(this.getBalance().add(quantity));
    }
}
