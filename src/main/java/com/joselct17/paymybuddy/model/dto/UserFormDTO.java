package com.joselct17.paymybuddy.model.dto;

import com.joselct17.paymybuddy.validation.PasswordEqual;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Currency;


@PasswordEqual
@Getter
@Setter
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
    @NotBlank
    private Currency currency;
    @NotBlank
    private BigDecimal amount;

}
