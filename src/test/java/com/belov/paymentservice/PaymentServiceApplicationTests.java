package com.belov.paymentservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class PaymentServiceApplicationTests {

    @Autowired
    TestRestTemplate testRestTemplate = new TestRestTemplate();

    //создание контейнера
    @Container
    private final GenericContainer<?> testContainer = new GenericContainer<>("transferapperror:1.0")
            .withExposedPorts(8080);

    //    @Ignore
    @Test
    //тело запроса
    void test200() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        String requestBody = "{" +
                "\"cardFromNumber\":\"0000000000000001\"," +
                "\"cardFromValidTill\":\"01/24\"," +
                "\"cardFromCVV\":\"111\"," +
                "\"cardToNumber\":\"0000000000000003\"," +
                "\"amount\":{" +
                "\"value\":100," +
                "\"currency\":\"RUR\"" +
                "}" +
                "}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        //Ожидаемое тело ответа
        String expectedResponseBody = "{\"operationId\":\"1\"}";


        //Получаем тело
        ResponseEntity<String> response = testRestTemplate.postForEntity(
                URI.create("http://localhost:" + testContainer.getMappedPort(8080) + "/transfer"),
                entity, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedResponseBody, response.getBody());
    }
}