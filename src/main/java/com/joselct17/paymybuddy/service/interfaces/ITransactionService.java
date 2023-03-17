package com.joselct17.paymybuddy.service.interfaces;

import com.joselct17.paymybuddy.model.Transaction;

import java.math.BigDecimal;
import java.util.Map;

public interface ITransactionService {
    Object getCurrentUserUserTransactionPage(int pageNumber, int size);

    void create(Transaction userTransactionToCreate, Map<String, BigDecimal> fees);
}
