package com.joselct17.PayMyBuddy.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;




public class UserFormDTO {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
    @NotBlank
    private String bankAccount;

}
