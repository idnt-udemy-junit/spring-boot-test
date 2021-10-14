package org.idnt.udemy.springboot.app.example.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.idnt.udemy.springboot.app.example.controller.dto.TransactionDTO;
import org.idnt.udemy.springboot.app.example.model.Account;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
        this.webTestClient.post().uri("/api/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(NEW_TRANSACTION)
                .exchange()

        //Then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(response -> {
                    try {
                        JsonNode json = this.objectMapper.readTree(response.getResponseBody());
                        JsonNode transaction = json.path("transaction");
                        assertEquals("Successful transfer", json.path("message").asText());
                        assertEquals("OK", json.path("status").asText());
                        assertEquals(LocalDate.now().toString(), json.path("date").asText());
                        assertEquals(ID_BANK, transaction.path("idBank").asLong());
                        assertEquals(ID_ACC_ORIGIN, transaction.path("idAccountOrigin").asLong());
                        assertEquals(ID_ACC_TARGET, transaction.path("idAccountTarget").asLong());
                        assertEquals(QUANTITY.toPlainString(), transaction.path("quantity").asText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
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

    @Test
    @Order(2)
    @DisplayName("1- Integration test that tests the endpoint that obtains the details of an account.")
    void testDetail1() throws JsonProcessingException {
        //Given
        final Long ID = 1L;
        final Account ACCOUNT_EXPECTED = new Account(ID, "Andrés", new BigDecimal("500"));

        //When
        this.webTestClient.get().uri(String.format("/api/accounts/%s", ID)).exchange()

        ///Then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.personName").isEqualTo("Andrés")
                .jsonPath("$.id").isEqualTo(ID)
                .jsonPath("$.balance").isEqualTo(500L)
                .json(this.objectMapper.writeValueAsString(ACCOUNT_EXPECTED));
    }

    @Test
    @Order(3)
    @DisplayName("2- Integration test that tests the endpoint that obtains the details of an account.")
    void testDetail2() {
        //Given
        final Long ID = 2L;
        final Account ACCOUNT_EXPECTED = new Account(ID, "Jhon", new BigDecimal("2500.00"));

        //When
        this.webTestClient.get().uri(String.format("/api/accounts/%s", ID)).exchange()

        ///Then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Account.class)
                .consumeWith(response -> {
                    final Account account = response.getResponseBody();
                    assertEquals(ID, account.getId());
                    assertEquals("Jhon", account.getPersonName());
                    assertEquals("2500.00", account.getBalance().toPlainString());
                    assertEquals(ACCOUNT_EXPECTED, account);
                });
    }

    @Test
    @Order(4)
    @DisplayName("1- Integration test that tests the endpoint that obtains the list of accounts.")
    void testList1() {
        //When
        this.webTestClient.get().uri("/api/accounts/list").exchange()

        //Then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").value(hasSize(2))
                .jsonPath("$[0].id").isEqualTo(1L)
                .jsonPath("$[0].personName").isEqualTo("Andrés")
                .jsonPath("$[0].balance").isEqualTo(500)
                .jsonPath("$[1].id").isEqualTo(2L)
                .jsonPath("$[1].personName").isEqualTo("Jhon")
                .jsonPath("$[1].balance").isEqualTo(2500);
    }

    @Test
    @Order(5)
    @DisplayName("2- Integration test that tests the endpoint that obtains the list of accounts.")
    void testList2() {
        //When
        this.webTestClient.get().uri("/api/accounts/list").exchange()

        //Then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Account.class)
                .consumeWith(response -> {
                    final List<Account> account = response.getResponseBody();

                    assertNotNull(account, () -> "The result mustn't be null");
                    assertEquals(2, account.size());
                    assertEquals(1L, account.get(0).getId());
                    assertEquals("Andrés", account.get(0).getPersonName());
                    assertEquals("500.0", account.get(0).getBalance().toPlainString());
                    assertEquals(2L, account.get(1).getId());
                    assertEquals("Jhon", account.get(1).getPersonName());
                    assertEquals("2500.0", account.get(1).getBalance().toPlainString());
                })
                .hasSize(2)
                .value(hasSize(2));
    }

    @Test
    @Order(6)
    @DisplayName("1- Integration test testing the endpoint that saves an account")
    void testSave1() throws JsonProcessingException {
        //Given
        final Account NEW_ACCOUNT = new Account(null, "Patricia", new BigDecimal("3000"));
        final Account ACCOUNT_EXPECTED = new Account(3L, "Patricia", new BigDecimal("3000"));
        final Map<String, Object> RESPONSE_EXCEPTED = new HashMap<>();
        RESPONSE_EXCEPTED.put("date", LocalDate.now().toString());
        RESPONSE_EXCEPTED.put("status", "CREATED");
        RESPONSE_EXCEPTED.put("message", "Successful saved");
        RESPONSE_EXCEPTED.put("result", ACCOUNT_EXPECTED);

        //When
        this.webTestClient.post().uri("/api/accounts/save")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(NEW_ACCOUNT)
                .exchange()

                //Then
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.date").isEqualTo(LocalDate.now().toString())
                .jsonPath("$.message").isEqualTo("Successful saved")
                .jsonPath("$.status").isEqualTo("CREATED")
                .jsonPath("$.result.id").isEqualTo(ACCOUNT_EXPECTED.getId())
                .jsonPath("$.result.personName").isEqualTo(ACCOUNT_EXPECTED.getPersonName())
                .jsonPath("$.result.balance").isEqualTo(ACCOUNT_EXPECTED.getBalance())
                .json(this.objectMapper.writeValueAsString(RESPONSE_EXCEPTED));
    }

    @Test
    @Order(7)
    @DisplayName("2- Integration test testing the endpoint that saves an account")
    void testSave2(){
        //Given
        final Account NEW_ACCOUNT = new Account(null, "Pepe", new BigDecimal("5000"));
        final Account ACCOUNT_EXPECTED = new Account(4L, "Pepe", new BigDecimal("5000"));
        final Map<String, Object> RESPONSE_EXCEPTED = new HashMap<>();
        RESPONSE_EXCEPTED.put("date", LocalDate.now().toString());
        RESPONSE_EXCEPTED.put("status", "CREATED");
        RESPONSE_EXCEPTED.put("message", "Successful saved");
        RESPONSE_EXCEPTED.put("result", ACCOUNT_EXPECTED);

        //When
        this.webTestClient.post().uri("/api/accounts/save")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(NEW_ACCOUNT)
                .exchange()

                //Then
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(response -> {
                    try {
                        final JsonNode json = this.objectMapper.readTree(response.getResponseBody());
                        final JsonNode resultJson = json.get("result");

                        assertEquals("Successful saved", json.get("message").asText());
                        assertEquals("CREATED", json.get("status").asText());
                        assertEquals(LocalDate.now().toString(), json.get("date").asText());
                        assertEquals(4L, resultJson.get("id").asLong());
                        assertEquals("Pepe", resultJson.get("personName").asText());
                        assertEquals("5000", resultJson.get("balance").asText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(8)
    @DisplayName("Integration test that tests the functionality of the endpoint that deletes an account.")
    void testDelete() {
        //Given
        final Long ID = 4L;

        //When list accounts
        this.webTestClient.get().uri("/api/accounts/list").exchange()

        //Then list size is 4
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Account.class)
                .hasSize(4);

        //When delete account with id 4
        this.webTestClient.delete().uri(String.format("/api/accounts/delete/%s", ID)).exchange()

        //Then
                .expectBody().isEmpty();

        //When list accounts
        this.webTestClient.get().uri("/api/accounts/list").exchange()

        //Then list size is 3
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Account.class)
                .hasSize(3);

        //When search deleted element by id
        this.webTestClient.get().uri(String.format("/api/accounts/%s", ID)).exchange()

        //Then
                .expectStatus().is5xxServerError();
    }
}
