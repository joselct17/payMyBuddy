package com.joselct17.PayMyBuddy.repository;

import com.joselct17.PayMyBuddy.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolesRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(String roleName);
}
