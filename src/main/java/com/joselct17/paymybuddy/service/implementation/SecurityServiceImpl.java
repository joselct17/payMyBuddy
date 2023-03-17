package com.joselct17.paymybuddy.service.implementation;


import com.joselct17.paymybuddy.service.interfaces.ISecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    @Override
    public String getCurrentUserDetailsUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof User) {
                return ((User) principal).getUsername();
            }
        }
        return null;

    }
}
