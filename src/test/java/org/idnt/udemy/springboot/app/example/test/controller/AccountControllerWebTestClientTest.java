package org.idnt.udemy.springboot.app.example.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerWebTestClientTest {
    @Autowired
    private WebTestClient webTestClient;
}
