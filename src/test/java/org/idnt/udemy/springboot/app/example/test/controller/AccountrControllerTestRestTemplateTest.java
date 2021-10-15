package org.idnt.udemy.springboot.app.example.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.idnt.udemy.springboot.app.example.controller.dto.TransactionDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountrControllerTestRestTemplateTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private String host;

    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        this.host = String.format("http://localhost:%s", this.port);
    }

    @Test
    @Order(1)
    @DisplayName("Integration test that tests the endpoint that performs a transfer between 2 accounts via their IDs.")
    void testTransfer() {
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
        final ResponseEntity<String> RESPONSE_ENTITY = this.testRestTemplate.postForEntity(
                String.format("%s/api/accounts/transfer", this.host), NEW_TRANSACTION, String.class);

        //Then
        assertEquals(HttpStatus.OK, RESPONSE_ENTITY.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, RESPONSE_ENTITY.getHeaders().getContentType());
        assertNotNull(RESPONSE_ENTITY, () -> "The response mutsn't be null");
        final String RESPONSE_STR = RESPONSE_ENTITY.getBody();
        assertNotNull(RESPONSE_STR, () -> "The body response mustn't be null");
        assertTrue(RESPONSE_STR.contains("Successful transfer"), () -> "The response must be contain \"Successful transfer\"");
        assertTrue(RESPONSE_STR.contains(LocalDate.now().toString()), () -> String.format("The response must be contain \"%s\"", LocalDate.now()));

        final String TRANSACTION_JSON_EXPECTED = String.format("{\"idBank\":%s,\"idAccountOrigin\":%s,\"idAccountTarget\":%s,\"quantity\":%s}",
                NEW_TRANSACTION.getIdBank(), NEW_TRANSACTION.getIdAccountOrigin(), NEW_TRANSACTION.getIdAccountTarget(), NEW_TRANSACTION.getQuantity());
        assertTrue(RESPONSE_STR.contains(TRANSACTION_JSON_EXPECTED), () -> String.format("The response must be contain \"%s\"",
                TRANSACTION_JSON_EXPECTED));
    }
}
