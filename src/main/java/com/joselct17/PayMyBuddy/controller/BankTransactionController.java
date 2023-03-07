package com.joselct17.PayMyBuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BankTransactionController {

    Logger logger = LoggerFactory.getLogger(BankTransactionController.class);

    @GetMapping("/banktransaction")
    public String bankTransaction() {
        logger.info("GET: /banktransaction" );
        return "banktransaction";
    }
}
