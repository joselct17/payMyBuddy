package com.joselct17.paymybuddy.service.interfaces;

import com.joselct17.paymybuddy.model.User;

public interface IUserService {


    void create(User user);


    User findById(Integer id);


    User findByEmail(String email);


    Boolean existsByEmail(String email);
}
