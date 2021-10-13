package org.idnt.udemy.springboot.app.example.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.number.BigDecimalCloseTo;
import org.idnt.udemy.springboot.app.example.controller.AccountController;
import org.idnt.udemy.springboot.app.example.controller.dto.TransactionDTO;
import org.idnt.udemy.springboot.app.example.model.Account;
import org.idnt.udemy.springboot.app.example.service.AccountService;
import org.idnt.udemy.springboot.app.example.test.util.DATA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Test that checks that the endpoint that retrieves the detail of the accounts from the account ID works correctly.")
    void testDetail() throws Exception {
        //Given
        final Long ID = 1L;
        final Account ACCOUNT_EXPECTED = DATA.getAccount001().get();
        when(this.accountService.findById(ID)).thenReturn(ACCOUNT_EXPECTED);

        //When
        this.mockMvc.perform(get(String.format("/api/accounts/%s", ID)).contentType(MediaType.APPLICATION_JSON))

        //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.personName").value("Andrés"))
                .andExpect(jsonPath("$.balance").value("1000"));

        verify(this.accountService).findById(ID);
    }

    @Test
    @DisplayName("Test that verifies that the endpoint that performs the transfer between accounts from the account IDs is working correctly.")
    void testTransfer() throws Exception {
        //Given
        final Long ID_BANK = 1L;
        final Long ID_ACC_ORIGIN = 1L;
        final Long ID_ACC_TARGET = 2L;
        final BigDecimal QUANTITY = new BigDecimal("500");
        final TransactionDTO TRANSACTION_DTO = new TransactionDTO(ID_BANK, ID_ACC_ORIGIN, ID_ACC_TARGET, QUANTITY);
        final Map<String, Object> TRANSACTION_EXPECTED = new HashMap<>();
        TRANSACTION_EXPECTED.put("date", LocalDate.now().toString());
        TRANSACTION_EXPECTED.put("status", "OK");
        TRANSACTION_EXPECTED.put("message", "Successful transfer");
        TRANSACTION_EXPECTED.put("transaction", TRANSACTION_DTO);

        doNothing().when(this.accountService).transfer(ID_BANK, ID_ACC_ORIGIN, ID_ACC_TARGET, QUANTITY);

        //When
        this.mockMvc.perform(post("/api/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(TRANSACTION_DTO)))

        //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Successful transfer"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(content().json(this.objectMapper.writeValueAsString(TRANSACTION_EXPECTED)))
                .andExpect(jsonPath("$.transaction.idAccountOrigin").value(ID_ACC_ORIGIN))
                .andExpect(jsonPath("$.transaction.idAccountTarget").value(ID_ACC_TARGET))
                .andExpect(jsonPath("$.transaction.idBank").value(ID_BANK))
                .andExpect(jsonPath("$.transaction.quantity").value(QUANTITY));

        verify(this.accountService).transfer(ID_BANK, ID_ACC_ORIGIN, ID_ACC_TARGET, QUANTITY);
    }

    @Test
    @DisplayName("Test that verifies that the endpoint that performs the account listing is working correctly.")
    void testList() throws Exception {
        //Given
        final List<Account> ACCOUNTS_LIST = Arrays.asList(DATA.getAccount001().get(), DATA.getAccount002().get());
        when(this.accountService.findAll()).thenReturn(ACCOUNTS_LIST);

        //When
        this.mockMvc.perform(get("/api/accounts/list").contentType(MediaType.APPLICATION_JSON))

        //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].personName").value("Andrés"))
                .andExpect(jsonPath("$[0].balance").value("1000"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].personName").value("Jhon"))
                .andExpect(jsonPath("$[1].balance").value("2000"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().json(this.objectMapper.writeValueAsString(ACCOUNTS_LIST)));
    }

    @Test
    @DisplayName("Test that verifies that the endpoint that performs the saving of an account is working correctly.")
    void testSave() throws Exception {
        //Given
        final Long ID = 5L;
        final Account ACCOUNT_EXPECTED = DATA.getAccount001().get();
        ACCOUNT_EXPECTED.setId(ID);
        final Account NEW_ACCOUNT = DATA.getAccount001().get();
        NEW_ACCOUNT.setId(null);
        when(this.accountService.save(any())).then(invocation -> {
            Account accountArgument = invocation.getArgument(0);
            accountArgument.setId(ID);
            return accountArgument;
        });
        final Map<String, Object> RESPONSE_EXPECTED = new HashMap<>();
        RESPONSE_EXPECTED.put("date", LocalDate.now().toString());
        RESPONSE_EXPECTED.put("status", "CREATED");
        RESPONSE_EXPECTED.put("message", "Successful saved");
        RESPONSE_EXPECTED.put("result", ACCOUNT_EXPECTED);

        //When
        this.mockMvc.perform(post("/api/accounts/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(NEW_ACCOUNT)))

        //Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Successful saved"))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(content().json(this.objectMapper.writeValueAsString(RESPONSE_EXPECTED)))
                .andExpect(jsonPath("$.result.id", is(ID.intValue())))
                .andExpect(jsonPath("$.result.personName", is("Andrés")))
                .andExpect(jsonPath("$.result.balance", is(1000)));

        verify(this.accountService).save(any());
    }
}
