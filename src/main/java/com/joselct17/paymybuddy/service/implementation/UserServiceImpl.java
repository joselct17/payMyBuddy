package com.joselct17.paymybuddy.service.implementation;


import com.joselct17.paymybuddy.model.Role;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.IRolesRepository;
import com.joselct17.paymybuddy.repository.IUserRepository;
import com.joselct17.paymybuddy.service.interfaces.*;
import com.joselct17.paymybuddy.util.TbConstants;
import com.joselct17.paymybuddy.utils.paging.Paged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    IRolesRepository roleRepository;
    @Autowired
    private IPagingService iPagingService;

    @Autowired
    private ILocalDateTimeService localDateTimeService;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private ICalculationService calculationService;

    @Autowired
    private CostumDetailsService costumDetailsService;


    @Override
    public Iterable<User> findAll() {
       return iUserRepository.findAll();
    }

    @Override
    public void create(User user) {

        Role role = roleRepository.findByroleName(TbConstants.Roles.USER);

        user.setDateTimeInscription(localDateTimeService.now());
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        List<Role> hashSetRoleUser = new ArrayList<>();
        hashSetRoleUser.add(roleRepository.findByroleName(TbConstants.Roles.USER));
        user.setRoles(hashSetRoleUser);

        iUserRepository.save(user);

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

    @Override
    public Boolean existsByEmail(String email) {
        return iUserRepository.existsByEmail(email);
    }

    @Override
    public User getCurrentUser() {
        return findByEmail(securityService.getCurrentUserDetailsUserName());
    }

    @Override
    public Paged<User> getCurrentUserConnectionPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<User> page = iUserRepository.findConnectionById(getCurrentUser().getId(),request);
        return new Paged<>(page, iPagingService.of(page.getTotalPages(), pageNumber));
    }

    @Override
    public BigDecimal sumAmountCalculate(User user, BigDecimal amount, Currency currency) {

        BigDecimal resultAmount = calculationService.sumCurrencies(user.getAmount(), user.getCurrency(), amount, currency);


        return resultAmount;

    }

    @Override
    public void update(User user) {
        iUserRepository.save(user);
    }

}


