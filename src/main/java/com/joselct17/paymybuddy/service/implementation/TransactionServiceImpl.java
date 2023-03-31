package com.joselct17.paymybuddy.service.implementation;

import com.joselct17.paymybuddy.controller.TransactionController;
import com.joselct17.paymybuddy.model.Transaction;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.ITransactionRepository;
import com.joselct17.paymybuddy.service.interfaces.ILocalDateTimeService;
import com.joselct17.paymybuddy.service.interfaces.IPagingService;
import com.joselct17.paymybuddy.service.interfaces.ITransactionService;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import com.joselct17.paymybuddy.utils.paging.Paged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private IPagingService pagingService;

    @Override
    public Paged<Transaction> getCurrentUserUserTransactionPage(int pageNumber, int size) {

        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Transaction> page = transactionRepository.findUserTransactionByUserId(userService.getCurrentUser().getId(),request);

        return new Paged<>(page, pagingService.of(page.getTotalPages(), pageNumber));
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
