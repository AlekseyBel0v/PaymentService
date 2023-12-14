package com.belov.paymentservice.controller;

import com.belov.paymentservice.RequestBody.ConfirmOperationParam;
import com.belov.paymentservice.RequestBody.TransferParam;
import com.belov.paymentservice.response_body.ResponseBody200;
import com.belov.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@CrossOrigin("https://serp-ya.github.io")
@Slf4j
@RestController
@Validated
public class PaymentController {
    PaymentService paymentService = new PaymentService();

    @PostMapping("/confirmOperation")
    ResponseBody200 confirmOperation(@RequestBody @Valid ConfirmOperationParam confirmOperationParam) throws IOException {
        log.info("Входящий запрос /confirmOperation");
        return paymentService.confirmOperation(confirmOperationParam);
    }

    @PostMapping("/transfer")
    public ResponseBody200 transferBetweenCards(@RequestBody @Valid TransferParam transferParam) throws IOException, ParseException {
        log.info("Входящий запрос /transfer");
        return paymentService.transfer(transferParam);
    }
}
