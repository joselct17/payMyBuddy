package com.joselct17.paymybuddy.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAmountException extends Exception{


    /**
     * Error code.
     */
    private final String errorCode;

    /**
     * Message to be displayed in view.
     */
    private final String defaultMessage;
}
