package com.joselct17.paymybuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);


    @GetMapping("/home")
    public String indexPage() {
        logger.info("GET: /" );
        return "index";
    }
}
