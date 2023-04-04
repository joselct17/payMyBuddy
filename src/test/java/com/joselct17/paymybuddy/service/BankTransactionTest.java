package com.joselct17.paymybuddy.service;


import com.joselct17.paymybuddy.model.BankTransaction;
import com.joselct17.paymybuddy.model.Transaction;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.IBankTransactionRepository;
import com.joselct17.paymybuddy.repository.ITransactionRepository;
import com.joselct17.paymybuddy.service.implementation.BankTransactionServiceImpl;
import com.joselct17.paymybuddy.service.implementation.LocalDateTimeServiceImpl;
import com.joselct17.paymybuddy.service.interfaces.IPagingService;
import com.joselct17.paymybuddy.service.interfaces.ITransactionService;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import com.joselct17.paymybuddy.utils.paging.Paged;
import com.joselct17.paymybuddy.utils.paging.Paging;
import jakarta.transaction.UserTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
    BankTransactionServiceImpl bankTransactionServiceImpl;

    @Mock
    IBankTransactionRepository bankTransactionRepository;

    @Mock
    LocalDateTimeServiceImpl localDateTimeServiceImpl;

    @Mock
    ITransactionRepository transactionRepository;

    @Mock
    IPagingService pagingService;


    LocalDateTime now;
    User user1;
    BankTransaction bankTransaction1;

    @BeforeEach
    void initialize() {
        now = LocalDateTime.of(2019, Month.MARCH, 28, 14, 33, 48);
        user1 = new User(1, "John", "Doe", "john@doe.com", "password", "XA5662AS", Currency.getInstance("EUR"), new BigDecimal(100), true, LocalDateTime.of(2023, 10, 5, 01, 20, 55),new HashSet<>(), new HashSet<>(), new ArrayList<>(), new HashSet<>());

        bankTransaction1 = new BankTransaction(50, user1,  now,new BigDecimal(1000), Currency.getInstance("USD"), "XA5445");
    }


    @Test
    void testCreate() {
        //Arrange

        when(userService.getCurrentUser()).thenReturn(user1);
        when(localDateTimeServiceImpl.now()).thenReturn(now);

        BankTransaction bankTransactionExpected = new BankTransaction(null, user1,  now, new BigDecimal("1000"), Currency.getInstance("USD"), "XA5662AS");

        BankTransaction bankTransactionToCreate = new BankTransaction();
        bankTransactionToCreate.setAmount(new BigDecimal("1000"));
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

    @Test
    void test_getCurrentUserUserTransactionPage() throws Exception {
        // Arrange

        //Page:
        when(userService.getCurrentUser()).thenReturn(user1);
        List<BankTransaction> userTransactions = new ArrayList<>();
        userTransactions.add(bankTransaction1);
        Page<BankTransaction> expectedPage = new PageImpl<>(userTransactions);
        when(bankTransactionRepository.findBankTransactionByUserId(any(Integer.class),any(Pageable.class))).thenReturn(expectedPage);
        //Paging:
        Paging expectedPaging = new Paging(false, false, 1, new ArrayList<>());
        when(pagingService.of(any(Integer.class), any(Integer.class))).thenReturn(expectedPaging);

        // Act
        Paged<BankTransaction> pagedUserTransaction =  bankTransactionServiceImpl.getCurrentUserBankTransactionPage(1, 5);

        // Assert
        assertEquals(expectedPage,pagedUserTransaction.getPage());
        assertEquals(expectedPaging,pagedUserTransaction.getPaging());

    }

}
