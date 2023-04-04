package com.joselct17.paymybuddy.controller;

import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.service.implementation.CostumDetailsService;
import com.joselct17.paymybuddy.service.interfaces.ITransactionService;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ConnectionController {

    @Autowired
    IUserService iUserService;

    @Autowired
    CostumDetailsService costumDetailsService;


    @GetMapping("/connection")
    public String connection(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                             Model model) {
        User user = iUserService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("paged", iUserService.getCurrentUserConnectionPage(pageNumber, size));
        return "connection";
    }


    @PostMapping("/connection")
    public String connectionAdd(@RequestParam String email , Model model) {

        User user = iUserService.getCurrentUser();
        //check connection mail exists in DB:
        if ( !iUserService.existsByEmail(email) ) {
            model.addAttribute("error", "Email Unknown");
            model.addAttribute("paged", iUserService.getCurrentUserConnectionPage(1, 5));
            return "connection";
        }
        //Check connection to himself:
        if ( user.getEmail().equalsIgnoreCase(email) ) {
            model.addAttribute("error", "You can't add yourself as a connection");
            model.addAttribute("paged", iUserService.getCurrentUserConnectionPage(1, 5));
            return "connection";
        }

        User newConnection = iUserService.findByEmail(email);
        user.getConnections().add(newConnection);
        iUserService.update(user);

        model.addAttribute("paged", iUserService.getCurrentUserConnectionPage(1, 5));
        return "redirect:/usertransaction";
    }

    @PostMapping("/connectionDelete")
    public String connectionDelete(@RequestParam Long id) {

        User user = iUserService.getCurrentUser();
        user.getConnections().removeIf(connectionUser -> (connectionUser.getId().equals(id)) );
        iUserService.update(user);

        return "redirect:/connection";
    }

}
