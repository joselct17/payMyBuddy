package com.joselct17.paymybuddy.service.interfaces;

import com.joselct17.paymybuddy.model.BankTransaction;
import com.joselct17.paymybuddy.utils.paging.Paged;

public interface IBankTransactionService {
    void create(BankTransaction bankTransaction);

    void deleteAll();
   public Paged<BankTransaction>getCurrentUserBankTransactionPage(int pageNumber, int size);
}
