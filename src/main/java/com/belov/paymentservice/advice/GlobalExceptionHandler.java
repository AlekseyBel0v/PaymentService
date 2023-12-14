package com.belov.paymentservice.advice;

import com.belov.paymentservice.exeption.PaymentException;
import com.belov.paymentservice.response_body.ResponseBodyError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBodyError> handleMANVE(MethodArgumentNotValidException manve) {
        String errors = manve.getBindingResult().getFieldErrors().stream()
                .map((x) -> x.getDefaultMessage() + "; ")
                .collect(Collectors.joining());
        log.info("BAD_REQUEST: {}", errors);
        return new ResponseEntity<>(new ResponseBodyError(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ResponseBodyError> handleRE(PaymentException re) {
        log.info("BAD_REQUEST: {}", re.getMessage());
        return new ResponseEntity<>(new ResponseBodyError(re.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseBodyError> handleRE(Exception e) {
        StringWriter msg = null;
        e.printStackTrace(new PrintWriter(msg));
        log.error("INTERNAL_SERVER_ERROR: " + msg);
        System.out.println(msg);
        return new ResponseEntity<>(new ResponseBodyError("Что-то пошло не так(("), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}