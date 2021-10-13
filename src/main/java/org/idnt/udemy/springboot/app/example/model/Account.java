package org.idnt.udemy.springboot.app.example.model;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.idnt.udemy.springboot.app.example.exception.NotEnoughMoneyException;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACCOUNTS")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PERSON_NAME")
    private String personName;

    @Column(name = "BALANCE")
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
