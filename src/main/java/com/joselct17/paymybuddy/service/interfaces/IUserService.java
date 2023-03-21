package com.joselct17.paymybuddy.service.interfaces;

import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.utils.paging.Paged;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public interface IUserService {


    Iterable<User> findAll();

    void create(User user);


    User findById(Integer id);


    User findByEmail(String email);


    Boolean existsByEmail(String email);

    User getCurrentUser();

    Paged<User> getCurrentUserConnectionPage(int pageNumber, int size);

    BigDecimal sumAmountCalculate(User userSource, BigDecimal negate, Currency currency);
}
