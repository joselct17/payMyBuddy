package com.joselct17.paymybuddy.service.interfaces;

import com.joselct17.paymybuddy.model.BankTransaction;

public interface IBankTransactionService {
    void create(BankTransaction bankTransactionToCreate);
}
