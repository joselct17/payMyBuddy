package com.joselct17.paymybuddy.service;


import com.joselct17.paymybuddy.model.Transaction;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.ITransactionRepository;
import com.joselct17.paymybuddy.service.implementation.TransactionServiceImpl;
import com.joselct17.paymybuddy.service.interfaces.ILocalDateTimeService;
import com.joselct17.paymybuddy.service.interfaces.IPagingService;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import com.joselct17.paymybuddy.utils.paging.Paged;
import com.joselct17.paymybuddy.utils.paging.Paging;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionServiceTest {

    @Mock
    IUserService userService;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Mock
    IPagingService pagingService;

    @Mock
    ILocalDateTimeService localDateTimeServiceImpl;

    @Mock
    ITransactionRepository transactionRepository;

    LocalDateTime now;
    User user1;
    User user2;

    Transaction userTransaction;

    @BeforeEach
    void initialize() {
        LocalDateTime now= LocalDateTime.of(2022, Month.MARCH, 16, 11,24,55);
        user1 = new User(1,"John", "Doe", "jhonDoe@gmail.com", "password", "1XA558", Currency.getInstance("EUR"), new BigDecimal(1000), true, LocalDateTime.of(2023,10,10, 10,10,10), new HashSet<>(), new HashSet<>(), new ArrayList<>(), new HashSet<>());
        user2 = new User(2, "Jane", "Doe", "janeDoe@gmail.com", "password", "1547SSD", Currency.getInstance("USD"),new BigDecimal(1000),true, LocalDateTime.of(2023,05,12,12,12,12), new HashSet<>(), new HashSet<>(), new ArrayList<>(), new HashSet<>());

       userTransaction = new Transaction(null, user1, user2, now, new BigDecimal("50"), Currency.getInstance("EUR"), new BigDecimal(1.3)  , "Remboursement");
    }

    @Test
    void testCreate() {
        //Arrange

        when(userService.getCurrentUser()).thenReturn(user1);
        when(localDateTimeServiceImpl.now()).thenReturn(now);

       Transaction userTransactionExpected = new Transaction(null, user1, user2, now, new BigDecimal("90"), Currency.getInstance("USD"), new BigDecimal("10"), "Remboursement");

        Transaction userTransactionToCreate = new Transaction(); //initial data data from DTO
        userTransactionToCreate.setUserDestination(user2);
        userTransactionToCreate.setAmount(new BigDecimal("1000"));
        userTransactionToCreate.setCurrency(Currency.getInstance("USD"));;

        Map<String,BigDecimal> fees = new HashMap<>();
        fees.put("fees", new BigDecimal("10"));
        fees.put("finalAmount", new BigDecimal("90"));

        //Act
        transactionService.create(userTransactionToCreate, fees);

        // Assert
        verify(transactionRepository, times(1)).save(userTransactionToCreate);
        assertNull(userTransactionToCreate.getId());

       assertEquals(userTransactionExpected.getAmount(),userTransactionToCreate.getAmount());
        assertEquals(userTransactionExpected.getCurrency(),userTransactionToCreate.getCurrency());
        assertEquals(userTransactionExpected.getDateTime(),userTransactionToCreate.getDateTime());
        assertEquals(userTransactionExpected.getFees(),userTransactionToCreate.getFees());
        assertEquals(userTransactionExpected.getId(),userTransactionToCreate.getId());
        assertEquals(userTransactionExpected.getUserDestination(),userTransactionToCreate.getUserDestination());
        assertEquals(userTransactionExpected.getUserSource(),userTransactionToCreate.getUserSource());

    }

    @Test
    void test_getCurrentUserUserTransactionPage() throws Exception {
        // Arrange

        //Page:
        when(userService.getCurrentUser()).thenReturn(user1);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(userTransaction);
        Page<Transaction> expectedPage = new PageImpl<>(transactions);
        when(transactionRepository.findUserTransactionByUserId(any(Integer.class),any(Pageable.class))).thenReturn(expectedPage);
        //Paging:
        Paging expectedPaging = new Paging(false, false, 1, new ArrayList<>());
        when(pagingService.of(any(Integer.class), any(Integer.class))).thenReturn(expectedPaging);

        // Act
        Paged<Transaction> pagedUserTransaction =  transactionService.getCurrentUserUserTransactionPage(1, 5);

        // Assert
        assertEquals(expectedPage,pagedUserTransaction.getPage());
        assertEquals(expectedPaging,pagedUserTransaction.getPaging());

    }
}
