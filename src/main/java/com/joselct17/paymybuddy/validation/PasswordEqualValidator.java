package com.joselct17.paymybuddy.validation;

import com.joselct17.paymybuddy.model.dto.UserFormDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordEqualValidator implements ConstraintValidator<PasswordEqual, UserFormDTO> {

        @Override
        public boolean isValid(UserFormDTO userFormDTO, ConstraintValidatorContext context) {

            if ( userFormDTO.getPassword() == null || userFormDTO.getConfirmPassword() == null ) {
                return false;
            }
            return userFormDTO.getPassword().equals(userFormDTO.getConfirmPassword());
        }
}
