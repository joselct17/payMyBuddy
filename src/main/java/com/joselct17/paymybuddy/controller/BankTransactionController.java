package com.joselct17.paymybuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@Controller
public class BankTransactionController {

    Logger logger = LoggerFactory.getLogger(BankTransactionController.class);

    @GetMapping("/banktransaction")
    public String bankTransaction() {
        logger.info("GET: /banktransaction" );
        return "bank";
    }
}
