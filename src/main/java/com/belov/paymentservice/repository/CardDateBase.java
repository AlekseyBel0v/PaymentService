package com.belov.paymentservice.repository;

import com.belov.paymentservice.model.Card;
import com.belov.paymentservice.model.Currency;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class CardDateBase {

    @Getter
    private static ConcurrentHashMap<String, Card> cardDateBase = new ConcurrentHashMap<>();

    static {
        try {
            cardDateBase.put("0000000000000001", new Card("0000000000000001", "01/24", "111").putMoneyToCard(Currency.RUR, 100000));
            cardDateBase.put("0000000000000002", new Card("0000000000000002", "02/22", "222").putMoneyToCard(Currency.RUR, 100000));
            cardDateBase.put("0000000000000003", new Card("0000000000000003", "03/24", "333"));
            cardDateBase.put("0000000000000004", new Card("0000000000000004", "04/24", "444"));
            log.info("База карт проинициализирована");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}