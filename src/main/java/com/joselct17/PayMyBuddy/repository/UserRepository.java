package com.joselct17.PayMyBuddy.repository;

import com.joselct17.PayMyBuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

}
