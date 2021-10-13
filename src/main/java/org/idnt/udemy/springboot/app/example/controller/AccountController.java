package org.idnt.udemy.springboot.app.example.controller;

import org.idnt.udemy.springboot.app.example.controller.dto.TransactionDTO;
import org.idnt.udemy.springboot.app.example.model.Account;
import org.idnt.udemy.springboot.app.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account detail(@PathVariable(name = "id") final Long ID){
        return this.accountService.findById(ID);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> transfer(@RequestBody final TransactionDTO transactionDTO){
        this.accountService.transfer(transactionDTO.getIdBank(), transactionDTO.getIdAccounOrigin(), transactionDTO.getIdAccountTarget(), transactionDTO.getQuantity());

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now());
        response.put("status", "OK");
        response.put("message", "Successful transfer");
        response.put("transaction", transactionDTO);

        return ResponseEntity.ok(response);
    }
}
