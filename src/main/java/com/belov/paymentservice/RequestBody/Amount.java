package com.belov.paymentservice.RequestBody;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Amount {
    @Min(value = 1, message = "минимальная сумма для перевода - 1")
    @NotNull(message = "Необходимо указать сумму перевода")
    Integer value;
    @NotNull(message = "Необходимо указать валюту")
    String currency;

    public Amount() {
    }
}
