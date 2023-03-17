package com.joselct17.paymybuddy.service.interfaces;

import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.utils.paging.Paged;

public interface IUserService {


    void create(User user);


    User findById(Integer id);


    User findByEmail(String email);


    Boolean existsByEmail(String email);

    User getCurrentUser();

    Paged<User> getCurrentUserConnectionPage(int pageNumber, int size);
}
