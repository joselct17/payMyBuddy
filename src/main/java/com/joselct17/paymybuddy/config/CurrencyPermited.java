package com.joselct17.paymybuddy.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Getter
@Component
public class CurrencyPermited {

    List<Currency> currencyList;


    /**
     * Constructor that uses application.properties file to populate currenciesAllowedList.
     * @param permittedCurrencyCodes list of allowed currencies in our application, comes from comma-separated values of the properties file
     */
    @Autowired
    public CurrencyPermited(@Value("${currencies.permitted}") String[] permittedCurrencyCodes) {

        this.currencyList = new ArrayList<>();
        for(String code: permittedCurrencyCodes) {
            this.currencyList.add(Currency.getInstance(code));
        }
    }
}
