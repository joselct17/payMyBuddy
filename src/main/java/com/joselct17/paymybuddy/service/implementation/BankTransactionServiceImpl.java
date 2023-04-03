package com.joselct17.paymybuddy.service.implementation;

import com.joselct17.paymybuddy.model.BankTransaction;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.IBankTransactionRepository;
import com.joselct17.paymybuddy.service.interfaces.IBankTransactionService;
import com.joselct17.paymybuddy.service.interfaces.ILocalDateTimeService;
import com.joselct17.paymybuddy.service.interfaces.IPagingService;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import com.joselct17.paymybuddy.utils.paging.Paged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BankTransactionServiceImpl implements IBankTransactionService {

    @Autowired
    IUserService userService;

    @Autowired
    IPagingService pagingService;

    @Autowired
    IBankTransactionRepository bankTransactionRepository;

    @Autowired
    ILocalDateTimeService localDateTimeService;



    @Override
    public void create(BankTransaction bankTransaction) {
        User currentUser= userService.getCurrentUser();

        bankTransaction.setBankAccount(currentUser.getBankAccount());
        bankTransaction.setDateTime(localDateTimeService.now());
        bankTransaction.setUser(currentUser);

        bankTransactionRepository.save(bankTransaction);
    }

    @Override
    public void deleteAll() {
        bankTransactionRepository.deleteAll();
    }

    @Override
    public Paged<BankTransaction>getCurrentUserBankTransactionPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<BankTransaction> page = bankTransactionRepository.findBankTransactionByUserId(userService.getCurrentUser().getId(), request);

        return new Paged<>(page, pagingService.of(page.getTotalPages(), pageNumber));
    }
}
