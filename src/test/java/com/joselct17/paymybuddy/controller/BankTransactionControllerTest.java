package com.joselct17.paymybuddy.controller;


import com.joselct17.paymybuddy.model.BankTransaction;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.IUserRepository;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.jdbc.JdbcTestUtils;
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


    @BeforeEach
    void clearDatabase(@Autowired JdbcTemplate jdbcTemplate) {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "banktransaction");
        jdbcTemplate.update("DELETE FROM user_roles");
        jdbcTemplate.update("DELETE FROM user");
        jdbcTemplate.update("INSERT INTO user (first_name, last_name, amount, currency, bank_account, date_time_inscription, email, password) " +
                "VALUES ('John', 'Doe', '1000', 'USD', 'XACSFDC', '2023-04-07 09:23:11.673329', 'john@doe.com', 'VDGZBZJBdegbh541'), " +
                "('Jane', 'Doe', '2000', 'USD', 'XACSFDC', '2023-04-07 09:23:11.673329', 'jane@doe.com', 'VDGZBZJBdegbh542')");    }



    @Test
    @WithMockUser(username = "john@doe.com")
    void getBankTransaction() throws Exception {
        mockMvc.perform(get("/banktransaction"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username="john@doe.com") //test@test.com exists in our test database
    void postBanktransactionGetMoney() throws Exception {
        mockMvc.perform(post("/banktransaction")
                        .param("getOrSendRadioOptions", "get")
                        .param("amount", "100")
                        .param("currency", "USD")
                        .with(csrf())
                )//.andDo(print())
                .andExpect(status().is3xxRedirection());

        User userTest = userService.findByEmail("john@doe.com");
        assertEquals(new BigDecimal("1100.00"), userTest.getAmount(), "1000 + bank transaction");
        Set<BankTransaction> banktransactions = userTest.getBankTransactions();
        assertEquals(1, banktransactions.size());

        BankTransaction bankTransaction = banktransactions.iterator().next();
        assertEquals(userTest, bankTransaction.getUser());
        //get time in second between transaction datetime and now :
        long durationInSec = Duration.between(bankTransaction.getDateTime(), LocalDateTime.now()).getSeconds();
        assertTrue(durationInSec < 2, "time difference between transaction time and now can't be more than 2s");
        assertEquals(new BigDecimal("100.00"), bankTransaction.getAmount());
        assertEquals(Currency.getInstance("USD"), bankTransaction.getCurrency());

    }

    @Test // envoi d'argent vers le compte en banque
    @WithMockUser(username="john@doe.com") //test@test.com exists in our test database
    void postBanktransactionSendMoney() throws Exception {
        mockMvc.perform(post("/banktransaction")
                        .param("getOrSendRadioOptions", "send")
                        .param("amount", "100")
                        .param("currency", "USD")
                        .with(csrf())
                )//.andDo(print())
                .andExpect(status().is3xxRedirection());

        User userTest = userService.findByEmail("john@doe.com");
        assertEquals(new BigDecimal("900.00"),userTest.getAmount(), "100 - bank transaction");
        Set<BankTransaction> banktransactions = userTest.getBankTransactions();
        assertEquals(1,banktransactions.size());

        BankTransaction bankTransaction = banktransactions.iterator().next();
        assertEquals(userTest, bankTransaction.getUser());
        //get time in second between transaction datetime and now :
        long durationInSec = Duration.between(bankTransaction.getDateTime(), LocalDateTime.now()).getSeconds();
        assertTrue(durationInSec<2, "time difference between transaction time and now can't be more than 2s");
        assertEquals(new BigDecimal("-100.00"),bankTransaction.getAmount());
        assertEquals(Currency.getInstance("USD"),bankTransaction.getCurrency());

    }

}
