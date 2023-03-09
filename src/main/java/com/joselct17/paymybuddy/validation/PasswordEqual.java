package com.joselct17.paymybuddy.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordEqualValidator.class)
public @interface PasswordEqual {

    String message() default "Password confirm is different from password";

    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
