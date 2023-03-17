package com.joselct17.paymybuddy.service;


import com.joselct17.paymybuddy.model.BankTransaction;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.IBankTransactionRepository;
import com.joselct17.paymybuddy.service.implementation.BankTransactionServicenImpl;
import com.joselct17.paymybuddy.service.implementation.LocalDateTimeServiceImpl;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Currency;
import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class BankTransactionTest {

    @Mock
    IUserService userService;

    @InjectMocks
    BankTransactionServicenImpl bankTransactionServiceImpl;

    @Mock
    IBankTransactionRepository bankTransactionRepository;

    @Mock
    LocalDateTimeServiceImpl localDateTimeServiceImpl;

    LocalDateTime now;
    User user1;
    BankTransaction bankTransaction1;

    @BeforeEach
    void initialize() {
        now = LocalDateTime.of(2019, Month.MARCH, 28, 14, 33, 48);
        user1 = new User(1, "John", "Doe", "john@doe.com", "password", "XA5662AS", Currency.getInstance("EUR"), new HashSet<>(), new HashSet<>(), new HashSet<>());

        bankTransaction1 = new BankTransaction(50, user1,  now,new Double(1000), Currency.getInstance("USD"), "XA5445");
    }


    @Test
    void testCreate() {
        //Arrange

        when(userService.getCurrentUser()).thenReturn(user1);
        when(localDateTimeServiceImpl.now()).thenReturn(now);

        BankTransaction bankTransactionExpected = new BankTransaction(null, user1,  now, new Double("1000"), Currency.getInstance("USD"), "XA5445");

        BankTransaction bankTransactionToCreate = new BankTransaction();
        bankTransactionToCreate.setAmount(new Double("1000"));
        bankTransactionToCreate.setCurrency(Currency.getInstance("USD"));;

        //Act
        bankTransactionServiceImpl.create(bankTransactionToCreate);

        // Assert
        verify(bankTransactionRepository, times(1)).save(bankTransactionToCreate);
        assertNull(bankTransactionToCreate.getId());
        assertEquals(bankTransactionExpected.getAmount(),bankTransactionToCreate.getAmount());
        assertEquals(bankTransactionExpected.getBankAccount(),bankTransactionToCreate.getBankAccount());
        assertEquals(bankTransactionExpected.getCurrency(),bankTransactionToCreate.getCurrency());
        assertEquals(bankTransactionExpected.getDateTime(),bankTransactionToCreate.getDateTime());
        assertEquals(bankTransactionExpected.getId(),bankTransactionToCreate.getId());
        assertEquals(bankTransactionExpected.getUser(),bankTransactionToCreate.getUser());

    }

}
