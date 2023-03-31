package com.joselct17.paymybuddy.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
public class TransactionFormDTO {

    @NotNull
    private Integer userDestinationId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private Currency currency;

    @NotNull
    private String description;

}
