package com.joselct17.paymybuddy.service;

import com.joselct17.paymybuddy.repository.IRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

    @Autowired
    IRolesRepository iRolesRepository;
}
