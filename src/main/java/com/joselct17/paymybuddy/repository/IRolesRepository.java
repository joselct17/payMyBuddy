package com.joselct17.paymybuddy.repository;

import com.joselct17.paymybuddy.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolesRepository extends CrudRepository<Role, Integer> {

    Role findByroleName(String roleName);
}
