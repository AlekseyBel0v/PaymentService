package com.belov.paymentservice.service;

import com.belov.paymentservice.RequestBody.ConfirmOperationParam;
import com.belov.paymentservice.exeption.PaymentException;
import com.belov.paymentservice.model.Card;
import com.belov.paymentservice.RequestBody.TransferParam;
import com.belov.paymentservice.model.Currency;
import com.belov.paymentservice.repository.CardDateBase;
import com.belov.paymentservice.repository.OperationIdRepository;
import com.belov.paymentservice.response_body.ResponseBody200;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertFalseValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Validated
@Service
public class PaymentService {
    public ResponseBody200 confirmOperation(ConfirmOperationParam confirmOperationParam) throws PaymentException {
        long id = Long.parseLong(confirmOperationParam.getOperationId());
        //Проверяем достаточно ли средств на счете плательщика. Если достаточно, то списываем. Если нет, то исключение.
        TransferParam transferParam = OperationIdRepository.getTransferParam(id);
        CardDateBase.getCardDateBase().compute(transferParam.getCardFromNumber(), (key, card) -> card.getMoneyFromCard(
                Currency.valueOf(transferParam.getAmount().getCurrency()),
                transferParam.getAmount().getValue()));
        /*
        Начисляем перевод на карту.
        todo: Должна быть реализована логика, которая будет отменять списание средств с CardFrom, если по каким-то
         причинам не произошло начисление на CardTo (не реализовано для упрощения задачи).
         Реализация может быть выполнена через транзакцию.
         */
        CardDateBase.getCardDateBase().compute(transferParam.getCardToNumber(), (key, card) -> card.putMoneyToCard(
                Currency.valueOf(transferParam.getAmount().getCurrency()),
                transferParam.getAmount().getValue()));
        return new ResponseBody200(confirmOperationParam.getOperationId());
    }

    public ResponseBody200 transfer(TransferParam transferParam) throws PaymentException, ParseException {
        long operationId = OperationIdRepository.createNewIdOperation(transferParam);
        Card cardFrom = new Card(transferParam.getCardFromNumber(), transferParam.getCardFromValidTill(), transferParam.getCardFromCVV());
        // Проверяем, существуют ли карты в базе
        if ((!CardDateBase.getCardDateBase().containsKey(transferParam.getCardFromNumber())) ||
                (!CardDateBase.getCardDateBase().containsKey(transferParam.getCardToNumber())) ||
                (!CardDateBase.getCardDateBase().get(transferParam.getCardFromNumber()).equals(cardFrom))) {  // Проверяем, верны ли данные карты списания
            throw new PaymentException("Введены неверные данные карты");
        }
        // Проверка срока годности карты получателя
        Date cardToValidTill = CardDateBase.getCardDateBase().get(transferParam.getCardToNumber()).getCardValidTill();
        SimpleDateFormat format = new SimpleDateFormat("MM/yy");
        // Данная проверка условная. На самом деле необходимо хранить дату в стринг "MM/yy" и производить валидацию
        if (cardToValidTill.before(format.parse(format.format(new Date())))) {
            throw new PaymentException(String.format("Срок годности карты с номером %s истек", transferParam.getCardToNumber()));
        }
        log.info("Данные запроса валидны. Операции присвоен id:{}", operationId);
        //TODO: Необходимо проверить срок годности карт. Для упрощения задачи проверка не будет реализована.
        return new ResponseBody200(String.valueOf(operationId));
    }
}