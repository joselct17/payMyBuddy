package com.joselct17.PayMyBuddy.service;

import com.joselct17.PayMyBuddy.model.User;
import com.joselct17.PayMyBuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getUser() {
        return userRepository.findAll();
    }
}
