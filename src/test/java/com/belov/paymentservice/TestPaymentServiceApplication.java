package com.belov.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(PaymentServiceApplication::main).with(TestPaymentServiceApplication.class).run(args);
    }
}