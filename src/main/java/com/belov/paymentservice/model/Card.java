/*
Данный класс описывает карту. На карте может быть несколько счетов (Bill), но не может быть нескольких счетов одной.
 */

package com.belov.paymentservice.model;

import com.belov.paymentservice.exeption.PaymentException;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

@Getter
@Setter
public class Card {
    private String cardNumber;
    private Date cardValidTill;
    private String cardCVV;
    private HashMap<Currency, Long> bills = new HashMap<>();  // ключи - валюта, значения - кол-во денег

    public Card(String cardNumber, String cardValidTill, String cardCVV) throws ParseException {
        this.cardNumber = cardNumber;
        this.cardValidTill = new SimpleDateFormat("MM/yy").parse(cardValidTill);
        this.cardCVV = cardCVV;
        Arrays.stream(Currency.values()).forEach(currency -> bills.put(currency, 0L));
    }

    public Card(String cardNumber, Date cardValidTill, String cardCVV) throws ParseException {
        this.cardNumber = cardNumber;
        this.cardValidTill = cardValidTill;
        this.cardCVV = cardCVV;
        Arrays.stream(Currency.values()).forEach(currency -> bills.put(currency, 0L));
    }

    public Card putMoneyToCard(Currency currency, long amountOfMoney) {
        bills.put(currency, bills.get(currency) + amountOfMoney);
        return this;
    }

    public Card getMoneyFromCard(Currency currency, long amountOfMoney) throws PaymentException {
        if (amountOfMoney <= bills.get(currency)) {
            bills.put(currency, bills.get(currency) - amountOfMoney);
        } else throw new PaymentException(String.format("На карте № %s недостаточно средств для выполнения операции", cardNumber));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(cardNumber, card.cardNumber) && Objects.equals(cardValidTill, card.cardValidTill) && Objects.equals(cardCVV, card.cardCVV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, cardValidTill, cardCVV);
    }
}
