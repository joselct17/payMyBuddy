package com.joselct17.paymybuddy.service;


import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.IUserRepository;
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
