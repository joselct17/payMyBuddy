package com.joselct17.paymybuddy.service.implementation;

import com.joselct17.paymybuddy.model.User;
import com.joselct17.paymybuddy.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CostumDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository iUserRepository;


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
      User user = iUserRepository.findByEmail(usernameOrEmail);

      if (user != null) {
          return new org.springframework.security.core.userdetails.User(user.getEmail()
          , user.getPassword(),
                  user.getRoles().stream()
                          .map((role) -> new SimpleGrantedAuthority(role.getRoleName()))
                                  .collect(Collectors.toList()));
      }else {
              throw new UsernameNotFoundException("Invalid email or password");
          }
      }
}

