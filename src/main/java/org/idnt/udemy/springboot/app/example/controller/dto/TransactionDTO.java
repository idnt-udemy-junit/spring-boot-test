package org.idnt.udemy.springboot.app.example.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long idBank;
    private Long idAccounOrigin;
    private Long idAccountTarget;
    private BigDecimal quantity;
}
