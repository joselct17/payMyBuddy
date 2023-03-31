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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;


//    @GetMapping("/usertransaction")
//    public String getUsertransaction(
//            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
//            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
//            Model model,
//            OAuth2AuthenticationToken authentication) {
//
//        User user = iUserService.getCurrentUser();
//        model.addAttribute("user", user);//needed to display current user amount + currency
//        model.addAttribute("paged", iTransactionService.getCurrentUserUserTransactionPage(pageNumber, size));
//       // model.addAttribute("userEmail", iUserService.findByEmail(user.getEmail()));
//
//        TransactionFormDTO transactionFormDTO = new TransactionFormDTO();
//        //transactionFormDTO.setCurrency(user.getCurrency()); //sets by default the form currency to currency of the user.
//        model.addAttribute("transactionForm",transactionFormDTO);
//
//        OAuth2AuthorizedClient client = authorizedClientService
//                .loadAuthorizedClient(
//                        authentication.getAuthorizedClientRegistrationId(),
//                        authentication.getName());
//
//        String userInfoEndpointUri = client.getClientRegistration()
//                .getProviderDetails().getUserInfoEndpoint().getUri();
//
//        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
//            RestTemplate restTemplate = new RestTemplate();
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
//                    .getTokenValue());
//            HttpEntity entity = new HttpEntity("", headers);
//            ResponseEntity<Map> response = restTemplate
//                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
//            Map userAttributes = response.getBody();
//            model.addAttribute("name", userAttributes.get("name"));
//        }
//
//        return "transaction";
//    }


    @GetMapping("/usertransaction")
    public String getUsertransaction(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            Model model) {


        User user = iUserService.getCurrentUser();
        model.addAttribute("user", user);//needed to display current user amount + currency
        model.addAttribute("paged", iTransactionService.getCurrentUserUserTransactionPage(pageNumber, size));

        TransactionFormDTO userTransactionFormDTO = new TransactionFormDTO();
        //userTransactionFormDTO.setCurrency(user.getCurrency()); //sets by default the form currency to currency of the user.
        model.addAttribute("transactionForm",userTransactionFormDTO);
        return "transaction";
    }


    @Transactional
    @PostMapping("/usertransaction")
    public String postTransactionGetMoney(@Valid @ModelAttribute("transactionForm") TransactionFormDTO transactionFormDTO, BindingResult bindingResult, Model model) {

        User userSource = iUserService.getCurrentUser();

        model.addAttribute("user", userSource);

        model.addAttribute("paged", iTransactionService.getCurrentUserUserTransactionPage(1,5));

        if (bindingResult.hasErrors()) {
            return "transaction";
        }

        User userDestination = iUserService.findById(transactionFormDTO.getUserDestinationId());


        if (!userSource.getConnections().contains(userDestination)) {
            bindingResult.rejectValue("userDestinationId", "userDestinationNotABuddy", "Please select a buddy !");
            return "transaction";
        }

        if (!currencyPermited.getCurrencyList().contains(transactionFormDTO.getCurrency())) {
            bindingResult.rejectValue("currency", "NotAllowedCurrency", "This currency is not permitted");
            return "transaction";
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
            return "transaction";
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
