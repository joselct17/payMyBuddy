package com.joselct17.paymybuddy.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Currency;

public class TransactionFormDTO {

    @NotNull
    private Integer userDestinationId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private Currency currency;



    public Integer getUserDestinationId() {
        return userDestinationId;
    }

    public void setUserDestinationId(Integer userDestinationId) {
        this.userDestinationId = userDestinationId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
