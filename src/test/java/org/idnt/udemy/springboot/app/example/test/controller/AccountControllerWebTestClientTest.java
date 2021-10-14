package org.idnt.udemy.springboot.app.example.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.idnt.udemy.springboot.app.example.controller.dto.TransactionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerWebTestClientTest {
    @Autowired
    private WebTestClient webTestClient;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Integration test that tests the endpoint that performs a transfer between 2 accounts via their IDs.")
    void testTransfer() throws JsonProcessingException {
        //Given
        final Long ID_BANK = 1L;
        final Long ID_ACC_ORIGIN = 1L;
        final Long ID_ACC_TARGET = 2L;
        final BigDecimal QUANTITY = new BigDecimal("500");
        final TransactionDTO NEW_TRANSACTION = new TransactionDTO(ID_BANK, ID_ACC_ORIGIN, ID_ACC_TARGET, QUANTITY);
        final Map<String, Object> RESPONSE_EXPECTED = new HashMap<>();
        RESPONSE_EXPECTED.put("date", LocalDate.now().toString());
        RESPONSE_EXPECTED.put("status", "OK");
        RESPONSE_EXPECTED.put("message", "Successful transfer");
        RESPONSE_EXPECTED.put("transaction", NEW_TRANSACTION);

        //When
        this.webTestClient.post().uri("/api/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(NEW_TRANSACTION)
                .exchange()

        //Then
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message").isNotEmpty()
                .jsonPath("$.message").value(is("Successful transfer"))
                .jsonPath("$.message").isEqualTo("Successful transfer")
                .jsonPath("$.status").isNotEmpty()
                .jsonPath("$.status").value(is("OK"))
                .jsonPath("$.status").isEqualTo("OK")
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.date").value(value -> assertEquals(LocalDate.now().toString(), value))
                .jsonPath("$.date").isEqualTo(LocalDate.now().toString())
                .jsonPath("$.transaction.idBank").isEqualTo(ID_BANK)
                .jsonPath("$.transaction.idAccountOrigin").isEqualTo(ID_ACC_ORIGIN)
                .jsonPath("$.transaction.idAccountTarget").isEqualTo(ID_ACC_TARGET)
                .jsonPath("$.transaction.quantity").isEqualTo(QUANTITY)
                .json(this.objectMapper.writeValueAsString(RESPONSE_EXPECTED));
    }
}
