package com.joselct17.paymybuddy.controller;


import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.model.dto.UserFormDTO;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    @Autowired
    private IUserService iUserService;


    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserFormDTO());

        return "registration";
    }


    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userForm") UserFormDTO userFormDTO, BindingResult bindingResult, Model model) {

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