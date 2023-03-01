package com.joselct17.PayMyBuddy.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    Logger logger = LoggerFactory.getLogger(LoginController.class);


    @GetMapping("/login")
    public String login() {
        logger.info("GET: /login");
        return "login";
    }
}
