package com.joselct17.paymybuddy.controller;

import com.joselct17.paymybuddy.config.CurrencyPermited;
import com.joselct17.paymybuddy.model.BankTransaction;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.model.dto.BankTransactionFormDTO;
import com.joselct17.paymybuddy.service.interfaces.IBankTransactionService;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;




@Controller
public class BankTransactionController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    IUserService userService;

    @Autowired
    IBankTransactionService bankTransactionService;

    @Autowired
    private CurrencyPermited currencyPermited;

    Logger logger = LoggerFactory.getLogger(BankTransactionController.class);

    @GetMapping("/banktransaction")
    public String getBanktransaction(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            Model model) {
        logger.info("GET: /banktransaction");

        User user = userService.getCurrentUser();
        model.addAttribute("user", user);//needed to display current user amount + currency
        model.addAttribute("paged", bankTransactionService.getCurrentUserBankTransactionPage(pageNumber, size));

        BankTransactionFormDTO bankTransactionFormDTO = new BankTransactionFormDTO();
        bankTransactionFormDTO.setCurrency(user.getCurrency()); //sets by default the form currency to currency of the user.
        bankTransactionFormDTO.setGetOrSendRadioOptions("send"); //sets by default the form GetOrSendRadioOptions to "send".
        model.addAttribute("banktransactionFormDTO",bankTransactionFormDTO);
        return "banktransaction";
    }


    @Transactional
    @PostMapping("/banktransaction")
    public String postBanktransactionGetMoney(
            @Valid @ModelAttribute("banktransactionFormDTO") BankTransactionFormDTO bankTransactionFormDTO,
            BindingResult bindingResult,
            Model model) {

        logger.info("POST: /banktransaction");
        User connectedUser = userService.getCurrentUser();
        model.addAttribute("user", connectedUser);//list of transactions + preferred currency
        model.addAttribute("paged", bankTransactionService.getCurrentUserBankTransactionPage(1, 5));

        if (bindingResult.hasErrors()) {
            return "banktransaction";
        }

        //cross-record validation : Currency not allowed in our list
        if ( !currencyPermited.getCurrencyList().contains(bankTransactionFormDTO.getCurrency()) ) {
            bindingResult.rejectValue("currency", "UnknownCurrency", "This currency is not allowed.");
            return "banktransaction";
        }

        BankTransaction bankTransaction = convertToEntity(bankTransactionFormDTO);

        //cross-record validation : calculate user amount after transaction, UserAmountException thrown if amount is invalid
        BigDecimal connectedUserAmountAfterTransaction;
        connectedUserAmountAfterTransaction = userService.sumAmountCalculate(connectedUser, bankTransaction.getAmount(), bankTransaction.getCurrency());

        //update user amount:
        connectedUser.setAmount(connectedUserAmountAfterTransaction);

        //create banktransaction:
        bankTransactionService.create(bankTransaction);


        //redirection do not use the current Model, it goes to GET /bantransaction
        return "redirect:/banktransaction";
    }






















    /**
     * This method converts a DTO object to an Entity
     *
     * @param bankTransactionFormDTO
     * @return Entity version of the DTO
     *
     * @see <a href="https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application"> Entity/DTO conversion
     */
    private BankTransaction convertToEntity(BankTransactionFormDTO bankTransactionFormDTO) {

        BankTransaction bankTransaction = modelMapper.map(bankTransactionFormDTO, BankTransaction.class);

        //If money sent to bank then amount becomes negative:
        if (bankTransactionFormDTO.getGetOrSendRadioOptions().equalsIgnoreCase("send")) {
            bankTransaction.setAmount(bankTransactionFormDTO.getAmount().negate());
        }
        return bankTransaction;
    }
}
