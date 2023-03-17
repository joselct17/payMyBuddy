package com.joselct17.paymybuddy.service.interfaces;

public interface ISecurityService {

    boolean isAuthenticated();

    void autoLogin(String username, String password);


    public String getCurrentUserDetailsUserName();
}
