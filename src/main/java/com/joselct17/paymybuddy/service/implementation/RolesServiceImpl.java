package com.joselct17.paymybuddy.service.implementation;

import com.joselct17.paymybuddy.repository.IRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesServiceImpl {

    @Autowired
    IRolesRepository iRolesRepository;
}
