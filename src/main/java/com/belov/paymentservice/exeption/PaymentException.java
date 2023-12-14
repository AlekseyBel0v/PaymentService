package com.belov.paymentservice.exeption;

public class PaymentException extends RuntimeException{
    public PaymentException(String message) {
        super(message);
    }
}
