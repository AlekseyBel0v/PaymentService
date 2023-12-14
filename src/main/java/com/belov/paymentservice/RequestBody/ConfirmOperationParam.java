package com.belov.paymentservice.RequestBody;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmOperationParam {
    @NotNull(message = "ID операции не указан")
    @Pattern(regexp = "\\d+", message = "ID операции должен содержать только цифры")
    private String operationId;
    @NotNull
    @Pattern(regexp = "\\d+", message = "Неверный код подтвержения операции")
    private String code;

    public ConfirmOperationParam() {
    }
}
