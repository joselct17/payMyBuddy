package com.joselct17.PayMyBuddy.service;


import com.joselct17.PayMyBuddy.model.User;
import com.joselct17.PayMyBuddy.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService  {
    @Autowired
    private IUserRepository iUserRepository;

    public Iterable<User> getUsers() {
        return iUserRepository.findAll();
    }

}
