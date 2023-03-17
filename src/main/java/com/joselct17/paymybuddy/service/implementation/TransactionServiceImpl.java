package com.joselct17.paymybuddy.service.implementation;

import com.joselct17.paymybuddy.model.Transaction;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.ITransactionRepository;
import com.joselct17.paymybuddy.service.interfaces.ILocalDateTimeService;
import com.joselct17.paymybuddy.service.interfaces.ITransactionService;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private IUserService userService;

    @Autowired
    private ILocalDateTimeService localDateTimeService;

    @Autowired
    private ITransactionRepository transactionRepository;

    @Override
    public Object getCurrentUserUserTransactionPage(int pageNumber, int size) {
        return null;
    }

    @Override
    public void create(Transaction transaction, Map<String, BigDecimal> fees) {

        User currentUser = userService.getCurrentUser();

        transaction.setDateTime(localDateTimeService.now());
        transaction.setUserSource(currentUser);
        transaction.setFees(fees.get("fees"));
        transaction.setAmount(fees.get("finalAmount"));

        transactionRepository.save(transaction);


    }
}
