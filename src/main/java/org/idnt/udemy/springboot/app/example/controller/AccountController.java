package org.idnt.udemy.springboot.app.example.controller;

import org.idnt.udemy.springboot.app.example.controller.dto.TransactionDTO;
import org.idnt.udemy.springboot.app.example.model.Account;
import org.idnt.udemy.springboot.app.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> detail(@PathVariable(name = "id") final Long ID){
        ResponseEntity<?> result;

        try {
            result = ResponseEntity.ok(this.accountService.findById(ID));
        } catch (NoSuchElementException e){
            result = ResponseEntity.notFound().build();
        }

        return result;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> list(){
        return this.accountService.findAll();
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> transfer(@RequestBody final TransactionDTO transactionDTO){
        this.accountService.transfer(transactionDTO.getIdBank(), transactionDTO.getIdAccountOrigin(), transactionDTO.getIdAccountTarget(), transactionDTO.getQuantity());

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("message", "Successful transfer");
        response.put("transaction", transactionDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> save(@RequestBody final Account account){
        final Account result = this.accountService.save(account);

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "CREATED");
        response.put("message", "Successful saved");
        response.put("result", result);

        return response;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") final Long id){
        this.accountService.deleteById(id);
    }
}
