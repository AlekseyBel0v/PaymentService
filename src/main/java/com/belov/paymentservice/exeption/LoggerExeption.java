package com.belov.paymentservice.exeption;

import java.io.IOException;

public class LoggerExeption extends IOException {
    public LoggerExeption(String msg) {
        super(msg);
    }

    public LoggerExeption() {
    }
}
