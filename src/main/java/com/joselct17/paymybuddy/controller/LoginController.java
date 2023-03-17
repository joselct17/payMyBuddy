package com.joselct17.paymybuddy.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {


    Logger logger = LoggerFactory.getLogger(LoginController.class);


    @GetMapping("/login")
    public String login() {
        logger.info("GET: /login");
        return "login";
    }
}
