package com.joselct17.paymybuddy.service.interfaces;

import org.springframework.security.core.Authentication;

public interface ISecurityService {

    boolean isAuthenticated();

   // void autoLogin(String username, String password);

    public String getCurrentUserDetailsUserName();
}
