package com.joselct17.paymybuddy.controller;


import com.joselct17.paymybuddy.config.CurrencyPermited;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.model.dto.UserFormDTO;
import com.joselct17.paymybuddy.service.interfaces.ISecurityService;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    @Autowired
    private IUserService iUserService;

    @Autowired
    private ISecurityService iSecurityService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CurrencyPermited currencyPermited;


    @GetMapping("/registration")
    public String registration(Model model) {
        logger.info("GET: /registration");
        model.addAttribute("userForm", new UserFormDTO());

        return "register";
    }


    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userForm") UserFormDTO userFormDTO, BindingResult bindingResult, Model model) {
        logger.info("POST: /registration");
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if ( iUserService.existsByEmail(userFormDTO.getEmail()) ) {
            bindingResult.rejectValue("email", "", "This email already exists");
            return "register";
        }

        User user = convertToEntity(userFormDTO);
        iUserService.create(user);


        return "redirect:/";
    }



    /**
     * This method converts a DTO object to an Entity
     *
     * @param userFormDTO
     * @return Entity version of the DTO
     *
     * @see <a href="https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application"> Entity/DTO conversion
     */
    private User convertToEntity(UserFormDTO userFormDTO) {
        User user = modelMapper.map(userFormDTO, User.class);

        return user;
    }

}
