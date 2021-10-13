package org.idnt.udemy.springboot.app.example.test.controller;

import org.idnt.udemy.springboot.app.example.controller.AccountController;
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

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

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
}
