package com.joselct17.paymybuddy.model.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
public class BankTransactionFormDTO {

    @NotBlank
    private String getOrSendRadioOptions;

    @NotNull
    @Positive
    private BigDecimal amount;


    private Currency currency;
}
