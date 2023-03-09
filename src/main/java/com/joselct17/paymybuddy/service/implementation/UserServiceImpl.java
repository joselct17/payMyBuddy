package com.joselct17.paymybuddy.service.implementation;


import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.IUserRepository;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository iUserRepository;


    @Override
    public void create(User user) {

    }

    @Override
    public User findById(Integer id) {
        Optional<User> userOptional = iUserRepository.findById(id);
        if (userOptional.isEmpty()) {
            return null;
        }
        return userOptional.get();
    }

    @Override
    public User findByEmail(String email) {
        return iUserRepository.findByEmail(email);
    }

}
