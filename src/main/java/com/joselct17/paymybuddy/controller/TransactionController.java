package com.joselct17.paymybuddy.controller;

import com.joselct17.paymybuddy.model.Transaction;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.model.dto.TransactionFormDTO;
import com.joselct17.paymybuddy.service.interfaces.ITransactionService;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TransactionController {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ITransactionService iTransactionService;


    @GetMapping("/usertransaction")
    public String getUsertransaction(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            Model model) {

        User user = iUserService.getCurrentUser();
        model.addAttribute("user", user);//needed to display current user amount + currency
        model.addAttribute("paged", iTransactionService.getCurrentUserUserTransactionPage(pageNumber, size));

        TransactionFormDTO transactionFormDTO = new TransactionFormDTO();
        transactionFormDTO.setCurrency(user.getCurrency()); //sets by default the form currency to currency of the user.
        model.addAttribute("transactionFormDTO",transactionFormDTO);
        return "userTransaction";
    }

















    /**
     * This method converts a DTO object to an Entity
     *
     * @param transactionFormDTO
     * @return Entity version of the DTO
     *
     * @see <a href="https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application"> Entity/DTO conversion
     */
    private Transaction convertToEntity(TransactionFormDTO transactionFormDTO, User userDestination) {


        Transaction transaction =  modelMapper.map(transactionFormDTO, Transaction.class);

        transaction.setId(null);
        transaction.setUserDestination(userDestination);

        return transaction;
    }


}
