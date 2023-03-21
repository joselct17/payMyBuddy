package com.joselct17.paymybuddy.service.implementation;


import com.joselct17.paymybuddy.model.Role;
import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.IRolesRepository;
import com.joselct17.paymybuddy.repository.IUserRepository;
import com.joselct17.paymybuddy.service.interfaces.ICalculationService;
import com.joselct17.paymybuddy.service.interfaces.IPagingService;
import com.joselct17.paymybuddy.service.interfaces.IUserService;
import com.joselct17.paymybuddy.utils.paging.Paged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
    private ICalculationService calculationService;


    @Override
    public Iterable<User> findAll() {
       return iUserRepository.findAll();
    }

    @Override
    public void create(User user) {
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        HashSet<Role> hashSetRoleUser = new HashSet<>();
        hashSetRoleUser.add(roleRepository.findByroleName("USER"));
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
        return null;
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

}


