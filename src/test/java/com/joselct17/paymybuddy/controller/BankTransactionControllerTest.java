package com.joselct17.paymybuddy.controller;


import com.joselct17.paymybuddy.model.BankTransaction;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BankTransactionControllerTest {

    @Autowired
    IUserService userService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "test@test.com")
    void getBankTransaction() throws Exception {
        mockMvc.perform(get("/banktransaction"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="test@test.com") //test@mail.com exists in our test database
    void postBanktransactionSendMoney() throws Exception {
        mockMvc.perform(post("/banktransaction")
                        .param("getOrSendRadioOptions", "send")
                        .param("amount", "100")
                        .param("currency", "USD")
                        .with(csrf())
                )//.andDo(print())
                .andExpect(status().is3xxRedirection());

        User userTest = userService.findByEmail("test@test.com");
        assertEquals(new BigDecimal("800.00"),userTest.getAmount(), "1000 - bank transaction");
        Set<BankTransaction> banktransactions = userTest.getBankTransactions();
        assertEquals(1,banktransactions.size());

        BankTransaction bankTransaction = banktransactions.iterator().next();
        assertEquals(userTest, bankTransaction.getUser());
        //get time in second between transaction datetime and now :
        long durationInSec = Duration.between(bankTransaction.getDateTime(), LocalDateTime.now()).getSeconds();
        assertTrue(durationInSec<2, "time difference between transaction time and now can't be more than 2s");
        assertEquals(new BigDecimal("-100"),bankTransaction.getAmount());
        assertEquals(Currency.getInstance("USD"),bankTransaction.getCurrency());


    }

    @Test
    @WithMockUser(username="test@test.com") //test@mail.com exists in our test database
    void postBanktransactionGetMoney() throws Exception {
        mockMvc.perform(post("/banktransaction")
                        .param("getOrSendRadioOptions", "get")
                        .param("amount", "100")
                        .param("currency", "USD")
                        .with(csrf())
                )//.andDo(print())
                .andExpect(status().is3xxRedirection());

        User userTest = userService.findByEmail("test@test.com");
        assertEquals(new BigDecimal("1100.00"),userTest.getAmount(), "1000 + bank transaction");
        Set<BankTransaction> banktransactions = userTest.getBankTransactions();
        assertEquals(1,banktransactions.size());

        BankTransaction bankTransaction = banktransactions.iterator().next();
        assertEquals(userTest, bankTransaction.getUser());
        //get time in second between transaction datetime and now :
        long durationInSec = Duration.between(bankTransaction.getDateTime(),LocalDateTime.now()).getSeconds();
        assertTrue(durationInSec<2, "time difference between transaction time and now can't be more than 2s");
        assertEquals(new BigDecimal("100"),bankTransaction.getAmount());
        assertEquals(Currency.getInstance("USD"),bankTransaction.getCurrency());

    }

}
