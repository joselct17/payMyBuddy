package com.joselct17.paymybuddy.service.implementation;

import com.joselct17.paymybuddy.service.interfaces.ISecurityService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements ISecurityService {
    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void autoLogin(String username, String password) {
    }
}
