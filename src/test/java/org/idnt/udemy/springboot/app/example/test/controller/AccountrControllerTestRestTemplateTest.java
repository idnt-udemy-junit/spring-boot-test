package org.idnt.udemy.springboot.app.example.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.idnt.udemy.springboot.app.example.controller.dto.TransactionDTO;
import org.idnt.udemy.springboot.app.example.model.Account;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

        final String TRANSACTION_JSON_STR_EXPECTED = String.format("{\"idBank\":%s,\"idAccountOrigin\":%s,\"idAccountTarget\":%s,\"quantity\":%s}",
                NEW_TRANSACTION.getIdBank(), NEW_TRANSACTION.getIdAccountOrigin(), NEW_TRANSACTION.getIdAccountTarget(), NEW_TRANSACTION.getQuantity());
        assertTrue(RESPONSE_STR.contains(TRANSACTION_JSON_STR_EXPECTED), () -> String.format("The response must be contain \"%s\"",
                TRANSACTION_JSON_STR_EXPECTED));

        final JsonNode RESPONSE_JSON = this.objectMapper.readTree(RESPONSE_STR);
        assertEquals("OK", RESPONSE_JSON.path("status").asText());
        assertEquals("Successful transfer", RESPONSE_JSON.path("message").asText());
        assertEquals(LocalDate.now().toString(), RESPONSE_JSON.path("date").asText());

        final JsonNode TRANSACTION_JSON_RESPONSE = RESPONSE_JSON.path("transaction");
        assertEquals(NEW_TRANSACTION.getIdBank(), TRANSACTION_JSON_RESPONSE.path("idBank").asLong());
        assertEquals(NEW_TRANSACTION.getIdAccountOrigin(), TRANSACTION_JSON_RESPONSE.path("idAccountOrigin").asLong());
        assertEquals(NEW_TRANSACTION.getIdAccountTarget(), TRANSACTION_JSON_RESPONSE.path("idAccountTarget").asLong());
        assertEquals(NEW_TRANSACTION.getQuantity().toPlainString(), TRANSACTION_JSON_RESPONSE.path("quantity").asText());
        assertEquals(this.objectMapper.writeValueAsString(RESPONSE_EXPECTED), RESPONSE_STR);
    }

    @Test
    @Order(2)
    @DisplayName("Integration test that tests the endpoint that obtains the details of an account.")
    void testDetail(){
        //Given
        final Long ID = 1L;
        final Account ACCOUNT_EXPECTED = new Account(ID, "Andrés", new BigDecimal("500"));

        //When
        final ResponseEntity<Account> RESPONSE_ENTITY_ACCOUNT = this.testRestTemplate.getForEntity(
                String.format("%s/api/accounts/%s", this.host, ID), Account.class);

        //Then
        assertNotNull(RESPONSE_ENTITY_ACCOUNT, () -> "The response mutsn't be null");
        assertEquals(HttpStatus.OK, RESPONSE_ENTITY_ACCOUNT.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, RESPONSE_ENTITY_ACCOUNT.getHeaders().getContentType());

        final Account account = RESPONSE_ENTITY_ACCOUNT.getBody();
        assertEquals(ID, account.getId());
        assertEquals("Andrés", account.getPersonName());
        assertEquals("500.00", account.getBalance().toPlainString());
    }

    @Test
    @Order(3)
    @DisplayName("Integration test that tests the endpoint that obtains the list of accounts.")
    void testList() throws JsonProcessingException {
        //When
        final ResponseEntity<Account[]> RESPONSE_ENTITY_ACCOUNT = this.testRestTemplate.getForEntity(
                String.format("%s/api/accounts/list", this.host), Account[].class);

        //Then
        assertNotNull(RESPONSE_ENTITY_ACCOUNT, () -> "The response mutsn't be null");
        assertEquals(HttpStatus.OK, RESPONSE_ENTITY_ACCOUNT.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, RESPONSE_ENTITY_ACCOUNT.getHeaders().getContentType());

        final List<Account> accounts = Arrays.asList(RESPONSE_ENTITY_ACCOUNT.getBody());

        assertNotNull(accounts, () -> "The result mustn't be null");
        assertEquals(2, accounts.size());
        assertEquals(1L, accounts.get(0).getId());
        assertEquals("Andrés", accounts.get(0).getPersonName());
        assertEquals("500.00", accounts.get(0).getBalance().toPlainString());
        assertEquals(2L, accounts.get(1).getId());
        assertEquals("Jhon", accounts.get(1).getPersonName());
        assertEquals("2500.00", accounts.get(1).getBalance().toPlainString());

        final JsonNode RESPONSE_JSON = this.objectMapper.readTree(this.objectMapper.writeValueAsString(accounts));
        assertNotNull(RESPONSE_JSON, () -> "The result mustn't be null");
        assertEquals(2, RESPONSE_JSON.size());
        assertEquals(1L, RESPONSE_JSON.get(0).path("id").asLong());
        assertEquals("Andrés", RESPONSE_JSON.get(0).path("personName").asText());
        assertEquals("500.0", RESPONSE_JSON.get(0).path("balance").asText());
        assertEquals(2L, RESPONSE_JSON.get(1).path("id").asLong());
        assertEquals("Jhon", RESPONSE_JSON.get(1).path("personName").asText());
        assertEquals("2500.0", RESPONSE_JSON.get(1).path("balance").asText());
    }

    @Test
    @Order(4)
    @DisplayName("Integration test testing the endpoint that saves an account")
    void testSave() throws JsonProcessingException {
        //Given
        final Long ID = 3L;
        final Account NEW_ACCOUNT = new Account(null, "Pepe", new BigDecimal("5000"));
        final Account ACCOUNT_EXPECTED = new Account(ID, "Pepe", new BigDecimal("5000"));
        final Map<String, Object> RESPONSE_EXCEPTED = new HashMap<>();
        RESPONSE_EXCEPTED.put("date", LocalDate.now().toString());
        RESPONSE_EXCEPTED.put("status", "CREATED");
        RESPONSE_EXCEPTED.put("message", "Successful saved");
        RESPONSE_EXCEPTED.put("result", ACCOUNT_EXPECTED);

        //When
        final ResponseEntity<String> RESPONSE_BODY= this.testRestTemplate.postForEntity(
                String.format("%s/api/accounts/save", this.host),NEW_ACCOUNT, String.class);

        //Then
        assertNotNull(RESPONSE_BODY, () -> "The response mutsn't be null");
        assertEquals(HttpStatus.CREATED, RESPONSE_BODY.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, RESPONSE_BODY.getHeaders().getContentType());

        final String RESPONSE_BODY_STR = RESPONSE_BODY.getBody();
        final JsonNode RESPONSE_BODY_JSON = this.objectMapper.readTree(RESPONSE_BODY_STR);
        final JsonNode RESPONSE_ACCOUNT_JSON = RESPONSE_BODY_JSON.get("result");

        assertEquals("Successful saved", RESPONSE_BODY_JSON.get("message").asText());
        assertEquals("CREATED", RESPONSE_BODY_JSON.get("status").asText());
        assertEquals(LocalDate.now().toString(), RESPONSE_BODY_JSON.get("date").asText());
        assertEquals(ID, RESPONSE_ACCOUNT_JSON.get("id").asLong());
        assertEquals("Pepe", RESPONSE_ACCOUNT_JSON.get("personName").asText());
        assertEquals("5000", RESPONSE_ACCOUNT_JSON.get("balance").asText());
    }

    @Test
    @Order(5)
    @DisplayName("Integration test that tests the functionality of the endpoint that deletes an account.")
    void testDelete() {
        //Given
        final Long ID = 3L;
        final ResponseEntity<Account[]> PRE_LIST_RESPONSE = this.testRestTemplate.getForEntity(
                String.format("%s/api/accounts/list", this.host), Account[].class);
        assertNotNull(PRE_LIST_RESPONSE, () -> "The pre list response mutsn't be null");
        assertEquals(HttpStatus.OK, PRE_LIST_RESPONSE.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, PRE_LIST_RESPONSE.getHeaders().getContentType());

        //When
//        this.testRestTemplate.delete(String.format("%s/api/accounts/delete/%s", this.host, ID));
        final Map<String, Object> PATH_VARIABLES = new HashMap<>();
        PATH_VARIABLES.put("id", ID);
        ResponseEntity<Void> RESPONSE_EXCHANGE = this.testRestTemplate.exchange(
//                String.format("%s/api/accounts/delete/%s", this.host, ID), HttpMethod.DELETE, null, Void.class);
                String.format("%s/api/accounts/delete/{id}", this.host, ID), HttpMethod.DELETE, null, Void.class, PATH_VARIABLES);
        assertNotNull(RESPONSE_EXCHANGE, () -> "The post list response mutsn't be null");
        assertEquals(HttpStatus.NO_CONTENT, RESPONSE_EXCHANGE.getStatusCode());
        assertFalse(RESPONSE_EXCHANGE.hasBody(), () -> "The response mustn't have body");

        final ResponseEntity<Account[]> POST_LIST_RESPONSE = this.testRestTemplate.getForEntity(
                String.format("%s/api/accounts/list", this.host), Account[].class);
        assertNotNull(POST_LIST_RESPONSE, () -> "The post list response mutsn't be null");
        assertEquals(HttpStatus.OK, PRE_LIST_RESPONSE.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, PRE_LIST_RESPONSE.getHeaders().getContentType());

        //Then
        List<Account> PRE_LIST = Arrays.asList(PRE_LIST_RESPONSE.getBody());
        assertEquals(3, PRE_LIST.size());
        List<Account> POST_LIST = Arrays.asList(POST_LIST_RESPONSE.getBody());
        assertEquals(2, POST_LIST.size());

    }
}
