package com.joselct17.paymybuddy.controller;

import com.joselct17.paymybuddy.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ConnectionController {

    @Autowired
    IUserService iUserService;


    @GetMapping("/connection")
    public String connection(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                             Model model) {
        model.addAttribute("paged", iUserService.getCurrentUserConnectionPage(pageNumber, size));
        return "connection";
    }

}
