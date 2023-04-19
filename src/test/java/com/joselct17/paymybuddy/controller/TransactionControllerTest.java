package com.joselct17.paymybuddy.controller;

import com.joselct17.paymybuddy.config.SpringWebTestConfig;
import com.joselct17.paymybuddy.model.Transaction;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.ITransactionRepository;
import com.joselct17.paymybuddy.service.implementation.TransactionServiceImpl;
import com.joselct17.paymybuddy.service.interfaces.ICalculationService;
import com.joselct17.paymybuddy.service.interfaces.ILocalDateTimeService;
import com.joselct17.paymybuddy.service.interfaces.ITransactionService;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import com.joselct17.paymybuddy.utils.paging.Paged;
import com.joselct17.paymybuddy.utils.paging.Paging;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(controllers = TransactionController.class)
@Import(SpringWebTestConfig.class)
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IUserService userServiceMock;

    @MockBean
    private ICalculationService calculationService;

    @MockBean
    private ITransactionService transactionService;

    User user1;
    User user2;
    User user99;
    Transaction transaction1;
    Transaction transaction2;
    Transaction transaction3;
    Paged<Transaction> paged;

    LocalDateTime now;
    @BeforeEach
    void setup() {

        now =  LocalDateTime.of(2023,04,04,18,18,00);

        user1 = new User(1, "firstname1", "lastname1", "user1@mail.com", "password1", "1AX256", Currency.getInstance("USD"),new BigDecimal(100),true, now,  new HashSet<>(), new HashSet<>(), new ArrayList<>(), new HashSet<>() );
        user2 = new User(2, "firstname2", "lastname2", "user2@mail.com","password21", "1AX256", Currency.getInstance("USD"), new BigDecimal(200), true,   now, new HashSet<>(),  new HashSet<>(), new ArrayList<>(), new HashSet<>() );
        user99 = new User(99, "firstname99", "lastname99", "anothertest@mail.com","password21", "1AX256", Currency.getInstance("USD"), new BigDecimal(200), true,   now, new HashSet<>(),  new HashSet<>(), new ArrayList<>(), new HashSet<>() );

        transaction1 = new Transaction(1, user1, user2, now, new BigDecimal("100.10"),Currency.getInstance("EUR"), new BigDecimal("1.00") , "Remboursement");
        transaction2 = new Transaction(2, user1, user2, now, new BigDecimal("200.20"), Currency.getInstance("USD"), new BigDecimal("2.00"), "Voyage Madagascar");
        transaction3 = new Transaction(3, user1, user2, now, new BigDecimal("300.30"),Currency.getInstance("GBP"), new BigDecimal("3.00"), "Location Voiture");

        Transaction[] userTransactionArray = {transaction1,transaction2,transaction3};
        List<Transaction> userTransactions = Arrays.asList(userTransactionArray);
        Page<Transaction> pagedUserTransaction = new PageImpl<Transaction>(userTransactions);

        Paging paging = Paging.of(1, 1); //, 5);
        paged = new Paged<Transaction>(pagedUserTransaction, paging);

    }






    @WithUserDetails("jane@doe.com") //user from SpringSecurityWebTestConfig.class
    @Test
    void PostUserTransaction_ShouldSucceedAndRedirected() throws Exception {
        //ARRANGE
        user1.getConnections().add(user99);
        when(userServiceMock.getCurrentUser()).thenReturn(user1);
        when(transactionService.getCurrentUserUserTransactionPage(1, 5)).thenReturn(paged); //display list of usertransactions
        when(userServiceMock.findById(99)).thenReturn(user99);
        //feesMap:
        BigDecimal transactionAmount = new BigDecimal("100");
        Currency transactionCurrency = Currency.getInstance("USD");
        Map<String, BigDecimal> feesMap = new HashMap<>();
        feesMap.put("finalAmount", new BigDecimal("99"));
        feesMap.put("fees", new BigDecimal("1"));
        when(calculationService.calculateFees(transactionAmount)).thenReturn(feesMap);
        //calculate amounts after transaction
        when(userServiceMock.sumAmountCalculate(user1,new BigDecimal("-100"),transactionCurrency))
                .thenReturn(new BigDecimal("0.00"));
        when(userServiceMock.sumAmountCalculate(user99,new BigDecimal("99"),transactionCurrency))
                .thenReturn(new BigDecimal("299.00"));

        //ACT+ASSERT:
        mockMvc.perform(post("/usertransaction")
                        .param("amount", "100")
                        .param("currency", "USD")
                        .param("userDestinationId","99")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection()) //redirection to usertransaction page
                .andExpect(redirectedUrl("/usertransaction"))
        ;

        assertEquals("expected result is 0.00", new BigDecimal("0.00"), user1.getAmount());
        assertEquals("expected result is 299.00", new BigDecimal("299.00"), user99.getAmount());
        verify(transactionService,times(1)).create(any(Transaction.class),any(Map.class));
    }

}
