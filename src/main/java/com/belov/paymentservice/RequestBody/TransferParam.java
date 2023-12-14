package com.belov.paymentservice.RequestBody;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Класс для серриализации параметров из тела пост-запроса
@Getter
@Setter
public class TransferParam {
    @NotNull(message = "Номер карты должен содержать 16 цифр")
    @Size(min = 16, max = 16, message = "Номер карты должен содержать 16 цифр")
    @Pattern(regexp = "\\d+", message = "Номер карты должен состоять только из цифр")
    String cardFromNumber;
    @FutureOrPresent(message = "Срок годности карты истек")
    Date cardFromValidTill;
    @NotNull @NotBlank @Size(min = 3, max = 3, message = "Код CVV должен содержать 3 символа")
    String cardFromCVV;
    @NotNull(message = "Номер карты должен содержать 16 цифр")
    @Size(min = 16, max = 16, message = "Номер карты должен содержать 16 цифр")
    @Pattern(regexp = "\\d+", message = "Номер карты должен состоять только из цифр")
    String cardToNumber;
    Amount amount;

    public TransferParam() {
    }

    public void setCardFromValidTill(String cardFromValidTill) throws ParseException {
        this.cardFromValidTill = new SimpleDateFormat("MM/yy").parse(cardFromValidTill);
    }
}