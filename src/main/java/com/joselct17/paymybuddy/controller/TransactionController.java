package com.joselct17.paymybuddy.controller;

import com.joselct17.paymybuddy.config.CurrencyPermited;
import com.joselct17.paymybuddy.model.Transaction;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.model.dto.TransactionFormDTO;
import com.joselct17.paymybuddy.service.interfaces.ICalculationService;
import com.joselct17.paymybuddy.service.interfaces.ITransactionService;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Map;


@Controller
public class TransactionController {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ITransactionService iTransactionService;

    @Autowired
    private CurrencyPermited currencyPermited;

    @Autowired
    private ICalculationService calculationService;


    @GetMapping("/usertransaction")
    public String getUsertransaction(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            Model model) {

        User user = iUserService.getCurrentUser();
        model.addAttribute("user", user);//needed to display current user amount + currency
        model.addAttribute("paged", iTransactionService.getCurrentUserUserTransactionPage(pageNumber, size));
        model.addAttribute("allUsers", iUserService.findAll());

        TransactionFormDTO transactionFormDTO = new TransactionFormDTO();
        //transactionFormDTO.setCurrency(user.getCurrency()); //sets by default the form currency to currency of the user.
        model.addAttribute("transactionFormDTO",transactionFormDTO);

        return "transaction";
    }

    @Transactional
    @PostMapping("/usertransaction")
    public String postTransactionGetMoney(@Valid @ModelAttribute("transactionForm") TransactionFormDTO transactionFormDTO, BindingResult bindingResult, Model model) {

        User userSource = iUserService.getCurrentUser();

        model.addAttribute("user", userSource);

        model.addAttribute("paged", iTransactionService.getCurrentUserUserTransactionPage(1,5));

        if (bindingResult.hasErrors()) {
            return "usertransaction";
        }

        User userDestination = iUserService.findById(transactionFormDTO.getUserDestinationId());


        if (!userSource.getConnections().contains(userDestination)) {
            bindingResult.rejectValue("userDestinationId", "userDestinationNotABuddy", "Please select a buddy !");
            return "usertransaction";
        }

        if (!currencyPermited.getCurrencyList().contains(transactionFormDTO.getCurrency())) {
            bindingResult.rejectValue("currency", "NotAllowedCurrency", "This currency is not permitted");
            return "usertransaction";
        }

        //Calculate fees:
        Map<String, BigDecimal> feesMap = calculationService.calculateFees(transactionFormDTO.getAmount());

        //DTO to Entity
        Transaction transaction = convertToEntity(transactionFormDTO, userDestination);

        //cross-record validation : calculate user amount after transaction, UserAmountException thrown if amount is invalid

        BigDecimal sourceUserAmountAfterTransaction;

        BigDecimal destinationUserAmountAfterTransaction;

        try {
            sourceUserAmountAfterTransaction = iUserService.sumAmountCalculate(
                    userSource,
                    transaction.getAmount().negate(),
                    transaction.getCurrency()
            );
            destinationUserAmountAfterTransaction = iUserService.sumAmountCalculate(
                    userDestination,
                    feesMap.get("finalAmount"),
                    transaction.getCurrency()
            );

        }catch (Exception e) {
            bindingResult.rejectValue("amount",  e.getMessage());
            return "usertransaction";
        }

        //update users amounts:
        userSource.setAmount(sourceUserAmountAfterTransaction);
        userDestination.setAmount(destinationUserAmountAfterTransaction);


        //create usertransaction
        iTransactionService.create(transaction, feesMap);


        return "redirect:/usertransaction";
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
