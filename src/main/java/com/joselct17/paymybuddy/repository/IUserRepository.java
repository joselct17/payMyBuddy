package com.joselct17.paymybuddy.repository;

import com.joselct17.paymybuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {

    public User findByEmail(String email);

    public Boolean existsByEmail(String email);
}
